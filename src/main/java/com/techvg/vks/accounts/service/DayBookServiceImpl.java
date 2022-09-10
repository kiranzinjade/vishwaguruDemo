package com.techvg.vks.accounts.service;

import com.techvg.vks.accounts.domain.DayBook;
import com.techvg.vks.accounts.domain.VoucherTrans;
import com.techvg.vks.accounts.mapper.DayBookMapper;
import com.techvg.vks.accounts.model.DayBookDto;
import com.techvg.vks.accounts.repository.DayBookRepository;
import com.techvg.vks.accounts.repository.VoucherTransRepository;
import com.techvg.vks.config.AccountConstants;
import com.techvg.vks.deposit.service.DepositLedgerService;
import com.techvg.vks.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DayBookServiceImpl implements DayBookService {

    private final DayBookMapper dayBookMapper;
    private final DayBookRepository dayBookRepository;
    private final VoucherTransRepository voucherTransRepository;

    private final DepositLedgerService depositLedgerService;

    @Override
    public List<DayBookDto> listDayBookEntries(Date transDate) {
        return dayBookMapper.toDtoList(dayBookRepository.findAllByTransDate(transDate));
    }

    @Override
    public DayBookDto getDayBookEntryById(Long dayBookId) {

        DayBook dayBook = dayBookRepository.findById(dayBookId).orElseThrow(
                () -> new NotFoundException("No Day Book entry found for Id : " +dayBookId));
        return dayBookMapper.toDto(dayBook);
    }

    @Override
    public DayBookDto deleteDayBookEntryById(Long dayBookId) {

        DayBook dayBook = dayBookRepository.findById(dayBookId).orElseThrow(
                () -> new NotFoundException("No Day Book found for Id : " +dayBookId));
        if (dayBook != null) {
            dayBook.setIsDeleted(true);
            dayBookRepository.save(dayBook);
        }
        return dayBookMapper.toDto(dayBook);
    }

    @Transactional
    public boolean addDayBookEntries(Date transDate){
        double balance = 0.0;
        DayBook dayBook2 = dayBookRepository.findByLastRecord(transDate);
        if(dayBook2 !=null)
            balance = dayBook2.getBalance();

        DayBook dayBook1 = dayBookRepository.findByLastRecordOfTheDay(transDate);
        if(dayBook1 !=null)
            balance = dayBook1.getBalance();

        List<VoucherTrans> voucherTransList = voucherTransRepository.getVoucherTransByTransDate(transDate);
        for (VoucherTrans trans:voucherTransList) {
            //if (!trans.getLedgerAccounts().getAccHeadCode().equalsIgnoreCase(AccountConstants.CASH_ON_HAND)) {
                DayBook dayBook = new DayBook();
                dayBook.setMode(trans.getMode());
                dayBook.setTransType(trans.getTransType());
                dayBook.setTransDate(trans.getTransDate());
                dayBook.setParticulars(trans.ledgerAccounts.getAccHeadCode());
                dayBook.setAccHeadCode(trans.ledgerAccounts.getAccHeadCode());
                dayBook.setCreditTotalAmt(0.0);
                dayBook.setDebitTotalAmt(0.0);

                dayBook = addDayBookEntry(dayBook, trans);
                trans.setDayBookCreated(true);
                if (dayBook.getTransType().equalsIgnoreCase(AccountConstants.DEBIT)) {
                    balance = balance + trans.getDebitAmt();
                   // balance = updateBalance(balance, trans.getDebitAmt(), trans.getLedgerAccounts().getAccountType().getName(), dayBook.getTransType());

                    dayBook.setBalance(balance);
                } else if (dayBook.getTransType().equalsIgnoreCase(AccountConstants.CREDIT)) {
                    balance = balance - trans.getCreditAmt();
                   // balance = updateBalance(balance, trans.getCreditAmt(), trans.getLedgerAccounts().getAccountType().getName(), dayBook.getTransType());
                    dayBook.setBalance(balance);
                }
                dayBookRepository.saveAndFlush(dayBook);
                voucherTransRepository.save(trans);

           // }
        }
        updateDayBookWithLedgers(transDate);
        return true;
    }

    @Override
    public DayBook addDayBook(DayBook dayBook) {
        return dayBookRepository.save(dayBook);
    }

    @Override
    public Optional<DayBook> findDayBook(DayBook dayBook) {
        return dayBookRepository.findByParticularsAndTransDateAndTransTypeOrderByIdAsc(
                dayBook.getParticulars(), dayBook.getTransDate(), dayBook.getTransType() );
    }

    private boolean updateDayBookWithLedgers(Date transDate){

        //Update from Deposits(Saving/Fixed/RD) Ledger
        depositLedgerService.populateDayBook(transDate);

        return true;
    }

    private DayBook  addDayBookEntry(DayBook dayBook, VoucherTrans trans){
        Optional<DayBook> dayBookOptional = dayBookRepository.findByParticularsAndTransDateAndTransTypeOrderByIdAsc(
                dayBook.getParticulars(), dayBook.getTransDate(), dayBook.getTransType() );

        if(dayBook.getTransType().equalsIgnoreCase(AccountConstants.DEBIT))
            dayBook = addDebitEntry(dayBookOptional, dayBook, trans);
        else if(dayBook.getTransType().equalsIgnoreCase(AccountConstants.CREDIT))
            dayBook = addCreditEntry(dayBookOptional, dayBook, trans);

        return dayBook;
    }

    private DayBook addDebitEntry(Optional<DayBook> dayBookOptional, DayBook dayBook, VoucherTrans trans) {
        Integer voucherCount=0;
        double debitTotalAmt=0.0;
        double debitCashAmt=0.0;
        double debitTransferAmt=0.0;
        double debitBalance = 0.0;

        if(dayBookOptional.isPresent()){
            dayBook = dayBookOptional.get();
            voucherCount = dayBook.getVoucherCount();
            debitTotalAmt = dayBook.getDebitTotalAmt();
            debitCashAmt = dayBook.getDebitCashAmt()==null?0.0:dayBook.getDebitCashAmt();
            debitTransferAmt = dayBook.getDebitTransferAmt()==null?0.0:dayBook.getDebitTransferAmt();
            debitBalance = dayBook.getDebitBalance();
        }

        dayBook.setVoucherCount(voucherCount + 1);
        dayBook.setDebitTotalAmt( debitTotalAmt + trans.getDebitAmt());
        dayBook.setDebitBalance(debitBalance + trans.getDebitAmt());

        if(trans.getMode().equalsIgnoreCase(AccountConstants.CASH)){
            dayBook.setDebitCashAmt(debitCashAmt + trans.getDebitAmt());
            dayBook.setDebitTransferAmt(debitTransferAmt);
        }
        else{
            dayBook.setDebitTransferAmt(debitTransferAmt + trans.getDebitAmt());
            dayBook.setDebitCashAmt(debitCashAmt);
        }

        return dayBook;
    }

    private DayBook addCreditEntry(Optional<DayBook> dayBookOptional, DayBook dayBook, VoucherTrans trans) {

        Integer voucherCount=0;
        double creditTotalAmt=0.0;
        double creditCashAmt=0.0;
        double creditTransferAmt=0.0;
        double creditBalance = 0.0;

        if(dayBookOptional.isPresent()){
            dayBook = dayBookOptional.get();
            voucherCount = dayBook.getVoucherCount();
            creditTotalAmt = dayBook.getCreditTotalAmt();
            creditCashAmt = dayBook.getCreditCashAmt()==null?0.0:dayBook.getCreditCashAmt();
            creditTransferAmt = dayBook.getCreditTransferAmt()==null?0.0:dayBook.getCreditTransferAmt();
            creditBalance = dayBook.getCreditBalance();
        }

        dayBook.setVoucherCount(voucherCount + 1);
        dayBook.setCreditTotalAmt( creditTotalAmt + trans.getCreditAmt());
        dayBook.setCreditBalance(creditBalance + trans.getCreditAmt());

        if(trans.getMode().equalsIgnoreCase(AccountConstants.CASH)){
            dayBook.setCreditCashAmt(creditCashAmt + trans.getCreditAmt());
            dayBook.setCreditTransferAmt(creditTransferAmt);
        }
        else{
            dayBook.setCreditTransferAmt(creditTransferAmt + trans.getCreditAmt());
            dayBook.setCreditCashAmt(creditCashAmt);
        }

        return dayBook;
    }

    public double updateBalance(double balance, double amt, String accountType, String transType){
        switch (accountType) {
            case AccountConstants.ASSET:
            case AccountConstants.EXPENSE:
                balance = increaseDebit(transType,amt,balance);
                break;
            case AccountConstants.LIABILITY:
            case AccountConstants.INCOME:
            case AccountConstants.CAPITAL:
                balance = decreaseDebit(transType,amt,balance);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + accountType);
        }
        return balance;
    }

    private double increaseDebit(String type, double amt, double bal){
        if (type.equalsIgnoreCase(AccountConstants.DEBIT)) {
            bal = bal + amt;
        } else if (type.equalsIgnoreCase(AccountConstants.CREDIT)) {
            bal = bal - amt;
        }
        return bal;
    }

    private double decreaseDebit(String type, double amt, double bal){
        if (type.equalsIgnoreCase(AccountConstants.DEBIT)) {
            bal = bal - amt;
        } else if (type.equalsIgnoreCase(AccountConstants.CREDIT)) {
            bal = bal + amt;
        }
        return bal;
    }
}

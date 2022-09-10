package com.techvg.vks.accounts.service;

import com.techvg.vks.accounts.domain.DayBook;
import com.techvg.vks.accounts.domain.GeneralLedger;
import com.techvg.vks.accounts.domain.LedgerAccounts;
import com.techvg.vks.accounts.domain.VoucherTrans;
import com.techvg.vks.accounts.mapper.DayBookMapper;
import com.techvg.vks.accounts.mapper.GeneralLedgerMapper;
import com.techvg.vks.accounts.mapper.VoucherTransMapper;
import com.techvg.vks.accounts.model.GeneralLedgerDto;
import com.techvg.vks.accounts.model.GeneralLedgerPageList;
import com.techvg.vks.accounts.repository.DayBookRepository;
import com.techvg.vks.accounts.repository.GeneralLedgerRepository;
import com.techvg.vks.accounts.repository.LedgerAccountsRepository;
import com.techvg.vks.accounts.repository.VoucherTransRepository;
import com.techvg.vks.config.AccountConstants;
import com.techvg.vks.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GeneralLedgerServiceImpl implements GeneralLedgerService {
    private final GeneralLedgerRepository generalLedgerRepository;
    private final GeneralLedgerMapper generalLedgerMapper;
    private final VoucherTransRepository voucherTransRepository;
    private final VoucherTransMapper voucherTransMapper;

    private final LedgerAccountsRepository ledgerAccountsRepository;
    private final DayBookService dayBookService;
    private final DayBookMapper dayBookMapper;
    private final DayBookRepository dayBookRepository;

    @Override
    @Transactional
    public boolean postGeneralLedger(Date transDate) {
        boolean flag = false;
        List<DayBook> dayBookList = dayBookRepository.findAllByTransDateAndGL(transDate);
        for (DayBook dayBook:dayBookList) {
            GeneralLedger generalLedger = dayBookMapper.toGeneralLedger(dayBook);
            LedgerAccounts ledgerAccounts = ledgerAccountsRepository.findByAccHeadCode(dayBook.getAccHeadCode());
            generalLedger.setLedgerAccounts(ledgerAccounts);
            generalLedger.setBalance(updateBalance(generalLedger, ledgerAccounts));
            generalLedgerRepository.saveAndFlush(generalLedger);

            dayBook.setGLCreated(true);
            dayBookRepository.save(dayBook);

            flag =updateParentLedger(generalLedger.getLedgerAccounts(), generalLedger);
        }
        return flag;
    }

    private double updateBalance(GeneralLedger generalLedger, LedgerAccounts ledgerAccounts){
        double balance =  0.0;
        String accType = ledgerAccounts.getAccountType().getName();
        if(generalLedger.getTransType().equalsIgnoreCase(AccountConstants.DEBIT)){
            balance = dayBookService.updateBalance(ledgerAccounts.getAccBalance(),
                    generalLedger.getDebitAmt(),accType, AccountConstants.DEBIT );
        }else if (generalLedger.getTransType().equalsIgnoreCase(AccountConstants.CREDIT)){
            balance = dayBookService.updateBalance(ledgerAccounts.getAccBalance(),
                    generalLedger.getCreditAmt(),accType, AccountConstants.CREDIT );
        }
        return balance;
    }

    @Override
    @Transactional
    public boolean postGeneralLedgerTrans(Date transDate) {
        boolean flag = false;
        List<VoucherTrans> voucherTransList = voucherTransRepository.getVoucherTransByTransDate(transDate);
        for (VoucherTrans trans:voucherTransList) {
            GeneralLedger generalLedger = voucherTransMapper.voucherTransToGeneralLedger(trans);

            generalLedgerRepository.saveAndFlush(generalLedger);
            flag =updateParentLedger(generalLedger.getLedgerAccounts(), generalLedger);
        }
        return flag;
    }

    private boolean updateParentLedger(LedgerAccounts ledgerAccount, GeneralLedger generalLedger){
        String accType = ledgerAccount.getAccountType().getName();
        if(ledgerAccount !=null){
            if(generalLedger.getTransType().equalsIgnoreCase(AccountConstants.DEBIT)){
                ledgerAccount.setAccBalance(dayBookService.updateBalance(ledgerAccount.getAccBalance(),
                        generalLedger.getDebitAmt(),accType, AccountConstants.DEBIT ));

            }else if(generalLedger.getTransType().equalsIgnoreCase(AccountConstants.CREDIT)){
                ledgerAccount.setAccBalance(dayBookService.updateBalance(ledgerAccount.getAccBalance(),
                        generalLedger.getCreditAmt(),accType, AccountConstants.CREDIT ));
            }
            ledgerAccountsRepository.saveAndFlush(ledgerAccount);
                LedgerAccounts parentAccount = ledgerAccount.getParentLedger();
                if (parentAccount != null)
                    updateParentLedger(parentAccount, generalLedger);
        }
        return true;
    }



    @Override
    public GeneralLedgerDto updateGeneralLedgerTrans(Long generalLedgerTransId, GeneralLedgerDto generalLedgerDto, Authentication authentication) {
        generalLedgerRepository.findById(generalLedgerTransId).orElseThrow(
                () -> new NotFoundException("No GeneralLedger found for Id : " +generalLedgerTransId));
        GeneralLedger generalLedger = generalLedgerMapper.toDomain(generalLedgerDto);
        generalLedger.setId(generalLedgerTransId);
        return generalLedgerMapper.toDto(generalLedgerRepository.save(generalLedger));
    }

    @Override
    public GeneralLedgerDto getGeneralLedgerTransById(Long generalLedgerTransId) {
        GeneralLedger generalLedger = generalLedgerRepository.findById(generalLedgerTransId).orElseThrow(
                () -> new NotFoundException("No GeneralLedger found for Id : " +generalLedgerTransId));
        return generalLedgerMapper.toDto(generalLedger);
    }

    @Override
    public GeneralLedgerDto deleteGeneralLedgerTransById(Long generalLedgerTransId) {
        GeneralLedger generalLedger = generalLedgerRepository.findById(generalLedgerTransId).orElseThrow(
                () -> new NotFoundException("No GeneralLedger found for Id : " +generalLedgerTransId));
        if (generalLedger != null) {
            generalLedger.setIsDeleted(true);
            generalLedgerRepository.save(generalLedger);
        }
        return generalLedgerMapper.toDto(generalLedger);
    }

    @Override
    public GeneralLedgerPageList listGeneralLedgerTrans(Pageable pageable) {
        Page<GeneralLedger> ledgerAccTransPage;
        ledgerAccTransPage = generalLedgerRepository.findAll(pageable);

        return new GeneralLedgerPageList(ledgerAccTransPage
                .getContent()
                .stream()
                .map(generalLedgerMapper::toDto)
                .collect(Collectors.toList()),
                PageRequest
                        .of(ledgerAccTransPage.getPageable().getPageNumber(),
                                ledgerAccTransPage.getPageable().getPageSize()),
                ledgerAccTransPage.getTotalElements());
    }
}

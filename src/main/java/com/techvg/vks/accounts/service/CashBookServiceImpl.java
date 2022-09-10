package com.techvg.vks.accounts.service;

import com.techvg.vks.accounts.domain.CashBook;
import com.techvg.vks.accounts.domain.LedgerAccounts;
import com.techvg.vks.accounts.domain.VoucherTrans;
import com.techvg.vks.accounts.mapper.CashBookMapper;
import com.techvg.vks.accounts.mapper.VoucherTransMapper;
import com.techvg.vks.accounts.model.CashBookDto;
import com.techvg.vks.accounts.model.CashBookPageList;
import com.techvg.vks.accounts.repository.CashBookRepository;
import com.techvg.vks.common.LoginUser;
import com.techvg.vks.config.AccountConstants;
import com.techvg.vks.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CashBookServiceImpl implements CashBookService {
    private final CashBookRepository cashBookRepository;
    private final CashBookMapper cashBookMapper;

    private final VoucherTransMapper voucherTransMapper;

	@Override
	public boolean addCashbookEntry(List<VoucherTrans> voucherTransList) {
		boolean flag=false;
		double balance=0.0;
		LedgerAccounts cashLedger = null;

		if(voucherTransList!=null){
			for (VoucherTrans voucherTrans:voucherTransList) {
				if (voucherTrans.getLedgerAccounts().getAccHeadCode().equalsIgnoreCase(AccountConstants.CASH_ON_HAND)) {
					cashLedger = voucherTrans.getLedgerAccounts();
				}
			}
			for (VoucherTrans voucherTrans:voucherTransList) {

				if(voucherTrans.getMode().equalsIgnoreCase(AccountConstants.CASH)) {
					if (!voucherTrans.getLedgerAccounts().getAccHeadCode().equalsIgnoreCase(AccountConstants.CASH_ON_HAND)) {
						CashBook cashBook = voucherTransMapper.voucherTransToCashbook(voucherTrans);
						cashBook.setAuthorisedBy(LoginUser.getUser());

						CashBook lastCashBookEntry = cashBookRepository.findTopByOrderByIdDesc();
						if (lastCashBookEntry != null) {
							balance = lastCashBookEntry.getBalance();
						}else{
							if(cashLedger !=null){
								balance = cashLedger.getAccBalance();
							}
						}

						if (cashBook.getTransType().equalsIgnoreCase(AccountConstants.CREDIT)) {
							balance = balance - cashBook.getCreditAmt();
							cashBook.setParticulars("By " + voucherTrans.getLedgerAccounts().getAccHeadCode());
							cashBook.setCreditAmt(cashBook.getCreditAmt());
							cashBook.setDebitAmt(0.0);
						} else if (cashBook.getTransType().equalsIgnoreCase(AccountConstants.DEBIT)) {
							balance = balance + cashBook.getDebitAmt();
							cashBook.setParticulars("To " + voucherTrans.getLedgerAccounts().getAccHeadCode());
							cashBook.setDebitAmt(cashBook.getDebitAmt());
							cashBook.setCreditAmt(0.0);
						}
						cashBook.setBalance(balance);
						cashBookRepository.save(cashBook);
						flag = true;
					}
				}
			}
		}
		return flag;
	}

	@Override
	public boolean addCashbookEntry(CashBookDto cashBookDto) {
		CashBook cash = cashBookRepository.findTopByOrderByIdDesc();
		double balanceAmt= 0.0;
		if(cash !=null)
			balanceAmt = cash.getBalance();
		if (cashBookDto.getTransType().equalsIgnoreCase(AccountConstants.CREDIT)) {
			cashBookDto.setBalance(balanceAmt - cashBookDto.getCreditAmt());
			cashBookDto.setDebitAmt(0.0);
		} else
		if (cashBookDto.getTransType().equalsIgnoreCase(AccountConstants.DEBIT)) {
			cashBookDto.setBalance(balanceAmt + cashBookDto.getDebitAmt());
			cashBookDto.setCreditAmt(0.0);
		}

		 cashBookMapper.toDto(cashBookRepository.save(cashBookMapper.toDomain(cashBookDto)));
		 return true;
	}

	@Override
    public CashBookDto updateCashbookEntry(Long cashbookId, CashBookDto cashBookDto, Authentication authentication) {
        cashBookRepository.findById(cashbookId).orElseThrow(
                () -> new NotFoundException("No CashBook entry found for Id : " +cashbookId));
        CashBook cashBook = cashBookMapper.toDomain(cashBookDto);
        cashBook.setId(cashbookId);
        return cashBookMapper.toDto(cashBookRepository.save(cashBook));
    }

    @Override
    public CashBookDto getCashbookEntryById(Long cashbookId) {

        CashBook cashBook = cashBookRepository.findById(cashbookId).orElseThrow(
                () -> new NotFoundException("No CashBook entry found for Id : " +cashbookId));
        return cashBookMapper.toDto(cashBook);
    }

    @Override
    public CashBookDto deleteCashbookEntryById(Long cashbookId) {

        CashBook cashBook = cashBookRepository.findById(cashbookId).orElseThrow(
                () -> new NotFoundException("No CashBook found for Id : " +cashbookId));
        if (cashBook != null) {
            cashBook.setIsDeleted(true);
            cashBookRepository.save(cashBook);
        }
        return cashBookMapper.toDto(cashBook);
    }

    @Override
    public CashBookPageList listCashbookEntries(Pageable pageable) {

        Page<CashBook> cashBookPage;
        cashBookPage = cashBookRepository.findAll(pageable);

        return new CashBookPageList(cashBookPage
                .getContent()
                .stream()
                .map(cashBookMapper::toDto)
                .collect(Collectors.toList()),
                PageRequest
                        .of(cashBookPage.getPageable().getPageNumber(),
                                cashBookPage.getPageable().getPageSize()),
                cashBookPage.getTotalElements());
    }

	@Override
	public CashBookDto creditAmount(CashBookDto cashBookDto, Authentication authentication) {
		CashBook cashBook1 = cashBookRepository.findTopByOrderByIdDesc();

		CashBook cashBook = cashBookMapper.toDomain(cashBookDto);
		if (cashBook1 == null) {

			throw new NotFoundException(" No Cash Available: " + cashBook.getBalance());
		} else

		{
			if(cashBook1.getBalance().compareTo(cashBookDto.getCreditAmt()) < 0) {
				throw new NotFoundException(" No Cash Available: " + cashBook.getBalance());
			}else {
			cashBook.setBalance((cashBook1.getBalance()) - (cashBookDto.getCreditAmt()));
			}
		}
		cashBook.setTransDate(new Date());
		return cashBookMapper.toDto(cashBookRepository.save(cashBook));
	
	}

	@Override
	public CashBookDto debitAmount(CashBookDto cashBookDto, Authentication authentication) {
		CashBook cashBook1 = cashBookRepository.findTopByOrderByIdDesc();

		CashBook cashBook = cashBookMapper.toDomain(cashBookDto);
		if (cashBook1 == null) {
			cashBook.setBalance(cashBookDto.getDebitAmt());
			
		} else

		{
				cashBook.setBalance((cashBook1.getBalance()) + (cashBookDto.getDebitAmt()));
		}
		cashBook.setTransDate(new Date());
		return cashBookMapper.toDto(cashBookRepository.save(cashBook));
	}

}

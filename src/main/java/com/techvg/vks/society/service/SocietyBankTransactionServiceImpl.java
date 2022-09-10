package com.techvg.vks.society.service;

import com.techvg.vks.accounts.domain.LedgerAccounts;
import com.techvg.vks.accounts.domain.VoucherTrans;
import com.techvg.vks.config.AccountConstants;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.society.domain.SocietyBank;
import com.techvg.vks.society.domain.SocietyBankTransaction;
import com.techvg.vks.society.mapper.SocietyBankTransactionMapper;
import com.techvg.vks.society.model.SocietyBankTransactionDto;
import com.techvg.vks.society.model.SocietyBankTransactionPageList;
import com.techvg.vks.society.repository.SocietyBankRepository;
import com.techvg.vks.society.repository.SocietyBankTransactionRepository;
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
public class SocietyBankTransactionServiceImpl implements SocietyBankTransactionService{

	private final SocietyBankTransactionMapper mapper;
	private final SocietyBankRepository societyBankRepository;
	private final SocietyBankTransactionRepository repo;

	@Override
	public SocietyBankTransactionDto addSocietyBankTransaction(SocietyBankTransactionDto societyBankTransactionDto, Authentication authentication) {
		return addSocietyBankTransaction(societyBankTransactionDto);
	}

	@Override
	public SocietyBankTransactionDto addSocietyBankTransaction(SocietyBankTransactionDto societyBankTransactionDto) {

		SocietyBankTransaction societyBankTransaction = mapper
				.toDomain(societyBankTransactionDto);

		SocietyBank bank=societyBankRepository.findById(societyBankTransactionDto.getBankId()).orElseThrow
				(() -> new NotFoundException("No transaction found for id : " +societyBankTransactionDto.getBankId()));
		
		societyBankTransaction.setSocietyBank(bank);
		societyBankTransaction.setDate(new Date());
		societyBankTransaction.setParticulars(societyBankTransactionDto.getParticulars());
		societyBankTransaction.setInitial(societyBankTransactionDto.getInitial());
		
		log.debug("REST request to save Bank Transaction: {}", societyBankTransactionDto);
		
		SocietyBankTransaction transaction=repo.findLastRecordById(societyBankTransactionDto.getBankId());
		double balance = 0.0;
		
		if(transaction==null) {
			societyBankTransaction.setBalance(societyBankTransactionDto.getCredit());
          
			
		}else {
			
			if(societyBankTransactionDto.getDebit()!=null) {
				balance = transaction.getBalance() - societyBankTransactionDto.getDebit();
				societyBankTransaction.setDebit(societyBankTransactionDto.getDebit());
			}
			else 
				if(societyBankTransactionDto.getCredit()!=null) {
					balance = transaction.getBalance() + societyBankTransactionDto.getCredit();
					societyBankTransaction.setCredit(societyBankTransactionDto.getCredit());	
			}
			societyBankTransaction.setSocietyBank(bank);
			societyBankTransaction.setBalance(balance);
		
		}

		return mapper
				.toDto(repo.save(societyBankTransaction));
	}


	@Override
	public SocietyBankTransactionPageList listsocietyBankTransactionService(Pageable pageable) {
		log.debug("Request to get Bank Transaction : {}");

		Page<SocietyBankTransaction> societyBankTransactionPage;
		societyBankTransactionPage = repo.findByisDeleted(pageable,false);

		return new SocietyBankTransactionPageList(societyBankTransactionPage
										.getContent()
										.stream()
										.map(mapper::toDto)
										.collect(Collectors.toList()),
										PageRequest
											.of(societyBankTransactionPage.getPageable().getPageNumber(),
													societyBankTransactionPage.getPageable().getPageSize()),
											societyBankTransactionPage.getTotalElements());

	}
	@Override
	public SocietyBankTransactionDto deleteTransactionById(Long id) {
		SocietyBankTransaction societyBankTransaction = repo.findById(id).orElseThrow(
					() -> new NotFoundException("No Transaction found for Id " + id));
			if (societyBankTransaction != null) {
				societyBankTransaction.setIsDeleted(true);
				repo.save(societyBankTransaction);
			}
		return mapper.toDto(societyBankTransaction);
	}

	@Override
	public SocietyBankTransactionDto updateTransaction(Long id, SocietyBankTransactionDto societyBankTransactionDto) {
		repo.findById(id).orElseThrow(
				() -> new NotFoundException("No transaction found for Id : " +id));
		societyBankTransactionDto.setId(id);	
				
		SocietyBankTransaction societyBankTransaction1 = mapper.toDomain(societyBankTransactionDto);
				SocietyBank bank=societyBankRepository.findById(societyBankTransactionDto.getBankId()).orElseThrow(
						() -> new NotFoundException("No transaction found for Id : " +id));
				societyBankTransaction1.setSocietyBank(bank);
				return mapper.toDto(repo.save(societyBankTransaction1));
	}

	@Override
	public SocietyBankTransactionDto getTransactionById(Long id) {
		log.debug("REST request to get Transaction : {}", id);
		SocietyBankTransaction societyBankTransaction = repo.findById(id).orElseThrow(
				() -> new NotFoundException("No transaction found for Id : " +id));

		return mapper.toDto(societyBankTransaction);
	}

	@Override
	public boolean addBankBookEntry(List<VoucherTrans> voucherTransList) {
		boolean flag=false;
		double balance=0.0;
		if(voucherTransList!=null){
			SocietyBank bank=null;
			LedgerAccounts bankLedger = null;
			for (VoucherTrans voucherTrans:voucherTransList) {
				if ((voucherTrans.getLedgerAccounts().getParentLedger().getAccHeadCode().equalsIgnoreCase(AccountConstants.SOCIETY_CURRENT_ACC_DCCB))) {
					bank = societyBankRepository.findByAccountNumberAndIsDeleted(voucherTrans.getLedgerAccounts().getAccountNo(), false);
					bankLedger = voucherTrans.getLedgerAccounts();
				}
			}
			if(bank == null)
				throw new NotFoundException("Select society current or savings account " );

			for (VoucherTrans voucherTrans:voucherTransList) {
				String aacHead = voucherTrans.getLedgerAccounts().getParentLedger().getAccHeadCode();
					if (!(aacHead.equalsIgnoreCase(AccountConstants.SOCIETY_CURRENT_ACC_DCCB) )) {
						SocietyBankTransaction bankBook = mapper.voucherTransToBankbook(voucherTrans);

						SocietyBankTransaction lastBankBookEntry = repo.findTopByOrderByIdDesc();
						if (lastBankBookEntry != null) {
							balance = lastBankBookEntry.getBalance();
						}else{
							if(bankLedger !=null){
								balance = bankLedger.getAccBalance();
							}
						}

						if (bankBook.getTransType().equalsIgnoreCase(AccountConstants.CREDIT)) {
							balance = balance - bankBook.getCredit();
							bankBook.setParticulars("By " + voucherTrans.getLedgerAccounts().getAccHeadCode());
							bankBook.setCredit(bankBook.getCredit());
							bankBook.setDebit(0.0);
						} else if (bankBook.getTransType().equalsIgnoreCase(AccountConstants.DEBIT)) {
							balance = balance + bankBook.getDebit();
							bankBook.setParticulars("To " + voucherTrans.getLedgerAccounts().getAccHeadCode());
							bankBook.setDebit(bankBook.getDebit());
							bankBook.setCredit(0.0);
						}
						bankBook.setBalance(balance);
						bankBook.setSocietyBank(bank);
						repo.save(bankBook);
						flag = true;
					}
			}
		}
		return flag;
	}
}

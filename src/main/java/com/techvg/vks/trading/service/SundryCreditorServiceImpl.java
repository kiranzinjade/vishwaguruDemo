package com.techvg.vks.trading.service;

import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.techvg.vks.exceptions.AlreadyExitsException;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.society.domain.SocietyBank;
import com.techvg.vks.trading.domain.SundryCreditor;
import com.techvg.vks.trading.domain.SundryCreditorTransaction;
import com.techvg.vks.trading.domain.VendorRegister;
import com.techvg.vks.trading.mapper.SundryCreditorMapper;
import com.techvg.vks.trading.model.SundryCreditorDto;
import com.techvg.vks.trading.model.SundryCreditorPageList;
import com.techvg.vks.trading.repository.SundryCreditorRepository;
import com.techvg.vks.trading.repository.SundryCreditorTransactionRepository;
import com.techvg.vks.trading.repository.VendorRegisterRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SundryCreditorServiceImpl implements SundryCreditorService {
	
	private final SundryCreditorMapper sundryCreditorMapper;
	private final SundryCreditorRepository sundryCreditorRepository;
	private final VendorRegisterRepository vendorRegisterRepository;
	private final SundryCreditorTransactionRepository sundryCreditorTransactionRepository;
	

	@Override
	public SundryCreditorDto addTransactions(SundryCreditorDto sundryCreditorDto) {


		SundryCreditor sundryCreditor = sundryCreditorMapper.sundryCreditorDtoToSundryCreditor(sundryCreditorDto);
		System.out.println("Sundry Creditor Dto-----------------"+sundryCreditorDto);
		 VendorRegister vendorRegister = vendorRegisterRepository.findById(sundryCreditorDto.getVendorId()).orElseThrow(
					() -> new NotFoundException("No Vendor found for Id : " +sundryCreditorDto.getVendorId()));

		sundryCreditor.setVendor(vendorRegister);
		sundryCreditor.setDate(new Date());

		log.debug("REST request to save Sundry Creditor: {}", sundryCreditorDto);
		SundryCreditor creditor = sundryCreditorRepository.findByLastRecord(sundryCreditorDto.getVendorId());
		SundryCreditor newSundryCreditor = null;
		SundryCreditorTransaction sundryCreditorTransaction = new SundryCreditorTransaction();

		double balance = 0.0;
		if (creditor == null && sundryCreditorDto.getCredit()!=null) {
			throw new AlreadyExitsException("1st entry should be debit");

		}
		if (creditor == null) {
			sundryCreditor.setBalance(sundryCreditorDto.getDebit());
			newSundryCreditor = sundryCreditorRepository.save(sundryCreditor);
			sundryCreditorTransaction.setSundryCreditor(newSundryCreditor);
			sundryCreditorTransaction.setCredit(sundryCreditorDto.getCredit());
			sundryCreditorTransaction.setBalance(sundryCreditorDto.getDebit());
			sundryCreditorTransaction.setParticulars(sundryCreditorDto.getParticulars());
			sundryCreditorTransaction.setTransactionDate(sundryCreditorDto.getTransDate());
			sundryCreditorTransactionRepository.save(sundryCreditorTransaction);
		}

		else if (creditor != null) {

			sundryCreditor = sundryCreditorRepository.findById(creditor.getId())
					.orElseThrow(() -> new NotFoundException("No Record found for Id : " + creditor.getId()));

			double debit = 0.0;
			double credit = 0.0;

			if (sundryCreditorDto.getDebit() != null) {
				balance = creditor.getBalance() + sundryCreditorDto.getDebit();
				if (creditor.getDebit() != null) {
					debit = creditor.getDebit() + sundryCreditorDto.getDebit();
				} else {
					debit = 0.0 + sundryCreditorDto.getDebit();
				}
				sundryCreditor.setDebit(debit);
			} else if (sundryCreditorDto.getCredit() != null) {
				balance = creditor.getBalance() - sundryCreditorDto.getCredit();
				
				if (creditor.getCredit() != null) {
					credit = creditor.getCredit() + sundryCreditorDto.getCredit();
				} else {
					credit = 0.0 + sundryCreditorDto.getCredit();
				}if(creditor.getBalance()<sundryCreditorDto.getCredit()) {
					throw new AlreadyExitsException("No Balace Available");
				}
				sundryCreditor.setCredit(credit);
			}
			sundryCreditor.setBalance(balance);

			newSundryCreditor = sundryCreditorRepository.save(sundryCreditor);

			//save master table
			
			
			SundryCreditor sundryCreditor1 = sundryCreditorRepository.findById(creditor.getId())
					.orElseThrow(() -> new NotFoundException("No debtor found for id : " + creditor.getId()));
			sundryCreditorTransaction.setSundryCreditor(sundryCreditor1);
			sundryCreditorTransaction.setParticulars(sundryCreditor1.getParticulars());

			
			if (newSundryCreditor == null) {
				sundryCreditorTransaction.setBalance(sundryCreditorDto.getDebit());


			} else {
				
				sundryCreditor1.setParticulars(sundryCreditorDto.getParticulars());
				sundryCreditor1.setTransDate(sundryCreditorDto.getTransDate());
				
				sundryCreditorTransaction.setBalance(sundryCreditor1.getBalance());
				sundryCreditorTransaction.setParticulars(sundryCreditor1.getParticulars());
				sundryCreditorTransaction.setTransactionDate(sundryCreditor1.getTransDate());

				if (sundryCreditorDto.getDebit() != null) {
					sundryCreditorTransaction.setDebit(sundryCreditorDto.getDebit());
				} else if (sundryCreditorDto.getCredit() != null) {
					sundryCreditorTransaction.setCredit(sundryCreditorDto.getCredit());
				}
				sundryCreditorTransaction.setBalance(balance);

			}

			sundryCreditorTransactionRepository.save(sundryCreditorTransaction);
			sundryCreditorDto.setBalance(sundryCreditorTransaction.getBalance());
			sundryCreditorDto.setTransDate(sundryCreditorTransaction.getTransactionDate());
		}
		return sundryCreditorDto;
	}


	@Override
	public SundryCreditorDto updateSundry(Long sundryCreditorId, SundryCreditorDto sundryCreditorDto) {
		SundryCreditor sundryCreditor = sundryCreditorRepository.findById(sundryCreditorId).orElseThrow(
				() -> new NotFoundException("No Deposit found for Id : " +sundryCreditorId));
		sundryCreditorDto.setId(sundryCreditor.getId());
		SundryCreditor sundryCreditors = sundryCreditorMapper.sundryCreditorDtoToSundryCreditor(sundryCreditorDto);			
		sundryCreditors.setVendor(sundryCreditor.getVendor());
		return sundryCreditorMapper.sundryCreditorToSundryCreditorDto(sundryCreditorRepository.save(sundryCreditors));
		
	}

	@Override
	public SundryCreditorPageList listAllCreditors(Pageable pageable) {
		log.debug("Request to get Creditors : {}");

		Page<SundryCreditor> creditorPage;
		creditorPage = sundryCreditorRepository.findByisDeleted(pageable,false);

		return new SundryCreditorPageList(creditorPage
										.getContent()
										.stream()
										.map(sundryCreditorMapper::sundryCreditorToSundryCreditorDto)
										.collect(Collectors.toList()),
										PageRequest
											.of(creditorPage.getPageable().getPageNumber(),
													creditorPage.getPageable().getPageSize()),
											creditorPage.getTotalElements());

	}

	@Override
	public SundryCreditorDto deleteCreditorById(long id) {
		SundryCreditor creditor = sundryCreditorRepository.findById(id).orElseThrow(
				() -> new NotFoundException("No Creditor found for Id " + id));
		if (creditor != null) {
			creditor.setIsDeleted(true);
			sundryCreditorRepository.save(creditor);
		}
	return sundryCreditorMapper.sundryCreditorToSundryCreditorDto(creditor);
}

	@Override
	public SundryCreditorDto getCreditorById(long id) {
		log.debug("REST request to get Creditors : {}", id);
		SundryCreditor creditor = sundryCreditorRepository.findById(id).orElseThrow(
				() -> new NotFoundException("No Creditor found for Id : " +id));

		return sundryCreditorMapper.sundryCreditorToSundryCreditorDto(creditor);
	}



	
}

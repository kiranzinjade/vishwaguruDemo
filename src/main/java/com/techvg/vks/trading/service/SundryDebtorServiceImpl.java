package com.techvg.vks.trading.service;

import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.techvg.vks.exceptions.AlreadyExitsException;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.membership.repository.MemberRepository;
import com.techvg.vks.trading.domain.SundryDebtor;
import com.techvg.vks.trading.domain.SundryDebtorTransaction;
import com.techvg.vks.trading.mapper.SundryDebtorMapper;
import com.techvg.vks.trading.model.SundryDebtorDto;
import com.techvg.vks.trading.model.SundryDebtorPageList;
import com.techvg.vks.trading.repository.SundryDebtorRepository;
import com.techvg.vks.trading.repository.SundryDebtorTransactionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SundryDebtorServiceImpl implements SundryDebtorService {

	private final SundryDebtorMapper sundryDebtorMapper;
	private final MemberRepository memberRepository;
	private final SundryDebtorRepository sundryDebtorRepository;
	private final SundryDebtorTransactionRepository sundryDebtorTransactionRepository;

	@Override
	public SundryDebtorDto addSundryDebitor(SundryDebtorDto sundryDebtorDto, Authentication authentication) {

		SundryDebtor sundryDebtor = sundryDebtorMapper.sundryDebtorDtoToSundryDebtor(sundryDebtorDto);

		Member member = memberRepository.findById(sundryDebtorDto.getMemberId())
				.orElseThrow(() -> new NotFoundException("No debtor found for id : " + sundryDebtorDto.getMemberId()));

		sundryDebtor.setMember(member);
		sundryDebtor.setDate(new Date());

		log.debug("REST request to save Sundry Debtor: {}", sundryDebtorDto);
		SundryDebtor debtor = sundryDebtorRepository.findLastRecordById(sundryDebtorDto.getMemberId());
		SundryDebtor newSundryDebtor = null;
		SundryDebtorTransaction sundryDebtorTransaction = new SundryDebtorTransaction();

		double balance = 0.0;
		if (debtor == null && sundryDebtorDto.getDebit()!=null) {
			throw new AlreadyExitsException("1st entry should be credit");

		}
		if (debtor == null) {
			sundryDebtor.setBalance(sundryDebtorDto.getCredit());
			newSundryDebtor = sundryDebtorRepository.save(sundryDebtor);
			sundryDebtorTransaction.setSundryDebtor(newSundryDebtor);
			sundryDebtorTransaction.setCredit(sundryDebtorDto.getCredit());
			sundryDebtorTransaction.setBalance(sundryDebtorDto.getCredit());
			sundryDebtorTransaction.setParticulars(sundryDebtorDto.getParticulars());
			sundryDebtorTransaction.setTransactionDate(sundryDebtorDto.getTransactionDate());
			sundryDebtorTransactionRepository.save(sundryDebtorTransaction);
		}
		

		else if (debtor != null) {

			sundryDebtor = sundryDebtorRepository.findById(debtor.getId())
					.orElseThrow(() -> new NotFoundException("No Record found for Id : " + debtor.getId()));

			double debit = 0.0;
			double credit = 0.0;


			if (sundryDebtorDto.getDebit() != null) {
				balance = debtor.getBalance() - sundryDebtorDto.getDebit();
				if (debtor.getDebit() != null) {
					debit = debtor.getDebit() + sundryDebtorDto.getDebit();
				}
				else {
					debit = 0.0 + sundryDebtorDto.getDebit();
				}
				if(debtor.getBalance()<sundryDebtorDto.getDebit()) {
					throw new AlreadyExitsException("No Balace Available");
				}
				
				sundryDebtor.setDebit(debit);
			} else if (sundryDebtorDto.getCredit() != null) {
				balance = debtor.getBalance() + sundryDebtorDto.getCredit();
				credit = credit + sundryDebtorDto.getCredit();
				sundryDebtor.setCredit(debtor.getCredit() + sundryDebtorDto.getCredit());
			}
			sundryDebtor.setBalance(balance);

			newSundryDebtor = sundryDebtorRepository.save(sundryDebtor);

			//save master table
			
			
			SundryDebtor sundryDebtor1 = sundryDebtorRepository.findById(debtor.getId())
					.orElseThrow(() -> new NotFoundException("No debtor found for id : " + debtor.getId()));
			sundryDebtorTransaction.setSundryDebtor(sundryDebtor1);
			sundryDebtorTransaction.setParticulars(sundryDebtor1.getParticulars());

			
			if (newSundryDebtor == null) {
				sundryDebtorTransaction.setBalance(sundryDebtorDto.getCredit());


			} else {
				
				sundryDebtor1.setParticulars(sundryDebtorDto.getParticulars());
				sundryDebtor1.setTransactionDate(sundryDebtorDto.getTransactionDate());
				
				sundryDebtorTransaction.setBalance(sundryDebtor1.getBalance());
				sundryDebtorTransaction.setParticulars(sundryDebtor1.getParticulars());
				sundryDebtorTransaction.setTransactionDate(sundryDebtor1.getTransactionDate());

				if (sundryDebtorDto.getDebit() != null) {
					sundryDebtorTransaction.setDebit(sundryDebtorDto.getDebit());
				} else if (sundryDebtorDto.getCredit() != null) {
					sundryDebtorTransaction.setCredit(sundryDebtorDto.getCredit());
				}
				
				sundryDebtorTransaction.setBalance(balance);
				sundryDebtorDto.setBalance(sundryDebtorTransaction.getBalance());
				sundryDebtorDto.setTransactionDate(sundryDebtorTransaction.getTransactionDate());
			}
            
			sundryDebtorTransactionRepository.save(sundryDebtorTransaction);
			}
//		}
		return sundryDebtorDto;
	}

	@Override
	public SundryDebtorDto updateSundry(Long sundryDebtorId, SundryDebtorDto sundryDebtorDto) {

		SundryDebtor sundryDebtor = sundryDebtorRepository.findById(sundryDebtorId)
				.orElseThrow(() -> new NotFoundException("No Record found for Id : " + sundryDebtorId));
		sundryDebtorDto.setId(sundryDebtor.getId());
		Member member = memberRepository.findById(sundryDebtorDto.getMemberId())
				.orElseThrow(() -> new NotFoundException("No debtor found for id : " + sundryDebtorDto.getMemberId()));

		SundryDebtor sundryDebtors = sundryDebtorMapper.sundryDebtorDtoToSundryDebtor(sundryDebtorDto);
		sundryDebtors.setMember(member);
		return sundryDebtorMapper.sundryDebtorToSundryDebtorDto(sundryDebtorRepository.save(sundryDebtors));

	}

	@Override
	public SundryDebtorPageList listAllDebtors(Pageable pageable) {
		log.debug("Request to get Debtors : {}");

		Page<SundryDebtor> debtorPage;
		debtorPage = sundryDebtorRepository.findByisDeleted(pageable, false);

		return new SundryDebtorPageList(
				debtorPage.getContent().stream().map(sundryDebtorMapper::sundryDebtorToSundryDebtorDto)
						.collect(Collectors.toList()),
				PageRequest.of(debtorPage.getPageable().getPageNumber(), debtorPage.getPageable().getPageSize()),
				debtorPage.getTotalElements());

	}

	@Override
	public SundryDebtorDto deleteDebtorsById(long id) {
		SundryDebtor debtor = sundryDebtorRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("No Debtor found for Id " + id));
		if (debtor != null) {
			debtor.setIsDeleted(true);
			sundryDebtorRepository.save(debtor);
		}
		return sundryDebtorMapper.sundryDebtorToSundryDebtorDto(debtor);
	}

	@Override
	public SundryDebtorDto getDebtorById(long id) {
		log.debug("REST request to get Debtor : {}", id);
		SundryDebtor debtor = sundryDebtorRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("No Creditor found for Id : " + id));

		return sundryDebtorMapper.sundryDebtorToSundryDebtorDto(debtor);
	}

}

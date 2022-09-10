package com.techvg.vks.society.service;

import com.techvg.vks.common.DateConverter;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.society.domain.BorrowingLedger;
import com.techvg.vks.society.domain.SocietyBank;
import com.techvg.vks.society.mapper.BorrowingLedgerMapper;
import com.techvg.vks.society.model.BorrowingLedgerDto;
import com.techvg.vks.society.model.BorrowingLedgerPageList;
import com.techvg.vks.society.repository.BorrowingLedgerRepository;
import com.techvg.vks.society.repository.SocietyBankRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class BorrowingLedgerServiceImpl implements BorrowingLedgerService {
	
	private final BorrowingLedgerMapper borrowingLedgerMapper;
	private final BorrowingLedgerRepository borrowingLedgerRepository;
	private final SocietyBankRepository bankRepository;
	

	@Override
	public BorrowingLedgerDto addborrowingDetails(BorrowingLedgerDto borrowingLedgerDto) {
		BorrowingLedger borrowingLedger1 = borrowingLedgerMapper.borrowingLedgerDtoToBorrowingLedger(borrowingLedgerDto);
		SocietyBank bank=bankRepository.findById(borrowingLedgerDto.getBankId()).orElseThrow
				(() -> new NotFoundException("No bank found for id : " +borrowingLedgerDto.getBankId()));

		borrowingLedger1.setBank(bank);
		
		Date date=borrowingLedger1.getDate();
		Integer duration=borrowingLedger1.getDuration();
		Date d1 = DateConverter.incrementDateByDays(duration, date);
		borrowingLedger1.setDueDate(d1);
		log.debug("REST request to save Prerequisite : {}", borrowingLedger1);
		return borrowingLedgerMapper.borrowingLedgerToBorrowingLedgerDto(borrowingLedgerRepository.save(borrowingLedger1));	
	}

	@Override
	public BorrowingLedgerPageList listborrow(Pageable pageable) {
		log.debug("Request to get Purchase : {}");

		Page<BorrowingLedger> borrowPage;
		borrowPage = borrowingLedgerRepository.findByisDeleted(pageable,false);

		return new BorrowingLedgerPageList(borrowPage
										.stream()
										.map(borrowingLedgerMapper::borrowingLedgerToBorrowingLedgerDto)
										.collect(Collectors.toList()),
										PageRequest
											.of(borrowPage.getPageable().getPageNumber(),
													borrowPage.getPageable().getPageSize()),
											borrowPage.getTotalElements());
	}

	@Override
	public BorrowingLedgerDto deleteBorrowById(Long id) {
		BorrowingLedger borrowingLedger = borrowingLedgerRepository.findById(id).orElseThrow
				(() -> new NotFoundException("No borrowlist found for Id : " +id));

		if (borrowingLedger != null) {
			borrowingLedger.setIsDeleted(true);
			borrowingLedgerRepository.save(borrowingLedger);
		}
	return borrowingLedgerMapper.borrowingLedgerToBorrowingLedgerDto(borrowingLedger);
	}

	@Override
	public BorrowingLedgerDto updateBorrowList(Long id, BorrowingLedgerDto borrowingLedgerDto) {
		borrowingLedgerRepository.findById(id).orElseThrow(
		() -> new NotFoundException("No borrowlist found for Id : " +id));
		borrowingLedgerDto.setId(id);	
		
		BorrowingLedger borrowingLedger1 = borrowingLedgerMapper.borrowingLedgerDtoToBorrowingLedger(borrowingLedgerDto);
		SocietyBank bank=bankRepository.findById(borrowingLedgerDto.getBankId()).orElseThrow(
				() -> new NotFoundException("No borrowlist found for Id : " +id));
		borrowingLedger1.setBank(bank);
		return borrowingLedgerMapper.borrowingLedgerToBorrowingLedgerDto(borrowingLedgerRepository.save(borrowingLedger1));
	}

	@Override
	public BorrowingLedgerDto getBorrowListById(Long id) {
		log.debug("REST request to get Purchase : {}", id);
		BorrowingLedger borrowingLedger = borrowingLedgerRepository.findById(id).orElseThrow(
				() -> new NotFoundException("No borrowlist found for Id : " +id));

		return borrowingLedgerMapper.borrowingLedgerToBorrowingLedgerDto(borrowingLedger);
	
	}

	@Override
	public List<BorrowingLedgerDto> listLedger() {
		return borrowingLedgerMapper.domainToDtoList(borrowingLedgerRepository.findAll());
	}

}

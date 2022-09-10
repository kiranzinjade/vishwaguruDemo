package com.techvg.vks.accounts.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.ibm.icu.util.Calendar;
import com.techvg.vks.accounts.domain.LedgerAccounts;
import com.techvg.vks.accounts.domain.ProfitDistributionLedger;
import com.techvg.vks.accounts.mapper.ProfitDistributionLedgerMapper;
import com.techvg.vks.accounts.model.ProfitDistributionLedgerDto;
import com.techvg.vks.accounts.model.ProfitDistributionLedgerPageList;
import com.techvg.vks.accounts.repository.LedgerAccountsRepository;
import com.techvg.vks.accounts.repository.ProfitDistributionLedgerRepository;
import com.techvg.vks.exceptions.NotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfitDistributionLedgerServiceImpl implements ProfitDistributionLedgerService {
	
	private final LedgerAccountsRepository ledgerAccountsRepository;
	private final ProfitDistributionLedgerMapper profitDistributionLedgerMapper;
	private final ProfitDistributionLedgerRepository profitDistributionLedgerRepository;
	int limit;
	
	@Override
	public ProfitDistributionLedgerDto addLedgerDetails(ProfitDistributionLedgerDto profitDistributionLedgerDto,
			Authentication authentication) {
		ProfitDistributionLedger profitDistributionLedger = profitDistributionLedgerMapper.profitDistributionLedgerDtoToProfitDistributionLedger(profitDistributionLedgerDto);	
		
		
		LedgerAccounts ledgerAccounts=ledgerAccountsRepository.findById(profitDistributionLedgerDto.getLedgerAccountId()).orElseThrow
				(() -> new NotFoundException("No Ledger found for id : " +profitDistributionLedgerDto.getLedgerAccountId()));
		profitDistributionLedger.setLedgerAccounts(ledgerAccounts);
	List<LedgerAccounts> ledgerAccountList = ledgerAccountsRepository.findLedgerList(profitDistributionLedgerDto.getLedgerAccountId());
	
		int count=ledgerAccountList.size();
		for(int i=0;i<count;i++) {
		ProfitDistributionLedger ProfitDistributionLedger1=new ProfitDistributionLedger();
		ProfitDistributionLedger1.setAccHeadCode(ledgerAccountList.get(i).getAccHeadCode());
		System.out.println("List 3"+ledgerAccountList.get(i).getAccHeadCode());
		}
		Calendar cal= Calendar.getInstance();
		int year=cal.get(Calendar.YEAR);
		profitDistributionLedger.setYear(year);
	return profitDistributionLedgerMapper.profitDistributionLedgerToProfitDistributionLedgerDto(profitDistributionLedgerRepository.save(profitDistributionLedger));	
	}

	@Override
	public ProfitDistributionLedgerPageList listLedger(Pageable pageable) {
		log.debug("Request to get Purchase : {}");

		Page<ProfitDistributionLedger> profitPage;
		profitPage = profitDistributionLedgerRepository.findByisDeleted(pageable,false);

		return new ProfitDistributionLedgerPageList(profitPage
										.stream()
										.map(profitDistributionLedgerMapper::profitDistributionLedgerToProfitDistributionLedgerDto)
										.collect(Collectors.toList()),
										PageRequest
											.of(profitPage.getPageable().getPageNumber(),
													profitPage.getPageable().getPageSize()),
											profitPage.getTotalElements());
	}

	@Override
	public ProfitDistributionLedgerDto deleteLedgerById(Long id) {
		ProfitDistributionLedger profitDistributionLedger = profitDistributionLedgerRepository.findById(id).orElseThrow
				(() -> new NotFoundException("No Ledger found for Id : " +id));

		if (profitDistributionLedger != null) {
			profitDistributionLedger.setIsDeleted(true);
			profitDistributionLedgerRepository.save(profitDistributionLedger);
		}
	return profitDistributionLedgerMapper.profitDistributionLedgerToProfitDistributionLedgerDto(profitDistributionLedger);
	}

	@Override
	public ProfitDistributionLedgerDto updateLedgerList(Long id,ProfitDistributionLedgerDto profitDistributionLedgerDto) {
		profitDistributionLedgerRepository.findById(id).orElseThrow(
				() -> new NotFoundException("No Ledger found for Id : " +id));
		profitDistributionLedgerDto.setId(id);	
				
		ProfitDistributionLedger profitDistributionLedger1 = profitDistributionLedgerMapper.profitDistributionLedgerDtoToProfitDistributionLedger(profitDistributionLedgerDto);
				LedgerAccounts ledgerAccounts=ledgerAccountsRepository.findById(profitDistributionLedgerDto.getLedgerAccountId()).orElseThrow(
						() -> new NotFoundException("No borrowlist found for Id : " +id));
				profitDistributionLedger1.setLedgerAccounts(ledgerAccounts);
				return profitDistributionLedgerMapper.profitDistributionLedgerToProfitDistributionLedgerDto(profitDistributionLedgerRepository.save(profitDistributionLedger1));
	}

	@Override
	public ProfitDistributionLedgerDto getLedgerListById(Long id) {
		log.debug("REST request to get Purchase : {}", id);
		ProfitDistributionLedger profitDistributionLedger = profitDistributionLedgerRepository.findById(id).orElseThrow(
				() -> new NotFoundException("No borrowlist found for Id : " +id));

		return profitDistributionLedgerMapper.profitDistributionLedgerToProfitDistributionLedgerDto(profitDistributionLedger);
	
	}
	}



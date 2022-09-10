package com.techvg.vks.society.service;


import com.techvg.vks.accounts.model.VouchersDto;
import com.techvg.vks.accounts.service.vouchers.ExpenseVoucherService;
import com.techvg.vks.common.DateConverter;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.society.domain.ExpenditureRegister;
import com.techvg.vks.society.domain.ExpenditureType;
import com.techvg.vks.society.mapper.ExpenditureRegisterMapper;
import com.techvg.vks.society.model.ExpenditureRegisterDto;
import com.techvg.vks.society.model.ExpenditureRegisterPageList;
import com.techvg.vks.society.repository.ExpenditureRegisterRepository;
import com.techvg.vks.society.repository.ExpenditureTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExpenditureRegisterServiceImpl  implements ExpenditureRegisterService {
	
	private final ExpenditureRegisterMapper expenditureRegisterMapper;
	private final  ExpenditureRegisterRepository expenditureRegisterRepository;
	private final ExpenditureTypeRepository expenditureTypeRepository;
	private final ExpenseVoucherService expenseVoucherService;
	
	
	@Override
	public ExpenditureRegisterDto addExpenditure(ExpenditureRegisterDto expenditureRegisterDto) {
			
			ExpenditureType expenditureType = expenditureTypeRepository.findById(expenditureRegisterDto.getExpenditureTypeId()).orElseThrow(NotFoundException::new);
            ExpenditureRegister expenditureRegister = expenditureRegisterMapper.toExpenditureRegister(expenditureRegisterDto);
            expenditureRegister.setExpenditureType(expenditureType);
			expenditureRegisterRepository.save(expenditureRegister);

            return expenditureRegisterMapper.toExpenditureRegisterDto(expenditureRegister);
          
	}

	@Override
	public ExpenditureRegisterDto updateExpenditure(Long id, ExpenditureRegisterDto expenditureRegisterDto) {
		ExpenditureRegister expenditureRegister = expenditureRegisterRepository.findById(id).orElseThrow(NotFoundException::new);
		expenditureRegisterDto.setId(expenditureRegister.getId());
		ExpenditureRegister expenditureRegisters = expenditureRegisterMapper.toExpenditureRegister(expenditureRegisterDto);
		expenditureRegisters.setExpenditureType(expenditureRegister.getExpenditureType());
		return expenditureRegisterMapper.toExpenditureRegisterDto(expenditureRegisterRepository.save(expenditureRegisters));
		
	}

	@Override
	public ExpenditureRegisterDto getExpenditure(Long expenditureId) {
		ExpenditureRegister expenditureRegister = expenditureRegisterRepository.findById(expenditureId).orElseThrow(
				() -> new NotFoundException("No Expenditure found for Id : " +expenditureId));
       return expenditureRegisterMapper.toExpenditureRegisterDto(expenditureRegister);
	}

	@Override
	public ExpenditureRegisterDto deleteExpenditureById(Long expenditureId) {
		ExpenditureRegister expenditureRegister = expenditureRegisterRepository.findById(expenditureId).orElseThrow(NotFoundException::new);
		if(expenditureRegister!=null) {
			expenditureRegister.setIsDeleted(true);
			expenditureRegisterRepository.save(expenditureRegister);
		}
	       return expenditureRegisterMapper.toExpenditureRegisterDto(expenditureRegister);

	}

	@Override
	public ExpenditureRegisterPageList listExpenditures(Pageable pageable) {
    log.debug("REST request to save Expenditure Register Page List : {}");
		
		Page<ExpenditureRegister> expenditureRegisterPage;
		expenditureRegisterPage = expenditureRegisterRepository.findByisDeleted(pageable,false);
       
		return new ExpenditureRegisterPageList(expenditureRegisterPage
										.getContent()
										.stream()
										.map(expenditureRegisterMapper::toExpenditureRegisterDto)
										.collect(Collectors.toList()),
										PageRequest
											.of(expenditureRegisterPage.getPageable().getPageNumber(),
													expenditureRegisterPage.getPageable().getPageSize()),
											expenditureRegisterPage.getTotalElements());
	
	}

	@Override
	public VouchersDto acceptExpensePayment(ExpenditureRegisterDto expenditureRegisterDto) {
		addExpenditure(expenditureRegisterDto);
		return expenseVoucherService.updateExpenseVoucher(expenditureRegisterDto.getVouchersDto());
	}

	@Override
	public ExpenditureRegisterDto previewExpensePayment(ExpenditureRegisterDto expenditureRegisterDto) {
		expenditureRegisterDto.setVouchersDto(expenseVoucherService.createExpenseVoucher(expenditureRegisterDto));
		return expenditureRegisterDto;
	}

	@Override
	public List<ExpenditureRegisterDto> getTradeExpenseList(String fromdate, String todate) {
		Date v=DateConverter.getDate(fromdate);
		Date h=DateConverter.getDate(todate);
		return expenditureRegisterMapper.domainToDtoList(expenditureRegisterRepository.findByExpenditureDateForTrade(v, h));
	}
	
	@Override
	public List<ExpenditureRegisterDto> getSocietyExpenseList(String fromdate, String todate) {
		Date v=DateConverter.getDate(fromdate);
		Date h=DateConverter.getDate(todate);
		return expenditureRegisterMapper.domainToDtoList(expenditureRegisterRepository.findByExpenditureDateForSociety(v, h));
	}

	@Override
	public ExpenditureRegisterPageList getListByTradeExpense(Pageable pageable) {
		
		Page<ExpenditureRegister> expenditureRegisterPage;
		List<ExpenditureRegister>list= expenditureRegisterRepository.findListByTradeExpense();
		expenditureRegisterPage =  new PageImpl<>(list,pageable,list.size());
       
		return new ExpenditureRegisterPageList(expenditureRegisterPage
										.getContent()
										.stream()
										.map(expenditureRegisterMapper::toExpenditureRegisterDto)
										.collect(Collectors.toList()),
										PageRequest
											.of(expenditureRegisterPage.getPageable().getPageNumber(),
													expenditureRegisterPage.getPageable().getPageSize()),
											expenditureRegisterPage.getTotalElements());
	}

	@Override
	public ExpenditureRegisterPageList getListBySocietyExpense(Pageable pageable) {
		Page<ExpenditureRegister> expenditureRegisterPage;
		List<ExpenditureRegister>list= expenditureRegisterRepository.findListBySocietyExpense();
		expenditureRegisterPage =  new PageImpl<>(list,pageable,list.size());
       
		return new ExpenditureRegisterPageList(expenditureRegisterPage
										.getContent()
										.stream()
										.map(expenditureRegisterMapper::toExpenditureRegisterDto)
										.collect(Collectors.toList()),
										PageRequest
											.of(expenditureRegisterPage.getPageable().getPageNumber(),
													expenditureRegisterPage.getPageable().getPageSize()),
											expenditureRegisterPage.getTotalElements());
	}

	@Override
	public ExpenditureRegisterPageList getListByProvisionExpense(Pageable pageable) {
		Page<ExpenditureRegister> expenditureRegisterPage;
		List<ExpenditureRegister>list= expenditureRegisterRepository.findListByProvisionExpense();
		expenditureRegisterPage =  new PageImpl<>(list,pageable,list.size());
       
		return new ExpenditureRegisterPageList(expenditureRegisterPage
										.getContent()
										.stream()
										.map(expenditureRegisterMapper::toExpenditureRegisterDto)
										.collect(Collectors.toList()),
										PageRequest
											.of(expenditureRegisterPage.getPageable().getPageNumber(),
													expenditureRegisterPage.getPageable().getPageSize()),
											expenditureRegisterPage.getTotalElements());
	}

	@Override
	public List<ExpenditureRegisterDto> getProvisionExpenseList(String fromdate, String todate) {
		Date v=DateConverter.getDate(fromdate);
		Date h=DateConverter.getDate(todate);
		return expenditureRegisterMapper.domainToDtoList(expenditureRegisterRepository.findByExpenditureDateForProvision(v, h));
	
	}

	
	
	 
}


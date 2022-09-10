package com.techvg.vks.society.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.techvg.vks.accounts.model.VouchersDto;
import com.techvg.vks.society.model.ExpenditureRegisterDto;
import com.techvg.vks.society.model.ExpenditureRegisterPageList;

@Service
public interface ExpenditureRegisterService {

	ExpenditureRegisterDto addExpenditure(ExpenditureRegisterDto expenditureRegisterDto);

	ExpenditureRegisterDto updateExpenditure(Long id,ExpenditureRegisterDto expenditureRegisterDto);

	ExpenditureRegisterDto deleteExpenditureById(Long expenditureId);

	ExpenditureRegisterDto getExpenditure(Long expenditureId);

	ExpenditureRegisterPageList listExpenditures(Pageable pageable);

	VouchersDto acceptExpensePayment(ExpenditureRegisterDto expenditureRegisterDto);

	ExpenditureRegisterDto previewExpensePayment(ExpenditureRegisterDto expenditureRegisterDto);

	ExpenditureRegisterPageList getListByTradeExpense(Pageable pageable);

	ExpenditureRegisterPageList getListBySocietyExpense(Pageable pageable);

	ExpenditureRegisterPageList getListByProvisionExpense(Pageable pageable);

	List<ExpenditureRegisterDto> getSocietyExpenseList(String fromdate, String todate);

	List<ExpenditureRegisterDto> getTradeExpenseList(String fromdate, String todate);

	List<ExpenditureRegisterDto> getProvisionExpenseList(String fromdate, String todate);

}

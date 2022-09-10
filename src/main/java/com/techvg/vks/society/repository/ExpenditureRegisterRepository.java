package com.techvg.vks.society.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.techvg.vks.society.domain.ExpenditureRegister;

public interface ExpenditureRegisterRepository extends JpaRepository<ExpenditureRegister, Long> {

	Page<ExpenditureRegister> findByisDeleted(Pageable pageable, boolean b);

	Optional<ExpenditureRegister> findByName(String name);

	@Query(value="select * FROM expenditure_register JOIN expenditure_type ON expenditure_register.expenditure_type_id = expenditure_type.id WHERE (expenditure_type.expenditure_category='Trade' and date(expenditure_register.expenditure_date)  between :fromdate and :todate)",nativeQuery = true)
	List<ExpenditureRegister> findByExpenditureDateForTrade(@Param("fromdate") Date fromDate,@Param("todate") Date toDate);
	
	@Query(value="select * FROM expenditure_register JOIN expenditure_type ON expenditure_register.expenditure_type_id = expenditure_type.id WHERE (expenditure_type.expenditure_category='Office Expense' and date(expenditure_register.expenditure_date)  between :fromdate and :todate)",nativeQuery = true)
	List<ExpenditureRegister> findByExpenditureDateForSociety(@Param("fromdate") Date fromDate,@Param("todate") Date toDate);
	
	@Query(value="select * FROM expenditure_register JOIN expenditure_type ON expenditure_register.expenditure_type_id = expenditure_type.id WHERE (expenditure_type.expenditure_category='Office Provision Expense' and date(expenditure_register.expenditure_date)  between :fromdate and :todate)",nativeQuery = true)
	List<ExpenditureRegister> findByExpenditureDateForProvision(@Param("fromdate") Date fromDate,@Param("todate") Date toDate);
	
	@Query(value="select * FROM expenditure_register JOIN expenditure_type ON expenditure_register.expenditure_type_id = expenditure_type.id WHERE (expenditure_type.expenditure_category='Trade')",nativeQuery = true)
	List<ExpenditureRegister> findListByTradeExpense();
	
	@Query(value="select * FROM expenditure_register JOIN expenditure_type ON expenditure_register.expenditure_type_id = expenditure_type.id WHERE (expenditure_type.expenditure_category='Office Expense')",nativeQuery = true)
	List<ExpenditureRegister> findListBySocietyExpense();
	
	@Query(value="select * FROM expenditure_register JOIN expenditure_type ON expenditure_register.expenditure_type_id = expenditure_type.id WHERE (expenditure_type.expenditure_category='Office Provision Expense')",nativeQuery = true)
	List<ExpenditureRegister> findListByProvisionExpense();
	
}

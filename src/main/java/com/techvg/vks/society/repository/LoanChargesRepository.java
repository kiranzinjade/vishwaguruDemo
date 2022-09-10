package com.techvg.vks.society.repository;

import com.techvg.vks.society.domain.LoanCharges;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LoanChargesRepository extends JpaRepository<LoanCharges, Long>{
	Page<LoanCharges> findByisDeleted(Pageable pageable, boolean deleted);
	
	@Query(value="select s from LoanCharges s where s.chargesCategory=:chargesCategory and s.loanType=:loanType")
	List<LoanCharges> findByCategoryandLoanType(String chargesCategory,String loanType);

}
package com.techvg.vks.loan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.techvg.vks.loan.domain.Disbursement;

@Repository
public interface DisbursementRepository extends JpaRepository<Disbursement, Long>{
	
	@Query(value="select d from Disbursement d where d.loanType=:loanType")
	List<Disbursement> findByLoanType(@Param("loanType") String loanType);

	@Query(value="select s from Disbursement s where s.loanDetails.id=:loanId ")
	List<Disbursement> findByLoanId(@Param("loanId") Long loanId);

}

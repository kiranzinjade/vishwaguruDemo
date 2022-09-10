package com.techvg.vks.loan.repository;

import com.techvg.vks.loan.domain.Repayment;
import com.techvg.vks.loan.model.RepaymentDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepaymentRepository  extends JpaRepository<Repayment, Long> {
	
	@Query(value="select r from Repayment r where r.loanDetails.id=:loanId")
    List<Repayment> findByLoanDetailsId(@Param("loanId")Long loanId);

	@Query(value="select s from Repayment s where s.loanDetails.id=:loanId ")
	List<Repayment> findByLoanId(@Param("loanId") Long loanId);

	void save(RepaymentDto repaymentDto);
	
	@Query(value="select s from Repayment s where s.loanDetails.id=:loanId  and s.id = (select max(s.id) from  Repayment s where s.loanDetails.id=:loanId)")
	Repayment findLastRepaymentByLoanId(@Param("loanId") Long loanId);

}

package com.techvg.vks.loan.repository;

import com.techvg.vks.loan.domain.Amortization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AmortizationRepository extends JpaRepository<Amortization, Long> {

	@Query(value="select a from Amortization a  where a.loanDetails.loanStatus=:loanStatus")
	List<Amortization> findByOutstanding(@Param("loanStatus")String loanStatus);

	@Query(value="select a from Amortization a where a.loanDetails.id=:loanId")
	List<Amortization> findByLoanId(@Param("loanId")Long loanId);

	@Query(value=" select d from Amortization d where d.loanDetails.id = :loanId and d.paymentStatus =:payStatus and d.id = (select min(d.id) from  Amortization d where d.loanDetails.id = :loanId and d.paymentStatus =:payStatus )")
	Amortization findByLoanIdAndStatus(@Param("loanId")Long loanId, String payStatus);

	//Amortization findTopByLoanDetails_IdAndPaymentStatusOrderByIdDesc(Long loanId, String paymentStatus);


}

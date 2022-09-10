package com.techvg.vks.loan.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.techvg.vks.loan.domain.LoanDetails;
import com.techvg.vks.membership.domain.Member;


@Repository
public interface LoanDetailsRepository extends JpaRepository<LoanDetails, Long>{
	 
	Page<LoanDetails> findByLoanStatus(String string, Pageable pageable);
	
	@Query(value="select s from LoanDetails s where s.loanType='short' and s.isDeleted= false")
	Page<LoanDetails> findByLoanType(Pageable pageable);

	@Query(value="select s from LoanDetails s where s.loanType='mid' or s.loanType='long'  and s.isDeleted =false")
	Page<LoanDetails> findByisDeleted(boolean b, Pageable pageable);

	@Query(value="select s from LoanDetails s where s.isDeleted =false and s.loanStatus='A'")
	List<LoanDetails> findAllActiveLoans();

	@Query(value="select s from LoanDetails s where s.isDeleted =false and s.loanStatus='A' and s.member.id=:memberId")
	List<LoanDetails> findAllActiveLoansForMember(Long memberId);

	@Query(value="select s from LoanDetails s where s.loanProduct.productType =:type and s.member.id=:memberId order by s.id desc")
    List<LoanDetails> findByMember(Long memberId, String type);

    @Query(value="select l from LoanDetails l where l.loanPlannedClosureDate <CURDATE() and l.loanType=:loanType ")
	List<LoanDetails> findAllDefaulter(@Param("loanType")String loanType);
	
	@Query(value="select s from LoanDetails s where s.id=:loanId ")
	LoanDetails findByLoanId(@Param("loanId") Long loanId);

	@Query(value="select l from LoanDetails l where l.loanStatus=:loanStatus AND l.loanType=:loanType")
	List<LoanDetails> findByLoanTypeAndLoanStatus(@Param("loanType") String loanType, @Param("loanStatus") String loanStatus);

	List<LoanDetails> findByLoanStatus(String string);
	 @Query(value="select l from LoanDetails l where  l.loanType=:loanType and l.loanClassification=:loanClassification ")
		List<LoanDetails> findRepaymentList(@Param("loanType")String loanType,@Param("loanClassification") String loanClassification);
	 

	 @Query(value="select l from LoanDetails l where l.loanType=:loanType")
		List<LoanDetails> findByLoanType(@Param("loanType") String loanType);


	 @Query(value="select l from LoanDetails l where  l.loanType=:loanType and l.loanAmt<300000 ")
		List<LoanDetails> findByLoanAmountLessThan(@Param("loanType")String loanType);
	 
	 @Query(value="select l from LoanDetails l where  l.loanType=:loanType and l.loanAmt>300000 ")
		List<LoanDetails> findByLoanAmountGreaterThan(@Param("loanType")String loanType);
	
	 @Query(value="SELECT l from LoanDetails l WHERE MONTH(l.loanPlannedClosureDate) = MONTH(CURDATE()) and l.loanStatus=:loanActive")
	  List<LoanDetails>findByLoanPlannedCloserMonthly(@Param("loanActive") String loanActive);
     
	 @Query(value="SELECT DISTINCT(trn_loan_details.loan_account_no) from trn_loan_details  WHERE trn_loan_details.member_id=:memberId",nativeQuery=true) 
		List<LoanDetails> findLoanAccountNoByMemberId(@Param("memberId") Long memberId);

	List<LoanDetails> findByisDeleted(boolean b);

	List<LoanDetails> findByMemberId(Long memberId);
	
	///////Dashboard 
	@Query(value= "select count(*) from loan_details where loan_classfication='Standard'",nativeQuery=true)
	int getStandardLoanCount();
	
	@Query( value = "select count(*) from loan_details where loan_planned_closure_date <CURDATE() and loan_status='A'",nativeQuery=true)
	int getDefaulterCount();
	
	@Query( value = "select * from loan_details where loan_status='A' and loan_planned_closure_date <CURDATE() limit 5;",nativeQuery=true)
	List<LoanDetails> getDefaulterList();

	///////loan member list
	@Query(value = "select s from LoanDetails s where s.isDeleted =false and s.loanStatus='A' and s.loanType=:loanType")
	List<LoanDetails> findShortLoanMembers(@Param("loanType") String loanType);
	
	@Query(value = "select s from LoanDetails s where s.isDeleted =false and s.loanStatus='A' and s.loanType='mid' or s.loanType='long'")
	List<LoanDetails> findMidLongLoanMembers();
	
	@Query(value="select s from LoanDetails s where s.member.id=:memberId and s.loanStatus='A'")
	LoanDetails findByMemberIdAndStatus(@Param("memberId") Long memberId);

	LoanDetails findByLoanAccountNo(Long loanAccountNo);
}

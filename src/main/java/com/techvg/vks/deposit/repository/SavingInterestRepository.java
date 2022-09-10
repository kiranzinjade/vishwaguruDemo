package com.techvg.vks.deposit.repository;

import com.techvg.vks.deposit.domain.SavingAccount;
import com.techvg.vks.deposit.domain.SavingInterest;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface SavingInterestRepository  extends JpaRepository<SavingInterest, Long> {


	@Query(value="select sum(monthlyInterest) from SavingInterest s where s.accountNo=:accountNo and s.year=:year")
	double findByMonthlyInterest(@Param("accountNo")long accountNo,@Param("year")int year);

	List<SavingInterest>  findByAccountNo(Long accountNo);

}

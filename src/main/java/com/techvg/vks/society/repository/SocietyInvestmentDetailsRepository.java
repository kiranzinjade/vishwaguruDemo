package com.techvg.vks.society.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.techvg.vks.society.domain.SocietyInvestmentDetails;

public interface SocietyInvestmentDetailsRepository extends JpaRepository<SocietyInvestmentDetails, Long> {
	
	@Query(value="select s from SocietyInvestmentDetails s where s.societyInvestment.id=:societyInvestmentId")
	List<SocietyInvestmentDetails> findBySocietyInvestmentId(@Param("societyInvestmentId")Long societyInvestmentId);
}

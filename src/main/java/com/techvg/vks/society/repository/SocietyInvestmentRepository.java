package com.techvg.vks.society.repository;

import com.techvg.vks.society.domain.SocietyInvestment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SocietyInvestmentRepository extends JpaRepository<SocietyInvestment, Long> {

	Page<SocietyInvestment> findByisDeleted(Pageable pageable, boolean b);

	
	@Query(value="select s from SocietyInvestment s where s.societyInvestmentMaster.id=:societyInvestmentId")
	List<SocietyInvestment> findBySocietyInvestmentId(@Param("societyInvestmentId")Long societyInvestmentId);
	
	
	
}

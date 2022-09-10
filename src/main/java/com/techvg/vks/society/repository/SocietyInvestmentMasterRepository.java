package com.techvg.vks.society.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.techvg.vks.society.domain.SocietyInvestmentMaster;

@Repository
public interface SocietyInvestmentMasterRepository extends JpaRepository<SocietyInvestmentMaster, Long> {

	Page<SocietyInvestmentMaster> findByisDeleted(Pageable pageable, boolean b);
	
	@Query(value="select distinct(society_investment_mst.society_bank_id) from society_investment_mst",nativeQuery = true)
	List<Long> findBankBySocietyInvestmentId();
	
	@Query(value="SELECT s FROM SocietyInvestmentMaster s WHERE s.societyBank.id=:societyBankId")
	List<SocietyInvestmentMaster> findSchemeByBankId(@Param("societyBankId") Long societyBankId);

}

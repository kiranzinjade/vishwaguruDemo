package com.techvg.vks.society.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.techvg.vks.society.domain.ProductType;
import com.techvg.vks.society.domain.ProfitsAppropriation;

@Repository
public interface ProfitsAppropriationRepository extends JpaRepository <ProfitsAppropriation, Long> {

	Page<ProfitsAppropriation> findByisDeleted(Pageable pageable, boolean b);
	
	Optional<ProfitsAppropriation> findByYearAndIsDeleted(int year, boolean b);
	
	Optional<ProfitsAppropriation> findByYear(int year);
	
	//List<ProfitsAppropriation> findByisDeleted(boolean b);
	
	}

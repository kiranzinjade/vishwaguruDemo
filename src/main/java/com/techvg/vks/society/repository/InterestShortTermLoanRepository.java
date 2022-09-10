
package com.techvg.vks.society.repository;

import com.techvg.vks.society.domain.InterestShortTermLoan;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestShortTermLoanRepository extends JpaRepository<InterestShortTermLoan,Long>{
	
	InterestShortTermLoan findByCriteriaAndIsDeleted(String criteria, boolean b);

	Page<InterestShortTermLoan> findByIsDeleted(boolean b, Pageable pageable);

}


package com.techvg.vks.society.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techvg.vks.society.domain.LoanProduct;
@Repository
public interface LoanProductRepository extends JpaRepository<LoanProduct, Long> {

	Page<LoanProduct> findByisDeleted(Pageable pageable, boolean b);
	Optional<LoanProduct> findByProductNameAndIsDeleted(String productName,boolean b);
	List<LoanProduct> findByProductTypeAndIsDeleted(String loanType, boolean b);
	

	
	
}

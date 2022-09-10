package com.techvg.vks.loan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.techvg.vks.loan.domain.LoanDocument;

@Repository
public interface LoanDocumentRepository extends JpaRepository<LoanDocument, Long> {

	
}

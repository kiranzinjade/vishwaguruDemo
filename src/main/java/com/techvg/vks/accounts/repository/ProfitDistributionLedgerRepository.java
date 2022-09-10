package com.techvg.vks.accounts.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.techvg.vks.accounts.domain.ProfitDistributionLedger;
import com.techvg.vks.society.domain.BorrowingLedger;

public interface ProfitDistributionLedgerRepository extends JpaRepository<ProfitDistributionLedger, Long>{

	Page<ProfitDistributionLedger> findByisDeleted(Pageable pageable, boolean b);

	
}

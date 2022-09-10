package com.techvg.vks.society.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.techvg.vks.society.domain.BorrowingLedger;

public interface BorrowingLedgerRepository extends JpaRepository<BorrowingLedger, Long> {

	Page<BorrowingLedger> findByisDeleted(Pageable pageable, boolean b);

	Optional<BorrowingLedger> findByBankId(Long bankId);

}

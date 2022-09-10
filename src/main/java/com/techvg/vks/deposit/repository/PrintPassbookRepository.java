package com.techvg.vks.deposit.repository;

import com.techvg.vks.deposit.domain.PrintPassbook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PrintPassbookRepository extends JpaRepository<PrintPassbook, Long>{

	@Query(value=" select max(d.depositLedgerMaxId) from PrintPassbook d where d.accountNo = :accountNo ")
	Optional <Long> findMaxDepositLedgerIdByAccountNo(@Param("accountNo") Long accountNo);

	//Optional <PrintPassbook> findTopByAccountNoOrderByDepositLedgerMaxIdDesc(Long accountNo);

}

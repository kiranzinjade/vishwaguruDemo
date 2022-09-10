package com.techvg.vks.society.repository;

import com.techvg.vks.deposit.domain.DepositLedger;
import com.techvg.vks.society.domain.BorrowingLedgerTransaction;
import com.techvg.vks.trading.domain.SundryDebtor;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowingLedgerTransactionRepository extends JpaRepository<BorrowingLedgerTransaction, Long> {

	Page<BorrowingLedgerTransaction> findByisDeleted(Pageable pageable, boolean b);
	
	BorrowingLedgerTransaction findTopByOrderByIdDesc();
	
//	@Query(value="SELECT * from borrowing_ledger JOIN borrowing_ledger_trans ON borrowing_ledger_trans.borrowing_ledger_id=:borrowing_ledger.id JOIN society_bank_master WHERE society_bank_master.id=:borrowing_ledger.bank_id",nativeQuery = true)
//	List<BorrowingLedgerTransaction> findByBankId(@Param("bankId") Long bankId);

	@Query(value = "select sum(borrowing_ledger.loan_amount) from borrowing_ledger \r\n" + 
			"join society_bank_master on borrowing_ledger.bank_id=society_bank_master.id\r\n" + 
			"where society_bank_master.acc_head_code='Saving Bank Account with DCCB/SCB' or society_bank_master.acc_head_code='Current Account with DCCB/SCB'",nativeQuery=true)
	double findTotalLoanAmount();
	
	@Query(value = "select distinct(borrowing_ledger_trans.borrowing_ledger_id) from borrowing_ledger_trans \r\n" + 
			"join borrowing_ledger on borrowing_ledger_trans.borrowing_ledger_id=borrowing_ledger.id\r\n" + 
			"join society_bank_master on borrowing_ledger.bank_id=society_bank_master.id \r\n" + 
			"where society_bank_master.acc_head_code='Saving Bank Account with DCCB/SCB' or society_bank_master.acc_head_code='Current Account with DCCB/SCB'",nativeQuery=true)
	List<Long> findRemainingAmount();
	
	@Query(value=" select d from BorrowingLedgerTransaction d where d.borrowingLedger.id = :borrowingLedgerId and d.id = (select max(d.id) from  BorrowingLedgerTransaction d where d.borrowingLedger.id = :borrowingLedgerId)")
	BorrowingLedgerTransaction findByLastRecord(@Param("borrowingLedgerId")Long borrowingLedgerId);
}

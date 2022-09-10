package com.techvg.vks.accounts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.techvg.vks.accounts.domain.GeneralLedger;
@Repository
public interface GeneralLedgerRepository extends JpaRepository<GeneralLedger, Long> {

	List<GeneralLedger> findByLedgerAccountsId(Long ledgerAccountId);
	

	
	@Query(value="select distinct(general_ledger_trans.ledger_account_id) from general_ledger_trans where month(trans_date)=:transMonth and year(trans_date)=year(current_date())",nativeQuery = true)
	List<Long> findDistinctId(@Param("transMonth")Integer transMonth);
	
	@Query(value="select * from general_ledger_trans JOIN ledger_accounts ON general_ledger_trans.ledger_account_id = ledger_accounts.id where month(trans_date)=:transMonth and year(trans_date)=:year and general_ledger_trans.id=(SELECT max(id) FROM general_ledger_trans  where month(trans_date)=:transMonth and year(trans_date)=:year and general_ledger_trans.ledger_account_id=:ledgerAccountId)and general_ledger_trans.ledger_account_id=:ledgerAccountId",nativeQuery=true)
	GeneralLedger findByTrialBalanceRecord(@Param("transMonth") Integer transMonth,@Param("year")Integer year,@Param("ledgerAccountId")Long ledgerAccountId);

	@Query(value="select MAX(gl.id)AS id, la.acc_head_code, sum(gl.balance) AS balance, sum(gl.credit_amt) AS credit_amt, sum(gl.debit_amt) AS debit_amt,gl.created,gl.created_by,gl.is_deleted,gl.last_modified,gl.last_modified_by,gl.ledger_account_id,gl.remark,gl.trans_date,gl.trans_type from general_ledger_trans gl join ledger_accounts la on gl.ledger_account_id = la.id join account_type at on la.account_type_id = at.id WHERE  MONTH(gl.trans_date)=:transMonth and year(gl.trans_date)=:year AND (at.name='Asset' OR at.name='Expense') GROUP BY la.acc_head_code",nativeQuery = true)
	List<GeneralLedger> findRecordsByAssetAndExpense(@Param("transMonth")Integer transMonth,@Param("year")Integer year);

	@Query(value="SELECT * FROM general_ledger_trans WHERE general_ledger_trans.id=(SELECT MAX(id) FROM general_ledger_trans  WHERE MONTH(general_ledger_trans.trans_date)=:transMonth AND YEAR(general_ledger_trans.trans_date)=:year and general_ledger_trans.ledger_account_id=:ledgerAccountId)",nativeQuery = true)
	GeneralLedger findLastRecordOfPreviousMonth(@Param("transMonth")Integer transMonth,@Param("year")Integer year,@Param("ledgerAccountId")Long ledgerAccountId);
	
	@Query(value="select MAX(gl.id)AS id, la.acc_head_code, sum(gl.balance) AS balance, sum(gl.credit_amt) AS credit_amt, sum(gl.debit_amt) AS debit_amt,gl.created,gl.created_by,gl.is_deleted,gl.last_modified,gl.last_modified_by,gl.ledger_account_id,gl.remark,gl.trans_date,gl.trans_type from general_ledger_trans gl join ledger_accounts la on gl.ledger_account_id = la.id join account_type at on la.account_type_id = at.id WHERE  MONTH(gl.trans_date)=:transMonth and year(gl.trans_date)=:year AND (at.name='Liability' OR at.name='Income') GROUP BY la.acc_head_code",nativeQuery = true)
	List<GeneralLedger> findRecordsByLiabilityAndIncome(@Param("transMonth")Integer transMonth,@Param("year")Integer year);

}
      
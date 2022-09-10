package com.techvg.vks.deposit.repository;

import com.techvg.vks.deposit.domain.DepositLedger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Repository
public interface DepositLedgerRepository  extends JpaRepository <DepositLedger, Long>{

	@Query(value=" select d from DepositLedger d where d.accountNo = :accountNo and d.savingAccount.id = :savingAccId and d.id = (select max(d.id) from  DepositLedger d where d.accountNo = :accountNo and d.savingAccount.id = :savingAccId)")
	DepositLedger findByLastRecordForSavings(@Param("accountNo")Long accountNo, @Param("savingAccId")Long savingAccId);

	@Query(value=" select d from DepositLedger d where d.accountNo = :accountNo and d.deposit.id = :depositId and d.id = (select max(d.id) from  DepositLedger d where d.accountNo = :accountNo and d.deposit.id = :depositId )")
	DepositLedger findByLastRecordForRD(@Param("accountNo")Long accountNo, @Param("depositId")Long depositId);

	@Query(value=" select d from DepositLedger d where d.accountNo = :accountNo and d.id = (select max(d.id) from  DepositLedger d where d.accountNo = :accountNo)")
	DepositLedger findByLastRecord(@Param("accountNo")Long accountNo);

	@Query(value = "SELECT * FROM deposit_ledger WHERE saving_account_id =:id OR deposit_id=:id  ORDER BY deposit_ledger.id DESC LIMIT 5",nativeQuery = true)
	List<DepositLedger> findLastRecordsById(@Param("id") Long id);


	@Query(value="select d from DepositLedger d where d.savingAccount.id=:id or d.deposit.id=:id and date(d.depositDate) between :fromDate and :toDate")
	List<DepositLedger> findByAccountNoAndDepositDate(Long id,Date fromDate,Date toDate);

	@Query(value="select d from DepositLedger d where date(d.depositDate) between :fromDate and :toDate and d.dayBookCreated=false order by d.lastModified ASC")
	List<DepositLedger> findByDepositDate(Date fromDate,Date toDate);

	//List<DepositLedger> findByAccountNoAndDepositDateBetween(Long accountNo,Date fromDate,Date toDate);

	List<DepositLedger> findByAccountNo(Long accountNo);

	@Query(value=" select d from DepositLedger d where d.accountNo = :accountNo and d.id > :id")
	List<DepositLedger> findByAccountNoAndId(@Param("accountNo")Long accountNo, @Param("id")Long id);

	@Query(value="select min(d.balanceAmount) from DepositLedger d where d.accountNo=:accountNo and d.depositDate>=:startDate and d.depositDate<=:endDate")
	double findByBalanceAmount(@Param("accountNo")long accountNo,@Param("startDate") Date startDate,@Param("endDate") Date endDate );

	@Query(value=" select max(d.id) from DepositLedger d where d.accountNo = :accountNo ")
	Optional<Integer> findMaxIdByAccountNo(@Param("accountNo") Long accountNo);

	@Query(value = "select distinct(deposit_ledger.deposit_id) from deposit_ledger join deposits on deposit_ledger.deposit_id=deposits.id join deposit_account on deposits.deposit_account_id=deposit_account.id join deposit_type on deposit_account.deposit_type_id=deposit_type.id where deposit_type.account_type='Recurring' and deposit_ledger.deposit_id is not null and deposits.deposit_status='Open'",nativeQuery=true)
	List<Long> findRecurringAmount();
	
	List<DepositLedger> findDLListById(Long id);
}

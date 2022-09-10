package com.techvg.vks.deposit.repository;

import com.techvg.vks.deposit.domain.Deposit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface DepositRepository extends JpaRepository<Deposit, Long>{

	

	Optional<Deposit> findById(Long depositId);

	Page<Deposit> findByisDeleted(Pageable pageable, boolean deleted);
	
	@Query( value = "select d from Deposit d where d.recurringAmount=0 and d.isDeleted=false")
	Page<Deposit> findFdByisDeleted(Pageable pageable, boolean deleted);
	
	@Query(value = "select d from Deposit d where d.recurringAmount!=0 and d.isDeleted=false")
	Page<Deposit> findRdByisDeleted(Pageable pageable, boolean deleted);
	
	List<Deposit> findByDepositAccountDepositTypeAccountType(String accountType);

	@Query(value="select d from Deposit d where d.maturityDate BETWEEN :fromdate4 and :todate5")
	List<Deposit> findByDeposit(@Param("fromdate4")Date fromdate4,@Param("todate5")Date todate5);

	List<Deposit> findByReinvestmentStatus(boolean b);
	
	Optional<Deposit> findByAccountNo(Long accountNo);
	
	@Query(value = "select d from Deposit d where d.depositStatus ='Open' and d.member.id=:memberId and d.isDeleted=false")
	List<Deposit> findOpenDepositsByMemberId(@Param("memberId") Long memberId);


	Optional<Deposit> findListByMemberId(Long memberId);


	Deposit findByMemberIdAndDepositAccountDepositTypeAccountType(Long memberId, String string);

	@Query(value = "SELECT * FROM deposits d WHERE d.recurring_amount != 0",nativeQuery = true)
	List<Deposit> findListByRDAccountType();

	@Query(value = "SELECT * FROM deposits d WHERE d.recurring_amount = 0",nativeQuery = true)
	List<Deposit> findListByFDAccountType();

	@Query(value="select sum(deposit_ledger.balance_amount) from deposit_ledger \r\n" + 
			"join deposits on deposit_ledger.deposit_id=deposits.id \r\n" + 
			"where deposits.recurring_amount=0 and deposits.deposit_status='Open'",nativeQuery=true)
	double getFDAmount();
	
	@Query(value = "select distinct(deposit_ledger.saving_account_id) from deposit_ledger join saving_account on deposit_ledger.saving_account_id=saving_account.id where deposit_id is null and saving_account.status='Open'",nativeQuery=true)
	List<Long> getSavingAmount();

	@Query(value = "select count(deposits.id) from deposits join deposit_account on deposits.deposit_account_id=deposit_account.id\r\n" + 
			"join deposit_type on deposit_account.deposit_type_id=deposit_type.id\r\n" + 
			"where deposit_type.account_type='Recurring' and deposits.deposit_status='Open'",nativeQuery=true)
	double findRecurringAccountCount();
	
	@Query( value = "select count(deposits.id) from deposits join deposit_account on deposits.deposit_account_id=deposit_account.id\r\n" + 
			"join deposit_type on deposit_account.deposit_type_id=deposit_type.id\r\n" + 
			"where deposit_type.account_type='Fixed' and deposits.deposit_status='Open'",nativeQuery=true)
	double findFixedAccountCount();

	List<Deposit> findByDepositStatusAndIsDeleted(String status, boolean flag);
}

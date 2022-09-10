package com.techvg.vks.deposit.repository;

import com.techvg.vks.deposit.domain.SavingAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavingAccountRepository extends JpaRepository<SavingAccount, Long> {
	
	 Page<SavingAccount> findByStatus(String status, Pageable pageable);
	 
	 Page<SavingAccount> findByisDeleted(Pageable pageable, boolean deleted);

	Optional<SavingAccount> findByAccountNo(Long accountNo);	

	 Optional<SavingAccount> findByMemberIdAndStatus(Long memberId,String status);
	 
	 SavingAccount findByMemberId(Long memberId);
	 
	 Optional<SavingAccount> findById(Long savingAccountId);

	@Query(value = "SELECT * FROM saving_account ",nativeQuery = true)
	List<SavingAccount> findListBySavingsAccountType();
	 
	 @Query(value = "select count(saving_account.id) from saving_account where status=\"Open\" and is_deleted=false",nativeQuery=true)
	 double findSavingAccountCount();

}

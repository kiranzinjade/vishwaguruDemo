package com.techvg.vks.society.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.techvg.vks.deposit.domain.SavingAccount;
import com.techvg.vks.society.domain.DepositAccount;
import com.techvg.vks.trading.domain.SundryDebtor;

@Repository
public interface DepositAccountRepository extends JpaRepository<DepositAccount, Long> {

    @Query(value="select d from DepositAccount d where d.name=:name and d.depositType.id=:accountTypeId and d.isDeleted=false ")
	Optional<DepositAccount> findByNameAndAccountTypeAndIsDeleted(@Param ("name")String name,@Param("accountTypeId") Long accountTypeId);
	
	Optional<DepositAccount> findByName(String name);

    List<DepositAccount> findByDepositTypeAccountType(String name);

	Page<DepositAccount> findByisDeleted(Pageable pageable, boolean b);
	

	@Query(value="select d from DepositAccount d inner join d.depositType t where d.depositType.accountType='Recurring' and d.isDeleted=FALSE")
	 List<DepositAccount> findByDepositAccount();

	@Query(value="select d from DepositAccount d inner join d.depositType t where d.depositType.accountType='Fixed' and d.isDeleted=FALSE")
    List<DepositAccount> findByAccountType();

	@Query(value="select d from DepositAccount d join d.depositType t where d.depositType.accountType=:accountType and d.isDeleted=FALSE")
    List<DepositAccount> findListByAccountType(@Param("accountType") String accountType);

}

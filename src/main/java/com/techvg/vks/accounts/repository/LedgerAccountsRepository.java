package com.techvg.vks.accounts.repository;

import com.techvg.vks.accounts.domain.LedgerAccounts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LedgerAccountsRepository extends JpaRepository<LedgerAccounts, Long> , JpaSpecificationExecutor<LedgerAccounts> {

	Optional<LedgerAccounts> findByAccountNameAndIsDeleted(String accountName, boolean b);
	
	Optional<LedgerAccounts> findByAccountName(String accountName);

    LedgerAccounts findByAccHeadCode(String accountHeadCode);


    List<LedgerAccounts> findByAccountTypeName(String name);
    
    @Query(value="select c from LedgerAccounts c  where c.parentLedger.id= :ledgerAccountId")
    List<LedgerAccounts> findLedgerList(@Param("ledgerAccountId") Long ledgerAccountId);

    @Query(value=" select c from LedgerAccounts c where c.accountNo = :accountNo ")
    LedgerAccounts findByAccountNo(@Param("accountNo") Long accountNo  );


    List<LedgerAccounts> findByParentLedger_Id( Long parentId);

    List<LedgerAccounts> findByIsDeleted( boolean b);
    
    Page<LedgerAccounts> findByIsDeleted(Pageable pageable, boolean b);
    
    @Query( value = "SELECT * from ledger_accounts JOIN account_type ON ledger_accounts.account_type_id=account_type.id WHERE account_type.name=:accountType and classification='BALANCE SHEET'",nativeQuery=true)
    List<LedgerAccounts> findBalanceSheetByAccountType(@Param("accountType") String accountType);
    
    @Query( value = "select l from LedgerAccounts l where l.accountType.id=46 and l.classification='BALANCE SHEET'")
    List<LedgerAccounts> findByAccountTypeId();
    
    @Query( value = "SELECT * from ledger_accounts JOIN account_type ON ledger_accounts.account_type_id=account_type.id WHERE account_type.name=:name and classification='PROFIT AND LOSS ACCOUNT'",nativeQuery=true)
    List<LedgerAccounts> findPLByAccountType(@Param("name") String name);
    
    @Query( value = "SELECT * from ledger_accounts JOIN account_type ON ledger_accounts.account_type_id=account_type.id WHERE account_type.name=:name and level=2 and classification='TRADING ACCOUNT'",nativeQuery=true)
    List<LedgerAccounts> findTradingByAccountType(@Param("name") String name);
    
    @Query(value = "SELECT l FROM LedgerAccounts l WHERE l.accHeadCode='Purchase Returns'")
    LedgerAccounts findRecordForPurchaseReturn(); 
    
    @Query(value = "SELECT l FROM LedgerAccounts l WHERE l.accHeadCode='Sales Returns'")
    LedgerAccounts findRecordForSalesReturn();  
    
    @Query(value="SELECT l FROM LedgerAccounts l WHERE l.accHeadCode='Other Non Credit Activities'")
    LedgerAccounts findRecordForNonCreditActivities();

    @Query(value="SELECT la FROM LedgerAccounts la\n" +
            "LEFT JOIN LedgerAccounts la1 ON la.id =la1.parentLedger.id\n" +
            "WHERE la1.id IS NULL\t\n" +
            "AND la.accHeadCode LIKE %:accHeadCode%")
    List<LedgerAccounts> findLikeAccountHead(@Param("accHeadCode") String accHeadCode);
}

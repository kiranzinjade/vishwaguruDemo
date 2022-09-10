package com.techvg.vks.society.repository;

import com.techvg.vks.society.domain.SocietyBankTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface SocietyBankTransactionRepository extends JpaRepository<SocietyBankTransaction, Long>{

	Page<SocietyBankTransaction> findByisDeleted(Pageable pageable,boolean deleted);
	
	@Query(value = "SELECT * FROM society_bank_trans WHERE bank_id =:bankId ORDER BY society_bank_trans.id DESC LIMIT 1",nativeQuery = true)
	SocietyBankTransaction findLastRecordById(@Param("bankId") Long bankId);
	
	@Query(value="select s from SocietyBankTransaction s where s.societyBank.accountNumber=:accountNo and date(s.transactionDate) between :fromDate and :toDate order by s.id")
	List<SocietyBankTransaction> findByAccountNo(@Param("accountNo") Long accountNo,Date fromDate,Date toDate);

    SocietyBankTransaction findTopByOrderByIdDesc();
        
    @Query(value= "SELECT  * FROM  society_bank_master INNER JOIN society_bank_trans ON society_bank_master.id=society_bank_trans.bank_id  WHERE society_bank_master.account_no=:accountNo AND society_bank_trans.transaction_date < :fromDate ORDER BY  society_bank_trans.id DESC LIMIT 1 ; ",nativeQuery = true)
    SocietyBankTransaction findLastRecord(@Param("accountNo") Long accountNo,Date fromDate);
    
 
}

package com.techvg.vks.accounts.repository;

import com.techvg.vks.accounts.domain.AccountMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountMappingRepository extends JpaRepository<AccountMapping, Long> {

    AccountMapping getAccountMappingByMappingName(String name);

    AccountMapping getAccountMappingByMappingNameAndMappingType(String name, String type);
    
   
	AccountMapping findByLedgerAccHeadCode(String ledgerAccHeadCode);
	
    @Query(value="select s from AccountMapping s where s.ledgerAccHeadCode=:ledgerAccHeadCode and s.mappingName=:mappingName")
	AccountMapping findByMappingNameAndLedgerAccHeadCode(@Param ("ledgerAccHeadCode")String ledgerAccHeadCode,@Param ("mappingName") String mappingName);


    
}

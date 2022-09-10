package com.techvg.vks.accounts.service;

import com.techvg.vks.accounts.domain.AccountMapping;
import com.techvg.vks.accounts.model.AccountMappingDto;
import com.techvg.vks.accounts.model.AccountMappingPageList;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccountMappingService {
    AccountMappingDto addAccountMapping(AccountMappingDto accountMappingDto);
    AccountMappingDto updateAccountMapping(Long id, AccountMappingDto accountMappingDto);
    AccountMapping getAccountMappingByName(String mappingName);
    AccountMapping getAccountMappingByNameAndType(String mappingName, String mappingType);
    
    @Query(value="SELECT DISTINCT a.mappingType FROM AccountMapping a where a.mappingType=mappingType")
    List<AccountMappingDto> listAccountMapping(String mappingType);
    
	AccountMappingPageList listAccountMapping(Pageable pageable);
}

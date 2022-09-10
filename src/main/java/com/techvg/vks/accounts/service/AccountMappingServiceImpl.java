package com.techvg.vks.accounts.service;

import com.techvg.vks.accounts.domain.AccountMapping;
import com.techvg.vks.accounts.domain.AccountType;
import com.techvg.vks.accounts.domain.LedgerAccounts;
import com.techvg.vks.accounts.mapper.AccountMappingMapper;
import com.techvg.vks.accounts.model.AccountMappingDto;
import com.techvg.vks.accounts.model.AccountMappingPageList;
import com.techvg.vks.accounts.model.AccountTypePageList;
import com.techvg.vks.accounts.repository.AccountMappingRepository;
import com.techvg.vks.accounts.repository.LedgerAccountsRepository;
import com.techvg.vks.exceptions.AlreadyExitsException;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.membership.domain.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountMappingServiceImpl implements AccountMappingService {

    private final AccountMappingRepository accMappingRepository;
    private final AccountMappingMapper accMappingMapper;
    private final LedgerAccountsRepository ledgerAccountsRepository;

    @Override
    public AccountMappingDto addAccountMapping(AccountMappingDto accountMappingDto) {
    	
        AccountMapping accountMapping = accMappingMapper.toDomain(accountMappingDto);
        LedgerAccounts account = ledgerAccountsRepository.findById(accountMappingDto.getLedgerAccId()).orElseThrow(
                () -> new NotFoundException("No Ledger Account found for Id : " +accountMappingDto.getLedgerAccId()));
        accountMapping.setLedgerAccount(account);
        return accMappingMapper.toDto(accMappingRepository.save(accountMapping));
    
    }
    @Override
    public AccountMappingDto updateAccountMapping(Long id, AccountMappingDto accountMappingDto) {
       AccountMapping accountMapping = accMappingMapper.toDomain(accountMappingDto);
        
        LedgerAccounts account = ledgerAccountsRepository.findById(accountMappingDto.getLedgerAccId()).orElseThrow(
                () -> new NotFoundException("No Ledger Account found for Id : " +accountMappingDto.getLedgerAccId()));
        
        System.out.println("HEadCode---->"+accountMappingDto.getLedgerAccHeadCode());
        System.out.println("Name----->"+accountMappingDto.getMappingName());
        AccountMapping accountMapping1=accMappingRepository.findByMappingNameAndLedgerAccHeadCode(accountMappingDto.getLedgerAccHeadCode(),accountMappingDto.getMappingName());       
        System.out.println("AccountMapping1---"+accountMapping1);
        if(accountMapping1!=null) {
    	   System.out.println("iffffffloop");
        	throw new AlreadyExitsException("Mapping is already exists");
        }
       
        accountMapping.setLedgerAccount(account);
        accountMapping.setId(id);
        return accMappingMapper.toDto(accMappingRepository.save(accountMapping));
    }
    

    @Override
    public AccountMapping getAccountMappingByName(String mappingName) {
        return accMappingRepository.getAccountMappingByMappingName(mappingName);
    }

    @Override
    public AccountMapping getAccountMappingByNameAndType(String mappingName, String mappingType) {
        return accMappingRepository.getAccountMappingByMappingNameAndMappingType(mappingName, mappingType);
    }

    @Override
    public List<AccountMappingDto> listAccountMapping(String mappingType) {

        return accMappingMapper.toDtoList(accMappingRepository.findAll());
    }

	@Override
	public AccountMappingPageList listAccountMapping(Pageable pageable) {
		Page<AccountMapping> accountMappingPage;
		accountMappingPage = accMappingRepository.findAll(pageable);

        return new AccountMappingPageList(accountMappingPage
                .getContent()
                .stream()
                .map(accMappingMapper::toDto)
                .collect(Collectors.toList()),
                PageRequest
                        .of(accountMappingPage.getPageable().getPageNumber(),
                        		accountMappingPage.getPageable().getPageSize()),
                        accountMappingPage.getTotalElements());

	}

}

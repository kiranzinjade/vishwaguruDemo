package com.techvg.vks.accounts.service;

import com.techvg.vks.accounts.domain.AccountType;
import com.techvg.vks.accounts.mapper.AccountTypeMapper;
import com.techvg.vks.accounts.model.AccountTypeDto;
import com.techvg.vks.accounts.model.AccountTypePageList;
import com.techvg.vks.accounts.repository.AccountTypeRepository;
import com.techvg.vks.exceptions.AlreadyExitsException;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.society.domain.SocietyBank;
import com.techvg.vks.society.mapper.SocietyBankMapper;
import com.techvg.vks.society.model.SocietyBankPageList;
import com.techvg.vks.society.repository.SocietyBankRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountTypeServiceImpl implements AccountTypeService {

    private final AccountTypeRepository accountTypeRepository;
    private final AccountTypeMapper accountTypeMapper;

    @Override
    public AccountTypeDto addAccountType(AccountTypeDto accountTypeDto) {
        Optional<AccountType> accountTypeObjOptional = accountTypeRepository.findByNameAndIsDeleted(accountTypeDto.getName(), false);
        if (accountTypeObjOptional.isPresent()){
            throw new AlreadyExitsException("Account Type already exists : " + accountTypeDto.getName());
        }
        else {
            return accountTypeMapper.toAccountTypeDto(accountTypeRepository.save(accountTypeMapper.toAccountType(accountTypeDto)));
        }
    }

    @Override
    public AccountTypeDto updateAccountType(Long accountTypeId, AccountTypeDto accountTypeDto, Authentication authentication) {
        accountTypeRepository.findById(accountTypeId).orElseThrow(
                () -> new NotFoundException("No Account Type  found for id : " +accountTypeId));
        AccountType accountType = accountTypeMapper.toAccountType(accountTypeDto);
        accountType.setId(accountTypeId);
        return accountTypeMapper.toAccountTypeDto(accountTypeRepository.save(accountType));
    }

    @Override
    public AccountTypeDto getAccountTypeById(Long accountTypeId) {
        AccountType accountType = accountTypeRepository.findById(accountTypeId).orElseThrow(
                () -> new NotFoundException("No Account Type found for Id : " +accountTypeId));

        return accountTypeMapper.toAccountTypeDto(accountType);
    }

    @Override
    public AccountTypePageList listAccountTypes(Pageable pageable) {
        Page<AccountType> accountTypePage;
        accountTypePage = accountTypeRepository.findAll(pageable);

        return new AccountTypePageList(accountTypePage
                .getContent()
                .stream()
                .map(accountTypeMapper::toAccountTypeDto)
                .collect(Collectors.toList()),
                PageRequest
                        .of(accountTypePage.getPageable().getPageNumber(),
                                accountTypePage.getPageable().getPageSize()),
                accountTypePage.getTotalElements());
    }

    @Override
    public AccountTypeDto deleteAccountTypeById(Long accountTypeId) {
        AccountType accountType = accountTypeRepository.findById(accountTypeId).orElseThrow(
                () -> new NotFoundException("No Account Type found for Id : " +accountTypeId));
        if (accountType != null) {
            accountType.setIsDeleted(true);
            accountTypeRepository.save(accountType);
        }
        return accountTypeMapper.toAccountTypeDto(accountType);
    }

	@Override
	public List<AccountTypeDto> listAllAccountType() {
		// TODO Auto-generated method stub
		return  accountTypeMapper.domainToDtoList(accountTypeRepository.findByisDeleted(false));
	}
}

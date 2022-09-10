package com.techvg.vks.accounts.service;

import com.techvg.vks.accounts.model.AccountTypeDto;
import com.techvg.vks.accounts.model.AccountTypePageList;
import com.techvg.vks.society.model.MeasuringUnitDto;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface AccountTypeService {

    AccountTypeDto addAccountType(AccountTypeDto accountTypeDto);
    AccountTypeDto updateAccountType(Long accountTypeId, AccountTypeDto accountTypeDto, Authentication authentication);
    AccountTypeDto getAccountTypeById(Long id);
    AccountTypePageList listAccountTypes(Pageable pageable);
    AccountTypeDto deleteAccountTypeById(Long id);
    List<AccountTypeDto> listAllAccountType();
}

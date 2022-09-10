package com.techvg.vks.society.service;

import com.techvg.vks.society.model.DepositAccountDto;
import com.techvg.vks.society.model.DepositAccountPageList;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface DepositAccountService {

    DepositAccountDto addDepositAccount(DepositAccountDto depositAccountDto, Authentication authentication);
    DepositAccountDto updateDepositAccount(Long id, DepositAccountDto depositAccountDto);
    DepositAccountDto getDepositAccountById(Long id);
    DepositAccountDto deleteDepositAccountById(Long id);
    DepositAccountPageList listDepositAccounts(Pageable pageable);

	List<DepositAccountDto> listSchemes();

    List<DepositAccountDto> listAllSchemes();


}

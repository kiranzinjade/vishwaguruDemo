package com.techvg.vks.accounts.controller;

import com.techvg.vks.accounts.model.AccountTypeDto;
import com.techvg.vks.accounts.model.AccountTypePageList;
import com.techvg.vks.accounts.service.AccountTypeService;
import com.techvg.vks.society.model.MeasuringUnitDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/accType")
public class AccountTypeController {

    private final AccountTypeService accountTypeService;

    @GetMapping
    public ResponseEntity<AccountTypePageList> listAllAccountTypes(Pageable pageable) {
        AccountTypePageList accountTypePageList = accountTypeService.listAccountTypes(pageable);
        return new ResponseEntity<>(accountTypePageList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AccountTypeDto> addAccountType(@Validated @RequestBody AccountTypeDto accountTypeDto) {
        return new ResponseEntity<>( accountTypeService.addAccountType(accountTypeDto), HttpStatus.CREATED);
    }

    @PutMapping(path = { "/{accountTypeId}" })
    public ResponseEntity<AccountTypeDto> updateAccountType(@PathVariable("accountTypeId") Long accountTypeId,
                                                     @Validated @RequestBody AccountTypeDto accountTypeDto, Authentication authentication) {
        return new ResponseEntity<>(accountTypeService.updateAccountType(accountTypeId, accountTypeDto, authentication), HttpStatus.OK);
    }

    @GetMapping({ "/{accountTypeId}" })
    public ResponseEntity<AccountTypeDto> getAccountType(@PathVariable("accountTypeId") Long accountTypeId) {
        return new ResponseEntity<>(accountTypeService.getAccountTypeById(accountTypeId), HttpStatus.OK);
    }

    @DeleteMapping(path = { "/{accountTypeId}" }) // deleting data
    public ResponseEntity<AccountTypeDto>  deleteAccountTypeById(@PathVariable("accountTypeId") Long accountTypeId) {
        return new ResponseEntity<>(accountTypeService.deleteAccountTypeById(accountTypeId), HttpStatus.OK);
    }
    
    @GetMapping( "/list")
  	public ResponseEntity<List<AccountTypeDto>> listAllAccountType() {
  	        return new ResponseEntity<>(accountTypeService.listAllAccountType(), HttpStatus.OK);
  	}
}

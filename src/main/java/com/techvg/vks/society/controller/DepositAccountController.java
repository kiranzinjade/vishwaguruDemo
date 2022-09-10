package com.techvg.vks.society.controller;

import com.techvg.vks.society.model.DepositAccountDto;
import com.techvg.vks.society.model.DepositAccountPageList;
import com.techvg.vks.society.model.SocietyBankDto;
import com.techvg.vks.society.service.DepositAccountService;
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
@RequestMapping("/api/depositaccount")
public class DepositAccountController {
    private final DepositAccountService depositAccountService;

    @PostMapping
    public ResponseEntity<DepositAccountDto> addDepositAccount(@Validated  @RequestBody DepositAccountDto depositAccountDto,  Authentication authentication) {
        return new ResponseEntity<>(depositAccountService.addDepositAccount(depositAccountDto,authentication) , HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<DepositAccountPageList> listAllDepositAccounts(Pageable pageable) {
        DepositAccountPageList depositAccountPageList = depositAccountService.listDepositAccounts(pageable);
        return new ResponseEntity<>(depositAccountPageList, HttpStatus.OK);
    }

    @DeleteMapping(path = { "/{depositAccId}" }) // deleting data
    public ResponseEntity<DepositAccountDto> deleteDepositAccountById(@PathVariable("depositAccId") Long depositAccId) {
        return new ResponseEntity<>(depositAccountService.deleteDepositAccountById(depositAccId), HttpStatus.OK);
    }
    @PutMapping(path = { "/{depositAccId}" })
    public ResponseEntity<DepositAccountDto> updateDepositAccount(@PathVariable("depositAccId") Long depositAccId, @Validated @RequestBody DepositAccountDto depositAccountDto) {
        return new ResponseEntity<>(depositAccountService.updateDepositAccount(depositAccId, depositAccountDto), HttpStatus.OK);
    }

    @GetMapping({ "/{depositAccId}" })
    public ResponseEntity<DepositAccountDto> getDepositAccount(@PathVariable("depositAccId") Long depositAccId) {
        return new ResponseEntity<>(depositAccountService.getDepositAccountById(depositAccId), HttpStatus.OK);
    }


    @GetMapping(path = { "/recurringschemeslist" })
	public ResponseEntity<List<DepositAccountDto>> listSchemes() {
		return new ResponseEntity<>(depositAccountService.listSchemes(), HttpStatus.OK);
	}

    


    @GetMapping( "/list")
      public ResponseEntity<List<DepositAccountDto>> listAllLedgerAccount() {
              return new ResponseEntity<>(depositAccountService.listAllSchemes(), HttpStatus.OK);
      }

}

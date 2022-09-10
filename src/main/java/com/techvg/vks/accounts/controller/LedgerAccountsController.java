package com.techvg.vks.accounts.controller;

import com.techvg.vks.accounts.model.LedgerAccountSearchCriteria;
import com.techvg.vks.accounts.model.LedgerAccountsDto;
import com.techvg.vks.accounts.model.LedgerAccountsPageList;
import com.techvg.vks.accounts.service.LedgerAccountsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/ledgerAcc")
public class LedgerAccountsController {
    private final LedgerAccountsService ledgerAccountsService;

    @GetMapping
    public ResponseEntity<LedgerAccountsPageList> listAllLedgerAccounts(Pageable pageable) {
        return new ResponseEntity<>(ledgerAccountsService.listLedgerAccounts(pageable), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<LedgerAccountsDto> addLedgerAccounts(@Validated @RequestBody LedgerAccountsDto ledgerAccountsDto) {
        return new ResponseEntity<>( ledgerAccountsService.addLedgerAccounts(ledgerAccountsDto), HttpStatus.CREATED);
    }

    @PutMapping(path = { "/{ledgerAccountsId}" })
    public ResponseEntity<LedgerAccountsDto> updateLedgerAccounts(@PathVariable("ledgerAccountsId") Long ledgerAccountsId,
                                                     @Validated @RequestBody LedgerAccountsDto ledgerAccountsDto, Authentication authentication) {
        return new ResponseEntity<>(ledgerAccountsService.updateLedgerAccounts(ledgerAccountsId, ledgerAccountsDto, authentication), HttpStatus.OK);
    }

    @GetMapping({ "/{ledgerAccountsId}" })
    public ResponseEntity<LedgerAccountsDto> getLedgerAccounts(@PathVariable("ledgerAccountsId") Long ledgerAccountsId) {
        return new ResponseEntity<>(ledgerAccountsService.getLedgerAccountsById(ledgerAccountsId), HttpStatus.OK);
    }

    @DeleteMapping(path = { "/{ledgerAccountsId}" }) // deleting data
    public ResponseEntity<LedgerAccountsDto>  deleteLedgerAccountsById(@PathVariable("ledgerAccountsId") Long ledgerAccountsId) {
        return new ResponseEntity<>(ledgerAccountsService.deleteLedgerAccountsById(ledgerAccountsId), HttpStatus.OK);
    }
    
    @GetMapping( "/list")
  	public ResponseEntity<List<LedgerAccountsDto>> listAllLedgerAccount() {
  	        return new ResponseEntity<>(ledgerAccountsService.listAllLedgerAccount(), HttpStatus.OK);
  	}

    @GetMapping( "/listchildren/{accheadcode}")
    public ResponseEntity<List<LedgerAccountsDto>> listChildLedgerAccount(@PathVariable("accheadcode") String accheadcode) {
        return new ResponseEntity<>(ledgerAccountsService.listLedgerAccountByParent(accheadcode), HttpStatus.OK);
    }

    @GetMapping( "/societyexpensetypes")
    public ResponseEntity<List<LedgerAccountsDto>> listSocietyExpenseAccounts() {
        return new ResponseEntity<>(ledgerAccountsService.listSocietyExpenseAccounts(), HttpStatus.OK);
    }

    @GetMapping( "/societyexpenseprovisiontypes")
    public ResponseEntity<List<LedgerAccountsDto>> listSocietyExpenseProvisionAccounts() {
        return new ResponseEntity<>(ledgerAccountsService.listSocietyExpenseProvisionAccounts(), HttpStatus.OK);
    }

    @GetMapping( "/tradingexpensetypes")
    public ResponseEntity<List<LedgerAccountsDto>> listTradingExpenseAccounts() {
        return new ResponseEntity<>(ledgerAccountsService.listTradingExpenseAccounts(), HttpStatus.OK);
    }

    @PostMapping( "/search")
    public ResponseEntity<List<LedgerAccountsDto>> ledgerAccList(@RequestBody LedgerAccountSearchCriteria criteria) {
        return new ResponseEntity<>(ledgerAccountsService.listLedgerAccounts(criteria), HttpStatus.OK);
    }

}

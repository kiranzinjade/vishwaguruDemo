package com.techvg.vks.accounts.controller;

import com.techvg.vks.accounts.model.AccountMappingDto;
import com.techvg.vks.accounts.model.AccountMappingPageList;
import com.techvg.vks.accounts.model.VoucherTypePageList;
import com.techvg.vks.accounts.service.AccountMappingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/accmapping")
public class AccountMappingController {

    private final AccountMappingService accountMappingService;
    
    @GetMapping
    public ResponseEntity<AccountMappingPageList> listAllAccountMapping(Pageable pageable) {
    	AccountMappingPageList accountMappingPageList = accountMappingService.listAccountMapping(pageable);
        return new ResponseEntity<>(accountMappingPageList, HttpStatus.OK);
    }
   

    @PostMapping
    public ResponseEntity<AccountMappingDto> addAccountMapping(@Validated @RequestBody AccountMappingDto accountMappingDto) {
        return new ResponseEntity<>( accountMappingService.addAccountMapping(accountMappingDto), HttpStatus.CREATED);
    }

    @PutMapping(path = { "/{accMappingId}" })
    public ResponseEntity<AccountMappingDto> updateAccountMapping(@PathVariable("accMappingId") Long accMappingId,
                                                               @Validated @RequestBody AccountMappingDto accountMappingDto) {
        return new ResponseEntity<>(accountMappingService.updateAccountMapping(accMappingId, accountMappingDto), HttpStatus.OK);
    }

    @GetMapping(path = { "/{type}" })
    public ResponseEntity<List<AccountMappingDto>> listAllAccountMappings(@PathVariable("type") String type) {
        return new ResponseEntity<>(accountMappingService.listAccountMapping(type), HttpStatus.OK);
    }
}

package com.techvg.vks.deposit.controller;

import com.techvg.vks.deposit.domain.DepositLedger;
import com.techvg.vks.deposit.service.PrintPassbookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/printpassbook")
public class PrintPassbookController {
    private final PrintPassbookService printPassbookService;

    @GetMapping({ "/{accountNo}" })
    public ResponseEntity<List<DepositLedger>> getAccountTransactions(@PathVariable("accountNo") Long accountNo) {
        return new ResponseEntity<>(printPassbookService.listPassbookEntries(accountNo), HttpStatus.OK);
    }

    @GetMapping({ "/{accountNo}/{transId}" })
    public ResponseEntity<List<DepositLedger>> getAccountTransactionsById(@PathVariable("accountNo") Long accountNo, @PathVariable("transId") Long transId) {
        return new ResponseEntity<>(printPassbookService.listPassbookEntries(accountNo, transId), HttpStatus.OK);
    }
}

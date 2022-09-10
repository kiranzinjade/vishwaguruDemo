package com.techvg.vks.accounts.controller;

import com.techvg.vks.accounts.model.CashBookDto;
import com.techvg.vks.accounts.model.CashBookPageList;
import com.techvg.vks.accounts.service.CashBookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/api/cashbook")
public class CashBookController {
    private final CashBookService cashBookService;

    @GetMapping
    public ResponseEntity<CashBookPageList> listAllCashbookEntries(Pageable pageable) {
        CashBookPageList cashBookPageList = cashBookService.listCashbookEntries(pageable);
        return new ResponseEntity<>(cashBookPageList, HttpStatus.OK);
    }

    @PostMapping(path = { "/credit" })
	public ResponseEntity<CashBookDto> creditAmount(
			@Validated @RequestBody CashBookDto cashBookDto, Authentication authentication) {
		log.debug("REST request to save credits : {}", cashBookDto);
		return new ResponseEntity<>(
				cashBookService.creditAmount(cashBookDto, authentication), HttpStatus.OK);
	}

	@PostMapping(path = { "/debit" })
	public ResponseEntity<CashBookDto> debitAmount(
			@Validated @RequestBody CashBookDto cashBookDto, Authentication authentication) {
		log.debug("REST request to save debits : {}", cashBookDto);
		return new ResponseEntity<>(
				cashBookService.debitAmount(cashBookDto, authentication), HttpStatus.OK);
	}
    
    @PutMapping(path = { "/{cashbookId}" })
    public ResponseEntity<CashBookDto> updateCashbookEntry(@PathVariable("cashbookId") Long cashbookId,
                                                            @Validated @RequestBody CashBookDto cashBookDto, Authentication authentication) {
        return new ResponseEntity<>(cashBookService.updateCashbookEntry(cashbookId, cashBookDto, authentication), HttpStatus.OK);
    }

    @GetMapping({ "/{cashbookId}" })
    public ResponseEntity<CashBookDto> getCashbookEntry(@PathVariable("cashbookId") Long cashbookId) {
        return new ResponseEntity<>(cashBookService.getCashbookEntryById(cashbookId), HttpStatus.OK);
    }

    @DeleteMapping(path = { "/{cashbookId}" }) // deleting data
    public ResponseEntity<CashBookDto>  deleteCashbookEntryById(@PathVariable("cashbookId") Long cashbookId) {
        return new ResponseEntity<>(cashBookService.deleteCashbookEntryById(cashbookId), HttpStatus.OK);
    }
}

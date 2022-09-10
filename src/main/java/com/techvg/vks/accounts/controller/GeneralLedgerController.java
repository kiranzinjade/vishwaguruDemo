package com.techvg.vks.accounts.controller;

import com.techvg.vks.accounts.model.GeneralLedgerDto;
import com.techvg.vks.accounts.model.GeneralLedgerPageList;
import com.techvg.vks.accounts.service.GeneralLedgerService;
import com.techvg.vks.common.DateConverter;
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
@RequestMapping("/api/generalledger")
public class GeneralLedgerController {

    private final GeneralLedgerService generalLedgerService;

    @GetMapping
    public ResponseEntity<GeneralLedgerPageList> listAllGeneralLedgerTrans(Pageable pageable) {
        return new ResponseEntity<>(generalLedgerService.listGeneralLedgerTrans(pageable), HttpStatus.OK);
    }

    @GetMapping(path = { "/posting/{transDate}" })
    public ResponseEntity<Boolean> postGeneralLedgerTrans(@PathVariable("transDate") String transDate) {
        return new ResponseEntity<>(
                generalLedgerService.postGeneralLedger(DateConverter.getDate(transDate)), HttpStatus.CREATED);
    }

    @PutMapping(path = { "/update/{generalLedgerTransId}" })
    public ResponseEntity<GeneralLedgerDto> updateGeneralLedgerTrans(@PathVariable("generalLedgerTransId") Long generalLedgerTransId,
                                                                 @Validated @RequestBody GeneralLedgerDto generalLedgerDto, Authentication authentication) {
        return new ResponseEntity<>(generalLedgerService.updateGeneralLedgerTrans(generalLedgerTransId, generalLedgerDto, authentication), HttpStatus.OK);
    }

    @GetMapping({ "/{generalLedgerTransId}" })
    public ResponseEntity<GeneralLedgerDto> getGeneralLedgerTrans(@PathVariable("generalLedgerTransId") Long generalLedgerTransId) {
        return new ResponseEntity<>(generalLedgerService.getGeneralLedgerTransById(generalLedgerTransId), HttpStatus.OK);
    }

    @DeleteMapping(path = { "/{generalLedgerTransId}" }) // deleting data
    public ResponseEntity<GeneralLedgerDto>  deleteGeneralLedgerTransById(@PathVariable("generalLedgerTransId") Long generalLedgerTransId) {
        return new ResponseEntity<>(generalLedgerService.deleteGeneralLedgerTransById(generalLedgerTransId), HttpStatus.OK);
    }
}

package com.techvg.vks.deposit.controller;

import com.techvg.vks.deposit.model.DepositAccrualDto;
import com.techvg.vks.deposit.model.DepositLedgerDto;
import com.techvg.vks.deposit.service.DepositAccrualService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/api/depositaccruals")
public class DepositAccrualController {
    private final DepositAccrualService service;

    @PostMapping
    public ResponseEntity<DepositAccrualDto> accrualPosting(
            @Validated @RequestBody DepositAccrualDto accrualDto, Authentication authentication) {
        log.debug("REST request to save deposits : {}", accrualDto);
        return new ResponseEntity<>(
                service.accrualInterestPosting(accrualDto), HttpStatus.OK);
    }

    @GetMapping( { "/{id}" })
    public ResponseEntity<List<DepositAccrualDto>> listAccruals(@PathVariable("id") Long id) {
        return new ResponseEntity<>(service.getAccrualsForDeposit(id), HttpStatus.OK);
    }
}

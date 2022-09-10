package com.techvg.vks.loan.controller;

import com.techvg.vks.loan.model.CropLoanDemandDto;
import com.techvg.vks.loan.service.CropLoanDemandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/croploandemand")
public class CropLoanDemandController {
    private final CropLoanDemandService service;

    @GetMapping
    public ResponseEntity<List<CropLoanDemandDto>> list() {
        Calendar cal = Calendar.getInstance();
        return new ResponseEntity<>(service.getList(cal.get(Calendar.YEAR)), HttpStatus.OK);
    }
 
    @GetMapping({ "/listByStatus/{kmpStatus}/{loanRegStatus}/{year}" })
    public ResponseEntity<List<CropLoanDemandDto>> listByStatus(@PathVariable("kmpStatus") boolean kmpStatus, @PathVariable("loanRegStatus") boolean loanRegStatus) {
        Calendar cal = Calendar.getInstance();
        return new ResponseEntity<>(service.getListByStatus(kmpStatus, loanRegStatus, cal.get(Calendar.YEAR)), HttpStatus.OK);
    }

    @PutMapping({ "/approvekmp/{cropLoanDemandId}" })
    public ResponseEntity<CropLoanDemandDto> approveKmp(@PathVariable("cropLoanDemandId") long cropLoanDemandId) {

        return new ResponseEntity<>(service.approveKmp(cropLoanDemandId), HttpStatus.OK);
    }

    @GetMapping({ "/listForLoanProduct" })
    public ResponseEntity<List<CropLoanDemandDto>> listForLoanProduct() {

        return new ResponseEntity<>(service.listForLoanProduct(), HttpStatus.OK);
    }

    @GetMapping({ "/listKMP" })
    public ResponseEntity<List<CropLoanDemandDto>> listKMP() {

        return new ResponseEntity<>(service.listForApproval(), HttpStatus.OK);
    }

    @GetMapping({ "/listForLoanDemand" })
    public ResponseEntity<List<CropLoanDemandDto>> listForLoanDemand() {

        return new ResponseEntity<>(service.listForLoanDemand(), HttpStatus.OK);
    }
}

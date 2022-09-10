package com.techvg.vks.loan.controller;

import com.techvg.vks.loan.model.KMPDto;
import com.techvg.vks.loan.model.NPADataDto;
import com.techvg.vks.loan.model.ShortTermSubsidyPageList;
import com.techvg.vks.loan.service.NPADataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/npadata")
public class NPADataController {
    private final NPADataService service;

    @PostMapping
    public ResponseEntity<NPADataDto> addNpaData(@RequestBody NPADataDto npaDataDto) {
        log.debug("REST request to save loan Demand details : {}", npaDataDto);
        return new ResponseEntity<>( service.storeNPAData(npaDataDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<NPADataDto>> listAll() {
        log.debug("REST request to get a all records");
        return new ResponseEntity<>(service.getLatestNPAData(), HttpStatus.OK);
    }

    @GetMapping(path = { "/generate" })
    public ResponseEntity<List<NPADataDto>> generate() {
        log.debug("REST request to get a all records");
        return new ResponseEntity<>(service.generateNPAData(), HttpStatus.OK);
    }
}

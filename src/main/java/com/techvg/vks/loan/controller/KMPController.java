package com.techvg.vks.loan.controller;

import com.techvg.vks.loan.model.KMPDto;
import com.techvg.vks.loan.service.KMPService;
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
@RequestMapping("/api/kmp")
public class KMPController {
    private final KMPService service;

    @PostMapping
    public ResponseEntity<KMPDto> addKMPCrop(@RequestBody KMPDto kmpDto) {
        log.debug("REST request to save loan Demand details : {}", kmpDto);
        return new ResponseEntity<>( service.addKMP(kmpDto), HttpStatus.CREATED);
    }

    @GetMapping({ "/approvedYear/{status}/{year}" })
    public ResponseEntity<KMPDto> listByApprovedYear(@PathVariable("status") boolean status, @PathVariable("year") int year) {
        return new ResponseEntity<>(service.getByYearAndApprovalStatus(year, status), HttpStatus.OK);
    }

    @GetMapping({ "/generatedYear/{status}/{year}" })
    public ResponseEntity<KMPDto> listByGeneratedYear(@PathVariable("status") boolean status, @PathVariable("year") int year) {
        return new ResponseEntity<>(service.getByYearAndGenerationStatus(year, status), HttpStatus.OK);
    }

    @GetMapping({ "/byId/{kmpId}" })
    public ResponseEntity<KMPDto> byKMPId(@PathVariable("kmpId") Long kmpId) {
        return new ResponseEntity<>(service.getByKMPId(kmpId), HttpStatus.OK);
    }

    @GetMapping({ "/byApprovalStatus/{status}" })
    public ResponseEntity<List<KMPDto>> byKMPId(@PathVariable("status") boolean status) {
        return new ResponseEntity<>(service.getByApprovalStatus(status), HttpStatus.OK);
    }
}

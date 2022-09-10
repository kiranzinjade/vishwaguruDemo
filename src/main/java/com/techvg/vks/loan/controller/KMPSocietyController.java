package com.techvg.vks.loan.controller;

import com.techvg.vks.loan.model.KMPSocietyDto;
import com.techvg.vks.loan.service.KMPSocietyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/kmpsociety")
public class KMPSocietyController {
    private final KMPSocietyService service;

    @PostMapping
    public ResponseEntity<KMPSocietyDto> addSocietyKmp(@RequestBody KMPSocietyDto kmpSocietyDto) {
        log.debug("REST request to save loan Demand details : {}", kmpSocietyDto);
        return new ResponseEntity<>( service.addSocietyKmp(kmpSocietyDto), HttpStatus.CREATED);
    }

    @GetMapping({ "/societykmp/{year}" })
    public ResponseEntity<KMPSocietyDto> getSocietyKmp(@PathVariable("year") int year) {
        return new ResponseEntity<>(service.getKMPByYear(year), HttpStatus.OK);
    }
}

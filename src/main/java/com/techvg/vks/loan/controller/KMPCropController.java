package com.techvg.vks.loan.controller;

import com.techvg.vks.loan.model.KMPCropDto;
import com.techvg.vks.loan.service.KMPCropService;
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
@RequestMapping("/api/kmpcrop")
public class KMPCropController {
    private final KMPCropService service;

    @GetMapping({ "/byYear/{year}" })
    public ResponseEntity<List<KMPCropDto>> listByYear(@PathVariable("year") int year) {
        return new ResponseEntity<>(service.cropsForKMPByYear(year), HttpStatus.OK);
    }

    @GetMapping({ "/byId/{kmpId}" })
    public ResponseEntity<List<KMPCropDto>> listByKmpId(@PathVariable("kmpId") Long kmpId) {
        return new ResponseEntity<>(service.cropsForKMPById(kmpId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<KMPCropDto> addKMPCrop(@RequestBody KMPCropDto kmpCropDto) {
        log.debug("REST request to save loan Demand details : {}", kmpCropDto);
        return new ResponseEntity<>( service.addKMPCrop(kmpCropDto), HttpStatus.CREATED);
    }
}

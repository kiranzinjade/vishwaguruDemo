package com.techvg.vks.loan.controller;

import com.techvg.vks.loan.model.KMPDto;
import com.techvg.vks.loan.model.KMPMemberDto;
import com.techvg.vks.loan.service.KMPMemberService;
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
@RequestMapping("/api/kmpmember")
public class KMPMemberController {
    private final KMPMemberService service;

    @PostMapping
    public ResponseEntity<KMPMemberDto> addMemberKmp(@RequestBody KMPMemberDto kmpMemberDto) {
        log.debug("REST request to save loan Demand details : {}", kmpMemberDto);
        return new ResponseEntity<>( service.addKmpMember(kmpMemberDto), HttpStatus.CREATED);
    }

    @GetMapping({ "/memberkmp/{memberId}/{year}" })
    public ResponseEntity<KMPMemberDto> getMemberKmp(@PathVariable("memberId") Long memberId, @PathVariable("year") int year) {
        return new ResponseEntity<>(service.getMemberKMPForYear(memberId, year), HttpStatus.OK);
    }

    @GetMapping({ "/allmemberkmp/{year}" })
    public ResponseEntity<List<KMPMemberDto>> getAllMemberKmp(@PathVariable("year") int year) {
        return new ResponseEntity<>(service.getKMPsForYear(year), HttpStatus.OK);
    }

}

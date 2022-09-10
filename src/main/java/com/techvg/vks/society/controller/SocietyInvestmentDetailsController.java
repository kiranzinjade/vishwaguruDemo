package com.techvg.vks.society.controller;

import com.techvg.vks.society.model.SocietyInvestmentDetailsDto;
import com.techvg.vks.society.service.SocietyInvestmentDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/societyinvestmentdetails")
public class SocietyInvestmentDetailsController {
    private final SocietyInvestmentDetailsService socInvestDetailsService;

    @GetMapping
    public ResponseEntity<List<SocietyInvestmentDetailsDto>> listAllSocietyInvestments() {
        return new ResponseEntity<>(socInvestDetailsService.listSocietyInvestmentDetails(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SocietyInvestmentDetailsDto> addSocietyInvestmentDetails(@Validated @RequestBody SocietyInvestmentDetailsDto societyInvestmentDetailsDto, Authentication authentication) {
        return new ResponseEntity<>(socInvestDetailsService.addSocietyInvestmentDetails(societyInvestmentDetailsDto, authentication), HttpStatus.CREATED);
    }

    @PutMapping(path = { "/{id}" })
    public ResponseEntity<SocietyInvestmentDetailsDto> updateSocietyInvestmentDetails(@PathVariable("id") Long id,
                                                                        @Validated @RequestBody SocietyInvestmentDetailsDto societyInvestmentDetailsDto) {
        return new ResponseEntity<>(socInvestDetailsService.updateSocietyInvestmentDetails(id, societyInvestmentDetailsDto),
                HttpStatus.OK);
    }

    @GetMapping({ "/{id}" })
    public ResponseEntity<SocietyInvestmentDetailsDto> getSocietyInvestmentDetailsById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(socInvestDetailsService.getSocietyInvestmentDetailsById(id), HttpStatus.OK);
    }

    @DeleteMapping(path = { "/{id}" }) // deleting data
    public ResponseEntity<SocietyInvestmentDetailsDto> deleteSocietyInvestmentById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(socInvestDetailsService.deleteSocietyInvestmentDetailsById(id),
                HttpStatus.OK);
    }
    @GetMapping({ "/byInvestmentId/{societyInvestmentId}" })
    public ResponseEntity<List<SocietyInvestmentDetailsDto>> getSocietyInvestmentDetailsByInvestmentId(@PathVariable("societyInvestmentId") Long societyInvestmentId) {
        return new ResponseEntity<>(socInvestDetailsService.getSocietyInvestmentDetailsByInvestmentId(societyInvestmentId), HttpStatus.OK);
    }
}

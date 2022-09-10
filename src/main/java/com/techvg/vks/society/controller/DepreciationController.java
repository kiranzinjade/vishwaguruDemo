package com.techvg.vks.society.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techvg.vks.accounts.model.VouchersDto;
import com.techvg.vks.society.model.DepreciationDto;
import com.techvg.vks.society.service.AgmAttendanceService;
import com.techvg.vks.society.service.DepreciationService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/depreciation")

public class DepreciationController {

	private final DepreciationService depreciationService;
	
    @PostMapping(path = { "/{assetId}" })
    public ResponseEntity<DepreciationDto> addDepriciation(@PathVariable("assetId") long assetId) {
        return new ResponseEntity<DepreciationDto>( depreciationService.addDepreciaton( assetId), HttpStatus.CREATED);
    }

}

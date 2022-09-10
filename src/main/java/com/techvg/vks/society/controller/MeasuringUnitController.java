package com.techvg.vks.society.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techvg.vks.society.model.MeasuringUnitDto;
import com.techvg.vks.society.model.MeasuringUnitPageList;
import com.techvg.vks.society.service.MeasuringUnitService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/measuringunit")
public class MeasuringUnitController {
    private final MeasuringUnitService measuringUnitService;

    @PostMapping
    public ResponseEntity<MeasuringUnitDto> addDepositAccount(@Validated @RequestBody MeasuringUnitDto measuringUnitDto, Authentication authentication) {
        return new ResponseEntity<>(measuringUnitService.addMeasuringUnit(measuringUnitDto,authentication) , HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<MeasuringUnitPageList> listAllDepositAccounts(Pageable pageable) {
        MeasuringUnitPageList measuringUnitPageList = measuringUnitService.listMeasuringUnits(pageable);
        return new ResponseEntity<>(measuringUnitPageList, HttpStatus.OK);
    }

    @DeleteMapping(path = { "/{measuringUnitId}" }) // deleting data
    public ResponseEntity<MeasuringUnitDto> deleteDepositAccountById(@PathVariable("measuringUnitId") Long measuringUnitId) {
        return new ResponseEntity<>(measuringUnitService.deleteMeasuringUnitById(measuringUnitId), HttpStatus.OK);
    }
    @PutMapping(path = { "/{measuringUnitId}" })
    public ResponseEntity<MeasuringUnitDto> updateDepositAccount(@PathVariable("measuringUnitId") Long measuringUnitId, @Validated @RequestBody MeasuringUnitDto measuringUnitDto) {
        return new ResponseEntity<>(measuringUnitService.updateMeasuringUnit(measuringUnitId, measuringUnitDto), HttpStatus.OK);
    }

    @GetMapping({ "/{measuringUnitId}" })
    public ResponseEntity<MeasuringUnitDto> getDepositAccount(@PathVariable("measuringUnitId")  Long measuringUnitId) {
        return new ResponseEntity<>(measuringUnitService.getMeasuringUnitById(measuringUnitId), HttpStatus.OK);
    }
    
    @GetMapping( "/list/measuringunit")
	public ResponseEntity<List<MeasuringUnitDto>> listAllMeasuringUnit() {
	        return new ResponseEntity<>(measuringUnitService.listAllMeasuringUnits(), HttpStatus.OK);
	}
}

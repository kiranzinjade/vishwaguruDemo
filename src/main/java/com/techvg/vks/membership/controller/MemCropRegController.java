package com.techvg.vks.membership.controller;

import com.techvg.vks.membership.model.MemCropRegDto;
import com.techvg.vks.membership.model.MemCropRegPageList;
import com.techvg.vks.membership.model.MemberDto;
import com.techvg.vks.membership.service.MemCropRegService;
import com.techvg.vks.society.model.CropRegistrationDto;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/membercrop")
public class MemCropRegController {
    private final MemCropRegService service;

    @PostMapping
    public ResponseEntity<MemCropRegDto> addKMPCrop(@Validated @RequestBody MemCropRegDto dto,Authentication authentication) {
        log.debug("REST request to save loan Demand details : {}", dto);
        return new ResponseEntity<>( service.registerCrop(dto,authentication), HttpStatus.CREATED);
    }

    @PutMapping(path = { "/{id}" })
    public ResponseEntity<MemCropRegDto> updateKMPCrop(@PathVariable("id") Long id, @Validated @RequestBody MemCropRegDto dto) {
        return new ResponseEntity<>(service.updateCropRegistration(id, dto), HttpStatus.OK);
    }

    @GetMapping(path = {"/{memberId}/{year}"})
    public ResponseEntity<List<MemCropRegDto>> getMemberCrops(@PathVariable("memberId")Long memberId, @PathVariable("year")int year){
        log.debug("Rest request to get Sales by Date : {}",memberId);
        return new ResponseEntity<>(service.getMemberCropsForYear(memberId, year), HttpStatus.OK);
    }

    @GetMapping(path = {"/byYear/{year}"})
    public ResponseEntity<List<MemCropRegDto>> getCropsByYear(@PathVariable("year")int year){
        return new ResponseEntity<>(service.getCropsForYear(year), HttpStatus.OK);
    }

    @GetMapping(path= {"/pageable/{year}"})
    public ResponseEntity<MemCropRegPageList> listAllCropsForYear(@PathVariable("year")int year, Pageable pageable) {
        return new ResponseEntity<>(service.listAllCropsForYear(year, pageable), HttpStatus.OK);
    }
    @GetMapping(path = {"/byCurrentYear/{memberId}"})
    public ResponseEntity<List<MemCropRegDto>> getCropsByCurrentYear(@PathVariable("memberId")Long memberId){
        return new ResponseEntity<>(service.getCropsByCurrentYear(memberId), HttpStatus.OK);
    }
    @GetMapping(path= {"/pageable/byCurrentYear"})
    public ResponseEntity<MemCropRegPageList> listAllCropsForCurrentYear(Pageable pageable) {
        return new ResponseEntity<>(service.listAllCropsForCurrentYear(pageable), HttpStatus.OK);
    }
    
    @GetMapping(path={"/memberList"})
    public ResponseEntity<List<MemberDto>>listAllMember() {
        return new ResponseEntity<>(service.listAllMember(), HttpStatus.OK);
    }
   
    @DeleteMapping(path = { "/{id}" }) // deleting data
	public ResponseEntity<MemCropRegDto> deleteCropRegistrationById(@PathVariable("id") Long id) {
		log.debug("REST request to delete Crop Registration : {}", id);
		return new ResponseEntity<>(service.deleteCropRegistrationById(id), HttpStatus.OK);
	}
}

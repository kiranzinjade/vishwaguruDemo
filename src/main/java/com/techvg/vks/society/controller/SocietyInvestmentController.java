package com.techvg.vks.society.controller;

import com.techvg.vks.society.model.SocietyInvestmentDto;
import com.techvg.vks.society.model.SocietyInvestmentPageList;
import com.techvg.vks.society.service.SocietyInvestmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/societyinvestment")
public class SocietyInvestmentController {

	private final SocietyInvestmentService societyInvestmentService;

	@PostMapping
	public ResponseEntity<SocietyInvestmentDto> addSocietyInvestment(@Validated @RequestBody SocietyInvestmentDto societyInvestmentDto, Authentication authentication) {
		log.debug("REST request to save SocietyInvestmentMaster : {}", societyInvestmentDto);
		return new ResponseEntity<>(societyInvestmentService.addSocietyInvestment(societyInvestmentDto, authentication), HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<SocietyInvestmentPageList> listAllSocietyInvestments(Pageable pageable) {
		log.debug("REST request to get a all records of SocietyInvestment ");
		SocietyInvestmentPageList societyInvestmentPageList = societyInvestmentService
				.listSocietyInvestments(pageable);
		return new ResponseEntity<>(societyInvestmentPageList, HttpStatus.OK);
	}

	@DeleteMapping(path = { "/{id}" }) // deleting data
	public ResponseEntity<SocietyInvestmentDto> deleteSocietyInvestmentById(@PathVariable("id") Long id) {
		log.debug("REST request to delete SocietyInvestmentMaster : {}", id);
		return new ResponseEntity<>(societyInvestmentService.deleteSocietyInvestmentById(id),
				HttpStatus.OK);
	}

	@PutMapping(path = { "/{id}" })
	public ResponseEntity<SocietyInvestmentDto> updateSocietyInvestment(@PathVariable("id") Long id,
                                                                               @Validated @RequestBody SocietyInvestmentDto societyInvestmentDto) {
		log.debug("REST request to update SocietyInvestmentMaster: {}", societyInvestmentDto);
		return new ResponseEntity<>(societyInvestmentService.updateSocietyInvestment(id, societyInvestmentDto),
				HttpStatus.OK);
	}

	@GetMapping({ "/{id}" })
	public ResponseEntity<SocietyInvestmentDto> getSocietyInvestmentById(@PathVariable("id") Long id) {
		log.debug("REST request to get Society Investment Master : {}", id);
		return new ResponseEntity<>(societyInvestmentService.getSocietyInvestmentById(id), HttpStatus.OK);
	}

}

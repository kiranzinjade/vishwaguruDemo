package com.techvg.vks.society.controller;

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

import com.techvg.vks.society.model.ExecutiveMembersDto;
import com.techvg.vks.society.model.ExecutivePageList;
import com.techvg.vks.society.service.ExecutiveMembersService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/society/executives")
@Slf4j
@RequiredArgsConstructor
public class ExecutiveMembersController {
	
	private final ExecutiveMembersService executiveMembersService;

	@PostMapping
	public ResponseEntity<ExecutiveMembersDto> addExecutiveMembers(
			@Validated @RequestBody ExecutiveMembersDto executiveMembers, Authentication authentication) {
		log.debug("REST request to save SocietyInvestmentMaster : {}", executiveMembers);
		return new ResponseEntity<>(executiveMembersService.addSocietyInvestment(executiveMembers, authentication),
				HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<ExecutivePageList> getAllExecutiveMembers(Pageable page) {
		ExecutivePageList executiveMembersPageList = executiveMembersService.getAllExecutiveMembers(page);
		return new ResponseEntity<>(executiveMembersPageList, HttpStatus.OK);

	}

	@GetMapping({ "/{id}" })
	public ResponseEntity<ExecutiveMembersDto> readExecutiveMember(@PathVariable("id") Long id) {
		return new ResponseEntity<>(executiveMembersService.readExecutiveMember(id), HttpStatus.OK);
	}

	@PutMapping(path = { "/{id}" })
	public ResponseEntity<ExecutiveMembersDto> updateExecutiveMember(@PathVariable("id") Long id,
			@Validated @RequestBody ExecutiveMembersDto memberdto) {
		return new ResponseEntity<>(executiveMembersService.updateExecutiveMember(id, memberdto), HttpStatus.OK);
	}

	@DeleteMapping(path = { "/{id}" })
	public ResponseEntity<ExecutiveMembersDto> deleteExecutiveMember(@PathVariable("id") Long id) {

		return new ResponseEntity<>(executiveMembersService.deleteExecutiveMember(id), HttpStatus.OK);
	}

}
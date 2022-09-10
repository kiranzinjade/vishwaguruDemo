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

import com.techvg.vks.society.model.ParameterDto;
import com.techvg.vks.society.model.ParameterPageList;
import com.techvg.vks.society.service.ParameterService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/parameter")
public class ParameterController {
	private final ParameterService parameterService;


	@PostMapping
	public ResponseEntity<ParameterDto> addParameter(@Validated  @RequestBody ParameterDto parameterDto,  Authentication authentication) {
		log.debug("REST request to save Parameter : {}", parameterDto);
		return new ResponseEntity<>(parameterService.addParameter(parameterDto,authentication) , HttpStatus.CREATED);
	}
	@GetMapping
	public ResponseEntity<ParameterPageList> listAllParameters(Pageable pageable) {

		log.debug("REST request to get a all records of Parameter");
		ParameterPageList parameterPageList = parameterService.listParameters(pageable);
		return new ResponseEntity<>(parameterPageList, HttpStatus.OK);
	}
	@DeleteMapping(path = { "/{id}" }) // deleting data
	public ResponseEntity<ParameterDto> deleteParameterById(@PathVariable("id") Long id) {
		log.debug("REST request to delete parameter : {}", id);
		return new ResponseEntity<>(parameterService.deleteParameterById(id), HttpStatus.OK);
	}
	
	@PutMapping(path = { "/{id}" })
	public ResponseEntity<ParameterDto> updateParameter(@PathVariable("id") Long id, @Validated @RequestBody ParameterDto parameterDto) {
		log.debug("REST request to update parameter: {}", parameterDto);
		return new ResponseEntity<>(parameterService.updateParameter(id, parameterDto), HttpStatus.OK);
	}
	@GetMapping({ "/{id}" })
	public ResponseEntity<ParameterDto> getPrereuisite(@PathVariable("id") Long id) {
		log.debug("REST request to get Prerequisite : {}", id);
		return new ResponseEntity<>(parameterService.getParameterById(id), HttpStatus.OK);
	}
	

	@GetMapping({"/list"})
	public ResponseEntity<List<ParameterDto>> listAllParameter() {
        return new ResponseEntity<>(parameterService.listAllParameters(), HttpStatus.OK);
}

	@GetMapping(path = { "/parameterlist/{type}" })
	public ResponseEntity<List<ParameterDto>> listParameter(@PathVariable("type") String type) {
		return new ResponseEntity<>(parameterService.listParameter(type), HttpStatus.OK);
	}

}

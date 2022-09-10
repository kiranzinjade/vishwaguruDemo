package com.techvg.vks.membership.controller;

import com.techvg.vks.membership.model.NomineeDto;
import com.techvg.vks.membership.model.NomineePageList;
import com.techvg.vks.membership.service.NomineeService;
import com.techvg.vks.society.model.ProductTypeDto;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@Slf4j
@RestController
@CrossOrigin(origins = "*", allowCredentials = "true")
@RequestMapping("/api/nominee")
public class NomineeController {
	
	private NomineeService nomineeService;
	
	
	@PostMapping(path = { "/{memberId}" })
	public  NomineeDto addNominee(@PathVariable("memberId") Long memberId,@RequestBody List<NomineeDto> nomineeDtoList,Authentication authentication) {
		
		log.debug("REST request to save nominee : {}", nomineeDtoList);
		return nomineeService.addNominee(nomineeDtoList,memberId,authentication);
	}

	@GetMapping
	public ResponseEntity<NomineePageList> listAllNominee(Pageable pageable) {

		log.debug("REST request to get a all records of Members");
		NomineePageList nomineeList = nomineeService.listNominees(pageable);
		return new ResponseEntity<>(nomineeList, HttpStatus.OK);
	}
	
	@GetMapping(path = {"/{memberId}" })
		public ResponseEntity<List<NomineeDto>> getNomineeById(@PathVariable("memberId") Long memberId) {
			log.debug("REST request to get Nominees : {}", memberId);
			return new ResponseEntity<>(nomineeService.getNomineeById(memberId), HttpStatus.OK);
		
	}

	@PutMapping(path = { "/{memberId}" })
	public  NomineeDto updateNominee(@PathVariable("memberId") Long memberId,@RequestBody List<NomineeDto> nomineeDtoList,Authentication authentication) {
		log.debug("REST request to Updtae Nominee : {}", nomineeDtoList);
		return nomineeService.updateNominee(nomineeDtoList,memberId,authentication);

	}

	@DeleteMapping(path = { "/{nomineeId}" }) // deleting data
	public NomineeDto delete(@PathVariable("nomineeId") Long nomineeId) {
		 log.debug("Request to delete Nominee Details : {}",nomineeId);  
		return nomineeService.delete(nomineeId);
	}

	
}


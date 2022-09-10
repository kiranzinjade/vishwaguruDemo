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

import com.techvg.vks.society.model.ProductTypeDto;
import com.techvg.vks.society.model.ProductTypePageList;
import com.techvg.vks.society.model.ProfitsAppropriationDto;
import com.techvg.vks.society.model.ProfitsAppropriationPageList;
import com.techvg.vks.society.service.ProfitsAppropriationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/profitapp")
public class ProfitsAppropriationController {
	
	private final ProfitsAppropriationService profitAppService;
	
	@PostMapping
	public ResponseEntity<ProfitsAppropriationDto> addProfitApp(@Validated  @RequestBody ProfitsAppropriationDto profAppDto,  Authentication authentication) {
		
		return new ResponseEntity<>(profitAppService.addProfitApp(profAppDto,authentication) , HttpStatus.CREATED);
	}

@PutMapping (path= {"/{id}"})
    public ResponseEntity<ProfitsAppropriationDto>updateProfitApp(@PathVariable("id") Long id, @Validated @RequestBody ProfitsAppropriationDto profAppDto){
	return new ResponseEntity<>(profitAppService.updateProfitApp(id, profAppDto), HttpStatus.OK);

}

@DeleteMapping(path = { "/{id}" }) // deleting data
public ResponseEntity<ProfitsAppropriationDto> deleteProfitAppById(@PathVariable("id") Long id) {
	
	return new ResponseEntity<>(profitAppService.deleteProfitAppById(id), HttpStatus.OK);
	
}

//@GetMapping( "/list/profitapp")
//public ResponseEntity<List<ProfitsAppropriationDto>> listAllProfitApp() {
       // return new ResponseEntity<>(profitAppService.listAllProfitApp(), HttpStatus.OK);
//}

@GetMapping
public ResponseEntity<ProfitsAppropriationPageList> listAllProfitApp(Pageable pageable) {

	//log.debug("REST request to get a all records of Product Type");
	ProfitsAppropriationPageList profitappPageList = profitAppService.listProfitApp(pageable);
	return new ResponseEntity<>(profitappPageList, HttpStatus.OK);
}

@GetMapping({ "/{id}" })
public ResponseEntity<ProfitsAppropriationDto> getProfitAppById(@PathVariable("id") Long id) {
	//log.debug("REST request to get ProductType : {}", id);
	return new ResponseEntity<>(profitAppService.getProfitAppById(id), HttpStatus.OK);
}

}



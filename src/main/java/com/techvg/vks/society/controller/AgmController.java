package com.techvg.vks.society.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.techvg.vks.society.model.AgmDto;
import com.techvg.vks.society.model.AgmPageList;
import com.techvg.vks.society.service.AgmService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/society/agm")

public class AgmController {

	private final AgmService agmService;

	@PostMapping(produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Object> addNewAgm(@RequestPart("agm") AgmDto agmDto,@RequestPart("File1") MultipartFile file, @RequestPart("File2") MultipartFile minutesOfMeeting)
	{
		log.debug("REST request to save Meeting details : {}", agmDto);
		return new ResponseEntity<>( agmService.addNewAgm(agmDto,minutesOfMeeting,file), HttpStatus.CREATED);
	}
	

	@GetMapping
	public ResponseEntity<AgmPageList> listAllAgm(Pageable pageable) {

		log.debug("REST request to get a all records of Agm");
		AgmPageList meetingList = agmService.listAgms(pageable);
		return new ResponseEntity<>(meetingList, HttpStatus.OK);
	}

	@GetMapping({ "/{agmId}" })
	public ResponseEntity<AgmDto> getMeeting(@PathVariable("agmId") Long agmId) {
		log.debug("REST request to get  : {}", agmId);
		return new ResponseEntity<>(agmService.getAgmById(agmId), HttpStatus.OK);
	}

	
	 @PutMapping(value="/{agmId}",produces = { MediaType.APPLICATION_JSON_VALUE })
	 public ResponseEntity<AgmDto> updateAgmWithFile(@PathVariable("agmId") Long agmId,  @RequestPart("agm") AgmDto agmDto,@RequestPart("File1") MultipartFile file,@RequestPart("File2") MultipartFile minutesOfMeeting)
	 {
		 log.debug("REST request to save Meeting details : {}", agmDto);
			return new ResponseEntity<>(agmService.updateAgmWithFile(agmDto, agmId ,file,minutesOfMeeting), HttpStatus.OK);
	 } 
	 
	 @PutMapping(path = { "/without/{agmId}" })
	 public ResponseEntity<AgmDto> updateAgm(@PathVariable("agmId") Long agmId, @RequestBody AgmDto agmDto) {
	        return new ResponseEntity<>(agmService.updateAgm(agmId, agmDto), HttpStatus.OK);
	    }
	 	

	@DeleteMapping(path = { "/{agmId}" })
	public ResponseEntity<AgmDto> deleteMemberById(@PathVariable("agmId") Long agmId) {
		return new ResponseEntity<>(agmService.deleteById(agmId), HttpStatus.OK);
	}

}

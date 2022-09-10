package com.techvg.vks.membership.controller;

import com.google.gson.Gson;
import com.techvg.vks.membership.model.LandDto;
import com.techvg.vks.membership.model.LandPageList;
import com.techvg.vks.membership.service.LandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/land")
public class LandController {
	
	 private final LandService landService;

		@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
		public ResponseEntity<Object> addNewLand(@RequestPart("land") LandDto landDto,@RequestPart("jindagiPatrakFile") MultipartFile jindagiPatrakFile,@RequestPart("eightAFile") MultipartFile eightAFile,@RequestPart("saatBaraFile") MultipartFile saatBaraFile)
		{
			log.debug("REST request to save Land : {}", landDto);
			return new ResponseEntity<>( landService.addNewland(landDto,jindagiPatrakFile,eightAFile,saatBaraFile), HttpStatus.CREATED);
		}

	  @PutMapping(value ="/add", consumes = "multipart/form-data")
		public ResponseEntity<Object> updateLand(@RequestParam("landType") String landType[], @RequestParam("landAreaHector") Integer landAreaHector[], @RequestParam("landAreaR") Integer landAreaR[], @RequestParam("landGatno") String landGatno[], @RequestParam("saatBara") MultipartFile saatBara[], @RequestParam("eightA") MultipartFile eightA[] , @RequestParam("jindagiPatrak") MultipartFile jindagiPatrak[] , @RequestParam("memberId") Long memberId, Authentication authentication)throws IOException {
		 log.debug("Request to save Land details : {}",jindagiPatrak,landType,landAreaHector,landAreaR,eightA  );
			return landService.updateLandInfo(landType,landAreaHector,landAreaR, landGatno,saatBara,eightA,jindagiPatrak,memberId,authentication);
		  
	  }
	  
	  @DeleteMapping(path = { "/{id}" }) // deleting data
		public ResponseEntity<LandDto> deleteLandById(@PathVariable("id") Long id) {
			log.debug("REST request to delete land : {}", id);
			return new ResponseEntity<>(landService.deleteLandById(id), HttpStatus.OK);
	 }
	  
	  @GetMapping
		public ResponseEntity<LandPageList> listAllLand(Pageable pageable) {
			log.debug("REST request to get a all records of Lands");
			LandPageList landList = landService.listLand(pageable);
			return new ResponseEntity<>(landList, HttpStatus.OK);
				
		}
	  
	  @GetMapping({ "/{id}" })
		public ResponseEntity<LandDto> getLand(@PathVariable("id") Long id) {
			log.debug("REST request to get land : {}", id);
			return new ResponseEntity<>(landService.getLandById(id), HttpStatus.OK);
		}
	  
	  @GetMapping( { "/landlist/{memberId}" })
	   public ResponseEntity<List<LandDto>> listLand(@PathVariable("memberId") Long memberId) {
	        return new ResponseEntity<>(landService.listLands(memberId), HttpStatus.OK);
	    }
	  
	  @GetMapping( { "/list/{id}/{fileName}" })
	   public ResponseEntity<LandDto> listLandDetails(@PathVariable("id") Long id,@PathVariable("fileName") String fileName) {
	        return new ResponseEntity<>(landService.listLandDetails(id,fileName), HttpStatus.OK);
	    }

}

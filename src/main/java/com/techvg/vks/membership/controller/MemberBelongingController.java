package com.techvg.vks.membership.controller;

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

import com.techvg.vks.membership.model.MemberBelongingDto;
import com.techvg.vks.membership.model.MemberBelongingPageList;
import com.techvg.vks.membership.service.MemberBelongingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/belonging")
public class MemberBelongingController {
	
	private final MemberBelongingService memberBelongingService;
	
	
	  @PostMapping
	  public ResponseEntity<MemberBelongingDto>addBelonging( @Validated @RequestBody MemberBelongingDto memberBelongingDto) {
	  log.debug("REST request to save memberBelonging : {}", memberBelongingDto);
	  return new ResponseEntity<>( memberBelongingService.addNewBelonging(memberBelongingDto), HttpStatus.CREATED);
	  
	  }

	  @PutMapping(path = { "/{memberId}" })
		public  MemberBelongingDto updateBelonging(@PathVariable("memberId") Long memberId,@RequestBody List<MemberBelongingDto> memberBelongingDtoList,Authentication authentication) {
			log.debug("REST request to Updtae Belonging : {}", memberBelongingDtoList);
			return memberBelongingService.updateMemberBelonging(memberBelongingDtoList,memberId,authentication);

		}
	  
	  @DeleteMapping(path = { "/{id}" }) // deleting data
		public ResponseEntity<MemberBelongingDto> deleteById(@PathVariable("id") Long id) {
			log.debug("REST request to delete MemberBelonging : {}", id);
			return new ResponseEntity<>(memberBelongingService.deleteBelongingById(id), HttpStatus.OK);
	 }
	  
     @GetMapping
		public ResponseEntity<MemberBelongingPageList> listAllBelonging(Pageable pageable) {
			log.debug("REST request to get a all records of Member Belonging");
			return new ResponseEntity<>(memberBelongingService.listBelonging(pageable), HttpStatus.OK);
		}
	  
	  @GetMapping({ "/{memberId}" })
		public ResponseEntity<List<MemberBelongingDto>> getBelonging(@PathVariable("memberId") Long memberId) {
			log.debug("REST request to get Belonging : {}", memberId);
			return new ResponseEntity<>(memberBelongingService.getBelongingById(memberId), HttpStatus.OK);
		}
	  
	  @GetMapping( { "/list/{memberId}" })
	   public ResponseEntity<List<MemberBelongingDto>> listBelonging(@PathVariable("memberId") Long memberId) {
	        return new ResponseEntity<>(memberBelongingService.listBelonging(memberId), HttpStatus.OK);
	    }
		
	

}

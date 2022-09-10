package com.techvg.vks.membership.controller;

import com.techvg.vks.accounts.model.VoucherTypeDto;
import com.techvg.vks.membership.model.MemberBankDto;
import com.techvg.vks.membership.model.MemberBankPageList;
import com.techvg.vks.membership.service.MemberBankService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

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
@RequestMapping("/api/memberbank")
public class MemberBankController {
	
	private final MemberBankService memberBankService;
	
	@PostMapping(path = { "/{memberId}" }) 
	  public ResponseEntity<MemberBankDto>addMemberBank( @Validated @RequestBody MemberBankDto memberBankDto,@PathVariable("memberId") Long memberId){
	  log.debug("REST request to save Member Bank : {}", memberBankDto); 
	  return new ResponseEntity<>( memberBankService.addNewMemberBank(memberId,memberBankDto), HttpStatus.CREATED);
	 }
	
	 @PutMapping(path = { "/{id}" })
		public ResponseEntity<MemberBankDto> updateMemberBank(@PathVariable("id") Long id,@Validated @RequestBody MemberBankDto memberBankDto, Authentication authentication) {
		 log.debug("REST request to update Member Bank : {}", memberBankDto);
		return new ResponseEntity<>(memberBankService.updateMemberBank(id,memberBankDto, authentication), HttpStatus.OK);
	}
	 
	 @DeleteMapping(path = { "/{id}" }) // deleting data
		public ResponseEntity<MemberBankDto> deleteMemberBankById(@PathVariable("id") Long id) {
			log.debug("REST request to delete Member Bank : {}", id);
			return new ResponseEntity<>(memberBankService.deleteMemberBank(id), HttpStatus.OK);
	 } 
     
	 @GetMapping
		public ResponseEntity<MemberBankPageList> listAllMemberBank(Pageable pageable) {
			log.debug("REST request to get a all records of Member Bank");
			MemberBankPageList memberBankList = memberBankService.listMemberBank(pageable);
			return new ResponseEntity<>(memberBankList, HttpStatus.OK);
		}
	 

	 @GetMapping({ "/{id}" })
		public ResponseEntity<MemberBankDto> getMemberBank(@PathVariable("id") Long id) {
			log.debug("REST request to get House : {}",id);
			return new ResponseEntity<>(memberBankService.getMemberBankById(id), HttpStatus.OK);
		}
	 @GetMapping({ "/member/{id}" })
		public ResponseEntity<MemberBankDto> getMemberBankByMember(@PathVariable("id") Long id) {
			log.debug("REST request to get House : {}",id);
			return new ResponseEntity<>(memberBankService.getMemberBankByMember(id), HttpStatus.OK);
		}
}

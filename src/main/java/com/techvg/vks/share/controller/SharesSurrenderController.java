package com.techvg.vks.share.controller;

import com.techvg.vks.share.model.SharesAllocationDto;
import com.techvg.vks.share.model.SharesDto;
import com.techvg.vks.share.service.SharesSurrenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/sharesurrender")
public class SharesSurrenderController {
    
	private final SharesSurrenderService sharesSurrenderService;
	
	@PutMapping(path = { "/{memberId}" })
	public ResponseEntity<SharesDto>updateShareSurrender(@PathVariable("memberId") Long memberId, Authentication authentication) {
		log.debug("REST request to update ShareApplication : {}",memberId);
		return new ResponseEntity<>(sharesSurrenderService.sharesSurrender(memberId), HttpStatus.OK);
	}
	
	@PutMapping(path = { "sharedetails/{memberId}" })
	public  List<SharesAllocationDto> updateShareSurrenderdetails(@PathVariable("memberId") Long memberId, Authentication authentication) {
		log.debug("REST request to update ShareApplication : {}",memberId);
		return sharesSurrenderService.sharesSurrenderDetails(memberId);
	}
	
	
	

	
}

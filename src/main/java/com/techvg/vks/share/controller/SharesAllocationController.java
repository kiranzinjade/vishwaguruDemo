package com.techvg.vks.share.controller;

import com.techvg.vks.share.model.ExceedShareCount;
import com.techvg.vks.share.model.SharesAllocationDto;
import com.techvg.vks.share.model.SharesAllocationPageList;
import com.techvg.vks.share.service.SharesAllocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/sharesallocation")
public class SharesAllocationController {
	
	private final SharesAllocationService sharesAllocationService;

	@PostMapping(path = { "/{memberId}/{shareApplicationId}" })
	public ResponseEntity<SharesAllocationDto> allocateNewShare(@PathVariable("memberId") Long memberId,@PathVariable("shareApplicationId") Long shareApplicationId, @Validated  @RequestBody SharesAllocationDto sharesAllocationDto, Authentication authentication) {
		log.debug("REST request to save ShareAllocation : {}", sharesAllocationDto);
		return new ResponseEntity<>(sharesAllocationService.allocateNewShare(sharesAllocationDto, memberId, shareApplicationId), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<SharesAllocationPageList> listAllSharesAllocated(Pageable pageable) {
		log.debug("REST request to get a all records of ShareAllocations :");
		SharesAllocationPageList sharesAllocationList = sharesAllocationService.listSharesAllocated(pageable);
		return new ResponseEntity<>(sharesAllocationList, HttpStatus.OK);
	}

	@DeleteMapping(path = { "/{shareAllocationId}" }) 
	public ResponseEntity<SharesAllocationDto> deleteAllocatedSharesById(@PathVariable("shareAllocationId") Long shareAllocationId) {
		log.debug("REST request to delete ShareAllocation : {}", shareAllocationId);
		return new ResponseEntity<>(sharesAllocationService.deleteAllocatedSharesById(shareAllocationId), HttpStatus.OK);
	}

	@PutMapping(path = { "/{shareAllocationId}" })
	public ResponseEntity<SharesAllocationDto> updateAllocatedShares(@PathVariable("shareAllocationId") Long shareAllocationId, @Validated @RequestBody SharesAllocationDto sharesAllocationDto, Authentication authentication) {
		log.info("Received Id is"+shareAllocationId);
		log.info("REST request to update ShareAllocation : {}", sharesAllocationDto);
		return new ResponseEntity<>(sharesAllocationService.updateAllocatedShares(shareAllocationId, sharesAllocationDto), HttpStatus.OK);
	}

	@GetMapping({ "/{shareAllocationId}" })
	public ResponseEntity<SharesAllocationDto> getAllocatedSharesById(@PathVariable("shareAllocationId") Long shareAllocationId) {
		log.debug("REST request to get ShareAllocation : {}", shareAllocationId);
		return new ResponseEntity<>(sharesAllocationService.getAllocatedSharesById(shareAllocationId), HttpStatus.OK);
	}
    
	@GetMapping( "/{memberId}/{amount}" )
	public ExceedShareCount getShareLimit(@PathVariable("memberId") Long memberId, @PathVariable("amount") Double amount) {
		return sharesAllocationService.exceedShareLimit(memberId, amount);
	}

	@GetMapping({ "/byMember/{memberId}" })
	public ResponseEntity<List<SharesAllocationDto>> getAllocatedShares(@PathVariable("memberId") Long memberId) {
		log.debug("REST request to get ShareAllocation : {}", memberId);
		return new ResponseEntity<>(sharesAllocationService.getAllocatedShares(memberId), HttpStatus.OK);
	}
	
	@GetMapping({ "/all/{memberId}" })
	public ResponseEntity<List<SharesAllocationDto>> getAllAllocatedShares(@PathVariable("memberId") Long memberId) {
		log.debug("REST request to get All ShareAllocation : {}", memberId);
		return new ResponseEntity<>(sharesAllocationService.getAllShares(memberId), HttpStatus.OK);
	}
}

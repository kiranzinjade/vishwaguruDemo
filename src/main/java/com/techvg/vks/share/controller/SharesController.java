package com.techvg.vks.share.controller;

import com.techvg.vks.share.model.SharesDto;
import com.techvg.vks.share.model.SharesPageList;
import com.techvg.vks.share.service.SharesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/shares")
public class SharesController {

	private final SharesService sharesService;

	@PostMapping(path = { "/{memberId}" })
	public ResponseEntity<SharesDto> addShare(@PathVariable("memberId") Long memberId, @Validated @RequestBody SharesDto sharesDto) {
		log.debug("REST request to save ShareApplication : {}", sharesDto);
		return new ResponseEntity<>(sharesService.addNewShare(sharesDto, memberId), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<SharesPageList> listAllShares(Pageable pageable) {
		log.debug("REST request to get all records of ShareApplication");
		SharesPageList shareList = sharesService.listShares(pageable);
		return new ResponseEntity<>(shareList, HttpStatus.OK);
	}

	@DeleteMapping(path = { "/{shareApplicationId}" }) // deleting data
	public ResponseEntity<SharesDto> deleteShareById(@PathVariable("shareApplicationId") Long shareApplicationId) {
		log.debug("REST request to delete ShareApplication : {}", shareApplicationId);
		return new ResponseEntity<>(sharesService.deleteShareById(shareApplicationId), HttpStatus.OK);
	}

	@PutMapping(path = { "/{shareApplicationId}" })
	public ResponseEntity<SharesDto> updateShare(@PathVariable("shareApplicationId") Long shareApplicationId, @Validated @RequestBody SharesDto sharesDto) {
		log.debug("REST request to update ShareApplication : {}", sharesDto);
		return new ResponseEntity<>(sharesService.updateShare(shareApplicationId, sharesDto), HttpStatus.OK);
	}

	@GetMapping({ "/{shareApplicationId}" })
	public ResponseEntity<SharesDto> getShare(@PathVariable("shareApplicationId") Long shareApplicationId) {
		log.debug("REST request to get ShareApplication : {}", shareApplicationId);
		return new ResponseEntity<>(sharesService.getShareById(shareApplicationId), HttpStatus.OK);
	}
	
	@GetMapping( "/shareAmount")
	public double getShareAmount() {
		return sharesService.getShareAmount();
	}

}

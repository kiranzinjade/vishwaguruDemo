package com.techvg.vks.membership.controller;

import com.techvg.vks.membership.model.HouseDto;
import com.techvg.vks.membership.model.HousePageList;
import com.techvg.vks.membership.service.HouseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/house")
public class HouseController {

	private final HouseService houseService;

	@PostMapping(path = { "/{memberId}" })
	public ResponseEntity<Set<HouseDto>> addHouse(@Validated @RequestBody HouseDto houseDto,
			@PathVariable("memberId") Long memberId) {
		log.debug("REST request to save House : {}", houseDto);
		return new ResponseEntity<>(houseService.addNewHouse(memberId, houseDto), HttpStatus.CREATED);
	}

	@PutMapping(path = { "/{houseId}" })
	public ResponseEntity<HouseDto> updateHouse(@PathVariable("houseId") Long houseId,
			@Validated @RequestBody HouseDto houseDto, Authentication authentication) {
		log.debug("REST request to update House : {}", houseDto);
		return new ResponseEntity<>(houseService.updateHouse(houseId, houseDto, authentication), HttpStatus.OK);
	}

	@DeleteMapping(path = { "/{houseId}" }) // deleting data
	public ResponseEntity<HouseDto> deleteHouseById(@PathVariable("houseId") Long houseId) {
		log.debug("REST request to delete house : {}", houseId);
		return new ResponseEntity<>(houseService.deleteHouseById(houseId), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<HousePageList> listAllHouse(Pageable pageable) {
		log.debug("REST request to get a all records of Houses");
		HousePageList houseList = houseService.listHouse(pageable);
		return new ResponseEntity<>(houseList, HttpStatus.OK);
	}

	@GetMapping({ "/{houseId}" })
	public ResponseEntity<HouseDto> getHouse(@PathVariable("houseId") Long houseId) {
		log.debug("REST request to get House : {}", houseId);
		return new ResponseEntity<>(houseService.getHouseById(houseId), HttpStatus.OK);
	}

}

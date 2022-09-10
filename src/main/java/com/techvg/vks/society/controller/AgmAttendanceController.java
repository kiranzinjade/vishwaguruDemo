package com.techvg.vks.society.controller;

import com.techvg.vks.society.model.AgmAttendanceDto;
import com.techvg.vks.society.model.AgmAttendancePageList;
import com.techvg.vks.society.service.AgmAttendanceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/society/agm/attendance")

public class AgmAttendanceController {

	private final AgmAttendanceService agmAttendanceService;

	@PostMapping(value = "/{agmId}", consumes = "multipart/form-data")
	public ResponseEntity<AgmAttendanceDto> importCsvFile(@RequestParam("file") MultipartFile file,
			@PathVariable("agmId") Long agmId, Authentication authentication) throws IOException {
		return new ResponseEntity<>(agmAttendanceService.importCsvFile(file, agmId), HttpStatus.OK);
	}

	@GetMapping(path = { "list/{agmId}" })
	public ResponseEntity<AgmAttendancePageList> listAll(@PathVariable("agmId") Long agmId,Pageable pageable) {

		log.debug("REST request to get a all records of AgmAttendance");
		AgmAttendancePageList attendanceList = agmAttendanceService.listAgmAttendaceMember(pageable,agmId);
		return new ResponseEntity<>(attendanceList, HttpStatus.OK);
	}

	@PutMapping(value = "/{agmId}", consumes = "multipart/form-data")
	public ResponseEntity<AgmAttendanceDto> updateAgmAttendance(@RequestParam("file") MultipartFile file,
			@PathVariable("agmId") Long agmId, Authentication authentication) throws IOException {

		log.debug("REST request to save agm details : {}", file);

		return new ResponseEntity<>(agmAttendanceService.updateCsvFile(file, agmId), HttpStatus.OK);

	}

	@DeleteMapping(path = { "/{agmAttendanceId}" }) // deleting data
	public ResponseEntity<AgmAttendanceDto> deleteMemberById(@PathVariable("agmAttendanceId") Long agmAttendanceId) {

		return new ResponseEntity<>(agmAttendanceService.deleteById(agmAttendanceId), HttpStatus.OK);
	}

	@GetMapping(path = { "/{agmAttendanceId}" })
	public ResponseEntity<AgmAttendanceDto> findById(@PathVariable("agmAttendanceId") Long agmAttendanceId) {

		return new ResponseEntity<>(agmAttendanceService.findById(agmAttendanceId), HttpStatus.OK);
	}

}

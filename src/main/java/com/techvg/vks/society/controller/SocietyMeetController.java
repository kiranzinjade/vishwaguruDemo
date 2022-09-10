package com.techvg.vks.society.controller;

import com.techvg.vks.society.model.SocietyMeetingDto;
import com.techvg.vks.society.model.SocietyMeetingPageList;
import com.techvg.vks.society.service.SocietyMeetService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/society/meeting")

public class SocietyMeetController {
	
	private final SocietyMeetService societyMeetService;

	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Object> addNewSocietyMeet(@RequestPart("society") SocietyMeetingDto society,@RequestPart("File") MultipartFile File)
	{
		log.debug("REST request to save Meeting details : {}", society);
		return new ResponseEntity<>( societyMeetService.addNewSocietyMeet(society,File), HttpStatus.CREATED);
	}

	@GetMapping
		public ResponseEntity<SocietyMeetingPageList> listAllMeetings(Pageable pageable)
		{
			log.debug("REST request to get a all records of Meeting");
			SocietyMeetingPageList meetingList = societyMeetService.listMeeting(pageable);
			return new ResponseEntity<>(meetingList, HttpStatus.OK);
		}


	 @GetMapping({ "/{meetingId}" })
		public ResponseEntity<SocietyMeetingDto> getMeeting(@PathVariable("meetingId") Long meetingId)
	  {
			log.debug("REST request to get  : {}", meetingId);
			return new ResponseEntity<>(societyMeetService.getMeetingById(meetingId), HttpStatus.OK);
		}
	 
	 
	 @PutMapping(value="/{meetingId}",produces = { MediaType.APPLICATION_JSON_VALUE })
		public ResponseEntity<SocietyMeetingDto> updateSocietyMeet(@PathVariable("meetingId") Long meetingId, @RequestPart("society") SocietyMeetingDto society,@RequestPart("File") MultipartFile File)
	 {
			log.debug("REST request to save Meeting details : {}", society);
			return new ResponseEntity<>(societyMeetService.updateSocietyMeet(society,meetingId,File), HttpStatus.OK);
	 } 

	 @DeleteMapping(path = { "/{meetingId}" }) 
		public ResponseEntity<SocietyMeetingDto> deleteMemberById(@PathVariable("meetingId") Long meetingId)
	 {
			return new ResponseEntity<>(societyMeetService.deleteById(meetingId), HttpStatus.OK);
	 }
	 
	 //
	 @GetMapping({ "/withoutFile" })
		public ResponseEntity<SocietyMeetingPageList> listAllMeetingsWithoutFile(Pageable pageable)
		{
			log.debug("REST request to get a all records of Meeting");
			SocietyMeetingPageList meetingList = societyMeetService.listMeetingWithoutFile(pageable);
			return new ResponseEntity<>(meetingList, HttpStatus.OK);
		}

	 @GetMapping({ "/withoutFile/{meetingId}" })
		public ResponseEntity<SocietyMeetingDto> getMeetingWithoutFile(@PathVariable("meetingId") Long meetingId)
	  {
			log.debug("REST request to get  : {}", meetingId);
			return new ResponseEntity<>(societyMeetService.getMeetingByIdTharavFile(meetingId), HttpStatus.OK);
		}
}












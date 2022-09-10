package com.techvg.vks.society.controller;

import com.techvg.vks.society.model.SocietyMeetDetailsDto;
import com.techvg.vks.society.model.SocietyMeetingDetailsDto;
import com.techvg.vks.society.service.SocietyMeetingDetailsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowCredentials = "")
@RequestMapping("/api/society/meetingdetails")
public class SocietyMeetingDetailsController {
    private final SocietyMeetingDetailsService service;

    
    @PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Object> addNewSocietyMeet(@RequestPart("societymeetdetails") SocietyMeetingDetailsDto dto, @RequestPart("File") MultipartFile File)
    {
        log.debug("REST request to save Meeting details : {}", dto);
        return new ResponseEntity<>( service.addNewSocietyMeetDetails(dto,File), HttpStatus.CREATED);
    }

    @GetMapping({"/detailList" })
    public ResponseEntity<List<SocietyMeetingDetailsDto>> listAllDetailMeetings()
    {
        log.debug("REST request to get a all records of Meeting");
        return new ResponseEntity<>(service.listMeeting(false), HttpStatus.OK);
    }

    
    @GetMapping({ "/typelist/{type}" })
    public ResponseEntity<List<SocietyMeetDetailsDto>> listAllMeetings(@PathVariable("type") String type)
    {
        log.debug("REST request to get a all records of Meeting");
        return new ResponseEntity<>(service.listAllMeeting(type), HttpStatus.OK);
    }

    @PutMapping({ "/{meetingId}" })
    public ResponseEntity<SocietyMeetingDetailsDto> updateSocietyMeet(@PathVariable("meetingId") Long meetingId, @RequestBody SocietyMeetingDetailsDto dto)
    {
        log.debug("REST request to save Meeting details : {}", dto);
        return new ResponseEntity<>(service.updateSocietyMeetDetails(dto,meetingId), HttpStatus.OK);
    }

    @DeleteMapping(path = { "/{meetingId}" })
    public ResponseEntity<SocietyMeetingDetailsDto> deleteSocietyMeetingDetailsById(@PathVariable("meetingId") Long meetingId)
    {
        return new ResponseEntity<>(service.deleteSocietyMeetDetails(meetingId), HttpStatus.OK);
    }

    @GetMapping(path = {"/{meetingId}"})
    public ResponseEntity<SocietyMeetingDetailsDto>getSocietyMeetingDetailsById(@PathVariable("meetingId") Long meetingId)
    {
    	return new ResponseEntity<>(service.getMeetingDetailsById(meetingId), HttpStatus.OK);
    }
    //
    @GetMapping({"/list/{meetingId}"})
    public ResponseEntity<List<SocietyMeetingDetailsDto>>MeetingList(@PathVariable("meetingId") Long meetingId)
    {
    	return new ResponseEntity<>(service.MeetingList(meetingId), HttpStatus.OK);
    }
}

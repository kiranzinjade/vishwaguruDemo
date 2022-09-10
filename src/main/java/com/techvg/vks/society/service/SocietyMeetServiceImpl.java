package com.techvg.vks.society.service;

import com.techvg.vks.exceptions.AlreadyExitsException;

import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.membership.reports.MemberwiseShareReport.MemberwiseShareWrapperPageList;
import com.techvg.vks.society.domain.SocietyMeeting;
import com.techvg.vks.society.mapper.SocietyMeetingMapper;
import com.techvg.vks.society.model.SocietyMeetingDto;
import com.techvg.vks.society.model.SocietyMeetingPageList;
import com.techvg.vks.society.repository.SocietyMeetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import java.net.FileNameMap;
import java.net.URLConnection;
//import javax.activation.MimetypesFileTypeMap;
import javax.activation.MimetypesFileTypeMap;

@Service
@RequiredArgsConstructor
@Slf4j

public class SocietyMeetServiceImpl implements SocietyMeetService {

	private final SocietyMeetRepository societyMeetRepository;
	private final SocietyMeetingMapper societyMeetingMapper;


	
	@Override
	public SocietyMeetingDto addNewSocietyMeet(SocietyMeetingDto societyMeetingDto ,MultipartFile meetingDetailsFile) {

		Optional<SocietyMeeting> societyMeeting = societyMeetRepository
				.findByMeetingNo(societyMeetingDto.getMeetingNo());
		if (societyMeeting.isPresent()) {
			throw new AlreadyExitsException(
					"Society Meeting Number is alerady exists: " + societyMeetingDto.getMeetingNo());
		}

		SocietyMeeting societyMeet = societyMeetingMapper.societyMeetingDtoToSocietyMeeting(societyMeetingDto);

		try {
			societyMeet.setMeetingDetailsFile(meetingDetailsFile.getBytes());
			societyMeet.setMeetingFileName(meetingDetailsFile.getOriginalFilename());
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return societyMeetingMapper.societyMeetingToSocietyMeetingDto(societyMeetRepository.save(societyMeet));
	}

	@Override
	public SocietyMeetingDto getMeetingById(Long meetingId) {
		log.debug("REST request to get  : {}", meetingId);
		SocietyMeeting societyMeeting = societyMeetRepository.findById(meetingId)
				.orElseThrow(() -> new NotFoundException("No society found for Id : " + meetingId));
		return societyMeetingMapper.societyMeetingToSocietyMeetingDto(societyMeeting);
	}



	@Override
	public SocietyMeetingDto updateSocietyMeet(SocietyMeetingDto societyMeetingDto, Long meetingId,MultipartFile meetingDetailsFile) {

		SocietyMeeting meeting = societyMeetRepository.findById(meetingId).orElseThrow(NotFoundException::new);
		societyMeetingDto.setId(meeting.getId());
		SocietyMeeting societyMeet = societyMeetingMapper.societyMeetingDtoToSocietyMeeting(societyMeetingDto);

		try {
			societyMeet.setMeetingDetailsFile(meetingDetailsFile.getBytes());
			societyMeet.setMeetingFileName(meetingDetailsFile.getOriginalFilename());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return societyMeetingMapper.societyMeetingToSocietyMeetingDto(societyMeetRepository.save(societyMeet));

	}
	
	@Override
	public SocietyMeetingDto deleteById(Long meetingId) {

		SocietyMeeting meeting = societyMeetRepository.findById(meetingId).orElseThrow(NotFoundException::new);
		if (meeting != null) {
			meeting.setIsDeleted(true);
			societyMeetRepository.save(meeting);
		}
		return societyMeetingMapper.societyMeetingToSocietyMeetingDto(meeting);
	}

	@Override
	public SocietyMeetingPageList listMeeting(Pageable pageable) {
		

		log.debug("Request to get meeting : {}");

		Page<SocietyMeeting> societyMeetingPage;
		societyMeetingPage = societyMeetRepository.findByIsDeleted(false, pageable);
		
		
		return new SocietyMeetingPageList(
				societyMeetingPage.getContent().stream().map(societyMeetingMapper::societyMeetingToSocietyMeetingDto)
						.collect(Collectors.toList()),
				PageRequest.of(societyMeetingPage.getPageable().getPageNumber(),
						societyMeetingPage.getPageable().getPageSize()),
				societyMeetingPage.getTotalElements());

	}
	
///method for listmeeting without file
	@Override
	public SocietyMeetingPageList listMeetingWithoutFile(Pageable pageable) {
	
		
		Page<SocietyMeeting> societyMeetingPage;
		societyMeetingPage = societyMeetRepository.findByIsDeleted(false, pageable);
		societyMeetingPage.forEach(action->{
			action.setMeetingDetailsFile(null);

		});
	
		
	    return new SocietyMeetingPageList(
				societyMeetingPage.getContent().stream().map(societyMeetingMapper::societyMeetingToSocietyMeetingDto)
						.collect(Collectors.toList()),
				PageRequest.of(societyMeetingPage.getPageable().getPageNumber(),
						societyMeetingPage.getPageable().getPageSize()),
				societyMeetingPage.getTotalElements());
	}
//without tharav files
	@Override
	public SocietyMeetingDto getMeetingByIdTharavFile(Long meetingId) {
				
		SocietyMeeting societyMeeting = societyMeetRepository.findById(meetingId)
				.orElseThrow(() -> new NotFoundException("No society found for Id : " + meetingId));
		societyMeeting.getSocietyMeetingDetails().forEach(action->{
			action.setTharavDetailsFile(null);
		});
		
		return societyMeetingMapper.societyMeetingToSocietyMeetingDto(societyMeeting);
	}

}

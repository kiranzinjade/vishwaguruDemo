package com.techvg.vks.society.service;


import com.techvg.vks.society.model.SocietyMeetingDto;
import com.techvg.vks.society.model.SocietyMeetingPageList;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface SocietyMeetService {
		
	SocietyMeetingDto addNewSocietyMeet(SocietyMeetingDto societyMeetingDto,MultipartFile meetingDetailsFile);

	SocietyMeetingDto getMeetingById(Long meetingId);
		
	SocietyMeetingDto updateSocietyMeet(SocietyMeetingDto societyMeetingDto, Long meetingId,MultipartFile meetingDetailsFile);
		
	SocietyMeetingDto deleteById(Long meetingId);

	SocietyMeetingPageList listMeeting(Pageable pageable);
	
	SocietyMeetingPageList listMeetingWithoutFile(Pageable pageable);
	
	SocietyMeetingDto getMeetingByIdTharavFile(Long meetingId);

}

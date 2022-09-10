package com.techvg.vks.society.service;

import com.techvg.vks.society.model.SocietyMeetDetailsDto;
import com.techvg.vks.society.model.SocietyMeetingDetailsDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface SocietyMeetingDetailsService {
      
    SocietyMeetingDetailsDto addNewSocietyMeetDetails(SocietyMeetingDetailsDto societyMeetingDetailsDto, MultipartFile tharavDetailsFile);

    SocietyMeetingDetailsDto updateSocietyMeetDetails(SocietyMeetingDetailsDto societyMeetingDetailsDto, Long meetingId);

    SocietyMeetingDetailsDto deleteSocietyMeetDetails(Long meetingId);
    
    SocietyMeetingDetailsDto getMeetingDetailsById(Long meetingId);

    List<SocietyMeetingDetailsDto> listMeeting(boolean b);
       
    List<SocietyMeetDetailsDto> listAllMeeting(String type);
    
    List<SocietyMeetingDetailsDto> MeetingList(Long meetingId);
}

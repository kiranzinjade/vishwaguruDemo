package com.techvg.vks.society.service;

import com.techvg.vks.config.MemberConstants;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.society.domain.SocietyMeeting;
import com.techvg.vks.society.domain.SocietyMeetingDetails;
import com.techvg.vks.society.mapper.SocietyMeetingDetailsMapper;
import com.techvg.vks.society.model.SocietyMeetDetailsDto;
import com.techvg.vks.society.model.SocietyMeetingDetailsDto;
import com.techvg.vks.society.repository.SocietyMeetRepository;
import com.techvg.vks.society.repository.SocietyMeetingDetailsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLConnection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SocietyMeetingDetailsServiceImpl implements SocietyMeetingDetailsService {

    private final SocietyMeetRepository societyMeetRepository;
    private final SocietyMeetingDetailsMapper mapper;
    private final SocietyMeetingDetailsRepository repository;

    
    @Override
    public SocietyMeetingDetailsDto addNewSocietyMeetDetails(SocietyMeetingDetailsDto societyMeetingDetailsDto,MultipartFile tharavDetailsFile) {
        SocietyMeeting societyMeeting = societyMeetRepository.findById(societyMeetingDetailsDto.getSocietyMeetingId())
                .orElseThrow(() -> new NotFoundException("No society meeting found for Id : " + societyMeetingDetailsDto.getSocietyMeetingId()));
        SocietyMeetingDetails societyMeetingDetails = mapper.toDomain(societyMeetingDetailsDto);
        
        
        societyMeetingDetails.setSocietyMeeting(societyMeeting);
    	try {
    		societyMeetingDetails.setTharavDetailsFile(tharavDetailsFile.getBytes());
    		societyMeetingDetails.setTharavFileName(tharavDetailsFile.getOriginalFilename());
		

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        repository.save(societyMeetingDetails);
        return mapper.toDetailDto(societyMeetingDetails);
    }

    @Override
    public SocietyMeetingDetailsDto updateSocietyMeetDetails(SocietyMeetingDetailsDto societyMeetingDetailsDto, Long meetingId) {
        repository.findById(meetingId)
                .orElseThrow(() -> new NotFoundException("No society meeting details found for Id : " + meetingId));

        SocietyMeeting societyMeeting = societyMeetRepository.findById(societyMeetingDetailsDto.getSocietyMeetingId())
                .orElseThrow(() -> new NotFoundException("No society meeting found for Id : " + societyMeetingDetailsDto.getSocietyMeetingId()));

        SocietyMeetingDetails societyMeetingDetails = mapper.toDomain(societyMeetingDetailsDto);
        societyMeetingDetails.setId(meetingId);
        societyMeetingDetails.setSocietyMeeting(societyMeeting);
        repository.save(societyMeetingDetails);
        return mapper.toDetailDto(societyMeetingDetails);
    }

    @Override
    public SocietyMeetingDetailsDto deleteSocietyMeetDetails(Long meetingId) {
        SocietyMeetingDetails societyMeetingDetails = repository.findById(meetingId)
                .orElseThrow(() -> new NotFoundException("No society meeting details found for Id : " + meetingId));
        societyMeetingDetails.setIsDeleted(true);
        repository.save(societyMeetingDetails);
        return mapper.toDetailDto(societyMeetingDetails);
    }

    @Override
    public List<SocietyMeetingDetailsDto> listMeeting(boolean b) {
        return mapper.toDetailDtoList(repository.findByIsDeleted(false));
    }


    @Override
    public List<SocietyMeetDetailsDto> listAllMeeting(String type) {
    	
        return mapper.toDtoList(repository.findByIsDeletedAndType(false, type));
    }
    
	@Override
	public SocietyMeetingDetailsDto getMeetingDetailsById(Long meetingId) {
		
		SocietyMeetingDetails societyMeetingDetails = repository.findById(meetingId)
				.orElseThrow(() -> new NotFoundException("No society found for Id : " + meetingId));
		return mapper.toDetailDto(societyMeetingDetails);
	}

	//get society meeting details withuout tharav file
	@Override
	public List<SocietyMeetingDetailsDto> MeetingList(Long meetingId) {
		
		List<SocietyMeetingDetails> newList=repository.findByMeetingIdAndIsDeleted(meetingId, false);
		newList.forEach(action->{
			action.setTharavDetailsFile(null);
		});
		
		return mapper.toDetailDtoList(newList);
	}
}

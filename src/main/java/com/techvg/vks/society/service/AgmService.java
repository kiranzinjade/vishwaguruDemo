package com.techvg.vks.society.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.techvg.vks.society.model.AgmDto;
import com.techvg.vks.society.model.AgmPageList;


@Service
public interface AgmService {
	

	AgmPageList listAgms(Pageable pageable);

	AgmDto getAgmById(Long agmId);
		
	AgmDto updateAgm( Long agmId,AgmDto agmDto);

	AgmDto updateAgmWithFile(AgmDto agmDto, Long agmId, MultipartFile file,MultipartFile minutesOfMeeting);

	AgmDto deleteById(Long agmId);

	AgmDto addNewAgm(AgmDto agmDto, MultipartFile minutesOfMeeting,MultipartFile file);

}

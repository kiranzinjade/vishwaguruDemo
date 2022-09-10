package com.techvg.vks.society.service;

import com.techvg.vks.exceptions.AlreadyExitsException;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.society.domain.Agm;
import com.techvg.vks.society.mapper.AgmMapper;
import com.techvg.vks.society.model.AgmDto;
import com.techvg.vks.society.model.AgmPageList;
import com.techvg.vks.society.repository.AgmAttendanceRepository;
import com.techvg.vks.society.repository.AgmRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j

public class AgmServiceImpl implements AgmService {

	private final AgmRepository agmRepository;
	private final AgmMapper agmMapper;
	private final AgmAttendanceService agmAttendanceService;
	private final AgmAttendanceRepository agmAttendanceRepository;

	@Override
	public AgmDto addNewAgm(AgmDto agmDto, MultipartFile minutesOfMeeting, MultipartFile file) {
				
		Optional<Agm> agm = agmRepository.findByYearAndIsDeleted(agmDto.getYear(), false);
		if (agm.isPresent()) {
			throw new AlreadyExitsException("Agm Meeting Deatils is alerady exists with year: " + agmDto.getYear());
		}
		Agm societyAgm = agmMapper.agmDtoToAgm(agmDto);
		
		try 
		{
			
			societyAgm.setMinutesOfMeeting(minutesOfMeeting.getBytes());
			societyAgm.setMinutesOfMeetingFileName(minutesOfMeeting.getOriginalFilename());
		}
		catch (IOException e) {
			
			e.printStackTrace();
		}
		
		Agm societyAgm1=agmRepository.save(societyAgm);
		
		try {
			agmAttendanceService.importCsvFile( file, societyAgm1.getId());
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return agmMapper.agmToAgmDto(societyAgm1);
		
	}

	@Override
	public AgmPageList listAgms(Pageable pageable) {

		log.debug("Request to get meeting : {}");

		Page<Agm> agmPage;
		agmPage = agmRepository.findByisDeleted(false, pageable);

		return new AgmPageList(agmPage.getContent().stream().map(agmMapper::agmToAgmDto).collect(Collectors.toList()),
				PageRequest.of(agmPage.getPageable().getPageNumber(), agmPage.getPageable().getPageSize()),
				agmPage.getTotalElements());

	}

	@Override
	public AgmDto getAgmById(Long agmId) {
		log.debug("REST request to get  : {}", agmId);
		Agm agm = agmRepository.findById(agmId)
				.orElseThrow(() -> new NotFoundException("No society found for Id : " + agmId));

		return agmMapper.agmToAgmDto(agm);

	}

	

	@Override
	public AgmDto deleteById(Long agmId) {

		Agm agm = agmRepository.findById(agmId).orElseThrow(NotFoundException::new);
		if (agm != null) {
			agm.setIsDeleted(true);
			agmRepository.save(agm);
		}
		return agmMapper.agmToAgmDto(agm);
	}

	@Override
	public AgmDto updateAgmWithFile(AgmDto agmDto, Long agmId, MultipartFile file, MultipartFile minutesOfMeeting) {
		
		Agm agm=agmRepository.findById(agmId).orElseThrow(NotFoundException::new);
		agmDto.setId(agm.getId());
		Agm agmSociety=agmMapper.agmDtoToAgm(agmDto);
		try 
		{
			agmSociety.setMinutesOfMeeting(minutesOfMeeting.getBytes());
			agmSociety.setMinutesOfMeetingFileName(minutesOfMeeting.getOriginalFilename());
		}
		catch (IOException e) {
			
			e.printStackTrace();
		}
		
		Agm agmSociety1=agmRepository.save(agmSociety);
		agmAttendanceRepository.setStatus(true, agmSociety1.getId());
		
		try {
			agmAttendanceService.importCsvFile( file, agmSociety1.getId());
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return agmMapper.agmToAgmDto(agmSociety1);
	}

	@Override
	public AgmDto updateAgm(Long agmId, AgmDto agmDto) {
		
		Agm agm = agmRepository.findById(agmId).orElseThrow(NotFoundException::new);
		agmDto.setId(agm.getId());
		Agm societyAgm = agmMapper.agmDtoToAgm(agmDto);
		return agmMapper.agmToAgmDto(agmRepository.save(societyAgm));
	}

	

	
}

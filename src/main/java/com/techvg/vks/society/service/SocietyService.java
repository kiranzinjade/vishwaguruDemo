package com.techvg.vks.society.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.techvg.vks.society.model.SocietyDto;

public interface SocietyService {

	
	SocietyDto deleteSocietyById(Long id);

	SocietyDto getSocietyById(Long id);

	SocietyDto updateSociety(SocietyDto societyDto, Long id);
	
	SocietyDto updateSocietyy(Long id, SocietyDto societyDto, MultipartFile imgFile);

	SocietyDto addSociety(SocietyDto society, MultipartFile imgFile);

	List<SocietyDto> listSocietyDetails();


}

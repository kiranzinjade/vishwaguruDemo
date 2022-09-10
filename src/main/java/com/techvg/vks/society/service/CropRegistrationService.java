package com.techvg.vks.society.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import com.techvg.vks.society.model.CropRegistrationDto;
import com.techvg.vks.society.model.CropRegistrationPageList;

public interface CropRegistrationService {

	CropRegistrationDto addCropRegistration(CropRegistrationDto cropRegistrationDto, Authentication authentication);

	CropRegistrationPageList listCropRegistration(Pageable pageable);

	CropRegistrationDto deleteCropRegistrationById(Long id);

	CropRegistrationDto updateCropRegistration(Long id, CropRegistrationDto cropRegistrationDto);

	CropRegistrationDto getCropRegistrationById(Long id);

	List<CropRegistrationDto> getCrops();

	List<CropRegistrationDto> getCropsBySeason(String season);

}

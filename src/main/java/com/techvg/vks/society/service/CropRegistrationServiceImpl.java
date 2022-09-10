package com.techvg.vks.society.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.techvg.vks.exceptions.AlreadyExitsException;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.society.domain.CropRegistration;
import com.techvg.vks.society.mapper.CropRegistrationMapper;
import com.techvg.vks.society.model.CropRegistrationDto;
import com.techvg.vks.society.model.CropRegistrationPageList;
import com.techvg.vks.society.repository.CropRegistrationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CropRegistrationServiceImpl implements CropRegistrationService{
	
	private final CropRegistrationMapper cropRegistrationMapper;
	private final CropRegistrationRepository cropRegistrationRepository;

	@Override
	public CropRegistrationDto addCropRegistration(CropRegistrationDto cropRegistrationDto,Authentication authentication) {
		 Optional<CropRegistration> cropRegistrationtOptional = cropRegistrationRepository.findByCropNameAndYearAndSeasonAndIsDeleted(cropRegistrationDto.getCropName(), cropRegistrationDto.getYear(), cropRegistrationDto.getSeason(),false);
		//log.debug("REST request to save Crop Registration : {}", cropRegistrationDto);
		  if (cropRegistrationtOptional.isPresent()){
	            throw new AlreadyExitsException("Crop Name already exists for that year and season : " + cropRegistrationDto.getCropName());
	        }
		  else {
		         return cropRegistrationMapper.cropRegistrationToCropRegistrationDto(cropRegistrationRepository.save(cropRegistrationMapper.cropRegistrationDtoToCropRegistration(cropRegistrationDto)));
		  }
	}

	@Override
	public CropRegistrationPageList listCropRegistration(Pageable pageable) {
		log.debug("Request to get Crop Registration : {}");

		Page<CropRegistration> cropRegistrationPage;
		cropRegistrationPage = cropRegistrationRepository.findByisDeleted(pageable,false);

		return new CropRegistrationPageList(cropRegistrationPage
										.getContent()
										.stream()
										.map(cropRegistrationMapper::cropRegistrationToCropRegistrationDto)
										.collect(Collectors.toList()),
										PageRequest
											.of(cropRegistrationPage.getPageable().getPageNumber(),
													cropRegistrationPage.getPageable().getPageSize()),
											cropRegistrationPage.getTotalElements());

	}

	@Override
	public CropRegistrationDto deleteCropRegistrationById(Long id) {
		CropRegistration cropRegistration = cropRegistrationRepository.findById(id).orElseThrow(NotFoundException::new);
		if (cropRegistration != null) {
			cropRegistration.setIsDeleted(true);
			cropRegistrationRepository.save(cropRegistration);
		}
	return cropRegistrationMapper.cropRegistrationToCropRegistrationDto(cropRegistration);
}


	
	@Override
	public CropRegistrationDto updateCropRegistration(Long id, CropRegistrationDto cropRegistrationDto) {
		CropRegistration cropRegistration = cropRegistrationRepository.findById(id).orElseThrow(NotFoundException::new);
		cropRegistrationDto.setId(cropRegistration.getId());	
		CropRegistration cropRegistration1 = cropRegistrationMapper.cropRegistrationDtoToCropRegistration(cropRegistrationDto);
		return cropRegistrationMapper.cropRegistrationToCropRegistrationDto(cropRegistrationRepository.save(cropRegistration1));
	}

	@Override
	public CropRegistrationDto getCropRegistrationById(Long id) {
		log.debug("REST request to get Crop Registration : {}", id);
		CropRegistration cropRegistration = cropRegistrationRepository.findById(id).orElseThrow(
				() -> new NotFoundException("No Crop Registration found for Id : " +id));

		return cropRegistrationMapper.cropRegistrationToCropRegistrationDto(cropRegistration);
	}

	@Override
	public List<CropRegistrationDto> getCrops() {
		
		return  cropRegistrationMapper.domainToDtoList(cropRegistrationRepository.findByisDeleted(false));	
	}

	@Override
	public List<CropRegistrationDto> getCropsBySeason(String season) {
		return  cropRegistrationMapper.domainToDtoList(cropRegistrationRepository.findBySeasonAndIsDeleted(season,false));	
	}
	
	
}



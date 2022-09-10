package com.techvg.vks.society.service;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import com.techvg.vks.society.model.AssetsRegistrationDto;
import com.techvg.vks.society.model.AssetsRegistrationPageList;

public interface AssetsRegistrationService {

	AssetsRegistrationDto addPurchaseAssets(AssetsRegistrationDto assetsRegistrationDto,
			Authentication authentication);

	AssetsRegistrationPageList listAssetsRegistration(Pageable pageable);

	AssetsRegistrationDto deleteAssetsRegistrationById(long id);

	AssetsRegistrationDto updateAssetsRegistration(Long id, 
			AssetsRegistrationDto assetsRegistrationDto);

	AssetsRegistrationDto getAssetsRegistrationById(long id);

}

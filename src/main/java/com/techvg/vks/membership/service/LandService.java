package com.techvg.vks.membership.service;

import com.techvg.vks.membership.model.LandDto;
import com.techvg.vks.membership.model.LandPageList;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface LandService {

	
	ResponseEntity<Object> addLandInfo(String[] landType,  Integer[] landAreaHector, Integer[] landAreaR, String[] landGatno,MultipartFile[] saatBara, MultipartFile[] eightA, MultipartFile[] jindagiPatrak, Long memberId,Authentication authentication) throws IOException;

	LandDto addNewLand(LandDto lanDto, MultipartFile[] saatBara, MultipartFile[] eightA, MultipartFile[] jindagiPatrak,
			Authentication authentication);

	ResponseEntity<Object> updateLandInfo(String[] landType,  Integer[] landAreaHector, Integer[] landAreaR, String[] landGatno,
			MultipartFile[] saatBara, MultipartFile[] eightA, MultipartFile[] jindagiPatrak, Long memberId,
			Authentication authentication) throws IOException;

	LandDto deleteLandById(Long id);

	LandPageList listLand(Pageable pageable);

	LandDto getLandById(Long id);

	List<LandDto> listLands(Long memberId);

	LandDto addNewland(LandDto landDto, MultipartFile jindagiPatrakFile, MultipartFile eightAFile,
			MultipartFile saatBaraFile);

	LandDto listLandDetails(Long id, String fileName);

	

	

}
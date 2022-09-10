package com.techvg.vks.membership.service;

import com.techvg.vks.membership.domain.MemCropReg;
import com.techvg.vks.membership.model.MemCropRegDto;
import com.techvg.vks.membership.model.MemCropRegPageList;
import com.techvg.vks.membership.model.MemberDto;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MemCropRegService {

    MemCropRegDto registerCrop(MemCropRegDto dto, Authentication authentication);
    MemCropRegDto updateCropRegistration(Long id, MemCropRegDto dto);
    List<MemCropRegDto> getMemberCropsForYear(Long memberId, int year);
    List<MemCropRegDto> getCropsForYear(int year);
    List<MemCropReg> getMemberCrops(Long memberId, int year);
    MemCropRegPageList listAllCropsForYear(int year, Pageable pageable);
	List<MemCropRegDto> getCropsByCurrentYear(Long memberId);
	MemCropRegPageList listAllCropsForCurrentYear(Pageable pageable);
	MemCropRegDto deleteCropRegistrationById(Long id);
	List<MemberDto> listAllMember();
}

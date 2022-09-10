package com.techvg.vks.membership.service;

import com.techvg.vks.membership.model.MemberBelongingDto;
import com.techvg.vks.membership.model.MemberBelongingPageList;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface MemberBelongingService {

	MemberBelongingDto addNewBelonging(MemberBelongingDto memberBelongingDto);

	MemberBelongingDto updateMemberBelonging(List<MemberBelongingDto> memberBelongingDtoList, Long memberId, Authentication authentication);

	MemberBelongingDto deleteBelongingById(Long id);

	MemberBelongingPageList listBelonging(Pageable pageable);

	List<MemberBelongingDto> getBelongingById(Long memberId);

	List<MemberBelongingDto> listBelonging(Long memberId);
	
	

}

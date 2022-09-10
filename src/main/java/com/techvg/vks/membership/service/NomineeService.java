package com.techvg.vks.membership.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.techvg.vks.membership.model.MemberDto;
import com.techvg.vks.membership.model.NomineeDto;
import com.techvg.vks.membership.model.NomineePageList;



@Service
public interface NomineeService {


	NomineeDto addNominee(List<NomineeDto> nomineeDto, Long memberId, Authentication authentication);
		
	NomineeDto updateNominee(List<NomineeDto> nomineeDto, Long memberId, Authentication authentication);

	NomineeDto delete(Long nomineeId);

	NomineePageList listNominees(Pageable pageable);

	List<NomineeDto> getNomineeById(Long memberId);
	


}

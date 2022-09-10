package com.techvg.vks.loan.service;

import com.techvg.vks.loan.model.KMPMemberDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface KMPMemberService {
	
	KMPMemberDto addKmpMember(KMPMemberDto KMPMemberDto);
	KMPMemberDto getMemberKMPForYear(Long memberId, int year);
	List<KMPMemberDto> getKMPsForYear(int year);
}

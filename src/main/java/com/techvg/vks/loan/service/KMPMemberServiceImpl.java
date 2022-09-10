package com.techvg.vks.loan.service;

import com.techvg.vks.loan.mapper.KMPMemberMapper;
import com.techvg.vks.loan.model.KMPMemberDto;
import org.springframework.stereotype.Service;

import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.loan.domain.KMPMember;
import com.techvg.vks.loan.repository.KMPMemberRepository;
import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.membership.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KMPMemberServiceImpl implements KMPMemberService {
	private final KMPMemberRepository repo;
	private final KMPMemberMapper mapper;
	private final MemberRepository memberRepo;
	
	@Override
	public KMPMemberDto addKmpMember(KMPMemberDto KMPMemberDto) {
		
		Member member = memberRepo.findById(KMPMemberDto.getMemberId())
				.orElseThrow(() -> new NotFoundException("No member found for Id : " + KMPMemberDto.getMemberId()));
		KMPMember KMPMember = mapper.toDomain(KMPMemberDto);
		KMPMember.setMember(member);
		repo.save(KMPMember);
		return mapper.toDto(KMPMember);
	}

	@Override
	public KMPMemberDto getMemberKMPForYear(Long memberId, int year) {
		return mapper.toDto(repo.findByMember_IdAndKmpYear(memberId, year));
	}

	@Override
	public List<KMPMemberDto> getKMPsForYear(int year) {
		return mapper.toDtoList(repo.findByKmpYear(year));
	}


}

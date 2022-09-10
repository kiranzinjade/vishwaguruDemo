package com.techvg.vks.membership.service;

import com.techvg.vks.exceptions.AlreadyExitsException;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.membership.domain.MemberBelonging;
import com.techvg.vks.membership.domain.Nominee;
import com.techvg.vks.membership.mapper.MemberBelongingMapper;
import com.techvg.vks.membership.model.MemberBelongingDto;
import com.techvg.vks.membership.model.MemberBelongingPageList;
import com.techvg.vks.membership.model.NomineeDto;
import com.techvg.vks.membership.repository.MemberBelongingRepository;
import com.techvg.vks.membership.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberBelongingServiceImpl implements MemberBelongingService {
	
	private final MemberBelongingRepository memberBelongingRepository;
	private final MemberBelongingMapper memberBelongingMapper;
	private final MemberRepository memberRepository;

	@Override
	public MemberBelongingDto addNewBelonging(MemberBelongingDto memberBelongingDto) {

		MemberBelonging memberBelongingObjOptional=memberBelongingRepository.findByBelongingType(memberBelongingDto.getBelongingType(),memberBelongingDto.getMemberId());
		 MemberBelonging memberBelonging = this.memberBelongingMapper.memberBelongingDtoToMemberBelonging(memberBelongingDto);
		if (memberBelongingObjOptional != null){
			if(memberBelongingDto.getBelongingType().equals(memberBelongingObjOptional.getBelongingType())) {
				memberBelongingObjOptional.setCount(memberBelongingDto.getCount() + memberBelongingObjOptional.getCount());
				memberBelongingObjOptional.setAmount(memberBelongingDto.getAmount() + memberBelongingObjOptional.getAmount());
			memberBelongingRepository.save(memberBelongingObjOptional);
			}
//			throw new AlreadyExitsException("memberBelonging already exists with the member Belonging Type : " + memberBelongingDto.getBelongingType());
		}
		else {
			 Member member = memberRepository.findById(memberBelongingDto.getMemberId()).orElseThrow(NotFoundException::new); 	
			 memberBelonging.setMember(member);                                 
			 memberBelonging=memberBelongingRepository.save(memberBelonging); 
	    	}
		 return memberBelongingMapper.memberBelongingToMemberBelongingDto(memberBelonging);
	}

	@Override
	public MemberBelongingDto updateMemberBelonging(List<MemberBelongingDto> memberBelongingDtoList, Long memberId,
			Authentication authentication) {

		Member member = memberRepository.findById(memberId).orElseThrow(NotFoundException::new);
		MemberBelonging memberBelonging=null;
	
		for(MemberBelongingDto bel:memberBelongingDtoList) {
			memberBelonging = this.memberBelongingMapper.memberBelongingDtoToMemberBelonging(bel);
			memberBelonging.setId(bel.getId());
			memberBelonging.setMember(member);
			memberBelongingRepository.save(memberBelonging);
		};
	return null;
}


	@Override
	public MemberBelongingDto deleteBelongingById(Long id) {
		MemberBelonging memberBelonging =memberBelongingRepository.findById(id).orElseThrow(NotFoundException::new);
		if (memberBelonging != null) {
			
			memberBelonging.setIsDeleted(true);
			memberBelongingRepository.save(memberBelonging);
	}
        return memberBelongingMapper.memberBelongingToMemberBelongingDto(memberBelonging);
	}

	@Override
	public MemberBelongingPageList listBelonging(Pageable pageable) {
		log.debug("Request to get MemberBelonging : {}");

		Page<MemberBelonging> memberBelongingPage;
		memberBelongingPage = memberBelongingRepository.findByisDeleted(false,pageable);

		return new MemberBelongingPageList(memberBelongingPage
										.getContent()
										.stream()
										.map(memberBelongingMapper::memberBelongingToMemberBelongingDto)
										.collect(Collectors.toList()),
										PageRequest
											.of(memberBelongingPage.getPageable().getPageNumber(),
													memberBelongingPage.getPageable().getPageSize()),
											memberBelongingPage.getTotalElements());
	}

	@Override
	public List<MemberBelongingDto> getBelongingById(Long memberId) {
		log.debug("REST request to getBelonging : {}", memberId);
	 	List<MemberBelonging> memberBelonging = memberBelongingRepository.findByMemberId(memberId);

		return memberBelongingMapper.toDtoList(memberBelonging);
	}

	@Override
	public List<MemberBelongingDto> listBelonging(Long memberId) {
		// TODO Auto-generated method stub
		return memberBelongingMapper.toDtoList(memberBelongingRepository.findByMemberId(memberId));
	}
	

}

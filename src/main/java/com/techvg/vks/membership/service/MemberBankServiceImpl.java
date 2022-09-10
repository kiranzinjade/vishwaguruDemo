package com.techvg.vks.membership.service;

import com.techvg.vks.exceptions.AlreadyExitsException;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.membership.domain.MemberBank;
import com.techvg.vks.membership.mapper.MemberBankMapper;
import com.techvg.vks.membership.model.MemberBankDto;
import com.techvg.vks.membership.model.MemberBankPageList;
import com.techvg.vks.membership.repository.MemberBankRepository;
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
public class MemberBankServiceImpl implements MemberBankService{
	
	private final  MemberBankRepository memberBankRepository; 
	private final  MemberBankMapper memberBankMapper;
	private final MemberRepository memberRepository;
	
	@Override
	public MemberBankDto addNewMemberBank(Long memberId , MemberBankDto memberBankDto) {
		Member member = memberRepository.findById(memberId).orElseThrow(NotFoundException::new);
		
		Optional<MemberBank>memberBankObjOptional=memberBankRepository.findByAccountNumber(memberBankDto.getAccountNumber());
		if (memberBankObjOptional.isPresent()){
			throw new AlreadyExitsException("Bank already exists with the Account Number : " + memberBankDto.getAccountNumber());
		}
		else {
			
			memberBankDto.setIsDeleted(false);
			MemberBank memberBank=this.memberBankMapper.memberBankDtoToMemberBank(memberBankDto);
			memberBank.setMember(member);
			memberBank =memberBankRepository.save(memberBank);
			return memberBankMapper.memberBankToMemberBankDto(memberBank);
		
	
		}
	}

	@Override
	public MemberBankDto updateMemberBank(Long id, MemberBankDto memberBankDto, Authentication authentication) {
		MemberBank memberBank =memberBankRepository.findById(id).orElseThrow(NotFoundException::new);
		memberBankDto.setIsDeleted(memberBankDto.getIsDeleted());
		memberBankDto.setId(memberBank.getId());
		MemberBank memberBank1=memberBankMapper.memberBankDtoToMemberBank(memberBankDto);
		memberBank1.setMember(memberBank.getMember());
		return memberBankMapper.memberBankToMemberBankDto(memberBankRepository.save(memberBank1));
	}

	@Override
	public MemberBankDto deleteMemberBank(Long id) {
		MemberBank memberBank =memberBankRepository.findById(id).orElseThrow(NotFoundException::new);
		if (memberBank != null) {
			memberBank.setIsDeleted(true);
			memberBankRepository.save(memberBank);
	}
		
    return memberBankMapper.memberBankToMemberBankDto(memberBank);
	}

	@Override
	public MemberBankPageList listMemberBank(Pageable pageable) {
		log.debug("Request to get Member Bank : {}");

		Page<MemberBank> memberBankPage;
		memberBankPage = memberBankRepository.findByisDeleted(false,pageable);

		return new MemberBankPageList(memberBankPage
										.getContent()
										.stream()
										.map(memberBankMapper::memberBankToMemberBankDto)
										.collect(Collectors.toList()),
										PageRequest
											.of(memberBankPage.getPageable().getPageNumber(),
													memberBankPage.getPageable().getPageSize()),
											memberBankPage.getTotalElements());

	}

	@Override
	public MemberBankDto getMemberBankById(Long id) {
		log.debug("REST request to getMemberBank : {}", id);	
		MemberBank memberBank =memberBankRepository.findById(id).orElseThrow(
				() -> new NotFoundException("No Member Bank found for id : " +id));

		return memberBankMapper.memberBankToMemberBankDto(memberBank);
	}

	@Override
	public MemberBankDto getMemberBankByMember(Long memberid) {
		//log.debug("REST request to getMemberBank : {}", id);
		Member member = memberRepository.findById(memberid).orElseThrow(() -> new NotFoundException("No Member Bank found for id : " +memberid));
		
		MemberBank memberBank = memberBankRepository.findByMember(member).orElseThrow(
				() -> new NotFoundException("No Member Bank found for id : " +memberid));

		return memberBankMapper.memberBankToMemberBankDto(memberBank);
	}


	
	
	
	
}

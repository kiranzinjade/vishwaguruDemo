
package com.techvg.vks.society.service;

import com.techvg.vks.exceptions.AlreadyExitsException;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.membership.repository.MemberRepository;
import com.techvg.vks.society.domain.ExecutiveMember;
import com.techvg.vks.society.domain.MeasuringUnit;
import com.techvg.vks.society.domain.SocietyInvestmentMaster;
import com.techvg.vks.society.mapper.ExecutiveMemberMapper;
import com.techvg.vks.society.model.ElectedMember;
import com.techvg.vks.society.model.ExecutiveMembersContainer;
import com.techvg.vks.society.model.ExecutiveMembersDto;
import com.techvg.vks.society.model.ExecutivePageList;
import com.techvg.vks.society.repository.ExecutiveMembersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j

public class ExecutivesMembersServiceImpl implements ExecutiveMembersService {
	private final ExecutiveMembersRepository execMembersRepo;
	private final MemberRepository memberRepo;
	private final ExecutiveMemberMapper executiveMemberMapper;

	@Override
	public ExecutiveMembersDto updateExecutiveMember(Long id, ExecutiveMembersDto electedMember) {
		ExecutiveMember executiveMember = execMembersRepo.findById(id).orElseThrow(NotFoundException::new);
		electedMember.setId(executiveMember.getId());
		System.out.println(electedMember.getMemberId());
		ExecutiveMember executiveMember1 = executiveMemberMapper.toExecutiveMember(electedMember);
		Member member = memberRepo.findById(electedMember.getMemberId())
				.orElseThrow(() -> new NotFoundException("Executive member could not be found !!"));
		executiveMember1.setMember(member);
		Date electeddate = electedMember.getElectedFrom();
		if(electedMember.getElectedTo()==null) 
		{
		log.info("Date elected from Caught Here = " + electeddate.toString());
		final Calendar electedTillDate = Calendar.getInstance();
		electedTillDate.setTime(electeddate);
		electedTillDate.set((electedTillDate.get(Calendar.YEAR) + 5), electedTillDate.get(Calendar.MONTH),
				electedTillDate.get(Calendar.DAY_OF_MONTH));
		log.info(" Date elected to Caught Here = " + electedTillDate.getTime().toString());
		executiveMember1.setElectedFrom(electeddate);
		executiveMember1.setElectedTo(electedTillDate.getTime());
		executiveMember1.setDesignation(electedMember.getDesignation());
		}
		else
		{
			executiveMember1.setElectedFrom(electeddate);
			executiveMember1.setElectedTo(electedMember.getElectedTo());
			executiveMember1.setDesignation(electedMember.getDesignation());
		}
		return executiveMemberMapper.toExecutiveMembersDto(execMembersRepo.save(executiveMember1));

	}

	@Override
	public ExecutiveMembersDto readExecutiveMember(Long executiveMemberId) {
		return executiveMemberMapper.toExecutiveMembersDto(execMembersRepo.findById(executiveMemberId)
				.orElseThrow(() -> new NotFoundException("Executive Not Found")));

	}

	@Override
	public ExecutiveMembersDto deleteExecutiveMember(Long executiveMemberId) {
		ExecutiveMember member = execMembersRepo.findById(executiveMemberId)
				.orElseThrow(() -> new NotFoundException("Executive member could not be found !!"));
		if (member != null) {
			member.setIsDeleted(true);
			execMembersRepo.save(member);
		}
		return executiveMemberMapper.toExecutiveMembersDto(member);

	}

	@Override
	public ExecutivePageList getAllExecutiveMembers(Pageable page) {
		Page<ExecutiveMember> execMemberPage;
		execMemberPage = execMembersRepo.findByisDeleted(page, false);

		return new ExecutivePageList(
				execMemberPage.getContent().stream().map(executiveMemberMapper::toExecutiveMembersDto)
						.collect(Collectors.toList()),
				PageRequest.of(execMemberPage.getPageable().getPageNumber(),
						execMemberPage.getPageable().getPageSize()),
				execMemberPage.getTotalElements());
	}

	@Override
	public ExecutiveMembersDto addSocietyInvestment(ExecutiveMembersDto executiveMembers,
			Authentication authentication) {
		Member member = memberRepo.findById(executiveMembers.getMemberId())
				.orElseThrow(() -> new NotFoundException("Executive member could not be found !!"));

		Optional<ExecutiveMember> executiveMemberOptional = execMembersRepo
				.findByMemberId(executiveMembers.getMemberId());

		if (executiveMemberOptional.isPresent()) {
			throw new AlreadyExitsException(
					"Member is  already elected : " + executiveMemberOptional.get().getMember().getFirstName()+"   "+executiveMemberOptional.get().getMember().getMiddleName()+"   "+executiveMemberOptional.get().getMember().getLastName());
		} else {
			ExecutiveMember executivemember = executiveMemberMapper.toExecutiveMember(executiveMembers);
			executivemember.setMember(member);
			System.out.println("member name-" + executiveMembers.getFullName());
			Date electeddate = executiveMembers.getElectedFrom();
			
			if(executiveMembers.getElectedTo()==null) {
			log.info("Date elected from Caught Here = " + electeddate.toString());
			final Calendar electedTillDate = Calendar.getInstance();
			electedTillDate.setTime(electeddate);
			electedTillDate.set((electedTillDate.get(Calendar.YEAR) + 5), electedTillDate.get(Calendar.MONTH),
					electedTillDate.get(Calendar.DAY_OF_MONTH));
			log.info(" Date elected to Caught Here = " + electedTillDate.getTime().toString());
			executivemember.setElectedFrom(electeddate);
			executivemember.setElectedTo(electedTillDate.getTime());
			}
			else {
				executivemember.setElectedFrom(electeddate);
				executivemember.setElectedTo(executiveMembers.getElectedTo());
			}
			return executiveMemberMapper.toExecutiveMembersDto(execMembersRepo.save(executivemember));
		}

	}
}
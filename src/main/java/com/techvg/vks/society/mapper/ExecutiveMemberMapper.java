package com.techvg.vks.society.mapper;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.TargetType;
import org.springframework.beans.factory.annotation.Autowired;

import com.techvg.vks.loan.domain.LoanDemand;
import com.techvg.vks.loan.model.LoanDemandDto;
import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.membership.repository.MemberRepository;
import com.techvg.vks.society.domain.ExecutiveMember;

import com.techvg.vks.society.domain.Society;
import com.techvg.vks.society.model.ExecutiveMembersDto;
import com.techvg.vks.society.model.SocietyDto;
import com.techvg.vks.society.repository.ExecutiveMembersRepository;

@Mapper(componentModel = "spring")
public interface ExecutiveMemberMapper {

	@Mapping(source="execMember.member.id", target="memberId")
	ExecutiveMembersDto toExecutiveMembersDto(ExecutiveMember execMember);

	ExecutiveMember toExecutiveMember(ExecutiveMembersDto societyDto);

	List<ExecutiveMembersDto> toExecutiveMembersDtoList(List<ExecutiveMember> execMemberList);

	List<ExecutiveMember> toExecutiveMemberList(List<ExecutiveMembersDto> execMemberDtoList);
	
	@AfterMapping
	default void getExecutiveMemberDetails(@MappingTarget ExecutiveMembersDto executiveMembersDto,ExecutiveMember executiveMember) {
		executiveMembersDto.setFullName(executiveMember.getMember().getFirstName()+" "+executiveMember.getMember().getMiddleName()+" "+executiveMember.getMember().getLastName());
		
	}
}

package com.techvg.vks.membership.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.techvg.vks.membership.domain.MemberBelonging;
import com.techvg.vks.membership.model.MemberBelongingDto;


@Mapper(componentModel = "spring")
public interface MemberBelongingMapper {
	MemberBelongingDto memberBelongingToMemberBelongingDto(MemberBelonging memberBelonging);
	MemberBelonging memberBelongingDtoToMemberBelonging(MemberBelongingDto memberBelongingDto);
	
	List<MemberBelongingDto> toDtoList(List<MemberBelonging> domainList);

	   
}
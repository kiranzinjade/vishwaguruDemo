package com.techvg.vks.loan.mapper;

import com.techvg.vks.loan.domain.KMPMember;
import org.mapstruct.Mapper;

import com.techvg.vks.loan.model.KMPMemberDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface KMPMemberMapper {

	KMPMemberDto toDto(KMPMember domain);
	KMPMember toDomain(KMPMemberDto dto);
	List<KMPMemberDto> toDtoList(List<KMPMember> domainList);
}

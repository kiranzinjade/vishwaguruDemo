package com.techvg.vks.membership.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.techvg.vks.membership.domain.MemberBank;
import com.techvg.vks.membership.model.MemberBankDto;



@Mapper(componentModel = "spring")
public interface MemberBankMapper {

	MemberBankDto memberBankToMemberBankDto(MemberBank memberBank);
	MemberBank memberBankDtoToMemberBank(MemberBankDto memberBankDto);

}
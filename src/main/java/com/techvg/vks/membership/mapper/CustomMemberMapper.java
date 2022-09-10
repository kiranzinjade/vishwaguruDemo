package com.techvg.vks.membership.mapper;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.membership.model.MemberDto;
import com.techvg.vks.share.domain.SharesAllocation;
import com.techvg.vks.share.mapper.SharesMapper;

@Mapper(uses= {HouseMapper.class,SharesMapper.class},componentModel = "spring")
public interface CustomMemberMapper {
	

    @Mapping(ignore=true,target="nomineeDtoSet")
    @Mapping(source="domain.shares",target="sharesDtoSet")
    @Mapping(ignore=true,target="documentDto")
    @Mapping(source="domain.house",target="houseDtoSet")
    @Mapping(ignore=true,target="loanDetailsDtoSet")
    @Mapping(ignore=true,target="landDtoSet")
    @Mapping(ignore=true,target="memberBankDtoSet")
    @Mapping(ignore=true,target="memberBelongingDtoSet")
    @Mapping(ignore=true,target="agmAttendanceDtoSet")
	MemberDto toCustomDto(Member domain);
	
	List<MemberDto> toCustomDtoList(List<Member> domainList);
    @AfterMapping
    default void updateMemberDto(@MappingTarget MemberDto memberDto, Member member ) {
        int totalShares= member.getSharesAllocation().stream().mapToInt(SharesAllocation::getNoOfShares).sum();
        memberDto.setTotalAllocatedShares(totalShares);
    }

}

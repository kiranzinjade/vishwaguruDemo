package com.techvg.vks.membership.mapper;

import java.util.List;

import com.techvg.vks.loan.domain.LoanDetails;
import com.techvg.vks.loan.mapper.STLoanDemandMapper;
import com.techvg.vks.loan.model.LoanBriefDto;
import com.techvg.vks.share.domain.SharesAllocation;
import org.mapstruct.*;

import com.techvg.vks.loan.mapper.LoanDetailsMapper;
import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.membership.model.MemberDto;
import com.techvg.vks.share.mapper.SharesMapper;

import com.techvg.vks.society.mapper.AgmAttendanceMapper;



@Mapper(uses = { NomineeMapper.class,SharesMapper.class,LandMapper.class,HouseMapper.class,MemberBankMapper.class, MemberBelongingMapper.class, AgmAttendanceMapper.class,LoanDetailsMapper.class, STLoanDemandMapper.class}, componentModel = "spring")
public interface MemberMapper {

   // @Mapping(source = "member.nominees", target = "nomineeDtoSet")
   // @Mapping(source="member.document", target="documentDto")
   // @Mapping(source = "member.shares", target = "sharesDtoSet") 
    //@Mapping(source = "member.land", target = "landDtoSet")
    @Mapping(source="member.house",target="houseDtoSet")
    @Mapping(source="member.memberBank",target="memberBankDtoSet")
   // @Mapping(source="member.memberBelonging",target="memberBelongingDtoSet")
   // @Mapping(source="member.agmAttendance",target="agmAttendanceDtoSet")
   // @Mapping(source="member.loanDetails",target="loanDetailsDtoSet")
    //@Mapping(source="member.loanDemands",target="loanDemandsDtoSet")
   	MemberDto memberToMemberDto(Member member);
    
    @InheritInverseConfiguration
    Member memberDtoToMember(MemberDto memberDto);
    List<MemberDto> domainToDtoList(List<Member> domainList);
    
    

    @AfterMapping
    default void updateMemberDto(@MappingTarget MemberDto memberDto, Member member ) {
        int totalShares= member.getSharesAllocation().stream().mapToInt(SharesAllocation::getNoOfShares).sum();
        memberDto.setTotalAllocatedShares(totalShares);
    }
}

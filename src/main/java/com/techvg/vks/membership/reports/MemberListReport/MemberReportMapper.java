package com.techvg.vks.membership.reports.MemberListReport;

import com.techvg.vks.membership.domain.Member;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel="spring")

public interface MemberReportMapper {
	@IterableMapping(qualifiedByName="all")
    List<MemberReportWrapper> mapAllMemberDataList(List<Member> memberlist);

	@Named("all")
	@Mappings({ 
		
		@Mapping(ignore=true,target="address"),
		@Mapping(ignore=true,target="name"),
		@Mapping(source="phoneNumber",target="phoneNumber"),
		@Mapping(source="status",target="status")
		
	})
    MemberReportWrapper mapAllMemberData(Member member);
	
	

@AfterMapping
default void fillInProperties(final Member member,
      @MappingTarget final MemberReportWrapper wrapper ) {
  member.getHouse().forEach(action->{ 
	  if(action.getAddressType().contentEquals("PERMANENT"))
	  {
		  wrapper.setAddress(action.getAddressLine1()+", "+action.getAddressLine2()+", "+action.getCity()+", "+action.getState()+", PIN - "+action.getPincode());	  
	  }
  });
	
	
  wrapper.setName(member.getLastName()+" "+member.getMiddleName()+" "+member.getFirstName());
	
	
}


}

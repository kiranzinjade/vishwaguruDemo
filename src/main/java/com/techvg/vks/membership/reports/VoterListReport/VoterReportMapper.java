package com.techvg.vks.membership.reports.VoterListReport;

import com.techvg.vks.membership.domain.Member;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel="spring")
public interface VoterReportMapper {
	@IterableMapping(qualifiedByName="all")
    List<VoterReportWrapper> mapAllVoterDataList(List<Member> memberlist);

	@Named("all")
	@Mappings({ 
		
		@Mapping(ignore=true,target="address"),
		@Mapping(ignore=true,target="name"),
		@Mapping(source="phoneNumber",target="phoneNumber"),
		@Mapping(source="status",target="status")
		
	})
    VoterReportWrapper mapAllVoterData(Member member);
	
@AfterMapping
default void fillInProperties(final Member member,
      @MappingTarget final VoterReportWrapper wrapper ) {
  member.getHouse().forEach(action->{ 
	  if(action.getAddressType().contentEquals("PERMANENT"))
	  {
		  wrapper.setAddress(action.getAddressLine1()+", "+action.getAddressLine2()+", "+action.getCity()+", "+action.getState()+", PIN - "+action.getPincode());	  
	  }
  });
	
  wrapper.setName(member.getLastName()+" "+member.getMiddleName()+" "+member.getFirstName());
	
}

}

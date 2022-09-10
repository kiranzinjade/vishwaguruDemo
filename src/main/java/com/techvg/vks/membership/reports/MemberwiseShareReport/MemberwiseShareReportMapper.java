package com.techvg.vks.membership.reports.MemberwiseShareReport;

import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.share.domain.SharesAllocation;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel="spring")
public interface MemberwiseShareReportMapper {
	
	@IterableMapping(qualifiedByName="all")
    List<MemberwiseShareReportWrapper> mapAllMemberwiseShareDataList(List<Member> memberList);

	@Named("all")
	@Mappings({ 
	})
    MemberwiseShareReportWrapper mapAllMemberwiseShareData(Member member);
	
	

@AfterMapping
default void fillInProperties(final Member member,
      @MappingTarget final MemberwiseShareReportWrapper wrapper ) {

		member.getHouse().forEach(action->{
		  if(action.getAddressType().contentEquals("PERMANENT"))
		  {
			  wrapper.setAddress(action.getAddressLine1()+", "+action.getAddressLine2()+", "+action.getCity()+", "+action.getState()+", PIN - "+action.getPincode());
		  }
		});
		wrapper.setName(member.getFirstName()+" "+member.getMiddleName()+ " " +member.getLastName());
		wrapper.setNoOfShares(member.getSharesAllocation().stream().mapToInt(SharesAllocation::getNoOfShares).sum());
		wrapper.setId(member.getId());

}



}

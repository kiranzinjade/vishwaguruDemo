package com.techvg.vks.membership.reports.namunaj.mapper;

import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.membership.reports.namunaj.model.NamunaJWrapper;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel="spring")
public interface  NamunaJMapper {
	
	@IterableMapping(qualifiedByName="nonactive")
    List<NamunaJWrapper> mapNonActiveMemberDataList(List<Member> memberlist);

	@IterableMapping(qualifiedByName = "active")
    List<NamunaJWrapper> mapActiveMemberDataList(List<Member> memberlist);

	@Named("active")
	@Mappings({

			@Mapping(source = "created", target = "classificationDate"), @Mapping(source = "email", target = "email"),
			@Mapping(ignore = true, target = "address"), @Mapping(ignore = true, target = "name")

	})
    NamunaJWrapper mapActiveMemberData(Member member);

	@Named("nonactive")
	@Mappings({

			@Mapping(source = "inactiveDate", target = "classificationDate"),
			@Mapping(source = "email", target = "email"), @Mapping(ignore = true, target = "address"),
			@Mapping(ignore = true, target = "name")

	})
    NamunaJWrapper mapNonActiveMemberData(Member member);

@AfterMapping
default void fillInProperties(final Member member,
      @MappingTarget final NamunaJWrapper wrapper ) {
  member.getHouse().forEach(action->{ 
	  if(action.getAddressType().contentEquals("PERMANENT"))
	  {
		  System.out.println(action.getAddressLine1());
		  wrapper.setAddress(action.getAddressLine1()+", "+action.getAddressLine2()+", "+action.getCity()+", "+action.getState()+", PIN - "+action.getPincode());	  
	  }
  });
	
	
  wrapper.setName(member.getLastName()+" "+member.getFirstName()+" "+member.getMiddleName());
	
}
}

package com.techvg.vks.loan.mapper;

import java.util.List;

import org.aspectj.lang.annotation.After;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.techvg.vks.loan.domain.LoanWatap;
import com.techvg.vks.loan.model.LoanWatapDto;

@Mapper(componentModel="spring")
public interface LoanWatapMapper {
	    LoanWatap toDomain(LoanWatapDto dto);
	    LoanWatapDto toDto(LoanWatap domain);
	    LoanWatap toNewDomain(LoanWatap domain);
	    List<LoanWatapDto> toDtoList(List<LoanWatap> domainList);
	    
	    @AfterMapping()
	    default void getMamberDetails(@MappingTarget LoanWatapDto dto ,LoanWatap domain) {
	    	
	    	dto.setMemberFullName(domain.getMember().getFirstName()+" "+domain.getMember().getMiddleName()+" "+domain.getMember().getLastName());
	        dto.setCropName(domain.getCrop().getCropName());
	    }
	    
}

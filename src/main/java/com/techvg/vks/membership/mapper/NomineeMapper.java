package com.techvg.vks.membership.mapper;

import com.techvg.vks.membership.domain.Nominee;
import com.techvg.vks.membership.model.NomineeDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NomineeMapper  {

	
	NomineeDto nomineeToNomineeDto(Nominee nominee);
    Nominee nomineeDtoToNominee(NomineeDto nomineeDto);
    List<NomineeDto> domainToDtoList(List<Nominee> domainList);
}

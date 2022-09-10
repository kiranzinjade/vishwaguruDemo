package com.techvg.vks.membership.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.techvg.vks.deposit.domain.DepositLedger;
import com.techvg.vks.deposit.model.DepositLedgerDto;
import com.techvg.vks.membership.domain.Land;
import com.techvg.vks.membership.model.LandDto;

@Mapper(componentModel = "spring")
public interface LandMapper {
	
	LandDto landToLandDto(Land land);
    Land landDtoToLand(LandDto landDto);
    
	List<LandDto> toDtoList(List<Land> domainList);

    
}

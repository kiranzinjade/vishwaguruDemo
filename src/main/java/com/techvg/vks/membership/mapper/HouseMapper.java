package com.techvg.vks.membership.mapper;

import org.mapstruct.Mapper;

import com.techvg.vks.membership.domain.House;
import com.techvg.vks.membership.model.HouseDto;

@Mapper(componentModel = "spring")
public interface HouseMapper {

	HouseDto houseToHouseDto(House house);
	House houseDtoToHouse(HouseDto houseDto);
}

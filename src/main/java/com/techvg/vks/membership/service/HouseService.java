package com.techvg.vks.membership.service;

import com.techvg.vks.membership.model.HouseDto;
import com.techvg.vks.membership.model.HousePageList;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.Set;

public interface HouseService {

	Set<HouseDto> addNewHouse(Long memberId, HouseDto houseDto);

	HouseDto updateHouse(Long houseId, HouseDto houseDto, Authentication authentication);

	HouseDto deleteHouseById(Long houseId);

	HousePageList listHouse(Pageable pageable);

	HouseDto getHouseById(Long houseId);

	
}

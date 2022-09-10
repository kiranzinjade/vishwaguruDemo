package com.techvg.vks.share.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.techvg.vks.share.domain.Shares;
import com.techvg.vks.share.model.SharesDto;

@Mapper(uses = { DateMapper.class,SharesAllocationMapper.class}, componentModel = "spring")
public interface SharesMapper  {

	@Mapping(source = "shares.sharesAllocation", target = "sharesAllocationDtoSet")
	SharesDto sharesToSharesDto(Shares shares);

    @InheritInverseConfiguration
    Shares sharesDtoToShares(SharesDto sharesDto);
}

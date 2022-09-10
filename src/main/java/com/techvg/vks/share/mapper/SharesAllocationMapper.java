package com.techvg.vks.share.mapper;

import com.techvg.vks.share.domain.SharesAllocation;
import com.techvg.vks.share.model.SharesAllocationDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(uses = { DateMapper.class }, componentModel = "spring")
public interface SharesAllocationMapper {

	SharesAllocationDto sharesAllocationToSharesAllocationDto(SharesAllocation sharesAllocation);
	SharesAllocation sharesAllocationDtoToSharesAllocation(SharesAllocationDto sharesAllocationDto);
	List<SharesAllocationDto> toDtoList(List<SharesAllocation> domainList);

	@AfterMapping
	default void updateDetails(SharesAllocation domain , @MappingTarget SharesAllocationDto dto ) {
		dto.setSharePrice(domain.getShares().getSharePrice());
		dto.setTotalSharePrice(domain.getShares().getSharePrice() * domain.getNoOfShares());
		dto.setMemberId(domain.getMember().getId());
		dto.setFullName(domain.getMember().getFirstName()+" "+domain.getMember().getMiddleName()+" "+domain.getMember().getLastName());
	}
}


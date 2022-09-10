package com.techvg.vks.share.reports.ShareCapitalLedger;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueMappingStrategy;

import com.techvg.vks.share.domain.SharesAllocation;

@Mapper(componentModel="spring")
public interface ShareCapitalLedgerMapper {

	@IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
	public List<ShareCapitalLedgerWrapper> mapAllShareCapitalLedgerList(List<SharesAllocation> sharesAllocationList);
	
@Mappings({ 
		
		@Mapping(ignore=true,target="name"),
		@Mapping(source="noOfShares",target="noOfShares")
		
	})
	public ShareCapitalLedgerWrapper mapAllShareCapitalLedger(SharesAllocation sharesAllocation);
	
	
	@AfterMapping
	default void fillInProperties(final SharesAllocation sharesAllocation,
	      @MappingTarget final ShareCapitalLedgerWrapper wrapper ) {
			wrapper.setMemberId(sharesAllocation.getMember().getId());
			wrapper.setName(sharesAllocation.getMember().getFirstName()+" "+sharesAllocation.getMember().getMiddleName()+ " " +sharesAllocation.getMember().getLastName());
		/*
		 * wrapper.setNoOfShares(sharesAllocation.getNoOfShares());
		 * wrapper.setSharesIdFrom(sharesAllocation.getSharesIdFrom());
		 * wrapper.setSharesIdTo(sharesAllocation.getSharesIdTo());
		 * wrapper.setShareCertificateNo(sharesAllocation.getShareCertificateNo());
		 */
			
			
	}

}

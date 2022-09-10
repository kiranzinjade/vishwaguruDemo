package com.techvg.vks.share.service;

import com.techvg.vks.share.model.ExceedShareCount;
import com.techvg.vks.share.model.SharesAllocationDto;
import com.techvg.vks.share.model.SharesAllocationPageList;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SharesAllocationService {

	SharesAllocationDto allocateNewShare(SharesAllocationDto sharesAllocationDto, Long memberId, Long shareApplicationId);
	
	SharesAllocationPageList listSharesAllocated(Pageable pageable);
	
	SharesAllocationDto deleteAllocatedSharesById(Long shareAllocationId);
	
	SharesAllocationDto updateAllocatedShares(Long shareAllocationId, SharesAllocationDto sharesAllocationDto);

	SharesAllocationDto getAllocatedSharesById(Long shareAllocationId);
	
	ExceedShareCount exceedShareLimit(Long memberId, Double shareAmount);

    List<SharesAllocationDto> getAllocatedShares(Long memberId);

	List<SharesAllocationDto> getAllShares(Long memberId);
}

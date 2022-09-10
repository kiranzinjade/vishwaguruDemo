package com.techvg.vks.share.service;

import com.techvg.vks.share.domain.Shares;
import com.techvg.vks.share.model.SharesAllocationDto;
import com.techvg.vks.share.model.SharesDto;

import java.util.List;

public interface SharesSurrenderService {

	SharesDto sharesSurrender(Long memberId);
	Shares sharesSurrenderDomain(Long memberId);
	List<SharesAllocationDto> sharesSurrenderDetails(Long memberId);

}

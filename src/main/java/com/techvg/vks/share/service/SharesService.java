package com.techvg.vks.share.service;

import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.share.domain.MemberShareAcc;
import com.techvg.vks.share.domain.Shares;
import com.techvg.vks.share.model.SharesDto;
import com.techvg.vks.share.model.SharesPageList;
import org.springframework.data.domain.Pageable;

public interface SharesService {

	SharesDto addNewShare(SharesDto sharesDto, Long memberId);
	
	SharesPageList listShares(Pageable pageable);

	SharesDto deleteShareById(Long shareApplicationId);

	SharesDto updateShare(Long shareApplicationId, SharesDto sharesDto);

	SharesDto getShareById(Long shareApplicationId);

	Shares addMemberShare(Member member, int shareCount, int useSuspenseAcc, String applicationType,String particulars);

	boolean updateShare(Shares shares);

	MemberShareAcc getMemberShareAcc(Long memberId);

	MemberShareAcc getMemberShareAccByAccNo(Long accNo);

	double getShareAmount();

}




package com.techvg.vks.share.service;

import com.techvg.vks.share.domain.SharesAllocation;
import com.techvg.vks.share.model.ShareHistoryPageList;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface ShareHistoryService {

	 void saveShareHistory(SharesAllocation shareAllocation);
	 ShareHistoryPageList getShareHistory(Pageable page);
}

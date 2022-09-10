package com.techvg.vks.share.service;

import com.techvg.vks.share.domain.ShareHistory;
import com.techvg.vks.share.domain.SharesAllocation;
import com.techvg.vks.share.mapper.ShareHistoryMapper;
import com.techvg.vks.share.model.ShareHistoryPageList;
import com.techvg.vks.share.repository.ShareHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShareHistoryServiceImpl implements ShareHistoryService {

	private final ShareHistoryRepository  shareHistoryRepository;
	private final ShareHistoryMapper shareHistoryMapper;
	@Override
	public void saveShareHistory(SharesAllocation shareAllocation) {
		
		ShareHistory shareHistory =  ShareHistory
				                            .builder()
				                            .allocationDate(shareAllocation.getAllocationDate())
				                            .memeberUniqueId(shareAllocation.getMember().getMemberUniqueId())
				                            .noOfShares(shareAllocation.getNoOfShares())
				                            .sharesIdFrom(shareAllocation.getSharesIdFrom())
				                            .sharesIdTo(shareAllocation.getSharesIdTo())
				                            .printedDate(new java.util.Date())
				                            .issuedDate(shareAllocation.getAllocationDate())
				                            .shareTotalAmount(shareAllocation.getShares().getSharePrice())
				                            .build();
		
		
		shareHistoryRepository.save(shareHistory);
		

	}

	@Override
	public ShareHistoryPageList getShareHistory(Pageable page) {
		Page<ShareHistory> shareHistoryPage;
		shareHistoryPage = shareHistoryRepository.findAll(page);

		return new ShareHistoryPageList(shareHistoryPage
										.getContent()
										.stream()
										.map(shareHistoryMapper::shareHistoryToShareHistoryDto)
										.collect(Collectors.toList()),
										PageRequest
											.of(shareHistoryPage.getPageable().getPageNumber(),
													shareHistoryPage.getPageable().getPageSize()),
										shareHistoryPage.getTotalElements());
		
	}

}

package com.techvg.vks.share.service;

import com.techvg.vks.config.ShareConstants;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.membership.repository.MemberRepository;
import com.techvg.vks.share.domain.Shares;
import com.techvg.vks.share.domain.SharesAllocation;
import com.techvg.vks.share.mapper.SharesAllocationMapper;
import com.techvg.vks.share.mapper.SharesMapper;
import com.techvg.vks.share.model.SharesAllocationDto;
import com.techvg.vks.share.model.SharesDto;
import com.techvg.vks.share.repository.SharesAllocationRepository;
import com.techvg.vks.share.repository.SharesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SharesSurrenderServiceImpl implements SharesSurrenderService {
	
	private final SharesRepository sharesRepository;
	private final MemberRepository memberRepository;
	private final SharesAllocationRepository sharesAllocationRepository;
	private final SharesMapper sharesMapper;
	private final SharesAllocationMapper sharesAllocationMapper;

	public SharesDto sharesSurrender(Long memberId) {
		return sharesMapper.sharesToSharesDto(sharesSurrenderDomain(memberId));
	}

	@Override
	public Shares sharesSurrenderDomain(Long memberId) {
	
		List<Shares> sharessurrenderlist=sharesRepository.findByMemberId(memberId);  
		 
	    List<SharesAllocation> shareAllocationDetails=sharesAllocationRepository.findByMemberId(memberId);  
		 	
		 
		 int numberOfShare=0;
		 for(SharesAllocation sharesallocation:shareAllocationDetails) { 
			 numberOfShare=numberOfShare+ sharesallocation.getNoOfShares();	  
		 } 
		 Member member = memberRepository.findById(memberId).orElseThrow(NotFoundException::new);		
		 Shares shares= new Shares();
		 shares.setApplicationType(ShareConstants.APPS_TYPE_SURRENDER);
		 shares.setShareApplicationStatus(ShareConstants.APPLICATION_STATUS_PENDING);
		 shares.setApplicationDate(new Date());
		 shares.setNumberOfShares(numberOfShare);
		 shares.setSharePrice(sharessurrenderlist.get(0).getSharePrice());// needed to taken master data
		 shares.setShareAmount(sharessurrenderlist.get(0).getSharePrice()*numberOfShare);
		 shares.setMember(member);
		 sharesRepository.save(shares);  
		 	  
		 return  shares;
	}

	@Override
	public List<SharesAllocationDto> sharesSurrenderDetails(Long memberId) {
		
		 List<SharesAllocation> shareAllocationDetails=sharesAllocationRepository.findByMemberId(memberId);  
		
		 List<SharesAllocationDto> sharesAllocationList= new ArrayList<>();
			
		 for(SharesAllocation sharesallocation:shareAllocationDetails) { 
			 sharesallocation.setSharesAllocationStatus(ShareConstants.ALLOCATION_STATUS_DEALLOCATE);
			 sharesAllocationRepository.save(sharesallocation); 
			 sharesAllocationList.add(sharesAllocationMapper.sharesAllocationToSharesAllocationDto(sharesallocation));
		 } 
	   
	    return sharesAllocationList;
		
	}

}

package com.techvg.vks.share.service;

import com.techvg.vks.config.ShareConstants;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.membership.repository.MemberRepository;
import com.techvg.vks.share.domain.Shares;
import com.techvg.vks.share.domain.SharesAllocation;
import com.techvg.vks.share.mapper.SharesAllocationMapper;
import com.techvg.vks.share.model.ExceedShareCount;
import com.techvg.vks.share.model.SharesAllocationDto;
import com.techvg.vks.share.model.SharesAllocationPageList;
import com.techvg.vks.share.repository.SharesAllocationRepository;
import com.techvg.vks.share.repository.SharesRepository;
import com.techvg.vks.society.domain.SocietyConfiguration;
import com.techvg.vks.society.repository.SocietyConfigurationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SharesAllocationServiceImpl implements SharesAllocationService {

	private final SharesAllocationRepository sharesAllocationRepository;
	private final SharesAllocationMapper sharesAllocationMapper;
	private final MemberRepository memberRepository;
	private final SharesRepository sharesRepository;
	private final SocietyConfigurationRepository societyConfigurationRepository;

	@Override
	@Transactional
	public SharesAllocationDto allocateNewShare(SharesAllocationDto sharesAllocationDto, Long memberId, Long shareApplicationId) {
		Member member = memberRepository.findById(memberId).orElseThrow(NotFoundException::new);
		SharesAllocation sharesAllocation = this.sharesAllocationMapper.sharesAllocationDtoToSharesAllocation(sharesAllocationDto); 
		Shares shares = sharesRepository.findById(shareApplicationId).orElseThrow(NotFoundException::new);
		sharesAllocation.setMember(member);
		sharesAllocation.setShares(shares);
		sharesAllocation.setNoOfShares(sharesAllocationDto.getSharesIdTo() - sharesAllocation.getSharesIdFrom());
		sharesAllocation =   sharesAllocationRepository.save(sharesAllocation);
		return sharesAllocationMapper.sharesAllocationToSharesAllocationDto(sharesAllocation);
	}

	public SharesAllocationPageList listSharesAllocated(Pageable pageable) {
		Page<SharesAllocation> sharesAllocationPage;
		sharesAllocationPage = sharesAllocationRepository.findByisDeleted(false, pageable);
		return new SharesAllocationPageList(sharesAllocationPage
										.getContent()
										.stream()
										.map(sharesAllocationMapper::sharesAllocationToSharesAllocationDto)
										.collect(Collectors.toList()),
										PageRequest
											.of(sharesAllocationPage.getPageable().getPageNumber(),
											sharesAllocationPage.getPageable().getPageSize()),
											sharesAllocationPage.getTotalElements());

	}
	
	public SharesAllocationDto deleteAllocatedSharesById(Long shareAllocationId) {
		SharesAllocation sharesAllocation = sharesAllocationRepository.findById(shareAllocationId).orElseThrow(NotFoundException::new);
		if (sharesAllocation != null) {
			sharesAllocation.setIsDeleted(true);
			sharesAllocationRepository.save(sharesAllocation);
		}
		return sharesAllocationMapper.sharesAllocationToSharesAllocationDto(sharesAllocation);
	}

	public SharesAllocationDto updateAllocatedShares(Long shareAllocationId, SharesAllocationDto sharesAllocationDto) {
		SharesAllocation sharesAllocation = sharesAllocationRepository.findById(shareAllocationId).orElseThrow(NotFoundException::new);
		sharesAllocationDto.setId(sharesAllocation.getId());	
		SharesAllocation sharesAllocation1 = sharesAllocationMapper.sharesAllocationDtoToSharesAllocation(sharesAllocationDto);
		sharesAllocation1.setMember(sharesAllocation.getMember());
		sharesAllocation1.setShares(sharesAllocation.getShares());
		return sharesAllocationMapper.sharesAllocationToSharesAllocationDto(sharesAllocationRepository.save(sharesAllocation1));
	}

	public SharesAllocationDto getAllocatedSharesById(Long shareAllocationId) {

		log.debug("REST request to get ShareAllocation : {}", shareAllocationId);
		SharesAllocation sharesAllocation = sharesAllocationRepository.findById(shareAllocationId).orElseThrow(
				() -> new NotFoundException("No Share found for Id : " +shareAllocationId));
		return sharesAllocationMapper.sharesAllocationToSharesAllocationDto(sharesAllocation);
	}

	
	@Override
	public ExceedShareCount exceedShareLimit(Long memberId, Double shareAmount) {

		List<SharesAllocation> sharesAllocation = sharesAllocationRepository.findByMemberId(memberId);

		ExceedShareCount exceedShareCount = new ExceedShareCount();
		
		int numberOfShare = 0;
		for (SharesAllocation sharesallocation : sharesAllocation) {
			numberOfShare = numberOfShare + sharesallocation.getNoOfShares();
		}

		SocietyConfiguration societyConfiguration = societyConfigurationRepository
				.findByConfigName(ShareConstants.SINGLE_SHARE_PRICE).orElseThrow(NotFoundException::new);

		double perShareValue = societyConfiguration.getValue();

		double allocatedShareAmount = numberOfShare * perShareValue;

		double finalShareAmount = allocatedShareAmount + shareAmount;

		societyConfiguration = societyConfigurationRepository.findByConfigName(ShareConstants.MAX_SHARE_AMOUNT)
				.orElseThrow(NotFoundException::new);

		double maxShareAmount = societyConfiguration.getValue();

		int canAllocateShareCount = 0;
		

		if (finalShareAmount > maxShareAmount) {

			double remainingShareAmount = maxShareAmount - allocatedShareAmount;
			canAllocateShareCount = (int) (remainingShareAmount / perShareValue);

			if(remainingShareAmount > 0 ) {
			exceedShareCount.setRemainingShareAmount(remainingShareAmount);
			exceedShareCount.setCanAllocateShareCount(canAllocateShareCount);
			} else {
				exceedShareCount.setRemainingShareAmount(0.0);
				exceedShareCount.setCanAllocateShareCount(0);
			}

		} else {
			canAllocateShareCount = (int)(shareAmount / perShareValue);
			exceedShareCount.setAllocatedShareAmount(shareAmount);
			exceedShareCount.setAllocatedShareCount(canAllocateShareCount);
		}
		return exceedShareCount;
	}

	@Override
	public List<SharesAllocationDto> getAllocatedShares(Long memberId) {
		return sharesAllocationMapper.toDtoList(sharesAllocationRepository.findByMemberId(memberId));
	}
	
	@Override
	public List<SharesAllocationDto> getAllShares(Long memberId) {
		Member member = memberRepository.findById(memberId).orElseThrow(NotFoundException::new);
		return sharesAllocationMapper.toDtoList(sharesAllocationRepository.findByMember(member));
	}

}

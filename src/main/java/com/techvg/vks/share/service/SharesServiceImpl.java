package com.techvg.vks.share.service;

import com.techvg.vks.accounts.domain.AccountMapping;
import com.techvg.vks.accounts.service.AccountMappingService;
import com.techvg.vks.common.SocietyConfig;
import com.techvg.vks.config.MappingName;
import com.techvg.vks.config.ShareConstants;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.idgenerator.MemShareAccSeq;
import com.techvg.vks.idgenerator.repository.MemShareAccSeqRepository;
import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.membership.repository.MemberRepository;
import com.techvg.vks.share.domain.MemberShareAcc;
import com.techvg.vks.share.domain.Shares;
import com.techvg.vks.share.domain.SharesAllocation;
import com.techvg.vks.share.mapper.SharesMapper;
import com.techvg.vks.share.model.SharesDto;
import com.techvg.vks.share.model.SharesPageList;
import com.techvg.vks.share.repository.MemberShareAccRepository;
import com.techvg.vks.share.repository.SharesAllocationRepository;
import com.techvg.vks.share.repository.SharesRepository;
import com.techvg.vks.society.service.SocietyConfigurationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SharesServiceImpl implements SharesService {

	private final SharesRepository sharesRepository;
	private final SharesAllocationRepository sharesAllocRepository;
	private final SharesMapper sharesMapper;
	private final MemberRepository memberRepository;
	private final AccountMappingService accountMappingService;
	private final SocietyConfigurationService configService;
	private final MemShareAccSeqRepository memShareAccSeqRepository;
	private final MemberShareAccRepository memberShareAccRepository;

	@Override
	@Transactional
	public SharesDto addNewShare(SharesDto sharesDto, Long memberId) {
		Member member = memberRepository.findById(memberId).orElseThrow(NotFoundException::new);
		Shares shares = this.sharesMapper.sharesDtoToShares(sharesDto);
		shares.setMember(member);
		shares = sharesRepository.save(shares);
		
		// Kaustubh's code for share certificate generation
		
		return sharesMapper.sharesToSharesDto(shares);
	}

	public SharesPageList listShares(Pageable pageable) {
		Page<Shares> sharePage;
		sharePage = sharesRepository.findByisDeleted(false, pageable);
		return new SharesPageList(sharePage
										.getContent()
										.stream()
										.map(sharesMapper::sharesToSharesDto)
										.collect(Collectors.toList()),
										PageRequest
											.of(sharePage.getPageable().getPageNumber(),
											sharePage.getPageable().getPageSize()),
											sharePage.getTotalElements());

	}

	public SharesDto deleteShareById(Long shareApplicationId) {
		Shares shares = sharesRepository.findById(shareApplicationId).orElseThrow(NotFoundException::new);
		if (shares != null) {
			shares.setIsDeleted(true);
			sharesRepository.save(shares);
		}
		return sharesMapper.sharesToSharesDto(shares);
	}

	public SharesDto updateShare(Long shareApplicationId, SharesDto sharesDto) {
		Shares shares = sharesRepository.findById(shareApplicationId).orElseThrow(NotFoundException::new);
		sharesDto.setId(shares.getId());	
		Shares shares1 = sharesMapper.sharesDtoToShares(sharesDto);
		shares1.setMember(shares.getMember());
		return sharesMapper.sharesToSharesDto(sharesRepository.save(shares1));
	}

	public SharesDto getShareById(Long shareApplicationId) {
		log.debug("REST request to get ShareApplication : {}", shareApplicationId);
		Shares shares = sharesRepository.findById(shareApplicationId).orElseThrow(
				() -> new NotFoundException("No Share found for Id : " +shareApplicationId));
		return sharesMapper.sharesToSharesDto(shares);
	}

	@Override
	@Transactional
	public Shares addMemberShare(Member member, int shareCount, int useSuspenseAcc, String applicationType,
									String particulars) {
		boolean isShareAllocated = false;
		Optional<MemberShareAcc> accObjOptional = memberShareAccRepository.findByMemberId(member.getId());
		if (!accObjOptional.isPresent()){
			addShareAcc(member, useSuspenseAcc );
		}

		Shares share = addShares(member, shareCount, applicationType);
			if(useSuspenseAcc == 0) {
				isShareAllocated = addShareDetails(member, share, shareCount, particulars);
			}
		if(isShareAllocated){
			share.setShareApplicationStatus(ShareConstants.APPLICATION_STATUS_ACCEPTED);
			sharesRepository.save(share);
		}
		return share;
	}


	private Shares addShares(Member member, int shareCount, String applicationType){
		Shares share = new Shares();
		share.setApplicationDate(new Date());
		share.setApplicationType(applicationType);
		share.setNumberOfShares(shareCount);
		share.setSharePrice(SocietyConfig.getValue(ShareConstants.SINGLE_SHARE_PRICE));
		share.setShareAmount(shareCount * SocietyConfig.getValue(ShareConstants.SINGLE_SHARE_PRICE));
		share.setShareApplicationStatus(ShareConstants.APPLICATION_STATUS_PENDING);
		share.setMember(member);

		return sharesRepository.save(share);
	}

	private MemberShareAcc addShareAcc(Member member, int useSuspenseAcc ){

		MemberShareAcc shareAcc = new MemberShareAcc();
		// Get the ledger mapped with the mapping
		String mappingName = MappingName.MEMBER_SHARE;
		if(useSuspenseAcc == 1) {
			mappingName = MappingName.MEMBER_SHARE_SUSPENSE;
		}
		AccountMapping accountMapping = accountMappingService.getAccountMappingByName(mappingName);

		shareAcc.setParentAccId(accountMapping.getLedgerAccount().getId());
		shareAcc.setParentAccHeadCode(accountMapping.getLedgerAccHeadCode());
		if(useSuspenseAcc != 1) {
			shareAcc.setShareAccName(accountMapping.getLedgerAccHeadCode() + " " + member.getFirstName() + " " + member.getLastName());

			// Get A/C no from sequence generated
			MemShareAccSeq no = new MemShareAccSeq();
			memShareAccSeqRepository.save(no);
			shareAcc.setShareAccNumber(no.getMemShareAccNo()); // Should be set using auto seq based on if suspension or actual acc
		}
		shareAcc.setMember(member);
		memberShareAccRepository.save(shareAcc);

		return shareAcc;
	}

	private boolean addShareDetails(Member member,Shares share, int shareCount, String particular){
		boolean flag = false;
		boolean flag1 = false;
		SharesAllocation sharesAllocation = new SharesAllocation();
		sharesAllocation.setMember(member);
		sharesAllocation.setShares(share);
		sharesAllocation.setSharesAllocationStatus(ShareConstants.ALLOCATION_STATUS_ALLOCATE);
		sharesAllocation.setAllocationDate(new Date());
		sharesAllocation.setNoOfShares(shareCount);
		sharesAllocation.setParticulars(particular);
		sharesAllocation.setNoAndDateOfBoardResolution(member.getBoardResolutionNoAndDate());

		//Get next certificate serial number from Society Config
		int certificateNo = (int)(configService.getConfigurationValue(ShareConstants.SHARE_CERTIFICATE_NO));
		sharesAllocation.setShareCertificateNo(certificateNo);

		//Get next share serial number
		int startSerialNo = (int)configService.getConfigurationValue(ShareConstants.SHARE_SERIAL_NO);
		int endSerialNo = startSerialNo+shareCount-1;
		sharesAllocation.setSharesIdFrom(startSerialNo);
		sharesAllocation.setSharesIdTo(endSerialNo);

		sharesAllocRepository.save(sharesAllocation);
		member.addSharesAllocation(sharesAllocation);
		flag=true;

		flag = configService.updateConfigValue(ShareConstants.SHARE_SERIAL_NO, (double)endSerialNo+1);
		flag1 = configService.updateConfigValue(ShareConstants.SHARE_CERTIFICATE_NO, (double)certificateNo+1);
		return (flag && flag1);
	}

	@Override
	public boolean updateShare(Shares shares) {
		boolean flag = false;
		if(shares !=null) {
			sharesRepository.save(shares);
			flag = true;
		}
		return flag;
	}

	public MemberShareAcc getMemberShareAcc(Long memberId){
		Optional<MemberShareAcc> accObjOptional = memberShareAccRepository.findByMemberId(memberId);
		if (accObjOptional.isPresent()){
			return accObjOptional.get();
		}
		return null;
	}

	@Override
	public MemberShareAcc getMemberShareAccByAccNo(Long accNo) {
		return memberShareAccRepository.findByShareAccNumber(accNo);
	}

	@Override
	public double getShareAmount() {
		double shareAmount= sharesRepository.getShareAmount();
		return shareAmount;
	}

}


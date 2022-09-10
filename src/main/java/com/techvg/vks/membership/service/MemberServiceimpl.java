package com.techvg.vks.membership.service;

import com.techvg.vks.accounts.domain.Vouchers;
import com.techvg.vks.accounts.model.CashBookDto;
import com.techvg.vks.accounts.service.CashBookService;
import com.techvg.vks.accounts.service.vouchers.ShareRegistrationVouchersService;
import com.techvg.vks.common.SocietyConfig;
import com.techvg.vks.config.AccountConstants;
import com.techvg.vks.config.LoanConstants;
import com.techvg.vks.config.MemberConstants;
import com.techvg.vks.config.ShareConstants;
import com.techvg.vks.deposit.domain.Deposit;
import com.techvg.vks.deposit.domain.SavingAccount;
import com.techvg.vks.deposit.repository.DepositRepository;
import com.techvg.vks.deposit.repository.SavingAccountRepository;
import com.techvg.vks.exceptions.AlreadyExitsException;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.loan.domain.LoanDetails;
import com.techvg.vks.loan.repository.LoanDetailsRepository;
import com.techvg.vks.membership.domain.Document;
import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.membership.mapper.CustomMemberMapper;
import com.techvg.vks.membership.mapper.DocumentMapper;
import com.techvg.vks.membership.mapper.MemberMapper;
import com.techvg.vks.membership.model.DocumentDto;
import com.techvg.vks.membership.model.DocumentPageList;
import com.techvg.vks.membership.model.MemberDto;
import com.techvg.vks.membership.model.MemberPageList;
import com.techvg.vks.membership.reports.MemberwiseShareReport.MemberwiseShareReportMapper;
import com.techvg.vks.membership.reports.MemberwiseShareReport.MemberwiseShareReportWrapper;
import com.techvg.vks.membership.reports.MemberwiseShareReport.MemberwiseShareWrapperPageList;
import com.techvg.vks.membership.repository.DocumentRepository;
import com.techvg.vks.membership.repository.MemberRepository;
import com.techvg.vks.share.domain.Shares;
import com.techvg.vks.share.model.SharesAllocationDto;
import com.techvg.vks.share.service.SharesService;
import com.techvg.vks.share.service.SharesSurrenderService;
import com.techvg.vks.society.domain.SocietyConfiguration;
import com.techvg.vks.society.repository.SocietyConfigurationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceimpl implements MemberService {

	private final MemberRepository memberRepository;
	private final DocumentRepository documentRepository;
	private final LoanDetailsRepository loanDetailsRepository;
	private final DepositRepository depositRepository;
	private final SavingAccountRepository savingAccountRepository;
	private final MemberMapper memberMapper;
	private final DocumentMapper documentMapper;
	private final SharesService sharesService;
	private final SharesSurrenderService sharesSurrenderService;
	private final ShareRegistrationVouchersService shareRegistrationVouchersService;
	private final MemberwiseShareReportMapper memberShareReportMapper;
	private final SocietyConfigurationRepository societyConfigurationRepository;
	private final CustomMemberMapper customMemberMapper;
	private final CashBookService cashBookService;

	
	@Transactional
	public MemberDto addNewMember(MemberDto memberDto) {

		Optional<Member> member = memberRepository.findByAadharCard(memberDto.getAadharCard());
		Optional<Member> member2= memberRepository.findByPanCard(memberDto.getPanCard());
		if (member.isPresent()) {
			throw new AlreadyExitsException(
					"Member already exists with Adhaar card number : " + memberDto.getAadharCard());
		} else if(member2.isPresent()) {
				throw new AlreadyExitsException("Member already exists with Pan card number : " + memberDto.getPanCard());
			}else {
				memberDto.setStatus("A");
				memberDto.setApplicationDate(new Date());
				Member member1= memberRepository.save(memberMapper.memberDtoToMember(memberDto));
				member1 = memberRepository.save(member1);
				int useSuspenseAcc = (SocietyConfig.getValue(ShareConstants.USE_SUSPENSE_ACCOUNT)).intValue();

				Shares newShare = sharesService.addMemberShare(member1, memberDto.getNoOfShare(), useSuspenseAcc,
						ShareConstants.APPS_TYPE_NEW,ShareConstants.ALLOC_PARTICULARS_NEW );

				Vouchers voucher = shareRegistrationVouchersService.createMemberShareRegistrationReceiptVoucher(memberDto, member1.getId(), useSuspenseAcc);

				newShare.setVoucherNo(voucher.getVoucherNo());
				sharesService.updateShare(newShare);

				return memberMapper.memberToMemberDto(member1);
			}
	}

	public MemberDto addPhotoSignature(Long memberId,MemberDto member,MultipartFile photo, MultipartFile signature) {
			
			try {
				System.out.println("is empty obj add Document"+member.getDocumentDto());
		        if(member.getDocumentDto()==null) {
				addDocument(memberId,photo,signature);
		        }
		        else {
		        	System.out.println("is Update Document"+member.getDocumentDto());
		        	updateDocument(memberId,photo,signature);
		        }
			} catch (IOException e) {
				
				e.printStackTrace();
			}
	       return null;
	}

	
	@Override
	public MemberDto updateMember(Long memberId, MemberDto memberDto,Authentication authentication) {

		Member member = memberRepository.findById(memberId).orElseThrow(NotFoundException::new);
		memberDto.setId(member.getId());
		memberDto.setStatus("A");
		Member persistMember = memberMapper.memberDtoToMember(memberDto);
		persistMember.setShares(member.getShares());
		persistMember.setSharesAllocation(member.getSharesAllocation());
		return memberMapper.memberToMemberDto(memberRepository.save(persistMember));
	}

	@Override
	public MemberDto findById(Long memberId) {
		log.debug("REST request to get Member : {}", memberId);
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new NotFoundException("No member found for Id : " + memberId));

		return memberMapper.memberToMemberDto(member);

	}

	@Override
	public MemberDto deleteMemberById(Long memberId) {

		Member member = memberRepository.findById(memberId).orElseThrow(NotFoundException::new);
		if (member != null) {
			member.setStatus("I");
			memberRepository.save(member);
		}
		return memberMapper.memberToMemberDto(member);
	}

	@Override
	public MemberPageList listMembers(Pageable pageable) {
		Page<Member> memberPage;
		memberPage = memberRepository.findByStatus("A", pageable);

		return new MemberPageList(
				memberPage.getContent().stream().map(customMemberMapper::toCustomDto).collect(Collectors.toList()),
				PageRequest.of(memberPage.getPageable().getPageNumber(), memberPage.getPageable().getPageSize()),
				memberPage.getTotalElements());

	}

	@Override
	public DocumentDto addDocument(Long memberId, MultipartFile photo, MultipartFile signature) throws IOException {

		Member member = memberRepository.findById(memberId).orElseThrow(NotFoundException::new);

		Document document = Document.builder().member(member).photo(photo.getBytes())
				.photoFileName(photo.getOriginalFilename()).signature(signature.getBytes())
				.signatureFileName(signature.getOriginalFilename()).build();

		documentRepository.save(document);

		return null;
	}

	@Override
	public DocumentDto updateDocument(Long memberId, MultipartFile photo,
			MultipartFile signature) throws IOException {

		Member member = memberRepository.findById(memberId).orElseThrow(NotFoundException::new);
		Document document1 = documentRepository.findByMember_Id(memberId);
		Document document2=new Document();
		document2.setMember(member);
		document2.setId(document1.getId());
		document2.setPhoto(photo.getBytes());
		document2.setPhotoFileName(photo.getOriginalFilename());
		document2.setSignature(signature.getBytes());
		document2.setSignatureFileName(signature.getOriginalFilename());
			
		documentRepository.save(document2);
		return null;
	}

	@Override
	public DocumentDto deleteById(Long documentId) {

		Document document = documentRepository.findById(documentId).orElseThrow(NotFoundException::new);

		if (document != null) {

			document.setIsDeleted(true);
			documentRepository.save(document);
		}
		return null;
	}

	@Override
	public DocumentPageList listDocuments(Pageable pageable) {
		Page<Document> documentPage;
		documentPage = documentRepository.findByisDeleted(false, pageable);

		return new DocumentPageList(
				documentPage.getContent().stream().map(documentMapper::documentToDocumentDto)
						.collect(Collectors.toList()),
				PageRequest.of(documentPage.getPageable().getPageNumber(), documentPage.getPageable().getPageSize()),
				documentPage.getTotalElements());

	}
	
	@Override
	public void downloadCsvfile(PrintWriter writer) {
	
		List<Member> members =memberRepository.findByStatus("A");  
		String attendanceStatus;
		String memberName;
		 try (	   
				 CSVPrinter csvPrinter = new CSVPrinter( writer, CSVFormat.DEFAULT);
			    ) {
			      for (Member member : members) {
			        List<String> data = Arrays.asList(
			        	member.getId().toString(),
							memberName= member.getFirstName()+" "+member.getFatherName()+" "+member.getLastName(),
							attendanceStatus="Yes"
			          );  
			        csvPrinter.printRecord(data);
			      }
			      csvPrinter.flush();
			    } catch (Exception e) {
			      log.error(e.getLocalizedMessage());
			    }
		
	}

	@Override
	public List<MemberDto> listMembers() {
				return memberMapper.domainToDtoList(memberRepository.findMemberByLoanStatus(LoanConstants.LOAN_Active));
	}

	@Override
	public List<MemberDto> listMembersWithDeposits() {
		return memberMapper.domainToDtoList(memberRepository.findMemberByDeposits());
	}

	@Override
	public DocumentDto findDocumentByMemberId(Long memberId) {
		
				
		Document document = documentRepository.findByMember_Id(memberId);
		DocumentDto document1=documentMapper.documentToDocumentDto(document);
		
		return document1;
	}

	@Override
	public MemberDto closeMembership(Long memberId) {
		Member member = memberRepository.findById(memberId).orElseThrow(NotFoundException::new);
		List<LoanDetails> loanDetails = loanDetailsRepository.findAllActiveLoansForMember(memberId);
		List<Deposit> deposit = depositRepository.findOpenDepositsByMemberId(memberId);
		SavingAccount savingAccount = savingAccountRepository.findByMemberId(memberId);
		System.out.println("Loan Details-----------"+loanDetails);
		
		if(loanDetails.isEmpty() && deposit.isEmpty() && savingAccount==null ) {

			Shares share = sharesSurrenderService.sharesSurrenderDomain(memberId);
			List<SharesAllocationDto> sharesAllocationList = sharesSurrenderService.sharesSurrenderDetails(memberId);

			int shareCount = sharesAllocationList.stream().mapToInt(SharesAllocationDto::getNoOfShares).sum();
			SocietyConfiguration societyConfiguration = societyConfigurationRepository
					.findByConfigName(ShareConstants.SINGLE_SHARE_PRICE).orElseThrow(NotFoundException::new);
			double shareAmt = shareCount * societyConfiguration.getValue();

			share.setShareApplicationStatus(ShareConstants.APPLICATION_STATUS_ACCEPTED);
			sharesService.updateShare(share);
			shareRegistrationVouchersService.createShareRefundPaymentVoucher(shareAmt, member);
			updateCashBookEntry(member, shareAmt);
			member.setStatus("Closed");
			memberRepository.save(member);
		}
		else {
			if (loanDetails.size() > 0)
				throw new AlreadyExitsException("Existing Loans not Closed, Membership can not be closed  ");

			System.out.println("Saving Account Details---------" + savingAccount);
			if (savingAccount != null) {
				throw new AlreadyExitsException("Saving Account Status Active, Membership can not be closed. Saving A/C no : " + savingAccount.getAccountNo());
			}
			System.out.println("Deposit Details-----------" + deposit);

			if (deposit.size() > 0) {
				throw new AlreadyExitsException("Member has active deposits, Membership can not be closed.");
			}
		}
		return memberMapper.memberToMemberDto(member);
	}

	private boolean updateCashBookEntry(Member member, double shareAmt){
		CashBookDto cashBookDto = CashBookDto.builder()
				.transDate(new Date())
				.creditAmt(shareAmt)
				.debitAmt(0.0)
				.transType(AccountConstants.CREDIT)
				.isDeleted(false)
				.build();

		cashBookDto.setParticulars(member.getMemberShareAcc().getShareAccName());
		cashBookService.addCashbookEntry(cashBookDto);
		return true;
	}

	@Override
	public List<MemberwiseShareReportWrapper> listMemberShares() {
		List<MemberwiseShareReportWrapper> reportWrapperList =
		memberShareReportMapper.mapAllMemberwiseShareDataList(memberRepository.findByStatus(MemberConstants.ACTIVE));

		double singleSharePrice = societyConfigurationRepository.findByConfigName(ShareConstants.SINGLE_SHARE_PRICE)
				.orElseThrow(NotFoundException::new).getValue();

		reportWrapperList.forEach(action->{
			action.setValue((action.getNoOfShares()) * (singleSharePrice));
		});
	
		return reportWrapperList;
	}

	@Override
	public List<MemberDto> memberList() {
		
		return customMemberMapper.toCustomDtoList(memberRepository.findByStatus(MemberConstants.ACTIVE));
	}

	public List<MemberwiseShareReportWrapper> listMemberShares(Pageable page) {
		Page<Member> smList = memberRepository.findByStatus(MemberConstants.ACTIVE,page);
		List<MemberwiseShareReportWrapper> reportWrapperList =
		memberShareReportMapper.mapAllMemberwiseShareDataList(smList.getContent());

		double singleSharePrice = societyConfigurationRepository.findByConfigName(ShareConstants.SINGLE_SHARE_PRICE)
				.orElseThrow(NotFoundException::new).getValue();

		reportWrapperList.forEach(action->{
			action.setValue((action.getNoOfShares()) * (singleSharePrice));
		});
	
		return reportWrapperList;
	}


	@Override
	public MemberwiseShareWrapperPageList listMemberSharesPage(Pageable pageable) {
		
							
		Page<MemberwiseShareReportWrapper> memberwiseSharePage;
		memberwiseSharePage = new PageImpl<MemberwiseShareReportWrapper>(listMemberShares(pageable), pageable,
				listMemberShares().size());

		return new MemberwiseShareWrapperPageList(
				memberwiseSharePage.getContent().stream().collect(Collectors.toList()),
				PageRequest.of(memberwiseSharePage.getPageable().getPageNumber(),
						memberwiseSharePage.getPageable().getPageSize()),
				memberwiseSharePage.getTotalElements());

	}

	@Override
	public int getMembersCount() {
		int count = memberRepository.findMemberByStatus();
		System.out.println("member count-----------------------------------"+count);
		return count;
	}
}



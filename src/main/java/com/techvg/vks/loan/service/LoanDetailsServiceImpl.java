package com.techvg.vks.loan.service;


import com.techvg.vks.accounts.domain.AccountMapping;
import com.techvg.vks.accounts.domain.Vouchers;
import com.techvg.vks.accounts.service.AccountMappingService;
import com.techvg.vks.accounts.service.vouchers.BorrowingVoucherService;
import com.techvg.vks.accounts.service.vouchers.LoanVoucherService;
import com.techvg.vks.common.DateConverter;
import com.techvg.vks.common.RoundOff;
import com.techvg.vks.common.SocietyConfig;
import com.techvg.vks.config.*;
import com.techvg.vks.exceptions.AlreadyExitsException;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.idgenerator.STLoanAccSeq;
import com.techvg.vks.idgenerator.repository.STLoanAccSeqRepository;
import com.techvg.vks.loan.domain.*;
import com.techvg.vks.loan.mapper.*;
import com.techvg.vks.loan.model.*;
import com.techvg.vks.loan.repository.*;
import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.membership.repository.MemberRepository;
import com.techvg.vks.share.domain.Shares;
import com.techvg.vks.share.model.ExceedShareCount;
import com.techvg.vks.share.service.SharesAllocationService;
import com.techvg.vks.share.service.SharesService;
import com.techvg.vks.society.domain.*;
import com.techvg.vks.society.model.BorrowingLedgerDto;
import com.techvg.vks.society.model.BorrowingLedgerTransactionDto;
import com.techvg.vks.society.repository.*;
import com.techvg.vks.society.service.BorrowingLedgerService;
import com.techvg.vks.society.service.BorrowingLedgerTransactionService;
import com.techvg.vks.society.service.SocietyBankService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoanDetailsServiceImpl implements LoanDetailsService {

	private final LoanDetailsRepository loanDetailsRepository;
	private final LoanDetailsMapper loanDetailsMapper;
	private final DisbursementMapper disbursementMapper;
	private final RepaymentRepository  repaymentRepository;
	private final DisbursementRepository disbursementRepository;
	private final MemberRepository memberRepository;
	private final ListingPageMapper listingPageMapper;
	private final ViewLoanDetailsMapper viewLoanDetailsMapper;
	private final LoanDemandRepository loanDemandRepository;
	private final SharesAllocationService sharesAllocationService ;
	private final SocietyConfigurationRepository societyConfigurationRepository;
	private final LoanProductRepository loanProductRepository;
	private final InterestShortTermLoanRepository interestShortTermLoanRepository;
	private final AmortizationRepository amortizationRepository;
	private final LoanChargesRepository  loanChargesRepository;
	private final LoanVoucherService loanVoucherService;
	private final STLoanAccSeqRepository stLoanAccSeqRepository;
	private final AccountMappingService accountMappingService;
	private final SharesService sharesService;
	private final LoanBriefMapper loanBriefMapper;
	private final CropLoanDemandRepository cropLoanDemandRepository;
	private final CropRegistrationRepository cropRegistrationRepository;
	private final CropLoanDetailsMapper  CropLoanDetailsMapper;
	private final AmortizationMapper amortizationMapper;

	private final BorrowingLedgerService borrowingLedgerService;
	private final BorrowingLedgerTransactionService borrowingLedgerTransService;
	private final BorrowingVoucherService borrowingVoucherService;
	private final SocietyBankService societyBankService;
    private final LoanWatapRepository loanWatapRepository;
	public LoanDetailsDto registerSTLoan(LoanDetailsDto loanDetailsDto) {

		LoanDetails loanDetails = saveLoanDetails(loanDetailsDto);
		Shares shares = addShares(loanDetailsDto);
		Vouchers loanVoucher = loanVoucherService.createLoanPaymentVoucher(loanDetails);
		Vouchers shareVoucher = loanVoucherService.createSharePaymentVoucher(loanDetails, shares);
		if(shares !=null && shareVoucher!=null) {
			shares.setVoucherNo(shareVoucher.getVoucherNo());
			sharesService.updateShare(shares);
		}
		if(loanDetails !=null && loanVoucher!=null) {
			loanDetails.getDisbursements().forEach(action->{
				action.setVoucherNo(loanVoucher.getVoucherNo());
				disbursementRepository.save(action);
			});
		}
		if(loanDetails.getLoanType().equalsIgnoreCase(LoanConstants.SHORT_TERM_LOAN))
		   {
			updateLoanWatapStatus(loanDetails);
		   }
		return loanDetailsMapper.loanDetailsToLoanDetailsDto(loanDetails);
	}

	private boolean updateLoanWatapStatus(LoanDetails loanDetails) {
		LoanWatap loanWatap = loanDetails.getLoanDemand().getLoanWatap();
		loanWatap.setStatus(LoanConstants.LOAN_DISBURSED);
		loanWatapRepository.save(loanWatap);
		return true;
	}

	@Override
	public List<LoanBriefDto> getSTLoanList(Long memberId) {
		return loanBriefMapper.toDtoList(loanDetailsRepository.findByMember(memberId, LoanConstants.SHORT_TERM_LOAN));
	}

	@Override
	public List<LoanBriefDto> getMTLoanList(Long memberId) {
		return loanBriefMapper.toDtoList(loanDetailsRepository.findByMember(memberId, LoanConstants.MID_TERM_LOAN));
	}

	@Override
	public List<LoanBriefDto> getLTLoanList(Long memberId) {
		return loanBriefMapper.toDtoList(loanDetailsRepository.findByMember(memberId, LoanConstants.LONG_TERM_LOAN));
	}

	@Override
	public List<LoanBriefDto> getActiveLoanList() {
		return loanBriefMapper.toDtoList(loanDetailsRepository.findAllActiveLoans());
	}

	@Override
	public List<LoanDetailsDto> registerAllSTLoansByKmp(Integer slot) {
			List<LoanWatap> loanWatap=loanWatapRepository.findBySlot(slot);//
		LoanProduct cropLoanProduct = loanWatap.get(0).getLoanProduct();
		List<LoanDetailsDto> stLoanList = new ArrayList<>();
		loanWatap.forEach(action->{
			LoanDetailsDto loanDetailsDto = LoanDetailsDto.builder()
											.loanAmt(action.getLoanAmount())
											.loanEffectiveDate(new Date())
											.loanProductId(cropLoanProduct.getId())
											.memberId(action.getMember().getId())
											.loanType(cropLoanProduct.getProductType())
											.cropId(action.getCrop().getId())
											.build();
			stLoanList.add(registerSTLoan(loanDetailsDto));
		});

		return stLoanList;
	}

	private LoanDetails saveLoanDetails(LoanDetailsDto loanDetailsDto){
		
		long id = 0;
		List<LoanDetails> loanList=loanDetailsRepository.findByMemberId(loanDetailsDto.getMemberId());
		LoanDetails  loanDetails= new LoanDetails();
		Boolean flag=true;
		if(loanList.isEmpty()) {
			loanDetails = loanRegistration(loanDetailsDto);
		}
		else {

			 for (LoanDetails loan : loanList) {
				 if(loan.getLoanType().equals(loanDetailsDto.getLoanType()) && loan.getLoanStatus().equals("A")) {
						flag=false;
						System.out.println("loan is active");
						id=loan.getId();
					}
			   }
			if(!flag) {
				throw new AlreadyExitsException(
						"Previous Loan is Active, Loan No: "+id );
			}
			else {
				loanDetails = loanRegistration(loanDetailsDto);
			}

			}
		

		return loanDetails;
	}
	
	private LoanDetails loanRegistration(LoanDetailsDto loanDetailsDto) {
		
		LoanProduct loanProduct=new LoanProduct();
		List<LoanDemand> loanDemand=new ArrayList<>();
		CropRegistration crop=new CropRegistration();
		Member member = memberRepository.findById(loanDetailsDto.getMemberId())
				.orElseThrow(() -> new NotFoundException("No member found for Id : " + loanDetailsDto.getMemberId()));


		if(loanDetailsDto.getLoanProductId()!=null) {
		 loanProduct=loanProductRepository.findById(loanDetailsDto.getLoanProductId())
				.orElseThrow(() -> new NotFoundException("No Loan Product found for Id : " + loanDetailsDto.getLoanProductId()));
		 loanDemand=loanDemandRepository.findByMemberIdAndLoanProductId(member.getId(),loanProduct.getId());
		}
	
		if(loanDetailsDto.getCropId()!=null) {
			 crop=cropRegistrationRepository.findById(loanDetailsDto.getCropId())
					.orElseThrow(() -> new NotFoundException("No crop found for Id : " + loanDetailsDto.getCropId()));
			loanDemand=loanDemandRepository.findByMemberIdAndCropId(member.getId(),crop.getId());
		}
		

		LoanDetails  loanDetails= new LoanDetails();

	
		for(LoanDemand loandemand : loanDemand) {
			String mappingName = MappingName.ML_LOANS;
			if(loandemand.getStatus().equals(LoanConstants.LOAN_DEMAND_APPLIED) ||
					loandemand.getStatus().equals(LoanConstants.LOAN_DEMAND_LOANWATAP)	) {

				loanDetails.setMember(member);
				loanDetails.setLoanDemand(loandemand);

				if(loanDetailsDto.getLoanType().equalsIgnoreCase(LoanConstants.MID_TERM_LOAN)) {
					if(loandemand.getStatus().equals(LoanConstants.LOAN_DEMAND_APPLIED)) {
						loanDetails.setLoanType(LoanConstants.MID_TERM_LOAN);
						loanDetails.setLoanAmt(loanDetailsDto.getLoanAmt());
						loanDetails.setLoanEffectiveDate(loanDetailsDto.getLoanEffectiveDate());
						loanDetails.setCostOfInvestment(loanDetailsDto.getCostOfInvestment());
						loanDetails.setResolutionNo(loanDetailsDto.getResolutionNo());
						loanDetails.setResolutionDate(loanDetailsDto.getResolutionDate());
						loanDetails.setSocietyMeetingNo(loanDetailsDto.getSocietyMeetingNo());
						loanDetails.setDccbLoanNo(loanDetailsDto.getDccbLoanNo());
						loandemand.setStatus(LoanConstants.LOAN_DEMAND_FULLFILLED);
						loanDetails.setLoanStatus("A");
						loanDetails.setLoanProduct(loanProduct);
						Date plannedClosureDate = calculateLoanPlannedCloserDate(loanDetailsDto.getLoanEffectiveDate(),loanProduct.getDuration() );
						loanDetails.setLoanPlannedClosureDate(plannedClosureDate);
						loanDetails.setLoanClassification(LoanConstants.STANDARD);
					}
				}
				else if(loanDetailsDto.getLoanType().equalsIgnoreCase(LoanConstants.LONG_TERM_LOAN)) {
					if(loandemand.getStatus().equals(LoanConstants.LOAN_DEMAND_APPLIED)) {
						loanDetails.setLoanType(LoanConstants.LONG_TERM_LOAN);
						loanDetails.setLoanAmt(loanDetailsDto.getLoanAmt());
						loanDetails.setLoanEffectiveDate(loanDetailsDto.getLoanEffectiveDate());
						loanDetails.setCostOfInvestment(loanDetailsDto.getCostOfInvestment());
						loanDetails.setResolutionNo(loanDetailsDto.getResolutionNo());
						loanDetails.setResolutionDate(loanDetailsDto.getResolutionDate());
						loanDetails.setSocietyMeetingNo(loanDetailsDto.getSocietyMeetingNo());
						loanDetails.setDccbLoanNo(loanDetailsDto.getDccbLoanNo());
						loanDetails.setMortgageDeedNo(loanDetailsDto.getMortgageDeedNo());
						loanDetails.setMortgageDate(loanDetailsDto.getMortgageDate());
						loanDetails.setExtentMorgage(loanDetailsDto.getExtentMorgage());
						loandemand.setStatus(LoanConstants.LOAN_DEMAND_FULLFILLED);
						loanDetails.setLoanProduct(loanProduct);
						loanDetails.setLoanStatus("A");
						Date plannedClosureDate = calculateLoanPlannedCloserDate(loanDetailsDto.getLoanEffectiveDate(),loanProduct.getDuration() );
						loanDetails.setLoanPlannedClosureDate(plannedClosureDate);
						loanDetails.setLoanClassification(LoanConstants.STANDARD);
					}
				}
				else if(loanDetailsDto.getLoanType().equalsIgnoreCase(LoanConstants.SHORT_TERM_LOAN)){
					if(loandemand.getLoanWatap().getStatus().equalsIgnoreCase(LoanConstants.LOAN_WATAP_APPROVED)){
						mappingName = MappingName.ST_LOANS;
						loanDetails.setLoanAmt(loanDetailsDto.getLoanAmt());
						loanDetails.setLoanEffectiveDate(loanDetailsDto.getLoanEffectiveDate());
						loanDetails.setLoanClassification(LoanConstants.STANDARD);
						loanDetails.setLoanStatus("A");
						Date plannedClosureDate = calculateLoanPlannedCloserDate(loanDetailsDto.getLoanEffectiveDate(),crop.getMonthDuration() );
						loanDetails.setLoanPlannedClosureDate(plannedClosureDate);

						loanDetails.setLoanType(LoanConstants.SHORT_TERM_LOAN);
						loandemand.setStatus(LoanConstants.LOAN_DEMAND_FULLFILLED);
						loanProduct = loandemand.getLoanWatap().getLoanProduct();
						loanDetails.setLoanProduct(loanProduct);
						loandemand.setLoanProduct(loanProduct);
					}
				}
				// Create Loan Account No from sequence
				STLoanAccSeq no = new STLoanAccSeq();
				stLoanAccSeqRepository.save(no);
				Long loanAccNo = no.getStLoanAccNo();
				loanDetails.setLoanAccountNo(loanAccNo);

				// Get the ledger mapped with the mapping
				AccountMapping accountMapping = accountMappingService.getAccountMappingByName(mappingName);
				loanDetails.setParentAccId(accountMapping.getLedgerAccount().getId());
				loanDetails.setParentAccHeadCode(accountMapping.getLedgerAccHeadCode());
				loanDetails.setLoanAccName(accountMapping.getLedgerAccHeadCode() + " " + member.getFirstName() + " " + member.getLastName());

				loanDetails.setDisbursementAmt(loanDetailsDto.getLoanAmt());
				loanDetails.setDisbursementStatus(LoanConstants.FULL_DISBURSEMENT);

				Disbursement disbursement = new Disbursement();
				disbursement.setLoanDetails(loanDetails);
				disbursement.setDisbursementDate(loanDetailsDto.getLoanEffectiveDate());
				disbursement.setDisbursementNo(1);
				disbursement.setPaymentMode(LoanConstants.TRANSFER);
				disbursement.setLoanType(loanDetailsDto.getLoanType());
				disbursement.setDisbursementAmt(loanDetailsDto.getLoanAmt());
				disbursement.setDisbursementStatus(LoanConstants.FULL_DISBURSEMENT);

				loanDetails.addToDisbursement(disbursement);
// Loan Saved
				 loanDetailsRepository.save(loanDetails);

				if( (loanDetailsDto.getLoanType().equalsIgnoreCase(LoanConstants.MID_TERM_LOAN)) ||
					(loanDetailsDto.getLoanType().equalsIgnoreCase(LoanConstants.LONG_TERM_LOAN)) ) {
					// Update Borrowing ledger
					updateBorrowingLedger(loanDetails);
					// Create borrowing vouchers
					borrowingVoucherService.createBorrowingReceiptVoucherForMLLoan(loanDetails);
				}

				scheduleAmortization(loanDetails);
				if(!loanDetailsDto.getLoanType().equals(LoanConstants.SHORT_TERM_LOAN)) {
					if(loanDetailsDto.getLandGatno()!=null) {
						member.getLand().forEach(action1->{
							if(loanDetailsDto.getLandGatno().equals(action1.getLandGatno())) {
								action1.setMLLoanNo(loanDetails.getId());
								loanDetails.setBenefitingArea(loanDetailsDto.getBenifitingArea());
							}
						});

					}
				 }
				}
				else if(loandemand.getStatus().equals(LoanConstants.LOAN_DEMAND_FULLFILLED)) {
					throw new AlreadyExitsException(
							"Loan Registration is already done for this demand - " + loandemand.getId());
				}
		}

		return loanDetails;
	}

	private boolean updateBorrowingLedger(LoanDetails loanDetails){
		BorrowingLedgerDto borrowingLedgerDto = BorrowingLedgerDto.builder()
				.date(new Date())
				.dueDate(loanDetails.getLoanPlannedClosureDate())
				.duration(loanDetails.getLoanProduct().getDuration())
				.loanAmt(loanDetails.getLoanAmt())
				.loanNo(loanDetails.getDccbLoanNo())
				.purpose(loanDetails.getLoanProduct().getProductName())
				.interest(loanDetails.getLoanProduct().getBorrowingInterestRate())
				.isDeleted(false)
				.build();
		borrowingLedgerDto.setBankId(societyBankService.getBankByAccHead(AccountConstants.SOCIETY_CURRENT_ACC_DCCB).getId());

		BorrowingLedgerDto dto = borrowingLedgerService.addborrowingDetails(borrowingLedgerDto);

		String narration = "Borrowing for "+loanDetails.getLoanProduct().getProductName() + " DCCB Loan No "+loanDetails.getDccbLoanNo() +
				" - " + loanDetails.getMember().getFirstName() + " " + loanDetails.getMember().getLastName();
		BorrowingLedgerTransactionDto transDto = BorrowingLedgerTransactionDto.builder()
				.borrowingId(dto.getId())
				.particulars(narration)
				.transactionDate(new Date())
				.isDeleted(false)
				.credit(loanDetails.getLoanAmt())
				.build();

		borrowingLedgerTransService.addTransactionDetails(transDto, DepositConstants.DEPOSIT_CREDIT);
		return true;
	}

	private Date calculateLoanPlannedCloserDate(Date date, Integer duration) {
 		return DateConverter.incrementDateByMonths(duration,date);
	}

	private Shares addShares(LoanDetailsDto loanDetailsDto){
		double shareAmount;
		Shares shares;
		Optional<SocietyConfiguration> societyConfiguration=societyConfigurationRepository.findByConfigName(ShareConstants.SHARE_ALLOCATION_PERCENT);
		shareAmount = loanDetailsDto.getLoanAmt() * societyConfiguration.get().getValue() /100;

		ExceedShareCount shareCount = sharesAllocationService.exceedShareLimit(loanDetailsDto.getMemberId(), shareAmount);
		if(shareCount.getAllocatedShareCount()>0)
		{
			Member member = memberRepository.findById(loanDetailsDto.getMemberId())
					.orElseThrow(() -> new NotFoundException("No member found in Loan Details for Id : " + loanDetailsDto.getMemberId()));
			int useSuspenseAcc = (SocietyConfig.getValue(ShareConstants.USE_SUSPENSE_ACCOUNT)).intValue();
			shares = sharesService.addMemberShare(member, shareCount.getAllocatedShareCount(), useSuspenseAcc,
					ShareConstants.APPLICATION_TYPE_LOAN,ShareConstants.ALLOC_PARTICULARS_LOAN);
		}
		else
		{
			return null;
		}
 		return shares;
	}

	@Override
	public DisbursementDto addDisbursementDetails(DisbursementDto disbursementDto) {
		LoanDetails loanDetails = loanDetailsRepository.findById(disbursementDto.getLoanDetailsId())
				.orElseThrow(() -> new NotFoundException("No Loan details found for Id : " + disbursementDto.getLoanDetailsId()));
		Disbursement disbursement = disbursementMapper.disbursementDtoToDisbursement(disbursementDto);
		disbursement.setLoanDetails(loanDetails);
		
		if((loanDetails.getDisbursementAmt()+disbursement.getDisbursementAmt())<loanDetails.getLoanAmt()) {
			loanDetails.setDisbursementStatus(LoanConstants.PARTIAL_DISBURSEMENT);
			disbursement.setDisbursementStatus(LoanConstants.PARTIAL_DISBURSEMENT);
		}else if((loanDetails.getDisbursementAmt()+disbursement.getDisbursementAmt())==loanDetails.getLoanAmt()) {
			loanDetails.setDisbursementStatus(LoanConstants.FULL_DISBURSEMENT);
			disbursement.setDisbursementStatus(LoanConstants.FULL_DISBURSEMENT);
		}
		disbursement = disbursementRepository.save(disbursement);
		loanDetails.setDisbursementAmt(loanDetails.getDisbursementAmt() + disbursement.getDisbursementAmt());
		loanDetails.setDisbursementStatus(disbursement.getDisbursementStatus());
		loanDetailsRepository.save(loanDetails);
		loanVoucherService.createDisbursementVoucher(loanDetails, disbursement);
		return disbursementMapper.disbursementToDisbursementDto(disbursement);
	}

	@Override
	public LoanDetailsDto updateLoanDetails(Long loanId, LoanDetailsDto loanDetailsDto) {

		    LoanDetails loanDetail = loanDetailsRepository.findById(loanId)
					.orElseThrow(() -> new NotFoundException("No Loan details found for Id : " + loanId));
		    loanDetailsDto.setId(loanDetail.getId());
		   final   LoanDetails  loanDetails=loanDetailsMapper.LoanDetailsDtoToLoanDetails(loanDetailsDto);
			loanDetails.setMember(loanDetail.getMember());
			loanDetails.setLoanDemand(loanDetail.getLoanDemand());
			loanDetails.setLoanProduct(loanDetail.getLoanProduct());
			loanDetails.getDisbursements().forEach(disbursement->{
				disbursement.setLoanDetails(loanDetails);
			});
			
			return loanDetailsMapper.loanDetailsToLoanDetailsDto(loanDetailsRepository.save(loanDetails));
	}

	@Override
	public DisbursementDto updateDisbursementDetails(Long disbursementId, DisbursementDto disbursementDto) {
		Disbursement disbursement = disbursementRepository.findById(disbursementId)
				.orElseThrow(() -> new NotFoundException("No Disbursement found for Id : " + disbursementId));
		disbursementDto.setId(disbursement.getId());
		Disbursement disbursements = disbursementMapper.disbursementDtoToDisbursement(disbursementDto);
		disbursements.setLoanDetails(disbursement.getLoanDetails());
		disbursements = disbursementRepository.save(disbursements);
		return disbursementMapper.disbursementToDisbursementDto(disbursements);
	}

	@Override
	public LoanDetailsDto getLoanDetailsById(Long loanId) {
		log.debug("REST request to get laonDetails : {}", loanId);
		LoanDetails loanDetail = loanDetailsRepository.findById(loanId)
				.orElseThrow(() -> new NotFoundException("No Loan Details found for Id : " + loanId));
	return loanDetailsMapper.loanDetailsToLoanDetailsDto(loanDetail);
	}

	@Override
	public LoanDetails getLoanAccNo(Long loanAccNo) {
		return loanDetailsRepository.findByLoanAccountNo(loanAccNo);
	}

	@Override
	public ListingPage_PageList listLoanMemberDetails(Pageable pageable) {
		Page<LoanDetails> listPage;
	
		listPage = loanDetailsRepository.findByLoanStatus("A", pageable);
		  return new ListingPage_PageList(
		  listPage.getContent().stream().map(listingPageMapper::
		  loanDetailsToListingPageDto) .collect(Collectors.toList()),
		  PageRequest.of(listPage.getPageable().getPageNumber(),
		  listPage.getPageable().getPageSize()), listPage.getTotalElements());
	}

	@Override
	public ViewLoanDetailsPageList listLoanMembers(Pageable pageable) {
		Page<LoanDetails> listPage;
		listPage = loanDetailsRepository.findByisDeleted(false,pageable);
		
		  return new ViewLoanDetailsPageList(
		  listPage.getContent().stream().map(viewLoanDetailsMapper::
		  loanDetailsToViewLoanDetailsDto) .collect(Collectors.toList()),
		  PageRequest.of(listPage.getPageable().getPageNumber(),
		  listPage.getPageable().getPageSize()), listPage.getTotalElements());
	}

	@Override
	public List<LoanInterestDto> listNpa(Pageable pageable) {
		log.debug("Request to get Npa : {}");

	List<LoanInterestDto> loanInterestDetailsList= new ArrayList<LoanInterestDto>();	
	
	List<LoanDetails>loanDetailList = loanDetailsRepository.findByisDeleted(false);
	

    for(int i=0;i<loanDetailList.size();i++)
    {
    	LoanInterestDto loanInterestDetail= new LoanInterestDto();
		System.out.println("Loan Interest"+ loanDetailList.get(i).getId());
		LoanInterestDetails loanInterestDetails= calculateLoanInterest(loanDetailList.get(i).getId(), new Date());
		loanInterestDetail.setTotalCurrentOutstanding(loanInterestDetails.getTotalCurrentOutstanding());
		loanInterestDetail.setOutstandingPrincipal(loanInterestDetails.getTotalOutstandingPrincipal());
		loanInterestDetail.setCurrentLoanInterest(loanInterestDetails.getCurrentLoanInterest());
		loanInterestDetail.setLastPaymentDate(loanInterestDetails.getLastPaymentDate());
		loanInterestDetail.setAccountNo(loanDetailList.get(i).getLoanAccountNo());
		loanInterestDetail.setLoanPlannedClosureDate(loanDetailList.get(i).getLoanPlannedClosureDate());
		loanInterestDetail.setMemberName(loanDetailList.get(i).getMember().getFirstName()+" "+loanDetailList.get(i).getMember().getMiddleName()+" "+loanDetailList.get(i).getMember().getLastName());
		loanInterestDetail.setNpaClassification(loanDetailList.get(i).getLoanClassification());
		loanInterestDetail.setLoanType(loanDetailList.get(i).getLoanType());
		loanInterestDetail.setLoanDate(loanDetailList.get(i).getLoanEffectiveDate());
		loanInterestDetailsList.add(loanInterestDetail);
    }  	
    
		return loanInterestDetailsList;
	}


	@Override
	public DisbursementDto getDisbursementById(Long disbursementId) {
		Disbursement disbursement = disbursementRepository.findById(disbursementId)
				.orElseThrow(() -> new NotFoundException("No Disbursement found for Id : " + disbursementId));
		return disbursementMapper.disbursementToDisbursementDto(disbursement);
	}

	@Override
	public List<DisbursementDto> getDisbursementListByLoanId(Long loanId) {
		return disbursementMapper.toDtoList(disbursementRepository.findByLoanId(loanId));
	}

	public LoanInterestDetails calculateLoanInterest(Long loanId, Date installmentDate){
		LoanDetails loanDetails = loanDetailsRepository.findByLoanId(loanId);
		return calculateLoanInterest(loanDetails, installmentDate);
	}

	public LoanInterestDetails calculateLoanInterest(LoanDetails loanDetails, Date installmentDate) {
		LoanInterestDetails loanInterestDetails = new LoanInterestDetails();
		if(loanDetails.getLoanType().equalsIgnoreCase(LoanConstants.SHORT_TERM_LOAN)){
			loanInterestDetails = calculateSTLoanInterest(loanDetails, installmentDate);
		}
		else if(loanDetails.getLoanType().equalsIgnoreCase(LoanConstants.MID_TERM_LOAN) || loanDetails.getLoanType().equalsIgnoreCase(LoanConstants.LONG_TERM_LOAN)){
			loanInterestDetails = calculateMLLoanInterest(loanDetails, installmentDate);
		}
		   return loanInterestDetails;
	}

	private LoanInterestDetails calculateSTLoanInterest(LoanDetails loanDetails, Date installmentDate)	{

		LoanInterestDetails loanInterestDetails = new LoanInterestDetails();
		loanInterestDetails.setId(loanDetails.getId());
		double loanAmount = loanDetails.getLoanAmt();
		double closingPrinciple = loanAmount;
		double prevBalanceInterest = 0.0;

		Date currentDate = installmentDate;
		Date interestCalcStartDate = null;
		Date plannedLoanClosingDate = loanDetails.getLoanPlannedClosureDate();

		List<Disbursement> disbursement = disbursementRepository.findByLoanId(loanDetails.getId());
		for (Disbursement disburse : disbursement) {
			interestCalcStartDate = disburse.getDisbursementDate();
		}

		List<Repayment> repayment = repaymentRepository.findByLoanId(loanDetails.getId());
		if(repayment!=null && repayment.size()>0) {
			for (Repayment payment : repayment) {
				interestCalcStartDate = payment.getInstallmentDate();
				prevBalanceInterest = payment.getBalanceInterest();
				closingPrinciple = payment.getClosingPrinciple();
			}
			Repayment lastRepayment=repayment.get(repayment.size()-1);
			loanInterestDetails.setLastPaymentDate(lastRepayment.getInstallmentDate());
		}

		int tillDateDuration = DateConverter.dayDiff(interestCalcStartDate, currentDate);

		int penaltyDays =0;

		loanInterestDetails.setLoanId(loanDetails.getId());

		double roi = 0.0;
		double lateRoi=0.0;
		if(loanDetails.getLoanType().equalsIgnoreCase(LoanConstants.SHORT_TERM_LOAN))
		{
			InterestShortTermLoan interest = null;
			InterestShortTermLoan largeAmtInterest = null;

			double roi_centralGovPay = 0.0;
			double roi_stateGovPay = 0.0;
			double roi_dccBankPay = 0.0;

			if(currentDate.after(plannedLoanClosingDate)){
				penaltyDays = DateConverter.dayDiff(plannedLoanClosingDate, currentDate);
				System.out.println(" penaltyDays for ST Loan " + penaltyDays);

				if(penaltyDays < 0)
					penaltyDays = 0;
			}
			if(loanAmount < 300000) {
				interest = interestShortTermLoanRepository.findByCriteriaAndIsDeleted(LoanConstants.LOAN_CRITERIA_BELOW_LACK, false);
			}
			else {
				interest = interestShortTermLoanRepository.findByCriteriaAndIsDeleted(LoanConstants.LOAN_CRITERIA_ABOVE_LACK, false);
			}
			roi = interest.getSelf();
			lateRoi = interest.getAfterOneYar();

			roi_centralGovPay = interest.getCentralGov();
			roi_stateGovPay = interest.getStateGov();
			roi_dccBankPay = interest.getDistBank();
			//roi = loanDetails.loanProduct.getInterestRate();

			Date lastRepaymentDate = loanDetails.getLoanProduct().getLastDateOfRepayment();
			Date loanStartDate = loanDetails.getLoanEffectiveDate();
			int loanTenureDuration = DateConverter.dayDiff(loanStartDate, lastRepaymentDate);


			double totalLoanInterest = loanAmount * ((roi / 100) / 365) * loanTenureDuration;
			loanInterestDetails.setTotalLoanInterest(RoundOff.roundDouble(totalLoanInterest));

			double currentLoanInterest = 0.0;
			if(loanDetails.getLoanPlannedClosureDate().after(currentDate)){
				currentLoanInterest = closingPrinciple * ((roi / 100) / 365) * tillDateDuration;
			}
			else{
				int diffOverdueDays = DateConverter.dayDiff(loanDetails.getLoanPlannedClosureDate(), currentDate);
				currentLoanInterest = totalLoanInterest + (closingPrinciple * ((lateRoi / 100) / 365) * diffOverdueDays);
			}
			loanInterestDetails.setCurrentLoanInterest(RoundOff.roundDouble(currentLoanInterest + prevBalanceInterest));
			loanInterestDetails.setCurrOutstandingPrincipal(RoundOff.roundDouble(closingPrinciple));
			loanInterestDetails.setTotalOutstandingPrincipal(RoundOff.roundDouble(closingPrinciple));

			double centralGovPayInterest = loanAmount * ((roi_centralGovPay / 100) / 365) * loanTenureDuration;
			loanInterestDetails.setCentralGovPayInterest(RoundOff.roundDouble(centralGovPayInterest));

			double stateGovPayInterest = loanAmount * ((roi_stateGovPay / 100) / 365) * loanTenureDuration;
			loanInterestDetails.setStateGovPayInterest(RoundOff.roundDouble(stateGovPayInterest));

			double dccBankPayInterest = loanAmount * ((roi_dccBankPay / 100) / 365) * loanTenureDuration;
			loanInterestDetails.setDccPayInterest(RoundOff.roundDouble(dccBankPayInterest));

			double roi_penalty = loanDetails.loanProduct.getPenaltyInterest();
			double penaltyInterest = closingPrinciple * ((roi_penalty / 100) / 365) * penaltyDays;
			loanInterestDetails.setPenaltyAmount(RoundOff.roundDouble(penaltyInterest));

			double roi_surcharge = loanDetails.loanProduct.getSurcharge();
			double surchargeInterest = penaltyInterest * ((roi_surcharge / 100) / 365) ;
			loanInterestDetails.setSurchargeAmt(RoundOff.roundDouble(surchargeInterest));

			loanInterestDetails.setTotalCurrentOutstanding(loanInterestDetails.getCurrOutstandingPrincipal() + loanInterestDetails.getCurrentLoanInterest() + loanInterestDetails.getPenaltyAmount() + loanInterestDetails.getSurchargeAmt());
			loanInterestDetails.setTotalOutstanding(loanInterestDetails.getCurrOutstandingPrincipal() + loanInterestDetails.getTotalLoanInterest() + loanInterestDetails.getPenaltyAmount() + loanInterestDetails.getSurchargeAmt());
		}
		return loanInterestDetails;
	}
	private LoanInterestDetails calculateMLLoanInterest(LoanDetails loanDetails, Date installmentDate){

		LoanInterestDetails loanInterestDetails = new LoanInterestDetails();
		loanInterestDetails.setId(loanDetails.getId());
		loanInterestDetails.setLoanId(loanDetails.getId());

		Date currentDate = installmentDate;
		Date lastPaymentDate = null;

		double currentOutstandingInterest = 0.0;
		double payableEMIPrincipleAmt = 0.0;

		double outstandingPrinciple = loanDetails.getLoanAmt();
		double prevBalInterest = 0.0;
		int interestDuration=0;
		double roi = loanDetails.loanProduct.getInterestRate();
		double roiPenalty = loanDetails.loanProduct.getPenaltyInterest();
		double roiSurcharge = loanDetails.loanProduct.getSurcharge();

		for (Disbursement disburse : loanDetails.getDisbursements()) {
			lastPaymentDate = disburse.getDisbursementDate();
		}

		double penaltyAmt=0.0;
		double surCharge=0.0;
		List<Amortization> overdueList = getOverDueEmi(loanDetails, LoanConstants.OVERDUE);
		List<Amortization> partialOverdueList = getOverDueEmi(loanDetails, LoanConstants.PARTIAL_OVERDUE);
		for(Amortization amortization : overdueList){
			penaltyAmt = penaltyAmt + calculatePenaltyForOverDueEmi(amortization, roiPenalty);
			payableEMIPrincipleAmt = payableEMIPrincipleAmt + amortization.getPrincipleEMI();
		}
		for(Amortization amortization : partialOverdueList){
			penaltyAmt = penaltyAmt + calculatePenaltyForOverDueEmi(amortization, roiPenalty);
			payableEMIPrincipleAmt = payableEMIPrincipleAmt + amortization.getBalPrincipleAmt();
		}
		Amortization emiDue = amortizationRepository.findByLoanIdAndStatus( loanDetails.getId() , LoanConstants.DUE);
		double currentEmi = emiDue.getTotalAmt();
		Set<Repayment> repaymentList = loanDetails.getRepayment();
		if(repaymentList !=null && repaymentList.size() > 0){
			for(Repayment repayment:repaymentList){
				lastPaymentDate = repayment.getInstallmentDate();
				outstandingPrinciple = repayment.getClosingPrinciple();
				prevBalInterest = repayment.getBalanceInterest();
			}
			interestDuration = DateConverter.dayDiff(lastPaymentDate, currentDate);
			currentOutstandingInterest = outstandingPrinciple * ((roi / 100) / 365) * interestDuration;
			currentOutstandingInterest = currentOutstandingInterest + prevBalInterest;

		}else{
			interestDuration = DateConverter.dayDiff(lastPaymentDate, currentDate);
			currentOutstandingInterest = outstandingPrinciple * ((roi / 100) / 365) * interestDuration;
		}
		surCharge = penaltyAmt * ((roiSurcharge / 100) / 365);

		loanInterestDetails.setCurrOutstandingPrincipal(RoundOff.roundDouble(payableEMIPrincipleAmt));
		loanInterestDetails.setCurrentLoanInterest(RoundOff.roundDouble(currentOutstandingInterest));
		loanInterestDetails.setPenaltyAmount(RoundOff.roundDouble(penaltyAmt));
		loanInterestDetails.setSurchargeAmt(RoundOff.roundDouble(surCharge));
		loanInterestDetails.setTotalCurrentOutstanding(RoundOff.roundDouble(payableEMIPrincipleAmt + currentOutstandingInterest + penaltyAmt + surCharge));

		loanInterestDetails.setTotalLoanInterest(RoundOff.roundDouble(loanDetails.getAmortizations().stream().mapToDouble(Amortization::getInterestAmt).sum() + penaltyAmt + surCharge));
		loanInterestDetails.setTotalOutstandingPrincipal(RoundOff.roundDouble(outstandingPrinciple));
		loanInterestDetails.setTotalOutstanding(loanInterestDetails.getTotalOutstandingPrincipal() + loanInterestDetails.getTotalLoanInterest());

		loanInterestDetails.setBarrierAmt(RoundOff.roundDouble(payableEMIPrincipleAmt + currentOutstandingInterest + penaltyAmt + surCharge + currentEmi));
		loanInterestDetails.setTotalPaymentAmt(loanInterestDetails.getBarrierAmt());

		System.out.println(" loanInterestDetails " + loanInterestDetails);

		return loanInterestDetails;

	}
	private double calculatePenaltyForOverDueEmi(Amortization amortization, double roiPenalty){
		double principleEmi = 0.0;
		if(amortization.getPaymentStatus().equalsIgnoreCase(LoanConstants.OVERDUE))
			principleEmi = amortization.getPrincipleEMI();
		else if(amortization.getPaymentStatus().equalsIgnoreCase(LoanConstants.PARTIAL_OVERDUE))
			principleEmi = amortization.getBalPrincipleAmt();

		Date currentDate = new Date();
		Date lastPaymentDate = amortization.getInstallmentDate();
		int penaltyDuration = DateConverter.dayDiff(lastPaymentDate, currentDate );
		return principleEmi * ((roiPenalty / 100) / 365) * penaltyDuration;
	}
	private List<Amortization> getOverDueEmi(LoanDetails loanDetails, String status){
		Set<Amortization> amortizationList = loanDetails.getAmortizations();
		List<Amortization> amortizationOverDueList = new ArrayList<>();
		for(Amortization amortization: amortizationList){
			if(amortization.getPaymentStatus().equalsIgnoreCase(status)){
				amortizationOverDueList.add(amortization);
			}
		}
		return amortizationOverDueList;
	}

	@Override
	public AmortizationDto scheduleAmortization(LoanDetails loanDetails) {
		
		//LoanDetails loanDetails = loanDetailsRepository.findById(loanId)
		//		.orElseThrow(() -> new NotFoundException("No Loan details found for Id : " + loanId));
		List<Amortization> amortizationList = calculateAmortization(loanDetails);
		amortizationList.forEach(action->{
			amortizationRepository.save(action);
		});
      return null;
	}

	private List<Amortization> calculateAmortization(LoanDetails loanDetails){
		List<Amortization> amortizationList = new ArrayList<>();

		double outstandingPrinciple=loanDetails.getLoanAmt();
		int installmentNo=loanDetails.loanProduct.getNumberOfInstallment();
		Integer tenure=loanDetails.loanProduct.getDuration();
		Integer EMIFrequency=tenure/installmentNo;
		double principleEMI=outstandingPrinciple/installmentNo;
		double interestAmt=0.0;
		double totalAmt=0.0;
		double roi = loanDetails.loanProduct.getInterestRate();

		Date installmentDate=new Date();//temporary date //set as per society config
		Calendar cal = Calendar.getInstance();
		cal.setTime(installmentDate);
		cal.add(Calendar.MONTH,EMIFrequency);

		for(int i=installmentNo;i>0;i--){

			Amortization amortization=new Amortization();
			interestAmt=(outstandingPrinciple*roi/100/12)*EMIFrequency;
			amortization.setPrincipleEMI(principleEMI);
			totalAmt=principleEMI+interestAmt;
			amortization.setTotalAmt(totalAmt);
			amortization.setInterestAmt(interestAmt);
			amortization.setInstallmentDate(cal.getTime());
			amortization.setOutstandingPrinciple(outstandingPrinciple);
			amortization.setLoanDetails(loanDetails);
			amortization.setPaymentStatus(LoanConstants.DUE);
			amortizationList.add(amortization);
			//amortizationRepository.save(amortization);

			outstandingPrinciple=outstandingPrinciple-principleEMI;
			cal.add(Calendar.MONTH,EMIFrequency);
		}
		return amortizationList;
	}

	@Override
	public LoanInterestDetails calculateForeclosureCharge(Long loanId, Date installmentDate,String loanType) {
		
		LoanDetails loanDetails = loanDetailsRepository.findById(loanId)
				.orElseThrow(() -> new NotFoundException("No Loan details found for Id : " + loanId));
		List<LoanCharges> chargeList=loanChargesRepository.findByCategoryandLoanType("foreclosure", loanType);

		final Map<String, Double> chargesMap = new HashMap<>();
		
		chargeList.forEach(charge->{
			chargesMap.put(charge.getChargesName(),charge.getChargesValue());
		});

		double foreclosureCharge=chargesMap.get(LoanConstants.FORECLOSURE_CHARGE);
		double foreclosureSurcharge=chargesMap.get(LoanConstants.FORECLOSURE_SURCHARGE);
		
		LoanInterestDetails loanInterestDetails = calculateLoanInterest(loanId,installmentDate);
		double outStandingPrinciple= loanInterestDetails.getTotalOutstandingPrincipal();
		
		double foreclosureChargeAmt= RoundOff.roundDouble(outStandingPrinciple * (foreclosureCharge/100));
		double foreclosureSurchargeAmt= RoundOff.roundDouble(foreclosureChargeAmt * ( foreclosureSurcharge/100));
		loanInterestDetails.setForeclosureChargeAmt(foreclosureChargeAmt);
		loanInterestDetails.setForeclosureSurchargeAmt(foreclosureSurchargeAmt);
		
		return loanInterestDetails;
	}

	@Override
	public List<LoanDetailsDto> listAccountNoByMemberId(Long memberId) {
		return loanDetailsMapper.domainToDtoList(loanDetailsRepository.findLoanAccountNoByMemberId(memberId));
	}

	@Override
	public List<LoanDetailsDto> listAllAccountNo() {
		return loanDetailsMapper.domainToDtoList(loanDetailsRepository.findAll());
	}

	@Override
	public LoanDetailsDto updateLoanDetailsforcloser(Long loanId) {

		 LoanDetails loanDetail = loanDetailsRepository.findById(loanId)
					.orElseThrow(() -> new NotFoundException("No Loan details found for Id : " + loanId));
		 
		    loanDetail.setLoanStatus(LoanConstants.LOAN_CLOSE);
			loanDetail.setLoanCloserDate(new Date());
		 
		return loanDetailsMapper.loanDetailsToLoanDetailsDto(loanDetailsRepository.save(loanDetail));
	}

	@Override
	public List<LoanDetailsDto> getLoanDetailsByMemberId(Long memberId) {
		return loanDetailsMapper.domainToDtoList(loanDetailsRepository.findByMemberId(memberId));
	}

	@Override
	public CropLoanDetailsPageList listCropLoanMembers(Pageable pageable) {
		Page<LoanDetails> listPage;
		listPage = loanDetailsRepository.findByLoanType(pageable);
		
		  return new CropLoanDetailsPageList(
		  listPage.getContent().stream().map(CropLoanDetailsMapper::loanDetailsToCropLoanDetailsDto) .collect(Collectors.toList()),
		  PageRequest.of(listPage.getPageable().getPageNumber(),
		  listPage.getPageable().getPageSize()), listPage.getTotalElements());
	}

	@Override
	public List<AmortizationDto> getAmortizationList(Long loanId) {
		
		return amortizationMapper.domainToDtoList(amortizationRepository.findByLoanId(loanId));
	}

	@Override
	public double getStandardLoanCount() {
		double count=loanDetailsRepository.getStandardLoanCount();
		return count;
	}

	@Override
	public double getDefaultLoanCount() {
		double defaultCount=loanDetailsRepository.getDefaulterCount();
		return defaultCount;
	}

	@Override
	public List<LoanBriefDto> getDefaultLoanList() {
		return loanBriefMapper.toDtoList(loanDetailsRepository.getDefaulterList());
	}

	@Override
	public List<LoanDetailsDto> getShortLoanList() {
		List<LoanDetails> loanList=loanDetailsRepository.findShortLoanMembers(LoanConstants.SHORT_TERM_LOAN);
		return loanDetailsMapper.domainToDtoList(loanList);
	}

	@Override
	public List<LoanDetailsDto> getMidLongLoanList() {
		List<LoanDetails> loanList=loanDetailsRepository.findMidLongLoanMembers();
		return loanDetailsMapper.domainToDtoList(loanList);
	}

}

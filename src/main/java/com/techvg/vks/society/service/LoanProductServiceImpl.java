package com.techvg.vks.society.service;

import com.techvg.vks.accounts.domain.AccountMapping;
import com.techvg.vks.accounts.service.AccountMappingService;
import com.techvg.vks.accounts.service.vouchers.BorrowingVoucherService;
import com.techvg.vks.config.AccountConstants;
import com.techvg.vks.config.DepositConstants;
import com.techvg.vks.config.LoanConstants;
import com.techvg.vks.config.MappingName;
import com.techvg.vks.exceptions.AlreadyExitsException;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.loan.domain.CropLoanDemand;
import com.techvg.vks.loan.domain.LoanDemand;
import com.techvg.vks.loan.domain.LoanWatap;
import com.techvg.vks.loan.repository.CropLoanDemandRepository;
import com.techvg.vks.loan.repository.LoanWatapRepository;
import com.techvg.vks.society.domain.LoanProduct;
import com.techvg.vks.society.mapper.LoanProductMapper;
import com.techvg.vks.society.model.BorrowingLedgerDto;
import com.techvg.vks.society.model.BorrowingLedgerTransactionDto;
import com.techvg.vks.society.model.LoanProductDto;
import com.techvg.vks.society.model.LoanProductList;
import com.techvg.vks.society.repository.LoanProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
@Slf4j
public class LoanProductServiceImpl implements LoanProductService{

	private final LoanProductRepository loanProductRepository;
	private final LoanProductMapper loanProductMapper;
	private final CropLoanDemandRepository cropLoanDemandRepo;
	private final AccountMappingService accountMappingService;
	private final BorrowingLedgerService borrowingLedgerService;
	private final BorrowingLedgerTransactionService borrowingLedgerTransService;
	private final BorrowingVoucherService borrowingVoucherService;
	private final SocietyBankService societyBankService;
	private final LoanWatapRepository loanWatapRepository;

	@Override
	public LoanProductDto addLoanProduct(LoanProductDto loanProductDto, Authentication authentication) {
		loanProductDto.setIsDeleted(false);
		loanProductDto.setStatus("A");
		if(loanProductDto.getProductType().equalsIgnoreCase(LoanConstants.SHORT_TERM_LOAN)) {
			
		//	CropLoanDemand croploanDemand = cropLoanDemandRepo.findByCropLoanDemandId(loanProductDto.getCropLoanDemandId());
			//double totalKMPLoanAmt = croploanDemand.getLoanDemand().stream().mapToDouble(LoanDemand::getLoanAmount).sum();
			//loanProductDto.setMaxLoanAmount(totalKMPLoanAmt);
		}
		Optional<LoanProduct> loanProductOptional = loanProductRepository.findByProductNameAndIsDeleted(loanProductDto.getProductName(),false);
		if (loanProductOptional.isPresent()) {
			throw new AlreadyExitsException(
					"Product Name Already Exists : " + loanProductDto.getProductName());
		} else {
			LoanProduct loanProduct = loanProductRepository.save(loanProductMapper.toLoanProduct(updateParentAccHead(loanProductDto)));

			if(loanProductDto.getProductType().equalsIgnoreCase(LoanConstants.SHORT_TERM_LOAN)){
			//	CropLoanDemand croploanDemand=cropLoanDemandRepo.findByCropLoanDemandId(loanProductDto.getCropLoanDemandId());
			List <LoanWatap> loanWatap =loanWatapRepository.findBySlot(loanProductDto.getSlot());
			for( LoanWatap loanWatapObj:loanWatap) {
				loanWatapObj.setLoanProduct(loanProduct);
				loanWatapRepository.save(loanWatapObj);
			}
				//croploanDemand.setLoanProduct(loanProduct);
				//cropLoanDemandRepo.save(croploanDemand);
				//Create borrowing entry
				updateBorrowingLedger(loanProduct);
				// Create borrowing vouchers
				borrowingVoucherService.createBorrowingReceiptVoucher(loanProduct);
			}
			return loanProductMapper.toLoanProductDto(loanProduct);
		}
	}

	private boolean updateBorrowingLedger(LoanProduct loanProduct){
		BorrowingLedgerDto borrowingLedgerDto = BorrowingLedgerDto.builder()
												.date(new Date())
												.dueDate(loanProduct.getLastDateOfRepayment())
												.duration(loanProduct.getDuration())
												.loanAmt(loanProduct.getMaxLoanAmount())
												.loanNo(loanProduct.getLoanNumber())
												.purpose(loanProduct.getAccHeadCode())
												.interest(loanProduct.getBorrowingInterestRate())
												.isDeleted(false)
												.build();
		borrowingLedgerDto.setBankId(societyBankService.getBankByAccHead(AccountConstants.SOCIETY_CURRENT_ACC_DCCB).getId());

		BorrowingLedgerDto dto = borrowingLedgerService.addborrowingDetails(borrowingLedgerDto);

		String narration = "Borrowing for "+loanProduct.getProductName() + " DCCB Loan No "+loanProduct.getLoanNumber();

		BorrowingLedgerTransactionDto transDto = BorrowingLedgerTransactionDto.builder()
													.borrowingId(dto.getId())
													.particulars(narration)
													.transactionDate(new Date())
													.credit(loanProduct.getMaxLoanAmount())
													.isDeleted(false)
													.build();

		borrowingLedgerTransService.addTransactionDetails(transDto, DepositConstants.DEPOSIT_CREDIT);
		return true;
	}

	private LoanProductDto updateParentAccHead(LoanProductDto loanProductDto){
		String mappingName="";
		if(loanProductDto.getProductType().equalsIgnoreCase(LoanConstants.SHORT_TERM_LOAN)){
			// Get the ledger mapped with the mapping
			mappingName = MappingName.ST_LOANS_ADVANCES_LEDGER;
		}
		if(loanProductDto.getProductType().equalsIgnoreCase(LoanConstants.MID_TERM_LOAN) ||
				loanProductDto.getProductType().equalsIgnoreCase(LoanConstants.LONG_TERM_LOAN)
		){
			// Get the ledger mapped with the mapping
			mappingName = MappingName.MTLT_LOANS_ADVANCES_LEDGER;
		}
		AccountMapping accountMapping = accountMappingService.getAccountMappingByName(mappingName);
		loanProductDto.setParentAccHeadCode(accountMapping.getLedgerAccHeadCode());
		loanProductDto.setParentAccHeadId(accountMapping.getLedgerAccount().getId());
		loanProductDto.setAccHeadCode(accountMapping.getLedgerAccHeadCode() + "_" + loanProductDto.getProductName());
		return loanProductDto;
	}
	@Override
	public LoanProductList getAllLoanProducts(Pageable pageable) {
		log.debug("Request to get Product : {}");

		Page<LoanProduct> loanProductPage;
		loanProductPage = loanProductRepository.findByisDeleted(pageable,false);
       
		
		return new LoanProductList(loanProductPage
										.getContent()
										.stream()
										.map(loanProductMapper::toLoanProductDto)
										.collect(Collectors.toList()),
										PageRequest
											.of(loanProductPage.getPageable().getPageNumber(),
													loanProductPage.getPageable().getPageSize()),
											loanProductPage.getTotalElements());
	}
	@Override
	public LoanProductDto deleteAllLoanProducts(Long id) {
		LoanProduct loanProduct = loanProductRepository.findById(id).orElseThrow(NotFoundException::new);
		if (loanProduct != null) {
			loanProduct.setIsDeleted(true);
			loanProductRepository.save(loanProduct);
		}
	return loanProductMapper.toLoanProductDto(loanProduct);
	}
	@Override
	public LoanProductDto updateLoanProduct(Long id, LoanProductDto loanProductDto) {
		LoanProduct loanProduct = loanProductRepository.findById(id).orElseThrow(NotFoundException::new);
		if(loanProduct.getProductType().equalsIgnoreCase(LoanConstants.SHORT_TERM_LOAN)){
		//	if(loanProduct.getCropLoanDemand().getKmpApprovalStatus()){
			//	throw new AlreadyExitsException(
				///		"Crop Loans already registered with the product : " + loanProductDto.getProductName() + " Can not update the product now");
			///}
		}
		loanProductDto.setStatus("A");
		LoanProduct loanProduct1 = loanProductMapper.toLoanProduct(updateParentAccHead(loanProductDto));
		loanProduct1.setId(id);
		loanProductRepository.save(loanProduct1);
		return loanProductMapper.toLoanProductDto(loanProduct1);
	}

	@Override
	public LoanProductDto readLoanProduct(Long id) {
		log.debug("REST request to get Product : {}", id);
		LoanProduct loanProduct = loanProductRepository.findById(id).orElseThrow(
				() -> new NotFoundException("No product found for Id : " +id));

		return loanProductMapper.toLoanProductDto(loanProduct);
	}

	@Override
	public List<LoanProductDto> listLoanProduct(String loanType) {
		return  loanProductMapper.domainToDtoList(loanProductRepository.findByProductTypeAndIsDeleted(loanType,false));
	}

}

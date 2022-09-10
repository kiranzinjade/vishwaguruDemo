package com.techvg.vks.loan.service;

import com.techvg.vks.config.LoanConstants;
import com.techvg.vks.exceptions.AlreadyExitsException;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.loan.domain.CropLoanDemand;
import com.techvg.vks.loan.domain.LoanDemand;
import com.techvg.vks.loan.mapper.STLoanDemandMapper;
import com.techvg.vks.loan.model.LoanDemandCountDto;
import com.techvg.vks.loan.model.LoanDemandDto;
import com.techvg.vks.loan.model.LoanDemandPageList;
import com.techvg.vks.loan.repository.CropLoanDemandRepository;
import com.techvg.vks.loan.repository.LoanDemandRepository;
import com.techvg.vks.membership.domain.Land;
import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.membership.repository.LandRepository;
import com.techvg.vks.membership.repository.MemberRepository;
import com.techvg.vks.society.domain.CropRegistration;
import com.techvg.vks.society.repository.CropRegistrationRepository;
import com.techvg.vks.society.repository.LoanProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShortLoanDemandServiceImpl implements ShortLoanDemandService {
	
	private final LoanDemandRepository loanDemandRepository;
	private final STLoanDemandMapper STLoanDemandMapper;
	private final  MemberRepository memberRepository ;
	private final LoanProductRepository loanProductRepository;
	private final CropRegistrationRepository cropRegistrationRepository;
	private final CropLoanDemandRepository cropLoanDemandRepository;
	private final LandRepository landRepository;


	@Override
	public LoanDemandDto addLoanDemandDetails(LoanDemandDto loanDemandDto) {


		Member member = memberRepository.findById(loanDemandDto.getMemberId())
				 .orElseThrow(() -> new NotFoundException("No member found for Id : " + loanDemandDto.getMemberId()));
		CropRegistration crop =cropRegistrationRepository.findById(loanDemandDto.getCropRegistrationId())
				 .orElseThrow(() -> new NotFoundException("No Crop found for Id : " + loanDemandDto.getCropRegistrationId()));
		CropLoanDemand cropLoanDemand = checkCropLoanDemand(loanDemandDto, crop );

		Land land = landRepository.findById(loanDemandDto.getLandId())
				.orElseThrow(() -> new NotFoundException("No Land found for Id : " + loanDemandDto.getLandId()));

		 LoanDemand loanDemand= STLoanDemandMapper.loanDemandDtoToLoanDemand(loanDemandDto);
		 int year = Calendar.getInstance().get(Calendar.YEAR);

		 Optional<LoanDemand> loanDemand1=loanDemandRepository.findByMember(loanDemandDto.getCropRegistrationId(),loanDemandDto.getYear(),loanDemandDto.getMemberId(),loanDemandDto.getLandId());
		System.out.println("loan demand--------------"+loanDemand1);
		 if(loanDemand1.isPresent()) {
			 throw new AlreadyExitsException("Loan Demand is already present for the crop "+crop.getCropName()+" in this year "+ loanDemandDto.getYear());
		 }else {
		 loanDemand.setMember(member);
		 loanDemand.setCrop(crop);
		 loanDemand.setCropLoanDemand(cropLoanDemand);
		 loanDemand.setLand(land);

			 int len = loanDemand.getCropLandAreaR().toString().length();
			 double factor=0.1;
			 if(len==2)
				 factor=0.01;

		 loanDemand.setMaxAllowed(crop.getCropLimit()* (loanDemand.getCropLandAreaHector() + (factor * loanDemand.getCropLandAreaR())));
		 loanDemand.setAdjustedDemand(loanDemand.getMaxAllowed());
		 loanDemand.setStatus(LoanConstants.LOAN_DEMAND_APPLIED);
		 
		 loanDemand=loanDemandRepository.save(loanDemand);
		 }
		return STLoanDemandMapper.loanDemandToLoanDemandDto(loanDemand);
	}
	private CropLoanDemand checkCropLoanDemand(LoanDemandDto loanDemandDto, CropRegistration crop ){
		CropLoanDemand cropLoanDemand = cropLoanDemandRepository.findByCropIDAndYear(loanDemandDto.getCropRegistrationId(),loanDemandDto.getYear());
		if(cropLoanDemand == null){
			cropLoanDemand =  new CropLoanDemand();
			cropLoanDemand.setCrop(crop);
			cropLoanDemand.setYear(loanDemandDto.getYear());
			cropLoanDemand.setTharavNo(loanDemandDto.getTharavNo());
			cropLoanDemandRepository.save(cropLoanDemand);
		}
		return cropLoanDemand;
	}
	@Override
	public LoanDemandDto updateLoanDemandDetails(Long loanDemandId, LoanDemandDto loanDemandDto) {
		
		LoanDemand loanDemand=loanDemandRepository.findById(loanDemandId)
				.orElseThrow(() -> new NotFoundException("No Loan demand found for Id : " + loanDemandId));
	    loanDemandDto.setId(loanDemand.getId());

		LoanDemand loanDemands= STLoanDemandMapper.loanDemandDtoToLoanDemand(loanDemandDto);

	    loanDemands.setMember(loanDemand.getMember());
	    loanDemands.setCrop(loanDemand.getCrop());
	    loanDemands.setCropLoanDemand(loanDemand.getCropLoanDemand());
		loanDemands.setLand(loanDemand.getLand());
	   	loanDemands = loanDemandRepository.save(loanDemands);
		return STLoanDemandMapper.loanDemandToLoanDemandDto(loanDemands) ;
	}

	@Override
	public LoanDemandPageList listShortLoanDemand(Pageable pageable) {
		Page<LoanDemand> loanDemandPage;
		loanDemandPage=loanDemandRepository.findAllByCropRegistration(pageable);
		return new LoanDemandPageList(loanDemandPage
				.getContent()
				.stream()
				.map(STLoanDemandMapper::loanDemandToLoanDemandDto)
				.collect(Collectors.toList()),
				PageRequest
					.of(loanDemandPage.getPageable().getPageNumber(),
							loanDemandPage.getPageable().getPageSize()),
					loanDemandPage.getTotalElements());
	}

	@Override
	public LoanDemandDto getLoanDemandDetailsById(Long id) {
		
		LoanDemand loanDemand = loanDemandRepository.findById(id).orElseThrow(
				() -> new NotFoundException("No loan demand found for Id : " +id));

		return STLoanDemandMapper.loanDemandToLoanDemandDto(loanDemand);
		
	}

	@Override
	public LoanDemandDto deleteLoanDemandDetailsById(Long id) {
		
		LoanDemand loanDemand = loanDemandRepository.findById(id).orElseThrow(
				() -> new NotFoundException("No loan demand found for Id : " +id));
		if (loanDemand != null) {
			loanDemand.setIsDeleted(true);
			loanDemandRepository.save(loanDemand);
		}
	return STLoanDemandMapper.loanDemandToLoanDemandDto(loanDemand);
	}

	@Override
	public List<LoanDemandDto> getSTLoanDemand() {
		return STLoanDemandMapper.toDtoList(loanDemandRepository.findAllByCropRegistration());
	}

	@Override
	public List<LoanDemandCountDto> getMemberCountForCrop(Integer year) {
		return loanDemandRepository.findCropMemberCount(year);
	}

	@Override
	public LoanDemandPageList getSTLoanDemandList(Pageable pageable, String cropName, Integer year) {
		Page<LoanDemand> loanDemandPage;
		loanDemandPage=loanDemandRepository.findAllByMember(pageable, cropName, year );
		return new LoanDemandPageList(loanDemandPage
				.getContent()
				.stream()
				.map(STLoanDemandMapper::loanDemandToLoanDemandDto)
				.collect(Collectors.toList()),
				PageRequest
						.of(loanDemandPage.getPageable().getPageNumber(),
								loanDemandPage.getPageable().getPageSize()),
				loanDemandPage.getTotalElements());
	}


}

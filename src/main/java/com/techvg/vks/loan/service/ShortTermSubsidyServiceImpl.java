package com.techvg.vks.loan.service;

import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.loan.domain.LoanDetails;
import com.techvg.vks.loan.domain.ShortTermSubsidy;
import com.techvg.vks.loan.mapper.ShortTermSubsidyMapper;
import com.techvg.vks.loan.model.LoanInterestDetails;
import com.techvg.vks.loan.model.ShortTermSubsidyDto;
import com.techvg.vks.loan.model.ShortTermSubsidyPageList;
import com.techvg.vks.loan.model.SubsidyDtoPageList;
import com.techvg.vks.loan.repository.LoanDetailsRepository;
import com.techvg.vks.loan.repository.ShortTermSubsidyRepository;
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
public class ShortTermSubsidyServiceImpl implements ShortTermSubsidyervice  {
	
	private final LoanDetailsRepository loanDetailsRepository;
	private final ShortTermSubsidyRepository shortTermSubsidyRepository;
	private final ShortTermSubsidyMapper shortTermSubsidyMapper;
	
	
	@Override
	public ShortTermSubsidyDto addSubsidyDetails(Long loanId, LoanInterestDetails loanInterestDetails) {
		
		LoanDetails loanDetails = loanDetailsRepository.findById(loanId)
				.orElseThrow(() -> new NotFoundException("No loan details found for Id : " +loanId));

			ShortTermSubsidy shortTermSubsidy=new ShortTermSubsidy();
			shortTermSubsidy.setLoanDetails(loanDetails);
			shortTermSubsidy.setDistBankInterest(loanInterestDetails.getDccPayInterest());
			shortTermSubsidy.setCentralGovInterest(loanInterestDetails.getCentralGovPayInterest());
			shortTermSubsidy.setStateGovInterest(loanInterestDetails.getStateGovPayInterest());
			shortTermSubsidyRepository.save(shortTermSubsidy);
			return shortTermSubsidyMapper.shortTermSubsidyToShortTermSubsidyDto(shortTermSubsidy);

	}	

	
	@Override
	public ShortTermSubsidyDto updateSubsidyDetails(Long subsidyId,ShortTermSubsidyDto shortTermSubsidyDto) {
		
		ShortTermSubsidy subsidyDetail = shortTermSubsidyRepository.findById(subsidyId)
				.orElseThrow(() -> new NotFoundException("No Subsidy details found for Id : " +subsidyId));
		shortTermSubsidyDto.setId(subsidyDetail.getId());
		ShortTermSubsidy subsidyDetails=shortTermSubsidyMapper.shortTermSubsidyDtoToShortTermSubsidy(shortTermSubsidyDto);
		subsidyDetails.setLoanDetails(subsidyDetail.getLoanDetails());
		subsidyDetails = shortTermSubsidyRepository.save(subsidyDetails);
		return shortTermSubsidyMapper.shortTermSubsidyToShortTermSubsidyDto(subsidyDetails);
	}

	
	@Override
	public ShortTermSubsidyPageList list(Pageable pageable) {
		
		Page<ShortTermSubsidy> shortSubsidyPage;
		shortSubsidyPage = shortTermSubsidyRepository.findByisDeleted(pageable,false);

		return new ShortTermSubsidyPageList(shortSubsidyPage
										.getContent()
										.stream()
										.map(shortTermSubsidyMapper::shortTermSubsidyToShortTermSubsidyDto)
										.collect(Collectors.toList()),
										PageRequest.of(shortSubsidyPage.getPageable().getPageNumber(),
												shortSubsidyPage.getPageable().getPageSize()),
												shortSubsidyPage.getTotalElements());


	}

	@Override
	public SubsidyDtoPageList listSubsidy(Pageable pageable,Long loanId) {
		LoanDetails loanDetails = loanDetailsRepository.findById(loanId)
				.orElseThrow(() -> new NotFoundException("No loan details found for Id : " +loanId));
		Page<ShortTermSubsidy> shortSubsidyPage;
		shortSubsidyPage = shortTermSubsidyRepository.findByLoanDetails(pageable,loanDetails);
		return new SubsidyDtoPageList(shortSubsidyPage
										.getContent()
										.stream()
										.map(shortTermSubsidyMapper::shortTermSubsidyToSubsidyDto)
										.collect(Collectors.toList()),
										PageRequest.of(shortSubsidyPage.getPageable().getPageNumber(),
												shortSubsidyPage.getPageable().getPageSize()),
												shortSubsidyPage.getTotalElements());
		}

	
}

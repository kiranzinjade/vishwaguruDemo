package com.techvg.vks.loan.service;

import com.techvg.vks.config.LoanConstants;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.loan.domain.LoanDemand;
import com.techvg.vks.loan.mapper.MLLoanDemandMapper;
import com.techvg.vks.loan.model.LongLoanDemandPageList;
import com.techvg.vks.loan.model.MidLoanDemandPageList;
import com.techvg.vks.loan.model.MidLongLoanDemandDto;
import com.techvg.vks.loan.repository.LoanDemandRepository;
import com.techvg.vks.membership.domain.House;
import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.membership.repository.HouseRepository;
import com.techvg.vks.membership.repository.MemberRepository;
import com.techvg.vks.society.domain.LoanProduct;
import com.techvg.vks.society.repository.LoanProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MidLongLoanDemandServiceImpl implements MidLongLoanDemandService {
	
	private final LoanDemandRepository loanDemandRepository;
	private final MLLoanDemandMapper mlLoanDemandMapper;
	private final  MemberRepository memberRepository ;
	private final LoanProductRepository loanProductRepository;
	private final HouseRepository houseRepository;
	
	
	@Override
	public MidLongLoanDemandDto addMidLongLoanDemandDetails(MidLongLoanDemandDto midLongLoanDemandDto) {
		 Set<House>house=null;
		 Member member = memberRepository.findById(midLongLoanDemandDto.getMemberId())
				 .orElseThrow(() -> new NotFoundException("No Member found for Id : " + midLongLoanDemandDto.getMemberId()));
		 LoanProduct loanProduct=loanProductRepository.findById(midLongLoanDemandDto.getProductId())
				 .orElseThrow(() -> new NotFoundException("No Loan Product found for Id : " + midLongLoanDemandDto.getProductId()));
		 LoanDemand loanDemand= mlLoanDemandMapper.midLongloanDemandDtoToLoanDemand(midLongLoanDemandDto);
		 loanDemand.setMember(member);
		 loanDemand.setLoanProduct(loanProduct);
		 loanDemand.setStatus(LoanConstants.LOAN_DEMAND_APPLIED);
		 house= member.getHouse();

		 if(house!=null && house.size()>0) {
		 loanDemand=loanDemandRepository.save(loanDemand);
		return mlLoanDemandMapper.loanDemandToMidLongLoanDemandDto(loanDemand);
		 }	
		 else 
		 {
			 throw new NotFoundException("Please provide address details " );
		 }
		 
	}


	@Override
	public MidLongLoanDemandDto updateMidLongLoanDemandDetails(Long loanDemandId,MidLongLoanDemandDto midLongLoanDemandDto) {
		
		LoanDemand loanDemand=loanDemandRepository.findById(loanDemandId)
				.orElseThrow(() -> new NotFoundException("No Loan demand found for Id : " + loanDemandId));
		midLongLoanDemandDto.setId(loanDemand.getId());
	    LoanDemand loanDemands= mlLoanDemandMapper.midLongloanDemandDtoToLoanDemand(midLongLoanDemandDto);
	    loanDemands.setMember(loanDemand.getMember());
	    loanDemands.setLoanProduct(loanDemand.getLoanProduct());
	   	loanDemands = loanDemandRepository.save(loanDemands);
		return mlLoanDemandMapper.loanDemandToMidLongLoanDemandDto(loanDemands) ;

	}

	@Override
	public MidLongLoanDemandDto getMidLongLoanDemandDetailsById(Long id) {
		
		LoanDemand loanDemand = loanDemandRepository.findById(id).orElseThrow(
				() -> new NotFoundException("No loan demand found for Id : " +id));

		return mlLoanDemandMapper.loanDemandToMidLongLoanDemandDto(loanDemand);

	}


	@Override
	public MidLongLoanDemandDto deleteMidLongLoanDemandDetailsById(Long id) {
		
		LoanDemand loanDemand = loanDemandRepository.findById(id).orElseThrow(
				() -> new NotFoundException("No loan demand found for Id : " +id));
		if (loanDemand != null) {
			loanDemand.setIsDeleted(true);
			loanDemandRepository.save(loanDemand);
		}
		return mlLoanDemandMapper.loanDemandToMidLongLoanDemandDto(loanDemand);
	}


	@Override
	public MidLoanDemandPageList listMidLoanDemand(Pageable pageable) {
		
		Page<LoanDemand> loanDemandPage;
		loanDemandPage=loanDemandRepository.findByIsDeletedAndLoanProductProductType(false, pageable,LoanConstants.MID_TERM_LOAN);
			return new MidLoanDemandPageList(loanDemandPage
					.getContent()
					.stream()
					.map(mlLoanDemandMapper::loanDemandToMidLongLoanDemandDto)
					.collect(Collectors.toList()),
					PageRequest
						.of(loanDemandPage.getPageable().getPageNumber(),
								loanDemandPage.getPageable().getPageSize()),
						loanDemandPage.getTotalElements());
	}


	@Override
	public LongLoanDemandPageList listLongLoanDemand(Pageable pageable) {
		
		Page<LoanDemand> loanDemandPage;
		loanDemandPage=loanDemandRepository.findByIsDeletedAndLoanProductProductType(false, pageable,LoanConstants.LONG_TERM_LOAN);
			return new LongLoanDemandPageList(loanDemandPage
					.getContent()
					.stream()
					.map(mlLoanDemandMapper::loanDemandToMidLongLoanDemandDto)
					.collect(Collectors.toList()),
					PageRequest
						.of(loanDemandPage.getPageable().getPageNumber(),
								loanDemandPage.getPageable().getPageSize()),
						loanDemandPage.getTotalElements());
	}

	@Override
	public List<MidLongLoanDemandDto> getMLLoanList(String type) {
		return mlLoanDemandMapper.toDtoList(loanDemandRepository.findAllByLoanProductProductType(type));
	}


	@Override
	public List<MidLongLoanDemandDto> listLoanDemand() {
		return mlLoanDemandMapper.toDtoList(loanDemandRepository.findByIsDeleted());
	}


	@Override
	public MidLoanDemandPageList listLoanDemand(Pageable pageable) {
		Page<LoanDemand> loanDemandPage;
		loanDemandPage=loanDemandRepository.findByisDeleted(pageable, false);
		System.out.println("loan demand ---------------------------------------"+loanDemandPage);
			return new MidLoanDemandPageList(loanDemandPage
					.getContent()
					.stream()
					.map(mlLoanDemandMapper::loanDemandToMidLongLoanDemandDto)
					.collect(Collectors.toList()),
					PageRequest
						.of(loanDemandPage.getPageable().getPageNumber(),
								loanDemandPage.getPageable().getPageSize()),
						loanDemandPage.getTotalElements());
	}


	

}

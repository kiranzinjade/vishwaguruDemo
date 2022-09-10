package com.techvg.vks.loan.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.techvg.vks.config.LoanConstants;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.loan.domain.LoanDemand;
import com.techvg.vks.loan.domain.LoanWatap;
import com.techvg.vks.loan.mapper.LoanWatapMapper;
import com.techvg.vks.loan.model.LoanWatapDto;
import com.techvg.vks.loan.model.LoanWatapPageList;
import com.techvg.vks.loan.repository.LoanDemandRepository;
import com.techvg.vks.loan.repository.LoanWatapRepository;
import com.techvg.vks.membership.domain.Land;
import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.society.domain.CropRegistration;
import com.techvg.vks.society.domain.ProductType;
import com.techvg.vks.society.model.ProductTypePageList;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoanWatapServiceImpl implements LoanWatapService  {
  private final LoanDemandRepository loanDemandRepository;	
  private final LoanWatapRepository loanWatapRepository;
  private final LoanWatapMapper loanWatapMapper;
  Integer i;
	@Override
	public Boolean addLoanWatap(List<Long> loanWatapId) {
		// TODO Auto-generated method stub
		
	   int slotNumber=1;
		List<LoanWatap> loanwatapslot= loanWatapRepository.findByLastSlot();
		List<Long> loanwatapCropId= loanWatapRepository.findByAllCrop();
		
		if(loanwatapslot.isEmpty())
		{
			slotNumber=1;     
		}
		
		for(i=0;i<loanWatapId.size();i++) {
			LoanWatap loanWatap =new LoanWatap(); 
			LoanDemand loanDemand=loanDemandRepository.findById(loanWatapId.get(i)).orElseThrow(() -> new NotFoundException("No Loan details found for Id : " + loanWatapId.get(i)));
			loanWatap.setSeason(loanDemand.getSeason());
			loanWatap.setYear(loanDemand.getYear());
			loanWatap.setLoanAmount(loanDemand.getLoanAmount());			
			loanWatap.setCropLandAreaHector(loanDemand.getCropLandAreaHector());
			loanWatap.setCropLandAreaR(loanDemand.getCropLandAreaR());
			loanWatap.setStatus("Pending");
			boolean flag=false;
			for (int i = 0; i < loanwatapCropId.size(); i++) {
				if (loanwatapCropId.get(i) == loanDemand.getCrop().getId()) {
					flag = true;
				}
			}

			if (flag) {
				if(!loanwatapslot.isEmpty()) {
				slotNumber = loanwatapslot.get(0).getSlot();
				}
			} else {
				if(!loanwatapslot.isEmpty()) {
				slotNumber = loanwatapslot.get(0).getSlot() + 1;
				}
			}
				
			loanWatap.setSlot(slotNumber);
			loanWatap.setMember(loanDemand.getMember());
			loanWatap.setLoanDemand(loanDemand);
			loanWatap.setCrop(loanDemand.getCrop());
			loanWatapRepository.save(loanWatap);	
			loanDemand.setStatus(LoanConstants.LOAN_DEMAND_LOANWATAP);
			loanDemandRepository.save(loanDemand);
		}
		return true;
	}
	
	@Override
	public LoanWatapDto updateLoanWatap(Long loanWatapId, LoanWatapDto loanWatapDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoanWatapDto getLoanWatapId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoanWatapPageList listAllLoanWatap(Pageable pageable) {
		// TODO Auto-generated method stub
   log.debug("REST request to save Product Type : {}");	
		Page<LoanWatap> loanWatapPage;
		loanWatapPage = loanWatapRepository.findByisDeleted(pageable,false);
		return new LoanWatapPageList(loanWatapPage
										.getContent()
										.stream()
										.map(loanWatapMapper::toDto)
										.collect(Collectors.toList()),
										PageRequest
											.of(loanWatapPage.getPageable().getPageNumber(),
													loanWatapPage.getPageable().getPageSize()),
											loanWatapPage.getTotalElements());
		
	}

	@Override
	public LoanWatapDto deleteLoanWatapById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public List<LoanWatapDto> listLoanWatapBySlot(int slot) {
		List<LoanWatap> loanWatapSlotList= loanWatapRepository.findBySlot(slot);	
		return loanWatapMapper.toDtoList(loanWatapSlotList);
	}

	@Override
	public List<LoanWatapDto> allSlot() {
		// TODO Auto-generated method stub
		List<LoanWatap> allLoanWatapList= new ArrayList<>();
		List<Integer> slotIdList= loanWatapRepository.findByAllSlot();
		for(int i=0;i<slotIdList.size();i++) {
			LoanWatap loanWatap= new LoanWatap();
			List<LoanWatap>loanwatapObj= loanWatapRepository.findBySlot(slotIdList.get(i));
			loanWatap = loanWatapMapper.toNewDomain(loanwatapObj.get(0));
			allLoanWatapList.add(loanWatap);
		}		
		return loanWatapMapper.toDtoList(allLoanWatapList);
	}

	@Override
	public Boolean approveLoanWatap(int slot) {
		// TODO Auto-generated method stub
		List<LoanWatap> loanWatap= loanWatapRepository.findBySlot(slot);
		for(LoanWatap loanWatapobj:loanWatap) {
			loanWatapobj.setStatus(LoanConstants.LOAN_WATAP_APPROVED);
			loanWatapRepository.save(loanWatapobj);
		}
		return null;
	}
	
	
	
	
	
		
}

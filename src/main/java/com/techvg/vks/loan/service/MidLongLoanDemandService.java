package com.techvg.vks.loan.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.techvg.vks.loan.model.LongLoanDemandPageList;
import com.techvg.vks.loan.model.MidLongLoanDemandDto;
import com.techvg.vks.loan.model.MidLoanDemandPageList;

import java.util.List;

@Service
public interface MidLongLoanDemandService {
	
	MidLongLoanDemandDto addMidLongLoanDemandDetails(MidLongLoanDemandDto midLongLoanDemandDto);

	MidLongLoanDemandDto updateMidLongLoanDemandDetails(Long loanDemandId, MidLongLoanDemandDto midLongLoanDemandDto);
	
	MidLongLoanDemandDto getMidLongLoanDemandDetailsById(Long id);
	
	MidLongLoanDemandDto deleteMidLongLoanDemandDetailsById(Long id);
	
	MidLoanDemandPageList listMidLoanDemand(Pageable pageable);
	
	LongLoanDemandPageList listLongLoanDemand(Pageable pageable);

	List<MidLongLoanDemandDto> getMLLoanList(String type);

	List<MidLongLoanDemandDto> listLoanDemand();

	MidLoanDemandPageList listLoanDemand(Pageable pageable);

	

}

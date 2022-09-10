package com.techvg.vks.loan.service;

import com.techvg.vks.loan.model.CropLoanDemandDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CropLoanDemandService {

    List<CropLoanDemandDto> getList(Integer year);

    List<CropLoanDemandDto>  getListByStatus(boolean kmpStatus, boolean loanRegStatus, int i);

    CropLoanDemandDto approveKmp(long cropLoanDemandId);

    List<CropLoanDemandDto> listForLoanProduct();

    List<CropLoanDemandDto> listForApproval();

    List<CropLoanDemandDto>  listForLoanDemand();
}

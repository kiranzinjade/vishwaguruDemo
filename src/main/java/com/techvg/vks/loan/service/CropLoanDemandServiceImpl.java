package com.techvg.vks.loan.service;

import com.techvg.vks.loan.domain.CropLoanDemand;
import com.techvg.vks.loan.domain.LoanDemand;
import com.techvg.vks.loan.mapper.CropLoanDemandMapper;
import com.techvg.vks.loan.model.CropLoanDemandDto;
import com.techvg.vks.loan.repository.CropLoanDemandRepository;
import com.techvg.vks.share.domain.SharesAllocation;
import com.techvg.vks.society.domain.LoanProduct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CropLoanDemandServiceImpl implements CropLoanDemandService {
    private final CropLoanDemandRepository repo;
    private final CropLoanDemandMapper mapper;

    @Override
    public List<CropLoanDemandDto> getList(Integer year) {
        return mapper.toDtoList(repo.findByKmpStatusAndLoanRegistrationStatusAndYear(false,false, year));
    }

    @Override
    public List<CropLoanDemandDto> getListByStatus(boolean kmpStatus, boolean loanRegStatus, int year) {
        return mapper.toDtoList(repo.findByKmpStatusAndLoanRegistrationStatusAndYear(kmpStatus,loanRegStatus, year));
    }

    @Override
    public CropLoanDemandDto approveKmp(long cropLoanDemandId) {
        CropLoanDemand croploanDemand=repo.findByCropLoanDemandId(cropLoanDemandId);
        croploanDemand.setKmpApprovalStatus(true);
        repo.save(croploanDemand);
        double totalKMPLoanAmt = croploanDemand.getLoanDemand().stream().mapToDouble(LoanDemand::getLoanAmount).sum();
        LoanProduct loanProduct = croploanDemand.getLoanProduct();

        return mapper.toDto(croploanDemand);
    }

    @Override
    public List<CropLoanDemandDto> listForLoanProduct() {
        return mapper.toDtoList(repo.findForLoanProduct());
    }

    @Override
    public List<CropLoanDemandDto> listForApproval() {
        return mapper.toDtoList(repo.findKMPForApproval());
    }

    @Override
    public List<CropLoanDemandDto> listForLoanDemand() {
        return mapper.toDtoList(repo.findForDemandLoan());
    }
}

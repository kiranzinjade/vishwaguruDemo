package com.techvg.vks.loan.repository;

import com.techvg.vks.loan.domain.CropLoanDemand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CropLoanDemandRepository extends JpaRepository<CropLoanDemand, Long> {

    @Query(value="select cl from CropLoanDemand cl where cl.isDeleted = false and cl.year =:year and cl.crop.id =:cropRegId")
    CropLoanDemand findByCropIDAndYear(@Param("cropRegId") Long cropRegId, @Param("year") Integer year);

    List<CropLoanDemand> findByKmpStatusAndLoanRegistrationStatusAndYear(boolean kmpStatus, boolean loanRegStatus, Integer year);

    @Query(value="select cl from CropLoanDemand cl where cl.isDeleted = false and cl.id =:cropLoanDemandId")
    CropLoanDemand findByCropLoanDemandId(Long cropLoanDemandId);

    @Query(value="select cl from CropLoanDemand cl where cl.isDeleted = false and cl.loanProduct.id IS NULL")
    List<CropLoanDemand> findForLoanProduct();

    @Query(value="select cl from CropLoanDemand cl where cl.isDeleted = false and cl.societyKmpStatus = true and cl.loanRegistrationStatus = false ")
    List<CropLoanDemand> findKMPForApproval();

    @Query(value="select cl from CropLoanDemand cl where cl.isDeleted = false and cl.kmpStatus = false ")
    List<CropLoanDemand> findForDemandLoan();
}

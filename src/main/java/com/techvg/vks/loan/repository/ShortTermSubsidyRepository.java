package com.techvg.vks.loan.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.techvg.vks.loan.domain.LoanDetails;
import com.techvg.vks.loan.domain.ShortTermSubsidy;
import com.techvg.vks.loan.model.SubsidyDto;

@Repository
public interface ShortTermSubsidyRepository extends JpaRepository<ShortTermSubsidy, Long>{

	Page<ShortTermSubsidy> findByisDeleted(Pageable pageable, boolean b);

	Page<ShortTermSubsidy>  findByLoanDetails(Pageable pageable,LoanDetails loanDetails);

}

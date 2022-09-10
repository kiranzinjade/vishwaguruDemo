package com.techvg.vks.loan.repository;

import com.techvg.vks.loan.domain.KMP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KMPRepository extends JpaRepository<KMP,Long> {

    KMP findByYearAndKmpApprovalStatus(int year, boolean status);
    KMP findByYearAndKmpGenerationStatus(int year, boolean status);
    List<KMP> findByKmpApprovalStatus(boolean status);
    List<KMP> findByKmpGenerationStatus(boolean status);
}

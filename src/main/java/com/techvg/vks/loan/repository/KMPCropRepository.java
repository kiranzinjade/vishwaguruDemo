package com.techvg.vks.loan.repository;

import com.techvg.vks.loan.domain.KMPCrop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KMPCropRepository extends JpaRepository<KMPCrop,Long> {

    List<KMPCrop> findByYear(int year);

    List<KMPCrop> findByKmp_Id(Long kmpId);
}

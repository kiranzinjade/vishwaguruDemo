package com.techvg.vks.loan.repository;

import com.techvg.vks.loan.domain.KMPSociety;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KMPSocietyRepository extends JpaRepository<KMPSociety,Long> {

    KMPSociety findByYear(int year);

}

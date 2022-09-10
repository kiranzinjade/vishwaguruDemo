package com.techvg.vks.loan.repository;

import com.techvg.vks.loan.domain.NPAData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NPADataRepository extends JpaRepository<NPAData, Long> {

}

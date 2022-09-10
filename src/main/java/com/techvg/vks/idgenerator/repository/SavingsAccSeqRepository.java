package com.techvg.vks.idgenerator.repository;

import com.techvg.vks.idgenerator.FDAccSeq;
import com.techvg.vks.idgenerator.SavingsAccSeq;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingsAccSeqRepository extends JpaRepository<SavingsAccSeq, Long> {
}

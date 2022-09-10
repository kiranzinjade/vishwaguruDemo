package com.techvg.vks.idgenerator.repository;

import com.techvg.vks.idgenerator.FDAccSeq;
import com.techvg.vks.idgenerator.RDAccSeq;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FDAccSeqRepository extends JpaRepository<FDAccSeq, Long> {
}

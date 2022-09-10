package com.techvg.vks.idgenerator.repository;

import com.techvg.vks.idgenerator.VoucherSeq;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoucherSeqRepository extends JpaRepository<VoucherSeq, Long> {
}

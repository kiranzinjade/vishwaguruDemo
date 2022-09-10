package com.techvg.vks.accounts.repository;

import com.techvg.vks.accounts.domain.VoucherType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface VoucherTypeRepository extends JpaRepository<VoucherType, Long> {

    Optional<VoucherType> findByNameAndIsDeleted(String name,boolean deleted);
    List<VoucherType> findByIsGeneralAndIsDeleted(boolean isGeneral, boolean deleted);
    List<VoucherType> findByIsDeleted(boolean deleted);
    Page<VoucherType> findByIsDeleted(boolean deleted, Pageable pageable);
}

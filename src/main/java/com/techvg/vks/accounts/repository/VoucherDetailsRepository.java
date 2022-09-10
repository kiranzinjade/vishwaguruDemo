package com.techvg.vks.accounts.repository;

import com.techvg.vks.accounts.domain.VoucherDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface VoucherDetailsRepository extends JpaRepository<VoucherDetails, Long> {

}

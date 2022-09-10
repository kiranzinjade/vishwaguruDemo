package com.techvg.vks.accounts.repository;

import com.techvg.vks.accounts.domain.Vouchers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface VouchersRepository extends JpaRepository<Vouchers, Long> {

    Optional<Vouchers> findByVoucherNo(Long voucherNo);
}

package com.techvg.vks.accounts.repository;

import com.techvg.vks.accounts.domain.VoucherTrans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface VoucherTransRepository extends JpaRepository<VoucherTrans, Long> {


    @Query(value=" select c from VoucherTrans c where date(c.transDate) = :transDate and c.dayBookCreated= false")
    List<VoucherTrans> getVoucherTransByTransDate(@Param("transDate") Date transDate);

}

package com.techvg.vks.deposit.repository;

import com.techvg.vks.deposit.domain.DepositAccrual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepositAccrualRepository extends JpaRepository<DepositAccrual, Long> {

    List<DepositAccrual> findByDeposit_id(Long depositId);

}

package com.techvg.vks.deposit.service;

import com.techvg.vks.deposit.model.DepositAccrualDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DepositAccrualService {

    DepositAccrualDto accrualInterestPosting(DepositAccrualDto accrualDto);

    List<DepositAccrualDto> getAccrualsForDeposit(Long depositId);
}

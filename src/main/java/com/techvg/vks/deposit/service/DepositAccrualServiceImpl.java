package com.techvg.vks.deposit.service;

import com.techvg.vks.deposit.domain.Deposit;
import com.techvg.vks.deposit.domain.DepositAccrual;
import com.techvg.vks.deposit.mapper.DepositAccrualMapper;
import com.techvg.vks.deposit.model.DepositAccrualDto;
import com.techvg.vks.deposit.repository.DepositAccrualRepository;
import com.techvg.vks.deposit.repository.DepositRepository;
import com.techvg.vks.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepositAccrualServiceImpl implements DepositAccrualService {

    private final DepositAccrualRepository repo;
    private final DepositAccrualMapper mapper;

    private final DepositRepository depositRepository;

    @Override
    public DepositAccrualDto accrualInterestPosting(DepositAccrualDto accrualDto) {

        Deposit deposit=depositRepository.findById(accrualDto.getDepositId()).orElseThrow(
                () -> new NotFoundException("No deposit Account found for Id : " +accrualDto.getDepositId()));

        DepositAccrual depositAccrual = mapper.toDomain(accrualDto);
        depositAccrual.setDeposit(deposit);

        return mapper.toDto(repo.save(depositAccrual));
    }

    @Override
    public List<DepositAccrualDto> getAccrualsForDeposit(Long depositId) {
        return mapper.toDtoList(repo.findByDeposit_id(depositId));
    }
}

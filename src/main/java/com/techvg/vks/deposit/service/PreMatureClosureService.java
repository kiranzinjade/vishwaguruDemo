package com.techvg.vks.deposit.service;

import org.springframework.stereotype.Service;

import com.techvg.vks.deposit.model.DepositDto;

@Service
public interface PreMatureClosureService {

	DepositDto preMatureDepositClose(Long id);

}

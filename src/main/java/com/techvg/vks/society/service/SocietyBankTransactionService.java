package com.techvg.vks.society.service;

import com.techvg.vks.accounts.domain.VoucherTrans;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import com.techvg.vks.society.model.SocietyBankTransactionDto;
import com.techvg.vks.society.model.SocietyBankTransactionPageList;

import java.util.List;

public interface SocietyBankTransactionService {

	SocietyBankTransactionDto addSocietyBankTransaction(SocietyBankTransactionDto societyBankTransactionDto,
			 Authentication authentication);

	SocietyBankTransactionDto addSocietyBankTransaction(SocietyBankTransactionDto societyBankTransactionDto);

	SocietyBankTransactionPageList listsocietyBankTransactionService(Pageable pageable);

	SocietyBankTransactionDto deleteTransactionById(Long id);

	SocietyBankTransactionDto updateTransaction(Long id, SocietyBankTransactionDto societyBankTransactionDto);

	SocietyBankTransactionDto getTransactionById(Long id);

	boolean addBankBookEntry(List<VoucherTrans> voucherTransList);

}

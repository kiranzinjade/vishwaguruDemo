package com.techvg.vks.society.service;

import com.techvg.vks.accounts.domain.VoucherTrans;
import com.techvg.vks.society.domain.SocietyBank;
import com.techvg.vks.society.model.SocietyBankDto;
import com.techvg.vks.society.model.SocietyBankPageList;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SocietyBankService {
	SocietyBankDto addNewBank(SocietyBankDto bankDto);

	SocietyBankDto updateBank(Long bankId, SocietyBankDto bankDto);

	SocietyBankDto getBankById(Long id);

	SocietyBankPageList listBanks(Pageable pageable);

	SocietyBankDto deleteBankById(Long id);

	List<SocietyBankDto> listBank();

	SocietyBank getBankByAccHead(String  accHead);


}

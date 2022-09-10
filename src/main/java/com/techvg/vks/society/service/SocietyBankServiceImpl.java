package com.techvg.vks.society.service;

import com.techvg.vks.accounts.domain.LedgerAccounts;
import com.techvg.vks.accounts.domain.VoucherTrans;
import com.techvg.vks.accounts.model.LedgerAccountsDto;
import com.techvg.vks.accounts.repository.LedgerAccountsRepository;
import com.techvg.vks.exceptions.AlreadyExitsException;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.society.domain.DepositType;
import com.techvg.vks.society.domain.SocietyBank;
import com.techvg.vks.society.mapper.SocietyBankMapper;
import com.techvg.vks.society.model.SocietyBankDto;
import com.techvg.vks.society.model.SocietyBankPageList;
import com.techvg.vks.society.repository.DepositTypeRepository;
import com.techvg.vks.society.repository.SocietyBankRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SocietyBankServiceImpl implements SocietyBankService {


	private final SocietyBankRepository bankRepository;
	private final SocietyBankMapper bankMapper;
	private final DepositTypeRepository  depositTypeRepository;
	private final LedgerAccountsRepository ledgerAccountsRepository;

	public SocietyBankDto addNewBank(SocietyBankDto bankDto) {

		SocietyBank  societybank=bankMapper.bankDtoToBank(bankDto);
		//DepositType   depositType=depositTypeRepository.findById(bankDto.getDepositTypeId()).orElseThrow(NotFoundException::new);
	//	societybank.setDepositType(depositType);
		Optional<SocietyBank> bankObjOptional = bankRepository.findByIfsccodeAndIsDeleted(bankDto.getIfsccode(),false);
		Optional<SocietyBank> bank = bankRepository.findByBankNameAndIsDeleted(bankDto.getBankName(),false);
		Optional<SocietyBank> bankbranch = bankRepository.findByBranchName(bankDto.getBranchName());
		Optional<SocietyBank> accHeadCode=bankRepository.findByAccHeadCode(bankDto.getAccHeadCode());

			if (bankObjOptional.isPresent()){
				throw new AlreadyExitsException("Bank already exists with the IFSC Code : " + bankDto.getIfsccode());
			}else if (bank.isPresent()){
				throw new AlreadyExitsException("Bank already exists with the Bank Name : " + bankDto.getBankName());
			}else if (bankbranch.isPresent()){
				throw new AlreadyExitsException("Bank already exists with the Branch Name : " + bankDto.getBranchName());
			}
			else if(accHeadCode.isPresent()) {
				throw new AlreadyExitsException("Bank already exists with the AccHeadCode : " + bankDto.getAccHeadCode());
			}
			else {
				societybank.setStatus("A");
				addToLedgerAccounts(societybank);
				bankRepository.save(societybank);
				return bankMapper.bankToBankDto(societybank);
			}
	}

	private boolean addToLedgerAccounts(SocietyBank societybank){

		LedgerAccounts ledgerAccounts = ledgerAccountsRepository.findByAccHeadCode(societybank.getAccHeadCode());

		LedgerAccounts accounts = new LedgerAccounts();
		accounts.setParentLedger(ledgerAccounts);
		accounts.setAccountType(ledgerAccounts.getAccountType());
		accounts.setLevel(ledgerAccounts.getLevel() + 1);
		accounts.setClassification(ledgerAccounts.getClassification());
		accounts.setAccBalance(0.0);
		accounts.setAccountName(societybank.getAccountName());
		accounts.setAccHeadCode(societybank.getAccountName() + "_" + societybank.getAccHeadCode());
		accounts.setAccountNo(societybank.getAccountNumber());
		ledgerAccountsRepository.save(accounts);

		return true;
	}

	public SocietyBankPageList listBanks(Pageable pageable) {
		log.debug("Request to get Bank : {}");

		Page<SocietyBank> bankPage;
		bankPage = bankRepository.findByisDeleted(pageable,false);

		return new SocietyBankPageList(bankPage
										.getContent()
										.stream()
										.map(bankMapper::bankToBankDto)
										.collect(Collectors.toList()),
										PageRequest
											.of(bankPage.getPageable().getPageNumber(),
													bankPage.getPageable().getPageSize()),
										bankPage.getTotalElements());

	}

	public SocietyBankDto getBankById(Long id) {

		log.debug("REST request to get BankMaster : {}", id);
		SocietyBank bank = bankRepository.findById(id).orElseThrow(
				() -> new NotFoundException("No Bank found for Id : " +id));

		return bankMapper.bankToBankDto(bank);
	}

	public SocietyBankDto updateBank(Long bankId, SocietyBankDto bankDto) {

			SocietyBank bank = bankRepository.findById(bankId).orElseThrow(NotFoundException::new);
		//	DepositType   depositType=depositTypeRepository.findById(bankDto.getDepositTypeId()).orElseThrow(NotFoundException::new);
		//	bank.setDepositType(depositType);
			
			bank.setStatus(bankDto.getStatus());
			bank.setBankName(bankDto.getBankName());
			bank.setBranchName(bankDto.getBranchName());
			bank.setIfsccode(bankDto.getIfsccode());
			bank.setAccountNumber(bankDto.getAccountNumber());
			bank.setAccountType(bankDto.getAccountType());
			
			return bankMapper.bankToBankDto(bankRepository.save(bank));
	}

	public SocietyBankDto deleteBankById(Long id) {
		SocietyBank bank = bankRepository.findById(id).orElseThrow(NotFoundException::new);
			if (bank != null) {
				bank.setIsDeleted(true);
				bankRepository.save(bank);
			}
		return bankMapper.bankToBankDto(bank);
	}

	@Override
	public List<SocietyBankDto> listBank() {
		return bankMapper.domainToDtoList(bankRepository.findAll());
	}

	@Override
	public SocietyBank getBankByAccHead(String accHead) {
		return bankRepository.findByAccHeadCodeAndIsDeleted(accHead, false);
	}

}

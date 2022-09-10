package com.techvg.vks.society.service;

import com.techvg.vks.exceptions.AlreadyExitsException;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.society.domain.DepositAccount;
import com.techvg.vks.society.domain.DepositType;
import com.techvg.vks.society.mapper.DepositAccountMapper;
import com.techvg.vks.society.model.DepositAccountDto;
import com.techvg.vks.society.model.DepositAccountPageList;
import com.techvg.vks.society.repository.DepositAccountRepository;
import com.techvg.vks.society.repository.DepositTypeRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepositAccountServiceImpl implements DepositAccountService {
    private final DepositAccountRepository depositAccountRepository;
    private final DepositAccountMapper depositAccountMapper;
    private final DepositTypeRepository depositTypeRepository;

    @Override
    public DepositAccountDto addDepositAccount(DepositAccountDto depositAccountDto, Authentication authentication) {
        Optional<DepositAccount> depositAccountOptional = depositAccountRepository.findByNameAndAccountTypeAndIsDeleted(depositAccountDto.getName(),depositAccountDto.getDepositAccTypeId());
        if (depositAccountOptional.isPresent()){
            throw new AlreadyExitsException("DepositAccount already exists : " + depositAccountDto.getName());
        }
        else {
            DepositType depositType = depositTypeRepository.findById(depositAccountDto.getDepositAccTypeId()).orElseThrow(NotFoundException::new);
            DepositAccount depositAccount = depositAccountMapper.toDomain(depositAccountDto);
            depositAccount.setDepositType(depositType);
            return depositAccountMapper.toDto(depositAccountRepository.save(depositAccount));
        }
    }

    @Override
    public DepositAccountDto updateDepositAccount(Long depositAccId, DepositAccountDto depositAccountDto) {
    	DepositAccount depositAccount = depositAccountRepository.findById(depositAccId).orElseThrow(NotFoundException::new);
    	 depositAccountDto.setId(depositAccount.getId());
    	 DepositAccount depositAccounts = depositAccountMapper.toDomain(depositAccountDto);
    	 depositAccounts.setDepositType(depositAccount.getDepositType());
         return depositAccountMapper.toDto(depositAccountRepository.save(depositAccounts));
    }

    @Override
    public DepositAccountDto getDepositAccountById(Long depositAccId) {

        DepositAccount depositAccount = depositAccountRepository.findById(depositAccId).orElseThrow(
                () -> new NotFoundException("No Deposit Account found for Id : " +depositAccId));
        return depositAccountMapper.toDto(depositAccount);
    }

    @Override
    public DepositAccountDto deleteDepositAccountById(Long depositAccId) {

        DepositAccount depositAccount = depositAccountRepository.findById(depositAccId).orElseThrow(NotFoundException::new);
        if (depositAccount != null) {
            depositAccount.setIsDeleted(true);
            depositAccountRepository.save(depositAccount);
        }
        return depositAccountMapper.toDto(depositAccount);
    }

    @Override
    public DepositAccountPageList listDepositAccounts(Pageable pageable) {

        Page<DepositAccount> depositAccountPage;
        depositAccountPage = depositAccountRepository.findByisDeleted(pageable,false);

        return new DepositAccountPageList(depositAccountPage
                .getContent()
                .stream()
                .map(depositAccountMapper::toDto)
                .collect(Collectors.toList()),
                PageRequest
                        .of(depositAccountPage.getPageable().getPageNumber(),
                                depositAccountPage.getPageable().getPageSize()),
                depositAccountPage.getTotalElements());
    }


	@Override
	public List<DepositAccountDto> listSchemes() {
		
		return depositAccountMapper.domainToDtoList(depositAccountRepository.findByDepositAccount());
	}
    
    @Override
	public List<DepositAccountDto> listAllSchemes() {
		return depositAccountMapper.domainToDtoList(depositAccountRepository.findByAccountType());


	}
}

package com.techvg.vks.accounts.service;

import com.techvg.vks.accounts.domain.AccountType;
import com.techvg.vks.accounts.domain.LedgerAccounts;
import com.techvg.vks.accounts.mapper.LedgerAccountMapper;
import com.techvg.vks.accounts.model.LedgerAccountSearchCriteria;
import com.techvg.vks.accounts.model.LedgerAccountsDto;
import com.techvg.vks.accounts.model.LedgerAccountsPageList;
import com.techvg.vks.accounts.repository.AccountTypeRepository;
import com.techvg.vks.accounts.repository.LedgerAccountsRepository;
import com.techvg.vks.config.AccountConstants;
import com.techvg.vks.exceptions.AlreadyExitsException;
import com.techvg.vks.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.techvg.vks.accounts.repository.LedgerAccountSpecification.getLedgerAccounts;

@Service
@RequiredArgsConstructor
public class LedgerAccountsServiceImpl implements LedgerAccountsService {
    private final LedgerAccountMapper ledgerAccountMapper;
    private final LedgerAccountsRepository ledgerAccountsRepository;
    private final AccountTypeRepository accountTypeRepository;

    @Override
    public LedgerAccountsDto addLedgerAccounts(LedgerAccountsDto ledgerAccountsDto) {
        LedgerAccounts parentLedgerAccount = null;
        AccountType accountType = null;
        accountType = accountTypeRepository.findById(ledgerAccountsDto.getAccTypeId()).orElseThrow(
                () -> new NotFoundException("No Account Type found for Id : " +ledgerAccountsDto.getAccTypeId()));
        if(ledgerAccountsDto.getHasParent()){
             parentLedgerAccount = ledgerAccountsRepository.findById(ledgerAccountsDto.getParentId()).orElseThrow(
                     () -> new NotFoundException("No Parent Ledger Account found for Id : " +ledgerAccountsDto.getParentId()));
        }

        Optional<LedgerAccounts> ledgerAccountsObjOptional = ledgerAccountsRepository.findByAccountNameAndIsDeleted(ledgerAccountsDto.getAccountName(), false);
        if (ledgerAccountsObjOptional.isPresent()){
            throw new AlreadyExitsException("LedgerAccount already exists : " + ledgerAccountsDto.getAccountName());
        }
        else {
            LedgerAccounts ledgerAccounts = ledgerAccountMapper.toLedgerAccounts(ledgerAccountsDto);
            if(ledgerAccountsDto.getHasParent()) {
                ledgerAccounts.setParentLedger(parentLedgerAccount);
                ledgerAccounts.setAccountType(parentLedgerAccount.getAccountType());
                ledgerAccounts.setClassification(parentLedgerAccount.getClassification());
                ledgerAccounts.setLevel(parentLedgerAccount.getLevel() + 1);
            }else{
                ledgerAccounts.setAccountType(accountType);
                ledgerAccounts.setLevel(1);
            }
            return ledgerAccountMapper.toLedgerAccountsDto(ledgerAccountsRepository.save(ledgerAccounts));
        }
    }

    @Override
    public LedgerAccountsDto updateLedgerAccounts(Long ledgerAccountsId, LedgerAccountsDto ledgerAccountsDto, Authentication authentication) {
        ledgerAccountsRepository.findById(ledgerAccountsId).orElseThrow(
                () -> new NotFoundException("No LedgerAccounts found for Id : " +ledgerAccountsId));
        LedgerAccounts ledgerAccounts = ledgerAccountMapper.toLedgerAccounts(ledgerAccountsDto);
        ledgerAccounts.setId(ledgerAccountsId);

        AccountType accountType = accountTypeRepository.findById(ledgerAccountsDto.getAccTypeId()).orElseThrow(
                () -> new NotFoundException("No Account Type found for Id : " +ledgerAccountsDto.getAccTypeId()));

        LedgerAccounts parentLedgerAccount = ledgerAccountsRepository.findById(ledgerAccountsDto.getParentId()).orElseThrow(
                () -> new NotFoundException("No Parent Ledger Account found for Id : " +ledgerAccountsDto.getParentId()));

        ledgerAccounts.setAccountType(accountType);
        ledgerAccounts.setParentLedger(parentLedgerAccount);
        ledgerAccounts.setAccountType(parentLedgerAccount.getAccountType());
        ledgerAccounts.setClassification(parentLedgerAccount.getClassification());
        ledgerAccounts.setLevel(parentLedgerAccount.getLevel() + 1);
        return ledgerAccountMapper.toLedgerAccountsDto(ledgerAccountsRepository.save(ledgerAccounts));
    }

    @Override
    public LedgerAccountsDto getLedgerAccountsById(Long ledgerAccountsId) {
        LedgerAccounts ledgerAccounts = ledgerAccountsRepository.findById(ledgerAccountsId).orElseThrow(
                () -> new NotFoundException("No LedgerAccounts found for Id : " +ledgerAccountsId));
        return ledgerAccountMapper.toLedgerAccountsDto(ledgerAccounts);
    }

    @Override
    public LedgerAccountsPageList listLedgerAccounts(Pageable pageable) {

        Page<LedgerAccounts> ledgerAccountsPage;
        ledgerAccountsPage = ledgerAccountsRepository.findByIsDeleted(pageable,false);

        List<LedgerAccountsDto> ledgerAccountsList = new ArrayList<>();
        for ( LedgerAccounts ledgerAccounts1 : ledgerAccountsPage ) {
            ledgerAccountsList.add( ledgerAccountMapper.toLedgerAccountsDto( ledgerAccounts1) );
        }

        return new LedgerAccountsPageList(ledgerAccountsList,
                PageRequest
                        .of(ledgerAccountsPage.getPageable().getPageNumber(),
                                ledgerAccountsPage.getPageable().getPageSize()),
                ledgerAccountsPage.getTotalElements());
    }

    @Override
    public LedgerAccountsDto deleteLedgerAccountsById(Long ledgerAccountsId) {

        LedgerAccounts ledgerAccounts = ledgerAccountsRepository.findById(ledgerAccountsId).orElseThrow(NotFoundException::new);
        if (ledgerAccounts != null) {
            ledgerAccounts.setIsDeleted(true);
            ledgerAccountsRepository.save(ledgerAccounts);
        }
        return ledgerAccountMapper.toLedgerAccountsDto(ledgerAccounts);
    }

	@Override
	public List<LedgerAccountsDto> listAllLedgerAccount() {
		return ledgerAccountMapper.domainToDtoList(ledgerAccountsRepository.findByIsDeleted(false));
	}

    @Override
    public List<LedgerAccountsDto> listLedgerAccountByParent(String parentAccHeadCode) {
        LedgerAccounts ledgerAccounts = ledgerAccountsRepository.findByAccHeadCode(parentAccHeadCode);
        return ledgerAccountMapper.domainToDtoList(ledgerAccountsRepository.findByParentLedger_Id(ledgerAccounts.getId()));
    }

    @Override
    public List<LedgerAccountsDto> listSocietyExpenseAccounts() {
        List<LedgerAccountsDto> ledgerAccountsFilteredList = new ArrayList<>();
        for(LedgerAccountsDto acc: getSocietyExpenseAccounts(AccountConstants.ESTABLISHMENT_AND_OTHER_EXPENSES)){
            if(acc.getAccHeadCode().contains(AccountConstants.EXPENSE_FILTER_SOCIETY)){
                continue;
            }
            ledgerAccountsFilteredList.add(acc);
        }
        return ledgerAccountsFilteredList;
    }

    @Override
    public List<LedgerAccountsDto> listSocietyExpenseProvisionAccounts() {
        List<LedgerAccountsDto> ledgerAccountsFilteredList = new ArrayList<>();
        for(LedgerAccountsDto acc: getSocietyExpenseAccounts(AccountConstants.ESTABLISHMENT_AND_OTHER_EXPENSES)){
            if(!(acc.getAccHeadCode().contains(AccountConstants.EXPENSE_FILTER_SOCIETY))){
                continue;
            }
            ledgerAccountsFilteredList.add(acc);
        }
        return ledgerAccountsFilteredList;
    }

    @Override
    public List<LedgerAccountsDto> listTradingExpenseAccounts() {
        List<LedgerAccountsDto> ledgerAccountsFilteredList = new ArrayList<>();
        for(LedgerAccountsDto acc: getSocietyExpenseAccounts(AccountConstants.TRADING_ACCOUNT)){
            if((acc.getAccHeadCode().contains(AccountConstants.EXPENSE_FILTER_SALE)) ||
               (acc.getAccHeadCode().startsWith(AccountConstants.EXPENSE_FILTER_PURCHASE)) ||
               (acc.getAccTypeName().equalsIgnoreCase(AccountConstants.INCOME))){
                continue;
            }
            ledgerAccountsFilteredList.add(acc);
        }
        return ledgerAccountsFilteredList;
    }

    @Override
    public List<LedgerAccountsDto> listLedgerAccounts(LedgerAccountSearchCriteria criteria) {
        //return ledgerAccountMapper.domainToDtoList(ledgerAccountsRepository.findAll(getLedgerAccounts(criteria)));
        return ledgerAccountMapper.domainToDtoList(ledgerAccountsRepository.findLikeAccountHead(criteria.getAccHeadCode()));
    }

    private List<LedgerAccountsDto> getSocietyExpenseAccounts(String accHeadCode){
        LedgerAccounts ledgerAccounts = ledgerAccountsRepository.findByAccHeadCode(accHeadCode);
        return ledgerAccountMapper.domainToDtoList(ledgerAccountsRepository.findByParentLedger_Id(ledgerAccounts.getId()));
    }
}

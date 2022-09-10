package com.techvg.vks.accounts.reports.TrialBalanceReport;

import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Service;

import com.techvg.vks.accounts.domain.GeneralLedger;
import com.techvg.vks.accounts.mapper.GeneralLedgerMapper;
import com.techvg.vks.accounts.model.GeneralLedgerDto;
import com.techvg.vks.accounts.repository.GeneralLedgerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrialBalanceReportServiceImpl implements TrialBalanceReportService {
	
	private final GeneralLedgerRepository generalLedgerRepository;
	private final GeneralLedgerMapper generalLedgerMapper;
	
	
	@Override
	public List<GeneralLedgerDto> getAssetAndExpenseList(Integer transMonth)  {
		int year = Calendar.getInstance().get(Calendar.YEAR);

		List<GeneralLedger> ledger = generalLedgerRepository.findRecordsByAssetAndExpense(transMonth, year);
		List<GeneralLedgerDto>ledger1=generalLedgerMapper.domainToDtoList(ledger);
		ledger1.forEach(action->{
			int year1=Calendar.getInstance().get(Calendar.YEAR);
			int transMonth1;
			if(transMonth==01) {
				 transMonth1=12;
				year1=year-1;
			}
			else {
				transMonth1=transMonth-1;
			}
			
			GeneralLedger ledger2 = generalLedgerRepository.findLastRecordOfPreviousMonth(transMonth1, year1,action.getLedgerAccountId());
			 action.setOpeningBalance(ledger2.getBalance());
		});
		return ledger1;


		
	}


	@Override
	public List<GeneralLedgerDto> getLiabilityAndIncomeList(Integer transMonth) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		List<GeneralLedger> ledger = generalLedgerRepository.findRecordsByLiabilityAndIncome(transMonth, year);
		List<GeneralLedgerDto>ledger1=generalLedgerMapper.domainToDtoList(ledger);
		ledger1.forEach(action->{
			int year1=Calendar.getInstance().get(Calendar.YEAR);
			int transMonth1;
			if(transMonth==01) {
				 transMonth1=12;
				year1=year-1;
			}
			else {
				transMonth1=transMonth-1;
			}
			GeneralLedger ledger2 = generalLedgerRepository.findLastRecordOfPreviousMonth(transMonth1, year1,action.getLedgerAccountId());
			 action.setOpeningBalance(ledger2.getBalance());
		});
		return ledger1;
		
	}
}
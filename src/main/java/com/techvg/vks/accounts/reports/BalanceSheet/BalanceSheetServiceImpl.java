package com.techvg.vks.accounts.reports.BalanceSheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.techvg.vks.accounts.domain.LedgerAccounts;
import com.techvg.vks.accounts.mapper.LedgerAccountMapper;
import com.techvg.vks.accounts.model.LedgerAccountsDto;
import com.techvg.vks.accounts.repository.LedgerAccountsRepository;
import com.techvg.vks.config.AccountConstants;
import com.techvg.vks.exceptions.NotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
@RequiredArgsConstructor
@Slf4j
public class BalanceSheetServiceImpl implements BalanceSheetService{
	
	private final LedgerAccountsRepository  ledgerAccountsRepository;
	private final BalanceSheetMapper balanceSheetMapper;
	private final LedgerAccountMapper ledgerAccountMapper;
	
	private static final String FILEPATH_BALANCESHEET_REPORT = "/balance_report.jrxml";
	
	@Override
	public byte[] generateBalanceSheet() {
		
		File resource = null;
		byte[] balanceReportblob = null;

		try {
			resource = new ClassPathResource(FILEPATH_BALANCESHEET_REPORT).getFile();
			log.info("file got for balance report = " + resource.getName());

		} catch (IOException e) {
			throw new NotFoundException("File Not Found...");
		}
		List<LedgerAccounts> ledgerList=ledgerAccountsRepository.findByAccountTypeId();
		List<LedgerAccounts> parentList=new ArrayList<LedgerAccounts>();
		Date date=new Date();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		Calendar previousYear=Calendar.getInstance();
		previousYear.add(Calendar.YEAR, -1);
		int year1=previousYear.get(Calendar.YEAR);
		for(int i=0;i<ledgerList.size();i++) {
			if(ledgerList.get(i).getLevel()==1) {
				parentList.add(ledgerList.get(i));
				List<LedgerAccounts> list=ledgerAccountsRepository.findLedgerList(ledgerList.get(i).getId());
				parentList.addAll(list);
				List<LedgerAccounts> list1=ledgerAccountsRepository.findLedgerList(list.get(i).getId());
				parentList.addAll(list1);
			}
		}
		List<BalanceSheetWrapper> balanceSheetWrapperList = this.balanceSheetMapper.mapAllLedgerList(parentList);
		JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(balanceSheetWrapperList);
		try {
			JasperReport report = JasperCompileManager.compileReport(new FileInputStream(resource));
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("SocietyName", "Vividh Karyakari Society");
			params.put("year","31 Mar "+year+"(Current Year)");
			params.put("year1", "31 Mar "+year1+"(Previous Year)");
			JasperPrint print = JasperFillManager.fillReport(report, params, beanDataSource);
			balanceReportblob = JasperExportManager.exportReportToPdf(print);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return balanceReportblob;
	}

	@Override
	public List<LedgerAccountsDto> listLiabilities() {
		List<LedgerAccounts> ledgerList=ledgerAccountsRepository.findBalanceSheetByAccountType(AccountConstants.LIABILITY);
		return ledgerAccountMapper.domainToDtoList(ledgerList);
	}

	@Override
	public List<LedgerAccountsDto> listAssets() {
		List<LedgerAccounts> ledgerList=ledgerAccountsRepository.findBalanceSheetByAccountType(AccountConstants.ASSET);
		return ledgerAccountMapper.domainToDtoList(ledgerList);
	}

}

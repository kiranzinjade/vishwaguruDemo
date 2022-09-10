package com.techvg.vks.deposit.reports.DepositLedger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.techvg.vks.deposit.domain.Deposit;
import com.techvg.vks.deposit.domain.DepositLedger;
import com.techvg.vks.deposit.repository.DepositLedgerRepository;
import com.techvg.vks.deposit.repository.DepositRepository;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.society.domain.SocietyInvestment;
import com.techvg.vks.society.report.InvestmentLedger.InvestmentLedgerWrapper;

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
public class DepositLedgerReportServiceImpl implements DepositLedgerReportService {
	
	private final DepositLedgerReportMapper  depositLedgerReportMapper;
	private final DepositLedgerRepository  depositLedgerRepository;
	private final DepositRepository depositRepository;
	private static final String FILEPATH_DEPOSIT_LEDGER_REPORT = "/Deposit_Ledger1.jrxml";
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public byte[] getDepositLedgerReport(Long id,Long accountNo) {
		File resource = null;
		byte[] depositledgerreportblob = null;
		Connection conn = null;
		
		try {
			resource = new ClassPathResource(FILEPATH_DEPOSIT_LEDGER_REPORT).getFile();
			log.info("file got for deposit ledger list report = "+resource.getName());
			
		}
		catch(IOException e)
		{ 
			throw new NotFoundException("File Not Found...");
		}
		
//		Optional<Deposit> deposit=depositRepository.findByAccountNo(accountNo);
		Optional<Deposit> deposit=depositRepository.findById(id);
		if(deposit.isPresent()) {
			
//		List<DepositLedger> depositLedgerList = depositLedgerRepository.findByAccountNo(accountNo);
			List<DepositLedger> depositLedgerList = depositLedgerRepository.findDLListById(id);

		List<DepositLedgerReportWrapper> depositLedgerListWrapper=this.depositLedgerReportMapper.mapAllRegisterDataList(depositLedgerList);
		
		ArrayList<Deposit> depositList=new ArrayList<>();
		depositList.add(deposit.get());

		try {
			conn = jdbcTemplate.getDataSource().getConnection();
			JasperReport report = JasperCompileManager.compileReport(new FileInputStream(resource));
			Map<String,Object> params = new HashMap<>();
			params.put("SocietyName","Vividh Karyakari Society");

			//params.put("accountNo", deposit.get().getAccountNo());
			params.put("depositId", deposit.get().getId());
			params.put("memberName", deposit.get().member.getFirstName()+" "+deposit.get().member.getMiddleName()+" "+deposit.get().member.getLastName());
			deposit.get().member.getNominees().forEach(action -> {

				params.put("nomineeFirstName",
						action.getFirstName() + " " + action.getMiddleName() + " " + action.getLastName());
				params.put("nomineeRelation", action.getNomineeRelation());
				params.put("nomineeAddress", action.getParentPermanentAddr());
				params.put("dob", action.getDob());

				params.put("guardian", action.getGuardianName() + "," + action.getParentPermanentAddr());
			});
			deposit.get().member.getHouse().forEach(action -> {
				if (action.getAddressType().contentEquals("PERMANENT")) {
				params.put("address", action.getAddressLine1() + ", " + action.getAddressLine2() + ", "
						+ action.getCity() + ", " + action.getState() + ", PIN - " + action.getPincode());
				}
			});
			deposit.get().member.getLoanDetails().forEach(action->{
				if(action.getLoanStatus().equals("A")) {
				params.put("loanStatus", "OnGoing");
				
				}
				
			});
		if(deposit.get().depositAccount.getFdDurationDays()==null) {
			params.put("Duration", deposit.get().depositAccount.getRdDurationMonths());
			params.put("time", "Months");
		}else {
			params.put("Duration", deposit.get().depositAccount.getFdDurationDays());
			params.put("time", "Days");
		}
		
		if(deposit.get().getRecurringAmount()!=0) {
			params.put("amount", deposit.get().getRecurringAmount());
		}else {
			params.put("amount", deposit.get().getDepositAmount());
		}
			
			JasperPrint print = JasperFillManager.fillReport(report, params,conn);

			depositledgerreportblob = JasperExportManager.exportReportToPdf(print);

		}
		catch(Exception e){
			log.error(e.getLocalizedMessage(), e.fillInStackTrace());
			e.printStackTrace();
		}
		return depositledgerreportblob;
		}
		else {
			 throw new NotFoundException("Account number not found: " +accountNo );
		}
	}
	}



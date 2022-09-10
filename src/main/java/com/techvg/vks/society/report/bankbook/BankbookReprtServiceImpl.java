package com.techvg.vks.society.report.bankbook;


import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;



import com.techvg.vks.society.domain.SocietyBankTransaction;
import com.techvg.vks.society.mapper.SocietyBankTransactionMapper;
import com.techvg.vks.society.model.BankBookPrintDto;

import com.techvg.vks.society.repository.SocietyBankRepository;
import com.techvg.vks.society.repository.SocietyBankTransactionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j

public class BankbookReprtServiceImpl implements BankbookReportService {

	private final BankbookReprtMapper bankbookReprtMapper;
	private final SocietyBankTransactionRepository societyBankTransactionRepository;
	private final SocietyBankTransactionMapper societyBankTransactionMapper;
	private final SocietyBankRepository societyBankRepository;

	
	//private static final String FILEPATH_REPORT = "/BankBook.jrxml";

//	@Override
//	public ResponseEntity<?> getBankbookListReport(Long accountNo) {
//		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_PDF)
//				.body(getBankListReport(accountNo));
//	}
//
//	@Override
//	public byte[] getBankListReport(Long accountNo) {
//		File resource = null;
//		byte[] reportblob = null;
//
//		try {
//			resource = new ClassPathResource(FILEPATH_REPORT).getFile();
//
//		} catch (IOException e) {
//			throw new NotFoundException("File Not Found...");
//		}
//
//		List<SocietyBankTransaction> societyBankTransaction = societyBankTransactionRepository
//				.findByAccountNo(accountNo);
//
//		List<BankbookReportWrapper> bankListWrapper = this.bankbookReprtMapper.mapAllDataList(societyBankTransaction);
//
//		double balSum = 0.0;
//		double debSum = 0.0;
//		double credSum = 0.0;
//
//		for (int i = 0; i < societyBankTransaction.size(); i++) {
//			if (societyBankTransaction.get(i).getCredit() != null) {
//				credSum = credSum + societyBankTransaction.get(i).getCredit();
//			}
//			if (societyBankTransaction.get(i).getDebit() != null) {
//				debSum = debSum + societyBankTransaction.get(i).getDebit();
//			}
//			balSum = credSum - debSum;
//		}
//
//		JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(bankListWrapper);
//
//		try {
//			JasperReport report = JasperCompileManager.compileReport(new FileInputStream(resource));
//			Map<String, Object> params = new HashMap<>();
//			params.put("SocietyName", "Vividh Karyakari Society");
//			params.put("totalCredit", credSum);
//			params.put("totalDebit", debSum);
//			params.put("totalBalance", balSum);
//
//			JasperPrint print = JasperFillManager.fillReport(report, params, beanDataSource);
//
//			reportblob = JasperExportManager.exportReportToPdf(print);
//
//		} catch (Exception e) {
//			log.error(e.getLocalizedMessage());
//		}
//		return reportblob;
//	}

////for report
	@Override
	public BankBookPrintDto getBankbookListReport(Long accountNo,String transDate) {
		double sumCredit = 0.0;
		double sumDebit = 0.0;
		
		String bankName=null;
		String accountType=null;
		
		
		Date dt = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(dt); 
		c.add(Calendar.DATE, 1);
		dt = c.getTime();
		
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
		LocalDate transDate1 = LocalDate.parse(transDate, formatter);

		Instant instant = transDate1.atStartOfDay(ZoneId.systemDefault()).toInstant();
		Date transDate2 = Date.from(instant);
		
		BankBookPrintDto bankBookPrintDto=new BankBookPrintDto();
		

		
		List<SocietyBankTransaction>societyBankTransList=societyBankTransactionRepository
				.findByAccountNo(accountNo,transDate2,dt);
		
		SocietyBankTransaction societyBankTransaction=societyBankTransactionRepository.findLastRecord(accountNo,transDate2);
		
		for (SocietyBankTransaction societyBankTransaction1 : societyBankTransList) {
		
			 bankName=societyBankTransaction1.getSocietyBank().getBankName();
			 accountType=societyBankTransaction1.getSocietyBank().getAccountType();
			if (societyBankTransaction1.getDebit() == 0) {
				sumCredit = sumCredit + societyBankTransaction1.getCredit() ;
				
		}
			else{
				sumDebit = sumDebit + societyBankTransaction1.getDebit();
			}
		}
		
		
		bankBookPrintDto.setBankName(bankName);
		bankBookPrintDto.setAccountType(accountType);
	
		bankBookPrintDto.setTotalCre(sumCredit);
		bankBookPrintDto.setTotalDeb(sumDebit);
		bankBookPrintDto.setOpeningBal(societyBankTransaction.getBalance());
		 bankBookPrintDto.setList(societyBankTransactionMapper.domainToDtoList(societyBankTransList)); 
		 
		 return bankBookPrintDto;
	}


	
	
	
}

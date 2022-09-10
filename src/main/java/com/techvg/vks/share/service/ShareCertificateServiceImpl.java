package com.techvg.vks.share.service;

import com.ibm.icu.util.Calendar;
import com.techvg.vks.common.NumberToWordConversionService;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.share.domain.SharesAllocation;
import com.techvg.vks.share.repository.SharesAllocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShareCertificateServiceImpl implements ShareCertificateService {

	private final NumberToWordConversionService numberToWordConversionService;
	private final Map<String,Object> reportParams =  new HashMap<>();
	private static final String FILEPATH_SHARE_CERTIFICATE= "/share_certificate.jrxml";	
private final ShareDividendService shareDividendService;
private final SharesAllocationRepository sharesAllocationRepository;	
private final ShareHistoryService shareHistoryService;

@Override
	public byte[] generateMemberCertificate(SharesAllocation shareAllocation,Double shareCapital,Integer totalSocietyShares,Double singleShareValue) {
		
		prepareReportParameters(shareAllocation,shareCapital,totalSocietyShares,singleShareValue);
		
		File templateResource = null;
		try {
			templateResource = new ClassPathResource(FILEPATH_SHARE_CERTIFICATE).getFile();
			log.info("file got for namuna j-1 = " + templateResource.getName());
		} catch (IOException e) {
			throw new NotFoundException("Report Template for Share Certificate Not Found...");
		}
		JasperReport report = null;
		try {
			report = JasperCompileManager.compileReport(new FileInputStream(templateResource));
			JasperPrint print = JasperFillManager.fillReport(report, reportParams);
			return JasperExportManager.exportReportToPdf(print);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new NotFoundException("Error While Preparing Share Certificate Document ! : " +templateResource.getName());
		}
		
		
	
	}

private void prepareReportParameters(SharesAllocation shareAllocation,Double shareCapital,Integer totalSocietyShares,Double singleShareValue)
{ 
	reportParams.clear();
	reportParams.put("certificate_title", "PUNE DISTRICT CENTRAL CO-OPERATIVE BANK LTD. PUNE");
	reportParams.put("title_address", "Head Office: 4B,B.J. Road,Pune - 411 001");
	reportParams.put("paragraph1", "AUTHORIZED CAPITAL OF Rs.  "+shareCapital+"  DIVIDED INTO  "+totalSocietyShares+  
			"  SHARES  OF Rs. "+singleShareValue+" /- EACH");
	reportParams.put("paragraph2",
			"This is to certify that the institution named in "
			+ "this Certificate is the registered Holder of the within-mentioned share(s) "
			+ "bearing the distinctive number(s) herein specified in the "
	        +reportParams.get("certificate_title").toString()
	        +",subject to the bye-laws of the Bank and that the amount of Rs. "
	        + singleShareValue 
	        + " only has been paid on each share.");
	
	reportParams.put("memberId", shareAllocation.getMember().getId());
	reportParams.put("certNo", ""+shareAllocation.getShareCertificateNo());
	reportParams.put("holdername", shareAllocation.getMember().getLastName()+" "+shareAllocation.getMember().getFirstName()+" "+shareAllocation.getMember().getMiddleName());
	reportParams.put("sharesHeld", ""+shareAllocation.getNoOfShares());
	reportParams.put("shareRs", ""+shareAllocation.getShares().getShareAmount());
	reportParams.put("amountInWords", numberToWordConversionService.convert(Integer.parseInt(""+shareAllocation.getShares().getShareAmount().intValue())).toUpperCase()+" ONLY");
	reportParams.put("shareNoFrom", ""+shareAllocation.getSharesIdFrom());
	reportParams.put("ShareNoTo", ""+shareAllocation.getSharesIdTo());
	
	
	try {
		String pattern = "dd-MM-yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		 
	reportParams.put("certDate", simpleDateFormat.format(new java.util.Date()));
	
	}catch(Exception e)
	{ 
		log.error("Invalid Date format to parse");
	}
     

	reportParams.put("dateInWords", getDateInWords(new java.util.Date()));
	
	
}

private String getDateInWords(java.util.Date dateForWording)
{ 
	Calendar cal = Calendar.getInstance();
	cal.setTime(dateForWording);
	String ordinal = getDayNumberSuffix(cal.get(Calendar.DATE));
	return ""+cal.get(Calendar.DATE)+" "+ordinal+" Day of "+new SimpleDateFormat("MMMM").format(dateForWording)+", "+cal.get(Calendar.YEAR);
}

private String getDayNumberSuffix(int day) {
    if (day >= 11 && day <= 13) {
        return "th";
    }
    switch (day % 10) {
    case 1:
        return "st";
    case 2:
        return "nd";
    case 3:
        return "rd";
    default:
        return "th";
    }
}

@Override
public byte[] getMemberCertificateById(Long shareAllocationId) {
	
	
	SharesAllocation sharesAllocation = sharesAllocationRepository.findById(shareAllocationId).orElseThrow(NotFoundException::new);	
	shareHistoryService.saveShareHistory(sharesAllocation);
	return generateMemberCertificate(sharesAllocation, shareDividendService.getShareCapital(2020), shareDividendService.getTotalNoOfShares(), shareDividendService.getPerSharePrice());
}
	
	
	
}

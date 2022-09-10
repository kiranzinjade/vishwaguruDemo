package com.techvg.vks.share.reports.ShareCapitalLedger;

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

import com.techvg.vks.common.SocietyConfig;
import com.techvg.vks.config.ShareConstants;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.share.domain.SharesAllocation;
import com.techvg.vks.share.repository.SharesAllocationRepository;
import com.techvg.vks.share.repository.SharesRepository;
import com.techvg.vks.share.service.ShareDividendService;
import com.techvg.vks.society.domain.SocietyConfigHistory;
import com.techvg.vks.society.repository.SocietyConfigHistoryRepository;
import com.techvg.vks.society.repository.SocietyConfigurationRepository;

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
public class ShareCapitalLedgerServiceImpl implements ShareCapitalLedgerService {

	private final ShareCapitalLedgerMapper shareCapitalLedgerMapper;
	private final SharesAllocationRepository sharesAllocationRepository;
	private final SharesRepository sharesRepository;
	private final SocietyConfigurationRepository societyConfigurationRepository;
	private final ShareDividendService shareDividendService;
	private final SharesAllocationRepository shareallocrepo;
	private final SocietyConfigurationRepository societyRepo;
	private final SocietyConfigHistoryRepository societyConfigHistoryRepository;

	private static final String FILEPATH_SHARECAPITALLEDGER_REPORT = "/ShareCapital.jrxml";
	// private static final String FILEPATH_SHARECAPITALLEDGER_REPORT =
	// "/ShareCapitalLedgerReport.jrxml";

	int totalShares;

	@Override
	public byte[] generateShareCapitalLedgerReport(Long memberId) {

		File resource = null;
		byte[] shareCapitalLedgerreportblob = null;

		try {
			resource = new ClassPathResource(FILEPATH_SHARECAPITALLEDGER_REPORT).getFile();
			log.info("file got for defaulter list report = " + resource.getName());

		} catch (IOException e) {
			throw new NotFoundException("File Not Found...");
		}

		List<SharesAllocation> sharesAllocationList = sharesAllocationRepository.findSharesId(memberId);
		System.out.println(" shares allocation list " + sharesAllocationList);

		List<ShareCapitalLedgerWrapper> shareCapitalLedgerWrapperList = this.shareCapitalLedgerMapper
				.mapAllShareCapitalLedgerList(sharesAllocationList);

		double singleSharePrice = societyConfigurationRepository.findByConfigName(ShareConstants.SINGLE_SHARE_PRICE)
				.orElseThrow(NotFoundException::new).getValue();
		System.out.println(" share price " + singleSharePrice);

		Calendar prevYear = Calendar.getInstance();
		int month = prevYear.get(Calendar.MONTH);
		int year2 = prevYear.get(Calendar.YEAR); // current year
		int year = prevYear.get(Calendar.YEAR) - 1; // previous year
		System.out.println(month);
		System.out.println(year);
		System.out.println(year2);
		Calendar prevYear3 = Calendar.getInstance();
		prevYear3.set(year, 3, 1);
		Date date = prevYear3.getTime(); // previous year date ( 1 April ----)
		System.out.println("date 2 - " + date);

		int limit = 0;
		double value = 0.0;
		int noOfShares = 0;
		double products = 0.0;
		for (ShareCapitalLedgerWrapper wrapper : shareCapitalLedgerWrapperList) {

			ArrayList<String> distinctShares = new ArrayList<>();
			ArrayList<Integer> shareCertificates = new ArrayList<>();
			limit = limit + 1;
			for (int i = 0; i < limit; i++) {
				System.out.println(" share allcation list" + sharesAllocationList.get(i).getShareCertificateNo());
				shareCertificates.add(sharesAllocationList.get(i).getShareCertificateNo());
				distinctShares.add(sharesAllocationList.get(i).getSharesIdFrom() + "-"
						+ sharesAllocationList.get(i).getSharesIdTo());
				System.out.println(" distinctive shares " + sharesAllocationList.get(i).getSharesIdFrom() + "-"
						+ sharesAllocationList.get(i).getSharesIdTo());
			}

			if (wrapper.getParticulars().equals(ShareConstants.ALLOC_PARTICULARS_NEW)) {
				wrapper.setValue((wrapper.getNoOfShares()) * (singleSharePrice));
				wrapper.setNoOfShares(wrapper.getNoOfShares());
				wrapper.setShareCertificateNo(wrapper.getShareCertificateNo());
				wrapper.setSharesIdFrom(wrapper.getSharesIdFrom());
				wrapper.setSharesIdTo(wrapper.getSharesIdTo());
				wrapper.setTotalShareCertificates(shareCertificates);
				wrapper.setDistinctiveShares(distinctShares);
				noOfShares = noOfShares + wrapper.getNoOfShares();
				wrapper.setNoOfShares2(noOfShares);
				value = value + wrapper.getValue();
				wrapper.setValue2(value);

			} else {
				wrapper.setValue1((wrapper.getNoOfShares()) * (singleSharePrice));
				wrapper.setNoOfShares1(wrapper.getNoOfShares());
				wrapper.setShareCertificateNo1(wrapper.getShareCertificateNo());
				wrapper.setSharesIdFrom1(wrapper.getSharesIdFrom());
				wrapper.setSharesIdTo1(wrapper.getSharesIdTo());
				wrapper.setTotalShareCertificates(shareCertificates);
				wrapper.setDistinctiveShares(distinctShares);
				noOfShares = noOfShares + wrapper.getNoOfShares();
				wrapper.setNoOfShares2(noOfShares);
				value = value + wrapper.getValue1();
				wrapper.setValue2(value);
				wrapper.setNoOfShares(null);
				wrapper.setShareCertificateNo(null);
				wrapper.setSharesIdFrom(null);
				wrapper.setSharesIdTo(null);

			}

			Date allocationDate = wrapper.getAllocationDate();
			Calendar cal = Calendar.getInstance();
			cal.setTime(allocationDate);
			int year4 = cal.get(Calendar.YEAR) + 1; // allocation date -> next year(yyyy)
			int year3 = cal.get(Calendar.YEAR);      // allocation date -> year(yyyy)
			System.out.println("year3 - " + year3);
			System.out.println("year4 - " + year4);
			Calendar cal1 = Calendar.getInstance();
			cal1.set(year3, 3, 1);
			Date date2 = cal1.getTime(); // allocation date compare with same year 1 april (yyyy april 1)
			System.out.println("date 2 - " + date2);

			if (month > 2 && (wrapper.getAllocationDate().compareTo(date) < 0)) {

				if ((wrapper.getAllocationDate().compareTo(date2) < 0)) {
					if (year4 == year2) {
						double dividend = SocietyConfig.getValue(ShareConstants.DIVIDEND_AMT_PERSHARE);
						wrapper.setNoOfDays(365);
						products = wrapper.getNoOfShares2() * dividend;
						wrapper.setProduct(products);
					} else {
						System.out.println(ShareConstants.DIVIDEND_AMT_PERSHARE);
						System.out.println(year);

						double societyConfigHistory = societyConfigHistoryRepository
								.findDividendPerShareByYear(ShareConstants.DIVIDEND_AMT_PERSHARE, year4);
						System.out.println(societyConfigHistory);
						wrapper.setNoOfDays(365);
						products = wrapper.getNoOfShares2() * societyConfigHistory;
						wrapper.setProduct(products);
					}

				} else {
					
					double dividend = SocietyConfig.getValue(ShareConstants.DIVIDEND_AMT_PERSHARE);
					wrapper.setNoOfDays(365);
					products = wrapper.getNoOfShares2() * dividend;
					wrapper.setProduct(products);
				}

			} else {
				wrapper.setNoOfDays(0);
				wrapper.setProduct(0.0);
			}

		}

		JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(shareCapitalLedgerWrapperList);

		try {
			JasperReport report = JasperCompileManager.compileReport(new FileInputStream(resource));
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("SocietyName", "Vividh Karyakari Society");

			JasperPrint print = JasperFillManager.fillReport(report, params, beanDataSource);

			shareCapitalLedgerreportblob = JasperExportManager.exportReportToPdf(print);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return shareCapitalLedgerreportblob;
	}

}

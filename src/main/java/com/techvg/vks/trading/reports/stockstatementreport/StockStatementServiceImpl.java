package com.techvg.vks.trading.reports.stockstatementreport;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.techvg.vks.config.TradingConstants;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.loan.domain.LoanDetails;
import com.techvg.vks.loan.reports.defaulterlist.DefaulterListWrapper;
import com.techvg.vks.trading.domain.StockRegister;
import com.techvg.vks.trading.repository.StockRegisterRepository;

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
public class StockStatementServiceImpl implements StockStatementService {
	private final StockStatementMapper stockStatementMapper;
	private final StockRegisterRepository stockRegisterReository;
	private static final String FILEPATH_STOCKSTATEMENT_REPORT1 = "/StockStatementReport1.jrxml";
	private static final String FILEPATH_STOCKSTATEMENT_REPORT = "/StockStatementReport.jrxml";
	private static final String FILEPATH_STOCKSTATEMENT_REPORT2 = "/StockStatementReport2.jrxml";

	@Override
	public ResponseEntity<?> getStockStatementReport(String fromDate, Long productId) {
		log.info("Deposit Id received (service) successfully");
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_PDF)
				.body(creatingStockStatementReport(fromDate, productId));

	}

	@Override
	public byte[] creatingStockStatementReport(String fromDate, Long productId) {
		File resource = null;
		byte[] stockstatementblob = null;

		try {
			resource = new ClassPathResource(FILEPATH_STOCKSTATEMENT_REPORT).getFile();
			log.info("file got for Kmp Report = " + resource.getName());

		} catch (IOException e) {
			throw new NotFoundException("File Not Found...");
		}

		// convert string into Local date format
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
		LocalDate fromdate1 = LocalDate.parse(fromDate, formatter);
		System.out.println("Local Date - from date" + fromdate1);

		Instant instant = fromdate1.atStartOfDay(ZoneId.systemDefault()).toInstant();
		Date fromdate4 = Date.from(instant);

		List<StockRegister> stockstatementlist1 = new ArrayList<>();

		List<StockRegister> stockstatementlist = stockRegisterReository.findByDateFromto(fromdate4, productId);
		if (stockstatementlist.size() == 1 || stockstatementlist.size() == 2) {
			if (stockstatementlist.size() == 1) {

				List<StockStatementWrapper> stockStatementWrapperList = this.stockStatementMapper
						.mapAllStockStatementDataList(stockstatementlist);

				JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(stockStatementWrapperList);

				try {

					resource = new ClassPathResource(FILEPATH_STOCKSTATEMENT_REPORT1).getFile();
					JasperReport report = JasperCompileManager.compileReport(new FileInputStream(resource));
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("SocietyName", "Vividh Karyakari Society");

					params.put("openingstock", stockstatementlist.get(0).getCurrentStock());
					params.put("openingstockvalue", stockstatementlist.get(0).getCurrentStockValue());

					params.put("date", stockstatementlist.get(0).getTransactionDate());
					if (stockstatementlist.get(0).getTransType().equalsIgnoreCase(TradingConstants.PURCHASE_TRANS)) {
						params.put("purchaseQuantity", stockstatementlist.get(0).getQuantity());
						params.put("purchaseValue", stockstatementlist.get(0).getProduct().getSellingPrice());
						params.put("salesQuantity1", 0.0);

					}
					double impairmentQuantity = 0.0;
					if (stockstatementlist.get(0).getTransType().equalsIgnoreCase(TradingConstants.IMPAIRMENT_TRANS)) {
						params.put("impairmentQuantity", stockstatementlist.get(0).getQuantity());
						impairmentQuantity = stockstatementlist.get(0).getQuantity();
					}

					if (stockstatementlist.get(stockstatementlist.size() - 1).getTransType()
							.equalsIgnoreCase(TradingConstants.SALES_TRANS)) {
						params.put("salesQuantity", stockstatementlist.get(0).getQuantity());
					
					}
					params.put("total", stockstatementlist.get(0).getCurrentStock());

					System.out.println("beanDataSource==" + params);
					JasperPrint print = JasperFillManager.fillReport(report, params, beanDataSource);
					stockstatementblob = JasperExportManager.exportReportToPdf(print);

				} catch (Exception e) {
					e.printStackTrace();
					log.error(e.getLocalizedMessage());
				}
			} else {
			
				if (stockstatementlist.size() == 1 || stockstatementlist.size() == 2) {
					if (stockstatementlist.size() == 2) {

						List<StockStatementWrapper> stockStatementWrapperList = this.stockStatementMapper
								.mapAllStockStatementDataList(stockstatementlist);

						JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(
								stockStatementWrapperList);

						try {

							resource = new ClassPathResource(FILEPATH_STOCKSTATEMENT_REPORT2).getFile();
							JasperReport report = JasperCompileManager.compileReport(new FileInputStream(resource));
							Map<String, Object> params = new HashMap<String, Object>();
  
							params.put("SocietyName", "Vividh Karyakari Society");
						
							params.put("openingstock", stockstatementlist.get(0).getCurrentStock());
							params.put("openingstockvalue", stockstatementlist.get(0).getCurrentStockValue());

							params.put("date", stockstatementlist.get(0).getTransactionDate());
							
							if (stockstatementlist.get(0).getTransType().equalsIgnoreCase(TradingConstants.PURCHASE_TRANS)) {
								params.put("purchaseQuantity", stockstatementlist.get(0).getQuantity());
								params.put("purchaseValue", stockstatementlist.get(0).getProduct().getSellingPrice());

								params.put("salesQuantity", 0.0);
								params.put("salesQuantity1", 0.0);

							}
							if (stockstatementlist.get(stockstatementlist.size() - 1) .getTransType().equalsIgnoreCase(TradingConstants.PURCHASE_TRANS)) {
						

								params.put("purchaseQuantity1",
										stockstatementlist.get(stockstatementlist.size() - 1).getQuantity());
								params.put("purchaseValue1",
										stockstatementlist.get(stockstatementlist.size() - 1).product
												.getSellingPrice());

								params.put("salesQuantity", 0.0);
								params.put("salesQuantity1", 0.0);

							}
							
							double impairmentQuantity = 0.0;
							if (stockstatementlist.get(0).getTransType()
									.equalsIgnoreCase(TradingConstants.IMPAIRMENT_TRANS)) {
								params.put("impairmentQuantity", stockstatementlist.get(0).getQuantity());
								impairmentQuantity = stockstatementlist.get(0).getQuantity();
							}

							if (stockstatementlist.get(0).getTransType()
									.equalsIgnoreCase(TradingConstants.SALES_TRANS)) {
								params.put("salesQuantity", stockstatementlist.get(0).getQuantity());
							
							}
							if (stockstatementlist.get(stockstatementlist.size() - 1).getTransType()
									.equalsIgnoreCase(TradingConstants.SALES_TRANS)) {
								params.put("salesQuantity1", stockstatementlist.get(stockstatementlist.size() - 1).getQuantity());
							}
							
							
							params.put("total", stockstatementlist.get(0).getCurrentStock());


							params.put("closingstock1",
									stockstatementlist.get(stockstatementlist.size() - 1).getCurrentStock());
							params.put("closingstockvalue1",
									stockstatementlist.get(stockstatementlist.size() - 1).getCurrentStockValue());

							params.put("date1",
									stockstatementlist.get(stockstatementlist.size() - 1).getTransactionDate());
						System.out.println("beanDataSource==" + params);
							JasperPrint print = JasperFillManager.fillReport(report, params, beanDataSource);
							stockstatementblob = JasperExportManager.exportReportToPdf(print);

						} catch (Exception e) {
							e.printStackTrace();
							log.error(e.getLocalizedMessage());
						}
					}

				}
			}
		} else {
			for (int i = 1; i < stockstatementlist.size() - 1; i++) {
				stockstatementlist1.add(stockstatementlist.get(i));

			}

			List<StockStatementWrapper> stockStatementWrapperList = this.stockStatementMapper
					.mapAllStockStatementDataList(stockstatementlist1);

			JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(stockStatementWrapperList);

			try {

				JasperReport report = JasperCompileManager.compileReport(new FileInputStream(resource));
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("SocietyName", "Vividh Karyakari Society");
			
				params.put("openingstock", stockstatementlist.get(0).getCurrentStock());
				params.put("openingstockvalue", stockstatementlist.get(0).getCurrentStockValue());

				params.put("date", stockstatementlist.get(0).getTransactionDate());
				if (stockstatementlist.get(0).getTransType().equalsIgnoreCase(TradingConstants.PURCHASE_TRANS)) {
					params.put("purchaseQuantity", stockstatementlist.get(0).getQuantity());
					params.put("purchaseValue", stockstatementlist.get(0).getProduct().getSellingPrice());

					params.put("purchaseQuantity1",
							stockstatementlist.get(stockstatementlist.size() - 1).getQuantity());
					params.put("purchaseValue1",
							stockstatementlist.get(stockstatementlist.size() - 1).product.getSellingPrice());

					params.put("salesQuantity", 0.0);
					params.put("salesQuantity1", 0.0);

				}
				double impairmentQuantity = 0.0;
				if (stockstatementlist.get(0).getTransType().equalsIgnoreCase(TradingConstants.IMPAIRMENT_TRANS)) {
					params.put("impairmentQuantity", stockstatementlist.get(0).getQuantity());
					impairmentQuantity = stockstatementlist.get(0).getQuantity();
				}

				if (stockstatementlist.get(stockstatementlist.size() - 1).getTransType()
						.equalsIgnoreCase(TradingConstants.SALES_TRANS)) {
					params.put("salesQuantity", stockstatementlist.get(0).getQuantity());
					params.put("salesQuantity1", stockstatementlist.get(stockstatementlist.size() - 1).getQuantity());
				}
				params.put("total", stockstatementlist.get(0).getCurrentStock());

        		params.put("closingstock1", stockstatementlist.get(stockstatementlist.size() - 1).getCurrentStock());
				params.put("closingstockvalue1",
						stockstatementlist.get(stockstatementlist.size() - 1).getCurrentStockValue());

				params.put("date1", stockstatementlist.get(stockstatementlist.size() - 1).getTransactionDate());
				
				System.out.println("beanDataSource==" + params);
				JasperPrint print = JasperFillManager.fillReport(report, params, beanDataSource);
				stockstatementblob = JasperExportManager.exportReportToPdf(print);

			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getLocalizedMessage());
			}
		}

		return stockstatementblob;

	}

}

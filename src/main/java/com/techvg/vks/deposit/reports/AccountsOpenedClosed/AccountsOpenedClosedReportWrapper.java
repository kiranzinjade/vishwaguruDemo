package com.techvg.vks.deposit.reports.AccountsOpenedClosed;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountsOpenedClosedReportWrapper {

	private Long accountNumber;
	private String nameOfAccountHolder;
	private Date dateOfOpening;
	private Date dateOfClosing;
	private Integer totalNumberOfAccountSubsisting;

}

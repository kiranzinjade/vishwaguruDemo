package com.techvg.vks.deposit.reports.SavingAccountList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavingAccountListWrapper {
	

	private String name;
	private String address;
	private Long accountNo;
	private Date accountOpeningDate;
	private String status;
	
}

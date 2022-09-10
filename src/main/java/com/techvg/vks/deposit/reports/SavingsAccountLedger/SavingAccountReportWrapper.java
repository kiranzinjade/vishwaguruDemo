package com.techvg.vks.deposit.reports.SavingsAccountLedger;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavingAccountReportWrapper{
	
	private Long accountNo;
	private Long membershipNo;
	private String name;
	private String occupation;
	private String address;
	private String phoneNumber;
	private String panCard;
	private String standingInstructions;
	private String registerSerialNo;
	private String nomineeName;
	private String nomineeRelation;
	private Date dob;
	private String guardianName;
	private String guardianAddress;
	private String operationalInstructions;
	private Date lastDayofOperation;
    private Date date;
    private String particulars;
    private Double withdrawnAmount;
    private Double depositedAmount;
    private Double balance;
    private Double monthlyMinimumBalance;
    private Double interest;
    
}

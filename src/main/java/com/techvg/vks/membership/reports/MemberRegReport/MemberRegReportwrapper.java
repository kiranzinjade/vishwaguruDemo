package com.techvg.vks.membership.reports.MemberRegReport;

import java.util.Date;

import com.techvg.vks.deposit.reports.SavingsAccountLedger.SavingAccountReportWrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberRegReportwrapper {

	private Long accountNo;
	private Long membershipNo;
	private String name;
	private String occupation;
	private String address;
	private String phoneNumber;
	private String panCard;
	private String boardResolutionNoAndDate;
	private String rationCard;
	private String nomineeName;
	private String nomineeRelation;
	private Date dob;
	private String guardianName;
	private String guardianAddress;

    private Date dateOfNomination;
    private String smallFarmer;  //membertype
    private Double landOwned;       //landAreaHector
    private String gender;
    private String category;
    private Double monthlyMinimumBalance;
    private Double interest;
}

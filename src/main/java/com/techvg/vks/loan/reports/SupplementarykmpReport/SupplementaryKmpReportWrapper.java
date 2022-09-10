package com.techvg.vks.loan.reports.SupplementarykmpReport;

import java.util.Date;

import com.techvg.vks.membership.reports.VoterListReport.VoterReportWrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplementaryKmpReportWrapper {
	
	private String society_Name;
	private Long memeber_id;
	private String member_Name;
	private String middle_Name;
	private String last_Name;
	private String contact_No;
	private String land_Type;
	private String land_Area;
	private String crop_Name;
	private String crop_Type;
	private Double crop_Area;
	Date syear;
	Date Eyear;   
	private double share_Amount;
	private double loan_Amount;
	private double approve_Loan_Amount;
	private String  loan_Type;
	private String  loan_Account_No;

}

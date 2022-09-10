package com.techvg.vks.loan.reports.AcknowledgmentOfDebt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcknowledgmentOfDebtWrapper {
	
	private Long member_id;
	private String nameBorrower;
	private Long loan_details_id;
	private String typeOfLoan;
	private Double outStanding;

}

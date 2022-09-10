package com.techvg.vks.deposit.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepositInterestCalculation {
	
	Long depositId;
	Double Interest=0.0;
	Date depositDate;
	Double depositAmount;
	Date maturityDate;
	Double maturityAmount;
	Double additionalInterest=0.0;


}

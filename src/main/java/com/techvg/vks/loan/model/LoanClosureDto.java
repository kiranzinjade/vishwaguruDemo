package com.techvg.vks.loan.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class LoanClosureDto {
	
	double outStandingPrinciple;
	
	double outStandingInterest;
	
	boolean settlement;
	
	double totalClosureAmount;
	
	double surCharge;
	
	double penalty;

	Long loanId;

	@Builder
	public LoanClosureDto(double outStandingPrinciple, double outStandingInterest, boolean settlement,
			double totalClosureAmount, double surCharge, double penalty, Long loanId) {
		super();
		this.outStandingPrinciple = outStandingPrinciple;
		this.outStandingInterest = outStandingInterest;
		this.settlement = settlement;
		this.totalClosureAmount = totalClosureAmount;
		this.surCharge = surCharge;
		this.penalty = penalty;
		this.loanId = loanId;
	}

	

	}
	
	



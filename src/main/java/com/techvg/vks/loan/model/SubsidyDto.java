package com.techvg.vks.loan.model;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class SubsidyDto {
	
    private Double centralGovInterest;
	private Double stateGovInterest;
	private Double distBankInterest;
	
	public SubsidyDto(Double centralGovInterest, Double stateGovInterest, Double distBankInterest) {
		super();
		this.centralGovInterest = centralGovInterest;
		this.stateGovInterest = stateGovInterest;
		this.distBankInterest = distBankInterest;
	} 
	


}

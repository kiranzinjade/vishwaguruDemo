package com.techvg.vks.society.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.Null;

import com.techvg.vks.base.BaseEntityDto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProfitsAppropriationDto extends BaseEntityDto implements Serializable {

	private static final long serialVersionUID = 8506582894893716606L;
	
	private Double agriCredStabFund;
	private Double reserveFund;
	private Double divEqFund;
	private Double otherFunds;
	private Double prevYearLosses;
	private Double prevYearProfits;
	private Double dividendShares;
	private int year;
	private Double unclaimedDividend;
	private Double currentYearProfit;
	
	@Builder
	public ProfitsAppropriationDto(@Null Long id, @Null LocalDateTime created, @Null String createdBy, @Null LocalDateTime lastModified,
			                       @Null String lastModifiedBy, Boolean isDeleted, Double agriCredStabFund, Double reserveFund, Double divEqFund, Double otherFunds,
			                       Double prevYearLosses, Double prevYearProfits, Double dividendShares, int year, Double unclaimedDividend, Double currentYearProfit) {
	super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
	
	this.agriCredStabFund=agriCredStabFund;
	this.reserveFund=reserveFund;
	this.divEqFund=divEqFund;
	this.otherFunds=otherFunds;
	this.prevYearLosses=prevYearLosses;
	this.prevYearProfits=prevYearProfits;
	this.dividendShares=dividendShares;
	this.year=year;
	this.unclaimedDividend=unclaimedDividend;
	this.currentYearProfit=currentYearProfit;
	
	}	
}

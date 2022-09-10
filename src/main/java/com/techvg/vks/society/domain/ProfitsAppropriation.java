package com.techvg.vks.society.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;

import com.techvg.vks.base.BaseEntity;
import lombok.*;
import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="profitsappropriation")

public class ProfitsAppropriation extends BaseEntity<String>  {

@Column(name="agricredstab_fund")
Double agriCredStabFund;

@Column(name="reserve_fund")
Double reserveFund;

@Column(name="diveq_fund")
Double divEqFund;

@Column(name="other_funds")
Double otherFunds;

@Column(name="prevyear_losses")
Double prevYearLosses;

@Column(name="prevyear_profits")
Double prevYearProfits;

@Column(name="dividend_shares")
Double dividendShares;

@Column(name="year")
int year;

@Column(name="unclaimed_dividend")
Double unclaimedDividend;

@Column(name="currentyear_profit")
Double currentYearProfit;



}

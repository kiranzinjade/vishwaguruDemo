package com.techvg.vks.deposit.domain;

import com.techvg.vks.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "post_saving_interest")

public class SavingInterest extends BaseEntity<String> {
	
	@Column(name = "account_no")
	Long accountNo;
	
	@Column(name = "month")
	Integer month;
	
	@Column(name = "year")
	Integer year;
	
	@Column(name = "monthly_balance")
	Double monthlyBalance;
	
	@Column(name = "monthly_interest")
	Double monthlyInterest;
	
	
}

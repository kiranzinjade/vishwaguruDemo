package com.techvg.vks.loan.domain;

import com.techvg.vks.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="short_term_subsidy")
public class ShortTermSubsidy extends BaseEntity<String>{
	
	@Column(name="interest_from_central_gov")    
	Double centralGovInterest;
	
	@Column(name = "central_gov_subsidy_date")
	Date centralGovDate;
	
	@Column(name="interest_from_state_gov")    
	Double stateGovInterest;
	
	@Column(name = "state_gov_subsidy_date")
	Date stateGovDate;
	
	@Column(name="interest_from_dist_bank")    
	Double distBankInterest; 
	
	@Column(name = "dist_bank_subsidy_date")
	Date distBankDate;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "loan_details_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	public LoanDetails loanDetails;
	
	
	
}

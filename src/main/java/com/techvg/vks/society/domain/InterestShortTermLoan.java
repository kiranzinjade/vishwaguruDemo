
package com.techvg.vks.society.domain;

import com.techvg.vks.base.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor

@Table(name="interest_short_term_loan")
public class InterestShortTermLoan  extends BaseEntity<String>{
	
	@Column(name="criteria")
	private String criteria;
	@Column(name="central_gov")    
	Double centralGov;
	@Column(name="state_gov")    
	Double stateGov;
	@Column(name="dist_bank")    
	Double distBank; 
	@Column(name="self")    
	Double self;
	@Column(name="after_one_year")    
	Double afterOneYar; 

}


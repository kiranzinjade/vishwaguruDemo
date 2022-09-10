package com.techvg.vks.society.domain;

import com.techvg.vks.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="expenditure_type")
public class ExpenditureType extends BaseEntity<String>{

	@Column(name = "expenditure_type")
	String expenditureType;    // eg office -> Salary mgmt, Rent, Insurance, Repair, Stationary etc

	@Column(name = "expenditure_category")
	String expenseCategory; // Office, Agri products related, PDS, Non-PDS, Day-Meal

	@Column(name = "expenditure_desc")
	String expenditureDesc;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "expenditureType", cascade = CascadeType.ALL)
	private Set<ExpenditureRegister> expenditureRegisters;
	
}

package com.techvg.vks.society.domain;

import com.techvg.vks.base.BaseEntity;
import com.techvg.vks.society.domain.ExpenditureType;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "expenditure_register")
public class ExpenditureRegister extends BaseEntity<String>{
	
    @Column(name = "name")
    String name;

	@Column(name = "expenditure_date")
	Date expenditureDate;
	
	@Column(name = "expenditure_amount")
	Double expenditureAmt;
	
	@Column(name = "expenditure_note")
	String expenditureNote;
	
	@Column(name = "debit_from")
	String debitFrom;
	
	@Column(name = "credit_to")
	String creditTo;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "expenditure_type_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    public ExpenditureType expenditureType;
 	
}

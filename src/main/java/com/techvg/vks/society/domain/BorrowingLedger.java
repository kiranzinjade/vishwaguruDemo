package com.techvg.vks.society.domain;

import com.techvg.vks.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "borrowing_ledger")

public class BorrowingLedger extends BaseEntity<String>{
	
	@Column(name = "purpose")
	String purpose;
	@Column(name = "loan_no")
	Long loanNo;
	@Column(name = "date")
	Date date;
	@Column(name = "loan_amount")
	Double loanAmt;
	@Column(name = "duration")
	Integer duration;
	@Column(name = "due_date")
	Date dueDate;
	@Column(name = "rate_of_interest")
	Double interest;
	@Column(name = "crop")
	String crop;
	
	    @EqualsAndHashCode.Exclude
	    @ManyToOne(fetch= FetchType.LAZY,cascade=CascadeType.ALL)
		@JoinColumn(name="bank_id",nullable=false)
		public SocietyBank bank;
	 
	    @EqualsAndHashCode.Exclude
		@ToString.Exclude
		@OneToMany(fetch= FetchType.LAZY,mappedBy = "borrowingLedger", cascade = CascadeType.ALL)
		private Set<BorrowingLedgerTransaction> borrowingLedgerTransaction;


		
}

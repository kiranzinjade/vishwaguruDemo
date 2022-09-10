package com.techvg.vks.loan.domain;

import com.techvg.vks.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "loan_disbursement")
public class Disbursement extends BaseEntity<String> {

	@Column(name = "disbursement_date")
	Date disbursementDate;
	
	@Column(name = "disbursement_no")
	Integer disbursementNo;
		
	@Column(name = "disbursement_amount")
	double disbursementAmt;
	
	@Column(name = "payment_mode")
	String paymentMode;
	
	@Column(name = "disbursement_status")
	String disbursementStatus;//full,partial
	
	@Column(name = "loan_type")
	String loanType;
	
	@Column(name = "voucher_no")
	Long voucherNo;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "loan_details_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	public LoanDetails loanDetails;
	
	
	
	
}

package com.techvg.vks.membership.domain;

import com.techvg.vks.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="member_bank")
public class MemberBank extends BaseEntity<String> {
	
	
	@Column(name = "district_bank_name")
	String districtBankName;

	@Column(name = "branch_name")
	String branchName;

	@Column(name = "account_number")
	Long accountNumber;
	
	@Column(name = "ifsc_code")
	String ifsccode;

	@ManyToOne
	@JoinColumn(name = "member_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	public Member member;
	
	
}

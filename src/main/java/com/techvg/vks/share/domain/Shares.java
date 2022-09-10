package com.techvg.vks.share.domain;

import com.techvg.vks.base.BaseEntity;
import com.techvg.vks.membership.domain.Member;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "share_application")
public class Shares extends BaseEntity<String> {

	@Column(name = "voucher_no")
	Long voucherNo;

	@Column(name = "application_date")
	Date applicationDate;
	
	@Column(name = "number_of_shares")
	Integer numberOfShares;
	
	@Column(name = "share_price")
	Double sharePrice;
	
	@Column(name = "application_type")
	String applicationType;//new,surrender,transfer
	
	@Column(name = "share_amount")
	Double shareAmount;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "member_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	public Member member;

	@Column(name = "share_application_status")
	String shareApplicationStatus;//pending,rejected,accepted
	
	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name = "from_member_id", nullable = true)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	public Member fromMember;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy="shares",cascade=CascadeType.ALL)
	private Set<SharesAllocation> sharesAllocation;
	

}



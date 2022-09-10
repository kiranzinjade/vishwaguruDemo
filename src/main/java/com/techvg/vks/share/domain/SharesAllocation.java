package com.techvg.vks.share.domain;

import com.techvg.vks.base.BaseEntity;
import com.techvg.vks.membership.domain.Member;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "share_allocation")
public class SharesAllocation extends BaseEntity<String>  {

	@ManyToOne
	@JoinColumn(name = "member_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	public Member member;
	
	@ManyToOne
	@JoinColumn(name = "shares_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	public Shares shares;
	
	@Column(name = "allocation_date")
	Date allocationDate;
	
	@Column(name = "no_of_shares")
	Integer noOfShares;
	
	@Column(name = "share_id_from")
	Integer sharesIdFrom;
	
	@Column(name = "share_id_to")
	Integer sharesIdTo;
	
	@Column(name = "share_certificate_no")
	Integer shareCertificateNo;

	@Column(name = "shares_allocation_status")
	String sharesAllocationStatus;//allocated ,transfer,surrender
	
	@Column(name = "particulars")
	String particulars;
	
	@Column(name = "no_and_date_board_resolution")
	String noAndDateOfBoardResolution;
}



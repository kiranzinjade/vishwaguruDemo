package com.techvg.vks.membership.domain;

import com.techvg.vks.base.BaseEntity;
import com.techvg.vks.deposit.domain.Deposit;
import com.techvg.vks.loan.domain.LoanDemand;
import com.techvg.vks.loan.domain.LoanDetails;
import com.techvg.vks.share.domain.MemberShareAcc;
import com.techvg.vks.share.domain.Shares;
import com.techvg.vks.share.domain.SharesAllocation;
import com.techvg.vks.society.domain.AgmAttendance;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "member")
public class Member extends BaseEntity<String> {

	@Column(name = "member_unique_id")
	String memberUniqueId;

	@Column(name = "first_name")
	String firstName;

	@Column(name = "middle_name")
	String middleName;

	@Column(name = "last_name")
	String lastName;

	@Column(name = "gender")
	String gender;

	@Column(name = "father_name")
	String fatherName;

	@Column(name = "mother_name")
	String motherName;

	@Column(name = "dob")
	Date dob;

	@Column(name = "email")
	String email;

	@Column(name = "phone_number")
	String phoneNumber;

	@Column(name = "religion")
	String religion;

	@Column(name = "category")
	String category;

	@Column(name = "cast")
	String cast;

	@Column(name = "aadhar_card")
	String aadharCard;

	@Column(name = "pan_card")
	String panCard;

	@Column(name = "ration_card")
	String rationCard;

	@Column(name = "family_member")
	Integer familyMemberCount;

	@Column(name = "occupation")
	String occupation;

	@Column(name = "dependents")
	Integer dependentsCount;

	@Column(name = "application_date")
	Date applicationDate;

	@Column(name = "status")
	String status;
	
	@Column(name = "kmp_status")
	Boolean kmpStatus;

	@Column(name = "board_resolution_no_date")
	String boardResolutionNoAndDate;
	
	@Column(name = "member_type")
	String memberType;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "member_house", joinColumns = {
			@JoinColumn(name = "member_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "house_id", referencedColumnName = "id") })
	private Set<House> house;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(fetch = FetchType.LAZY, mappedBy="member",cascade=CascadeType.ALL)
	private Set<MemberBank> memberBank;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "member",fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<MemberBelonging> memberBelonging;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "member",fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Land> land;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "member",fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Nominee> nominees;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "member",fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Shares> shares = new HashSet<>();

	@Column(name = "inactive_date")
	java.util.Date inactiveDate;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "member",fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<SharesAllocation> sharesAllocation = new HashSet<>();

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy="member",fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	private Set<AgmAttendance> agmAttendance;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(fetch = FetchType.LAZY, mappedBy="member",cascade=CascadeType.ALL)
	private Set<LoanDetails> loanDetails;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy="member",fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	private Set<LoanDemand> loanDemands;


	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "member",fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Deposit> deposit;
    
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "member",fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<NotificationDetails> notificationDetails;

	@OneToOne(mappedBy="member")
	private MemberShareAcc memberShareAcc;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "member",fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<MemCropReg> memCropRegs;

	public void addShares(Shares newShare) {
		this.shares.add(newShare);
	}

	public void addSharesAllocation(SharesAllocation sharesAllocation) {
		this.sharesAllocation.add(sharesAllocation);
	}
}

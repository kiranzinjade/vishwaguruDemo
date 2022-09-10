package com.techvg.vks.membership.domain;

import com.techvg.vks.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "nominee")
public class Nominee extends BaseEntity<String> {

	@Column(name ="first_name")
	String firstName;
	
	@Column(name ="middle_name")
	String middleName;
	
	@Column(name ="last_name")
	String lastName;
	
	@Column(name = "father_name")
	String fatherName;
	
	@Column(name = "mother_name")
	String motherName;
	
	@Column(name = "guardian_name")
	String guardianName;
	
	@Column(name ="gender")
	String gender;
		
	@Column(name ="dob")
	Date dob;
		
	@Column(name ="aadhar_card")
	String aadharCard;
	
	@Column(name ="nominee_declaration_date")
	Date nomineeDeclareDate;
	
	@Column(name ="nominee_relation")
	String nomineeRelation;
	
	@Column(name ="permanent_address")
	String nomineePermanentAddr;

	@Column(name ="parent_permanent_address")
	String parentPermanentAddr;
	
	@EqualsAndHashCode.Exclude
    @ManyToOne(fetch= FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinColumn(name="member_id",nullable=false)
	public Member member;

}

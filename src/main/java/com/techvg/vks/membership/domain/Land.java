
package com.techvg.vks.membership.domain;


import com.techvg.vks.base.BaseEntity;
import com.techvg.vks.loan.domain.LoanDemand;
import lombok.*;

import java.util.Set;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="land_details")
public class Land extends BaseEntity<String>{
	
	@Column(name="land_type")
	String landType;
	@Column(name="land_gatno") 
	String landGatno;
	@Column(name="land_area_hector")
	Integer landAreaHector;
	@Column(name="land_area_r")
	Integer landAreaR;
	@Column(name="jindagi_patrak_no")
	String jindagiPatrakNo;
	@Column(name="jindagi_amount")
	double jindagiAmount;
	@Column(name = "land_address")
	String landAddress;
	@Column(name = "value_of_property")
	Double valueOfProperty;
	@Column(name = "land_assignee")
	Boolean assigneeOfLand;
	@Column(name= "mLLoanNo")
	Long mLLoanNo;
	
	@Lob
	@Column(name = "jindagi_patrak_file")
	private byte[] jindagiPatrakFile;
	
	@Column(name = "jindagi_patrak_name")
	String jindagiPatrakName;
	
	@Lob
	@Column(name = "eight_A")
	private byte[] eightAFile;
	
	@Column(name = "eight_A_name")
	String eightAName;
	
	@Lob
	@Column(name = "saat_bara")
	private byte[] saatBaraFile;
	
	@Column(name = "saat_bara_name")
	String saatBaraName;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
    @ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name="member_id",nullable=false)
	public Member member;

	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "land", cascade = CascadeType.ALL)
	public Set<LoanDemand> loanDemand;
		
}

package com.techvg.vks.membership.domain;

import com.techvg.vks.base.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "member_belonging")
public class MemberBelonging extends BaseEntity<String> {
	
	@Column(name = "belonging_type")
	String  belongingType ;
	
	@Column(name = "count")
	Integer count ;
	
	@Column(name = "amount")
	double amount ;

	
	@ManyToOne
	@JoinColumn(name = "member_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	public Member member;
	
	
}

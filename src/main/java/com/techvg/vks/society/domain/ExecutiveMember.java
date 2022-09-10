package com.techvg.vks.society.domain;

import com.techvg.vks.base.BaseEntity;
import com.techvg.vks.membership.domain.Member;
import lombok.*;

import javax.persistence.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "executive_members")
public class ExecutiveMember extends BaseEntity<String> {

	@Column(name = "designation")
	private String designation;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "member")
	public Member member;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "elected_from")
	private java.util.Date electedFrom;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "elected_to")
	private java.util.Date electedTo;

}

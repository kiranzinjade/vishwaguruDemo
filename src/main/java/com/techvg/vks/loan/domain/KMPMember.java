package com.techvg.vks.loan.domain;

import com.techvg.vks.base.BaseEntity;
import com.techvg.vks.membership.domain.Member;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "kmp_member")
public class KMPMember extends BaseEntity<String> {

	@Column(name = "kmp_year")
	Integer kmpYear;

	@Column(name = "kmp_syear")
	Integer kmpStartYear;

	@Column(name = "kmp_eyear")
	Integer kmpEndYear;

	@Column(name = "kmp_type")
	String kmpType;      // society,induval, supplement

	@Column(name = "kmp_crop_name")
	String KmpCropName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kmp_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	public KMP kmp;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "member_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	public Member member;
	
	
}

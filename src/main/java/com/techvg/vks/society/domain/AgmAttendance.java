package com.techvg.vks.society.domain;

import com.opencsv.bean.CsvBindByName;
import com.techvg.vks.base.BaseEntity;
import com.techvg.vks.membership.domain.Member;
import lombok.*;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "agm_attendance")

public class AgmAttendance extends BaseEntity<String> {

	@CsvBindByName(column = "member_id")
	String memberId;

	@CsvBindByName(column = "member_name")
	String memberName;

	@CsvBindByName(column = "attendance_status")
	String attendanceStatus;

	@EqualsAndHashCode.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "agm_id")
	public Agm agm;

	@ManyToOne
	@JoinColumn(name = "member_id1")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	public Member member;


}

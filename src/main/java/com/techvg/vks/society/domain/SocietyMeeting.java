package com.techvg.vks.society.domain;

import com.techvg.vks.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Society_meeting")

public class SocietyMeeting extends BaseEntity<String> {

	@Column(name = "meeting_no")
	String meetingNo;

	@Column(name = "meeting_subject")
	String meetingSub;

	@Column(name = "meeting_date")
	Date meetingDate;

	@Lob
	@Column(name = "meeting_details_file")
	private byte[] meetingDetailsFile;
	
	@Column(name = "meeting_file_name")
	String meetingFileName;
		

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "societyMeeting", cascade = CascadeType.ALL)
	private Set<SocietyMeetingDetails> societyMeetingDetails = new HashSet<>();

	public void addSocietyMeetingDetails(SocietyMeetingDetails details) {
		this.societyMeetingDetails.add(details);
	}
}

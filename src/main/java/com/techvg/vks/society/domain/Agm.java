package com.techvg.vks.society.domain;

import com.opencsv.bean.CsvBindByName;
import com.techvg.vks.base.BaseEntity;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "agm")

public class Agm extends BaseEntity<String> {

	@CsvBindByName(column = "year")
	Integer year;

	@CsvBindByName(column = "agm_date")
	Date agmDate;

	@CsvBindByName(column = "agm_subject")
	String agmSubject;
	
	@Lob
	@Column(name = "minutes_of_meeting")
	byte[] minutesOfMeeting;
	
	@Column(name = "minutes_of_meeting_fileName")
	String minutesOfMeetingFileName;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "agm_id", nullable = false, insertable = false)
	private Set<AgmAttendance> agmAttendance;

	@CsvBindByName(column = "agm_description")
	String agmDescription;

	
}

package com.techvg.vks.society.model;

import com.techvg.vks.base.BaseEntityDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Data

@NoArgsConstructor
public class AgmDto extends BaseEntityDto implements Serializable {

	private static final long serialVersionUID = 8506582894893716606L;

	private Integer year;

	private Date agmDate;

	private String agmSubject;
		
	@Lob
	private byte[] minutesOfMeeting;
	
	private String minutesOfMeetingFileName;

	private String agmDescription;
		
	private Set<AgmAttendanceDto> agmAttendanceDtoSet;

	@Builder
	public AgmDto(@Null Long id, @Null LocalDateTime created, @Null String createdBy,
			@Null LocalDateTime lastModified, @Null String lastModifiedBy, Boolean isDeleted,Integer year,
			Date agmDate, String agmSubject,byte[] minutesOfMeeting,String minutesOfMeetingFileName, String agmDescription,
		    Set<AgmAttendanceDto> agmAttendanceDtoSet) {
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.year = year;
		this.agmDate = agmDate;
		this.agmSubject = agmSubject;
		this.minutesOfMeeting=minutesOfMeeting;
		this.minutesOfMeetingFileName=minutesOfMeetingFileName;
		this.agmDescription = agmDescription;
		this.agmAttendanceDtoSet = agmAttendanceDtoSet;
	}

}

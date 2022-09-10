package com.techvg.vks.society.model;

import com.techvg.vks.base.BaseEntityDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Data

@NoArgsConstructor

public class SocietyMeetingDto  extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = 8506582894893716606L;
	

	@NotBlank
	private String meetingNo;
	
	@NotBlank
	private String meetingSub;

	private Date meetingDate;
	
	@Lob
	private byte[] meetingDetailsFile;
	
	private String meetingFileName;
		
	private Set<SocietyMeetingDetailsDto> meetingDetailsList;
    
	
	@Builder
	public SocietyMeetingDto(Long id, LocalDateTime created, String createdBy, LocalDateTime lastModified,
			String lastModifiedBy, Boolean isDeleted, @NotBlank String meetingNo, @NotBlank String meetingSub,
			Date meetingDate, byte[] meetingDetailsFile, String meetingFileName,
			Set<SocietyMeetingDetailsDto> meetingDetailsList) {
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.meetingNo = meetingNo;
		this.meetingSub = meetingSub;
		this.meetingDate = meetingDate;
		this.meetingDetailsFile = meetingDetailsFile;
		this.meetingFileName = meetingFileName;
		this.meetingDetailsList = meetingDetailsList;
	}

	
	
	
	
	
}

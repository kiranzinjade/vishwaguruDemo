package com.techvg.vks.society.model;

import com.techvg.vks.base.BaseEntityDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data

@NoArgsConstructor

public class SocietyMeetingDetailsDto extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = 48186642465404826L;


	@NotBlank
	private String tharavNo;

	@NotBlank
	private String subject;

	private Date tharavDate;
		
	@Lob
	private byte[] tharavDetailsFile;

	private String tharavFileName;

	private Long societyMeetingId;
	
	private String type;

	public SocietyMeetingDetailsDto(@Null Long id, @Null LocalDateTime created, @Null String createdBy,
									@Null LocalDateTime lastModified, @Null String lastModifiedBy,
									Boolean isDeleted, @NotBlank String tharavNo, @NotBlank String subject,
									Date tharavDate,byte[] tharavDetailsFile, String tharavFileName, Long societyMeetingId, String type) {

		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.tharavNo = tharavNo;
		this.subject = subject;
		this.tharavDate = tharavDate;
		this.tharavDetailsFile=tharavDetailsFile;
		this.tharavFileName = tharavFileName;
		this.societyMeetingId = societyMeetingId;
		this.type=type;
	}
}

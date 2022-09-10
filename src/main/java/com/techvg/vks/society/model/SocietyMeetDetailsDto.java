package com.techvg.vks.society.model;

import com.techvg.vks.base.BaseEntityDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data

@NoArgsConstructor

public class SocietyMeetDetailsDto extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = 48186642465404826L;


	@NotBlank
	private String tharavNo;
	private Date tharavDate;

	public SocietyMeetDetailsDto(@Null Long id, @Null LocalDateTime created, @Null String createdBy,
                                 @Null LocalDateTime lastModified, @Null String lastModifiedBy,
                                 Boolean isDeleted, @NotBlank String tharavNo, Date tharavDate) {

		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.tharavNo = tharavNo;
		this.tharavDate = tharavDate;
	}
}

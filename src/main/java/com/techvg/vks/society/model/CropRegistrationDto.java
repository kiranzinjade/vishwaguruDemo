package com.techvg.vks.society.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import com.techvg.vks.base.BaseEntityDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CropRegistrationDto extends BaseEntityDto implements Serializable{
	private static final long serialVersionUID = 8506582894893716606L;
	
	@NotBlank
	private String season;

	@NotBlank
	private String cropName;
	
	private Integer monthDuration;
	
	private Double cropLimit;
	
	private Integer year;

	@Builder
	public CropRegistrationDto(@Null Long id,@Null LocalDateTime created,@Null String createdBy,@Null LocalDateTime lastModified,
			@Null String lastModifiedBy, Boolean isDeleted, @NotBlank String season, @NotBlank String cropName,
			Integer monthDuration, Double cropLimit, Integer year) {
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.season = season;
		this.cropName = cropName;
		this.monthDuration = monthDuration;
		this.cropLimit = cropLimit;
		this.year = year;
	}
	
	
}

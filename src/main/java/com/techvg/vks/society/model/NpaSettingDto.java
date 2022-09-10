package com.techvg.vks.society.model;

import com.techvg.vks.base.BaseEntityDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
public class NpaSettingDto extends BaseEntityDto implements Serializable{
	private static final long serialVersionUID = 8506582894893716606L;

	private Integer durationStart;
	
	private Integer durationEnd;
	
	private Double provision;
	
	@NotBlank
	private String classification;
	
	private Integer year;

	@Builder
	public NpaSettingDto(Long id, LocalDateTime created, String createdBy, LocalDateTime lastModified,
			String lastModifiedBy, Boolean isDeleted, Integer durationStart,
			Integer durationEnd, Double provision, @NotBlank String classification, Integer year) {
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.durationStart = durationStart;
		this.durationEnd = durationEnd;
		this.provision = provision;
		this.classification = classification;
		this.year = year;
	}


	
	
	

	

	
	

	
	

	
}

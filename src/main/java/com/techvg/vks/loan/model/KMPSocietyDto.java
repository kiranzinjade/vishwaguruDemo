package com.techvg.vks.loan.model;

import com.techvg.vks.base.BaseEntityDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class KMPSocietyDto extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = 8506582894893716606L;

	private Integer kmpStartYear;
	private Integer kmpEndYear;
	private String KmpCropName;
	private Integer year;
	private Long kmpId;


	@Builder
	public KMPSocietyDto(Long id, LocalDateTime created, String createdBy, LocalDateTime lastModified,
                         String lastModifiedBy, Boolean isDeleted, Integer kmpStartYear, Integer kmpEndYear,
                         String kmpCropName, Integer year, Long kmpId) {
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.kmpStartYear = kmpStartYear;
		this.kmpEndYear = kmpEndYear;
		this.KmpCropName = kmpCropName;
		this.year = year;
		this.kmpId = kmpId;
	}

	
	

	
	
}

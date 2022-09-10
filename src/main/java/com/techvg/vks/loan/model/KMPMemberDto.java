package com.techvg.vks.loan.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import com.techvg.vks.base.BaseEntityDto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class KMPMemberDto extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = 8506582894893716606L;
    
    private Integer kmpYear;
	private Integer kmpStartYear;
	private Integer kmpEndYear;
	private String kmpType;
	private String KmpCropName;
	private Long kmpId;
	private Long memberId;
	
	@Builder
	public KMPMemberDto(Long id, LocalDateTime created, String createdBy, LocalDateTime lastModified,
						String lastModifiedBy, Boolean isDeleted, Integer kmpYear, Integer kmpStartYear, Integer kmpEndYear, String kmpType,
						String kmpCropName, Long kmpId, Long memberId) {
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.kmpYear = kmpYear;
		this.kmpStartYear = kmpStartYear;
		this.kmpEndYear = kmpEndYear;
		this.kmpType = kmpType;
		KmpCropName = kmpCropName;
		this.kmpId = kmpId;
		this.memberId = memberId;
	}

	
	

	
	
}

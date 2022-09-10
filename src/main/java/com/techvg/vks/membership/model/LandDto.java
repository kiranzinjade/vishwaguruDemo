package com.techvg.vks.membership.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;

import com.techvg.vks.base.BaseEntityDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class LandDto extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = 8506582894893716606L;
	
	@NotBlank
	private String landType;
	@NotBlank
	private String landGatno;
	@NotBlank
	private Integer landAreaHector;
	private Integer landAreaR;
	private String jindagiPatrakNo;
	private double jindagiAmount;
	private String landAddress;
	private Double valueOfProperty;
	private Boolean assigneeOfLand;
	private Long mLLoanNo;

	@Lob
	private byte[] jindagiPatrakFile;

	private String jindagiPatrakName;

	@Lob
	private byte[] eightAFile;

	private String eightAName;
	@Lob
	private byte[] saatBaraFile;

	private String saatBaraName;
	private Long memberId;

	public LandDto(Long id, LocalDateTime created, String createdBy, LocalDateTime lastModified, String lastModifiedBy,
			Boolean isDeleted, @NotBlank String landType, @NotBlank String landGatno, @NotBlank Integer landAreaHector,
			Integer landAreaR, String jindagiPatrakNo, double jindagiAmount, String landAddress, Double valueOfProperty,
			Boolean assigneeOfLand, byte[] jindagiPatrakFile, String jindagiPatrakName, byte[] eightAFile,Long memberId,
			String eightAName, byte[] saatBaraFile, String saatBaraName, Long mLLoanNo) {
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.landType = landType;
		this.landGatno = landGatno;
		this.landAreaHector = landAreaHector;
		this.landAreaR = landAreaR;
		this.jindagiPatrakNo = jindagiPatrakNo;
		this.jindagiAmount = jindagiAmount;
		this.landAddress = landAddress;
		this.valueOfProperty = valueOfProperty;
		this.assigneeOfLand=assigneeOfLand;
		this.mLLoanNo=mLLoanNo;
		this.jindagiPatrakFile = jindagiPatrakFile;
		this.jindagiPatrakName = jindagiPatrakName;
		this.eightAFile = eightAFile;
		this.eightAName = eightAName;
		this.saatBaraFile = saatBaraFile;
		this.saatBaraName = saatBaraName;
		this.memberId=memberId;
	}



}

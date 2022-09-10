package com.techvg.vks.society.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import com.techvg.vks.base.BaseEntityDto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SocietyConfigurationDto extends BaseEntityDto implements Serializable {
	private static final long serialVersionUID = 8506582894893716606L;
	@NotBlank
	private String configName;

	@NotBlank
	private String configType;
	private Double value;
	@NotBlank
	private String year;
	private String status;

	@Builder
	public SocietyConfigurationDto(Long id, LocalDateTime created, String createdBy, LocalDateTime lastModified,
			String lastModifiedBy, Boolean isDeleted, @NotBlank String configName, @NotBlank String configType,
			Double value, @NotBlank String year, String status) {
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.configName = configName;
		this.configType = configType;
		this.value = value;
		this.year = year;
		this.status = status;
	}

}

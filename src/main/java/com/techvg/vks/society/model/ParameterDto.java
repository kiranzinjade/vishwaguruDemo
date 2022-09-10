package com.techvg.vks.society.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.techvg.vks.base.BaseEntityDto;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class ParameterDto extends BaseEntityDto implements Serializable{
	private static final long serialVersionUID = 8506582894893716606L;
	
	private String value;
	private String type;
	
	public ParameterDto(Long id, LocalDateTime created, String createdBy, LocalDateTime lastModified,
			String lastModifiedBy, Boolean isDeleted, String value, String type) {
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.value = value;
		this.type = type;
	}
	
	

}

package com.techvg.vks.society.model;

import java.time.LocalDateTime;

import com.techvg.vks.base.BaseEntityDto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductTypeDto extends BaseEntityDto {

	String type;
	String typeDesc;

	@Builder
	public ProductTypeDto(Long id, LocalDateTime created, String createdBy, LocalDateTime lastModified,
						  String lastModifiedBy, Boolean isDeleted, String type, String typeDesc) {
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.type = type;
		this.typeDesc = typeDesc;
	}
	
	
}

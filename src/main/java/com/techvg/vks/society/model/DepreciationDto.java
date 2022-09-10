package com.techvg.vks.society.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.techvg.vks.base.BaseEntityDto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DepreciationDto extends BaseEntityDto implements Serializable {

	private Integer year;
	
	private Double wdv ;
	
	private Double depreciation ;
	
	private Double bookValue;
	
	private Double wdvSoldAsset;

	private Long assetRegId;
	
	@Builder
	public DepreciationDto(Long id, LocalDateTime created, String createdBy, LocalDateTime lastModified,
			String lastModifiedBy, Boolean isDeleted,Integer year, Double wdv, Double depreciation, Double bookValue, Double wdvSoldAsset,Long assetRegId) {
		super(id, created,createdBy,lastModified,lastModifiedBy,isDeleted);
		this.year = year;
		this.wdv = wdv;
		this.depreciation = depreciation;
		this.bookValue = bookValue;
		this.wdvSoldAsset = wdvSoldAsset;
		this.assetRegId=assetRegId;
	}

	
}

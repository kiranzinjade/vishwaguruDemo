package com.techvg.vks.society.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import com.techvg.vks.base.BaseEntityDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AssetsDto extends BaseEntityDto implements Serializable {
	private static final long serialVersionUID = 8506582894893716606L;
	
	private String assetsName;

	private String assetsType;

	private String catagory;

	private Double depreciation;
	@Builder
	public AssetsDto(Long id, LocalDateTime created, String createdBy, LocalDateTime lastModified,
			String lastModifiedBy, Boolean isDeleted, String assetsName, String assetsType, String catagory,Double depreciation) {
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.assetsName = assetsName;
		this.assetsType = assetsType;
		this.catagory = catagory;
		this.depreciation=depreciation;
	}

}

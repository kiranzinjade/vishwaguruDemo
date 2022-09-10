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
public class DepositTypeDto extends BaseEntityDto implements Serializable {
	private static final long serialVersionUID = -5440735825093051060L;

	@NotBlank
	private String accountType;
	
	private String description;
	
	@Builder
	public DepositTypeDto(Long id, LocalDateTime created, String createdBy, LocalDateTime lastModified,
			String lastModifiedBy, Boolean isDeleted,  String accountType,  String description) {
		super(id, created,createdBy,lastModified,lastModifiedBy,isDeleted);
		this.accountType = accountType;
		this.description = description;
		
}

}

package com.techvg.vks.membership.model;


import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

import com.techvg.vks.base.BaseEntityDto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class MemberBelongingDto extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = 8506582894893716606L;
	
    @NotBlank
	private	 String belongingType;
	private	 Integer count;
	private double amount;
	private Long memberId;
	
	
	@Builder
	public MemberBelongingDto(@Null Long id, @Null LocalDateTime created, @Null String createdBy, @Null LocalDateTime lastModified, @Null String lastModifiedBy, Boolean isDeleted, @NotBlank String belongingType, Integer count, double amount,Long memberId) {
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.belongingType = belongingType;
		this.count = count;
		this.amount = amount;
		this.memberId=memberId;
	}
	
	

	
	
}

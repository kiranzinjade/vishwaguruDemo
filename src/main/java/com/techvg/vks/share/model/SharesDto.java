package com.techvg.vks.share.model;

import com.techvg.vks.base.BaseEntityDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
public class SharesDto extends BaseEntityDto implements Serializable  {
	private static final long serialVersionUID = 8506582894893716606L;

	private Long voucherNo;
	private Date applicationDate;
	private Integer numberOfShares;
	private Double sharePrice;
	private String applicationType;
	private Double shareAmount;
    
	@Null
    private Integer shareApplicationId;
	
	private String shareApplicationStatus;
	
    private Set<SharesAllocationDto> sharesAllocationDtoSet;
	
	@Builder
	public SharesDto(Long id, LocalDateTime created, String createdBy, LocalDateTime lastModified,
			String lastModifiedBy, Boolean isDeleted, Date applicationDate, Integer numberOfShares, Double sharePrice,
			Long voucherNo, String applicationType, Double shareAmount, @Null Integer shareApplicationId,
			String shareApplicationStatus, Set<SharesAllocationDto> sharesAllocationDtoSet)
	{
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.voucherNo = voucherNo;
		this.applicationDate = applicationDate;
		this.numberOfShares = numberOfShares;
		this.sharePrice = sharePrice;
		this.applicationType = applicationType;
		this.shareAmount = shareAmount;
		this.shareApplicationId = shareApplicationId;
		this.shareApplicationStatus = shareApplicationStatus;
		this.sharesAllocationDtoSet = sharesAllocationDtoSet;
	}
	
}



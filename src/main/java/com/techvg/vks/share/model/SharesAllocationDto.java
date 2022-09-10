package com.techvg.vks.share.model;

import com.techvg.vks.base.BaseEntityDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
public class SharesAllocationDto extends BaseEntityDto implements Serializable {
	private static final long serialVersionUID = 8506582894893716606L;

	private Date allocationDate;
	private Integer noOfShares;
	private Integer sharesIdFrom;
	private Integer sharesIdTo;
	private Integer shareCertificateNo;
    
	@Null
    //private Integer shareAllocationId;
    
    private String sharesAllocationStatus;
    private String particulars;
    private String noAndDateOfBoardResolution;

    private Double sharePrice;
    private Double totalSharePrice;
    
    private Long memberId;
    private String fullName;
    
	public SharesAllocationDto(Long id, LocalDateTime created, String createdBy, LocalDateTime lastModified,
			String lastModifiedBy, Boolean isDeleted, Date allocationDate, Integer noOfShares, Integer sharesIdFrom,
			Integer sharesIdTo, Integer shareCertificateNo, @Null Integer shareAllocationId, @NotBlank String status,
			String sharesAllocationStatus,String particulars,String noAndDateOfBoardResolution,
			Double sharePrice, Double totalSharePrice,Long memberId,String fullName)
	{
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.allocationDate = allocationDate;
		this.noOfShares = noOfShares;
		this.sharesIdFrom = sharesIdFrom;
		this.sharesIdTo = sharesIdTo;
		this.shareCertificateNo = shareCertificateNo;
		//this.shareAllocationId = shareAllocationId;
		this.sharesAllocationStatus = sharesAllocationStatus;
		this.particulars=particulars;
		this.noAndDateOfBoardResolution=noAndDateOfBoardResolution;
		this.sharePrice = sharePrice;
		this.totalSharePrice = totalSharePrice;
		this.memberId=memberId;
		this.fullName=fullName;
	}

    
}




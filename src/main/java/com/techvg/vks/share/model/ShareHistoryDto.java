package com.techvg.vks.share.model;

import com.techvg.vks.base.BaseEntityDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
public class ShareHistoryDto extends BaseEntityDto {

	private String memeberUniqueId;
	private Date allocationDate;
	private Integer noOfShares;
	private Date issuedDate;
	private Date printedDate;
	private Double shareTotalAmount;
	private Integer sharesIdFrom;
	private Integer sharesIdTo;
	private Integer shareCertificateNo;

	@Builder
	public ShareHistoryDto(Long id, LocalDateTime created, String createdBy, LocalDateTime lastModified,
			String lastModifiedBy, Boolean isDeleted, String memeberUniqueId, Date allocationDate, Integer noOfShares,
			Date issuedDate, Date printedDate, Double shareTotalAmount, Integer sharesIdFrom, Integer sharesIdTo,
			Integer shareCertificateNo) {
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.memeberUniqueId = memeberUniqueId;
		this.allocationDate = allocationDate;
		this.noOfShares = noOfShares;
		this.issuedDate = issuedDate;
		this.printedDate = printedDate;
		this.shareTotalAmount = shareTotalAmount;
		this.sharesIdFrom = sharesIdFrom;
		this.sharesIdTo = sharesIdTo;
		this.shareCertificateNo = shareCertificateNo;
	}
	

	
}

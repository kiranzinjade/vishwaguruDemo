package com.techvg.vks.trading.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.techvg.vks.base.BaseEntityDto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PurchaseRegisterDto extends BaseEntityDto implements Serializable{
	private static final long serialVersionUID = 8506582894893716606L;
	
		private Double quantity;
		private Date expiryDate;
		private String batchNo;
		private Double buyingPrice;
		private Double totalAmt;

		private Double weight;

		private Double igstAmt;
		private Double cgstAmt;
		private Double sgstAmt;

		private Long productId;
		private String name;
		private Long purchaseOrderId;

		
		@Builder
		public PurchaseRegisterDto(Long id, LocalDateTime created, String createdBy, LocalDateTime lastModified,
				String lastModifiedBy, Boolean isDeleted, Double quantity, Double totalAmt,
                  Double weight, Date expiryDate, String batchNo, Double igstAmt, Double cgstAmt,Double sgstAmt,Double buyingPrice,Long productId,String name, Long purchaseOrderId ) {
			super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
			this.quantity = quantity;
			this.totalAmt = totalAmt;
			this.weight = weight;
			this.expiryDate = expiryDate;
			this.batchNo = batchNo;
			this.cgstAmt=cgstAmt;
			this.igstAmt=igstAmt;
			this.sgstAmt=sgstAmt;
			this.buyingPrice=buyingPrice;
			this.productId=productId;
			this.name=name;
			this.purchaseOrderId = purchaseOrderId;
		}
	

		
}

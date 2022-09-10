package com.techvg.vks.trading.model;

import com.techvg.vks.base.BaseEntityDto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
public class StockRegisterDto extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = 3045671664203584634L;

    private Date expiryDate;
    private String batchNo;

    private Date transactionDate;
    private String transType;  // Sales / Purchase / Impairment
    private Double quantity;
    private Double currentStock;
    private Double currentStockValue;
    private Long productId;
    private Long voucherId;

    private String productName;

	public StockRegisterDto(Long id, LocalDateTime created, String createdBy, LocalDateTime lastModified,
			String lastModifiedBy, Boolean isDeleted, Date expiryDate, String batchNo, Date transactionDate,
			String transType, Double quantity, Double currentStock, Double currentStockValue, Long productId,
			Long voucherId, String productName) {
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.expiryDate = expiryDate;
		this.batchNo = batchNo;
		this.transactionDate = transactionDate;
		this.transType = transType;
		this.quantity = quantity;
		this.currentStock = currentStock;
		this.currentStockValue = currentStockValue;
		this.productId = productId;
		this.voucherId = voucherId;
		this.productName = productName;
	}

    
    
}

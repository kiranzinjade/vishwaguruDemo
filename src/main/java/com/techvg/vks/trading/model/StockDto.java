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

public class StockDto extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = 3045671664203584634L;
	
	 private Double openingStockQuantity;
	    private Double openingStockValue;
	    private Double closingStockQuantity;
	    private Double closingStockValue;
	    private Date stockDate;
	    
	    
	    @Builder
		public StockDto(Long id, LocalDateTime created, String createdBy, LocalDateTime lastModified,
				String lastModifiedBy, Boolean isDeleted, Double openingStockQuantity, Double openingStockValue,
				Double closingStockQuantity, Double closingStockValue, Date stockDate) {
			super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
			this.openingStockQuantity = openingStockQuantity;
			this.openingStockValue = openingStockValue;
			this.closingStockQuantity = closingStockQuantity;
			this.closingStockValue = closingStockValue;
			this.stockDate = stockDate;
		}


}

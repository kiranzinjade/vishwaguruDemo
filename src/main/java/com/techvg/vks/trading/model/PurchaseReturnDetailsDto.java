package com.techvg.vks.trading.model;

import com.techvg.vks.base.BaseEntityDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class PurchaseReturnDetailsDto extends BaseEntityDto implements Serializable {
	private static final long serialVersionUID = 8198851645691266541L;

	private Double returnQuantity;
	private Double price;
	private Date expiryDate;
	private String batchNo;
	private Boolean isDefective;
	private String returnReason;
	private Double totalPrice;
	private Long productId;

}

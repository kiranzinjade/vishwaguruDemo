package com.techvg.vks.trading.model;

import com.techvg.vks.base.BaseEntityDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor

public class SalesRegisterDto extends BaseEntityDto implements Serializable{
	private static final long serialVersionUID = 8506582894893716606L;

	private Double quantity;
	private Date expiryDate;
	private String batchNo;
	private Double sellingPrice;
	private Double totalAmt;

	private Double igstAmt;
	private Double cgstAmt;
	private Double sgstAmt;

	private Long productId;
	private String name;
	private Long salesOrderId;
}

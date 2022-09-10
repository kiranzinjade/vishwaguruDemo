package com.techvg.vks.trading.model;

import com.techvg.vks.base.BaseEntityDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
public class PurchaseReturnDto extends BaseEntityDto implements Serializable {
	private static final long serialVersionUID = 8506582894893716606L;
	
	private Integer billNo;
	private Date returnDate;
	private Double returnAmt;
	private Double balanceAmount;
	private Double paidAmount;
	private String returnMode;
	private Long purchaseOrderId;
	private Set<PurchaseReturnDetailsDto> purchaseReturnDetails;

}

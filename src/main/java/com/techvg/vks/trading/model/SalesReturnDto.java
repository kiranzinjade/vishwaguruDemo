package com.techvg.vks.trading.model;

import com.techvg.vks.base.BaseEntityDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
public class SalesReturnDto extends BaseEntityDto implements Serializable{
	private static final long serialVersionUID = 8506582894893716606L;

	private Date returnDate;
	private Integer billNo;
	private Double returnAmt;
	private Long salesOrderId;
	private Double balanceAmount;
	private Double paidAmount;
	private String returnMode;     
  
	private Set<SalesReturnDetailsDto> salesReturnDetails;

}

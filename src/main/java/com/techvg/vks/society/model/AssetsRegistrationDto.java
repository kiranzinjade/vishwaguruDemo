package com.techvg.vks.society.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

import com.techvg.vks.accounts.model.VouchersDto;
import com.techvg.vks.base.BaseEntityDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@NoArgsConstructor
public class AssetsRegistrationDto extends BaseEntityDto implements Serializable {
	private static final long serialVersionUID = 8506582894893716606L;


	private String transactionType;
//sales or purchse
	private Integer quantity;

	private Date date;
	private Double amount;
    private Double cost;
    private Long billNo;
	private Integer balanceQuantity;
	private Long voucherTypeId;
	private Double balanceValue;
	private String narration;
	private Long assetsId;
	private String assetsName;
	private String assetsType;
	private String mode;
	private String catagory;
	private VouchersDto vouchersDto;

	public AssetsRegistrationDto(Long id, LocalDateTime created, String createdBy, LocalDateTime lastModified,
			String lastModifiedBy, Boolean isDeleted, String transactionType,Long billNo, Integer quantity,
			Date date, Double amount, Double cost, Integer balanceQuantity, Double balanceValue, String narration,
			String assetsName,VouchersDto vouchersDto,Long voucherTypeId, String assetsType,String mode,String catagory,
			Integer closingBalanceQuantity, Double closingBalnceValue, Long assetsId) {
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.transactionType = transactionType;
		this.quantity = quantity;
		this.date = date;
		this.amount = amount;
		this.cost = cost;
		this.billNo=billNo;
		this.balanceQuantity = balanceQuantity;
		this.balanceValue = balanceValue;
	    this.narration=narration;
		this.assetsId = assetsId;
		this.voucherTypeId=voucherTypeId;
		this.vouchersDto=vouchersDto;
		this.assetsName=assetsName;
		this.mode=mode;
		this.assetsType=assetsType;
		this.catagory=catagory;
	}

	
	
	

	
}

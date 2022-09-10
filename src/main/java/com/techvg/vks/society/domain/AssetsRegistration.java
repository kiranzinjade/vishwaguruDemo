package com.techvg.vks.society.domain;

import com.techvg.vks.base.BaseEntity;
import com.techvg.vks.trading.domain.PurchaseRegister;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "assets_registration")
public class AssetsRegistration extends BaseEntity<String>{
	
	@Column(name = "transaction_date")
	Date date;
	
	@Column(name = "transaction_type")
	String transactionType;
	
	
	@Column(name = "quantity")
	Integer quantity;
	
	@Column(name = "amount")
	Double amount;
	
	@Column(name = "bill_no")
	Long billNo;
	
	@Column(name = "cost")
	Double cost;
	
	@Column(name = "balance_quantity")
	Integer balanceQuantity;

	@Column(name = "balance_value")
	Double balanceValue;
	
	@Column(name = "narration")
    String narration;
	
	@Column(name = "mode")
    String mode;
	
	
	@EqualsAndHashCode.Exclude
    @ManyToOne(fetch= FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinColumn(name="assets_id",nullable=false)
	public Assets assets;
	
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "assetsReg", fetch= FetchType.LAZY)
    private Set<Depreciation> depreciations = new HashSet<>();
    
	
//	@EqualsAndHashCode.Exclude
//    @ManyToOne(fetch= FetchType.LAZY,cascade=CascadeType.ALL)
//	@JoinColumn(name="voucher_id",nullable=true)
//	public Vouchers vouchers;
}

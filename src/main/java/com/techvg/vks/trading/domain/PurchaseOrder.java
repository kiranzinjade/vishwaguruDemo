package com.techvg.vks.trading.domain;

import com.techvg.vks.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "purchase_order")
public class PurchaseOrder extends BaseEntity<String> {

    @Column(name="order_no")
    Long orderNo;

    @Column(name = "purchase_date")
    Date purchaseDate;

    @Column(name = "bill_no")
    Integer billNo;

    @Column(name = "department")
    String department;

    @Column(name = "purchase_mode")
    String purchaseMode;               // Cash / Credit
   
    @Column(name = "acc_head_code") 
    String accHeadCode;

    @Column(name = "bill_amount")
    Double billAmount;

    @Column(name = "balance_amount")
    Double balanceAmount;

    @Column(name = "paid_amount")
    Double paidAmount;

    @Column(name = "particulars")
    String particulars;

    @Column(name = "voucherNo")
    Long voucherNo;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch= FetchType.LAZY,cascade=CascadeType.ALL)
    @JoinColumn(name="vendorregister_id")
    public VendorRegister vendorRegister;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "purchaseOrder", fetch= FetchType.LAZY)
    private Set<PurchaseRegister> purchaseRegisters = new HashSet<>();
    
    public void addPurchaseRegister(PurchaseRegister purchaseRegister) {
    	purchaseRegister.setPurchaseOrder(this);
    	purchaseRegisters.add(purchaseRegister);
    }
}

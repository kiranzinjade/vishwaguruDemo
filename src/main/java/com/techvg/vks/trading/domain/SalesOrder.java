package com.techvg.vks.trading.domain;

import com.techvg.vks.base.BaseEntity;
import com.techvg.vks.membership.domain.Member;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sales_order")
public class SalesOrder extends BaseEntity<String> {

    @Column(name="order_no")
    Long orderNo;

    @Column(name = "sales_date")
    Date salesDate;

    @Column(name = "bill_no")
    Integer billNo;

    @Column(name = "department")
    String department;

    @Column(name = "sales_mode")
    String salesMode;               // Cash / Credit

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
    @JoinColumn(name="member_id")
    public Member member;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "salesOrder", fetch= FetchType.LAZY)
    private Set<SalesRegister> salesRegisters = new HashSet<>();
    
    public void addSalesRegister(SalesRegister salesRegister) {
    	salesRegister.setSalesOrder(this);
    	salesRegisters.add(salesRegister);
    }
}

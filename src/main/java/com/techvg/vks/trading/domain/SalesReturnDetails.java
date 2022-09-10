package com.techvg.vks.trading.domain;

import com.techvg.vks.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sales_return_details")
public class SalesReturnDetails extends BaseEntity<String> {

    @Column(name = "return_quantity")
    Double returnQuantity;

    @Column(name = "expiry_date")
    Date expiryDate;

    @Column(name = "batch_no")
    String batchNo;

    @Column(name = "is_defective")
    Boolean isDefective;

    @Column(name = "price")
    Double price;

    @Column(name= "return_reason")
    String returnReason;
    
    @Column(name = "total_price")
    Double totalPrice;

    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch= FetchType.LAZY,cascade=CascadeType.ALL)
    @JoinColumn(name="product_id",nullable=false)
    public Product product;

    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch= FetchType.LAZY,cascade=CascadeType.ALL)
    @JoinColumn(name="sales_return_id",nullable=false)
    public SalesReturn salesReturn;
}

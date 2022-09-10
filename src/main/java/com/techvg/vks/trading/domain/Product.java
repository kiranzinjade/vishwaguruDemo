package com.techvg.vks.trading.domain;

import com.techvg.vks.base.BaseEntity;
import com.techvg.vks.society.domain.MeasuringUnit;
import com.techvg.vks.society.domain.ProductType;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class  Product extends BaseEntity<String> {

    @Column(name = "name")
    String name;

    @Column(name = "selling_price")
    Double sellingPrice;

    @Column(name = "rate_igst")
    Double rateIGST;

    @Column(name = "rate_cgst")
    Double rateCGST;

    @Column(name = "rate_sgst")
    Double rateSGST;

    @Column(name = "unit_qty")
    Integer unitQty;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "measuring_unit_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    public MeasuringUnit measuringUnit;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch= FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn(name="product_type_id",nullable=false)
    public ProductType productType;
    
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "product", fetch= FetchType.LAZY)
    private List<ProductVendor> productVendors = new ArrayList<>();


    public void addVendor(ProductVendor productVendor) {
        this.productVendors.add(productVendor);
    }


}

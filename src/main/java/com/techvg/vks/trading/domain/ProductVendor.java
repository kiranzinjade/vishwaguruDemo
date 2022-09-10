package com.techvg.vks.trading.domain;

import com.techvg.vks.base.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_vendor")
public class ProductVendor extends BaseEntity<String> {

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL,fetch= FetchType.LAZY)
    @JoinColumn(name = "product_id",  referencedColumnName = "id")
    private Product product;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL,fetch= FetchType.LAZY)
    @JoinColumn(name = "vendor_id",  referencedColumnName = "id")
    private VendorRegister vendor;


}

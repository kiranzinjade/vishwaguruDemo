package com.techvg.vks.society.domain;

import com.techvg.vks.base.BaseEntity;
import com.techvg.vks.trading.domain.Product;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "measuring_unit")
public class MeasuringUnit extends BaseEntity<String> {

    @Column(name = "name")
    String name;

    @Column(name = "description")
    String description;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(fetch= FetchType.LAZY,mappedBy="measuringUnit",cascade=CascadeType.ALL)
    private Set<Product> product;
}

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
@Table(name="product_type")
public class ProductType extends BaseEntity<String> {

	@Column(name = "type_name")
	String type;
	
	@Column(name = "type_desc")
	String typeDesc;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany( fetch= FetchType.LAZY, mappedBy="productType", cascade= CascadeType.ALL)
	private Set<Product> products;

}

package com.techvg.vks.trading.domain;

import com.techvg.vks.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vendor_register")
public class VendorRegister extends BaseEntity<String> {
	
	@Column(name = "company_name")
	String companyName;
	
	@Column(name = "owner_name")
	String ownerName;
	
	@Column(name = "phone_number")
	String phoneNumber;
	
	@Column(name = "pan_card")
	String panCard;
	
	@Column(name = "address1")
	String address1;
	
	@Column(name = "address2")
	String address2;
	
	@Column(name = "gst_number")
	String gstNumber;
	
	@Column(name = "account_number")
	Long accountNumber;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "vendor", fetch= FetchType.LAZY)
	private List<ProductVendor> productVendors = new ArrayList<>();

}

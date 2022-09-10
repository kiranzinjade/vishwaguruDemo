package com.techvg.vks.trading.model;

import com.techvg.vks.base.BaseEntityDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
@Data
@NoArgsConstructor
public class VendorRegisterDto extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = 3045671664203584634L;
    
     private String companyName;
     private String ownerName;
     private String phoneNumber;
     private String panCard;
     private String address1;
     private String address2;
     private String gstNumber;
     private Long accountNumber;
   
     private Set<ProductDto> products;
     

	public VendorRegisterDto(@Null Long id, @Null LocalDateTime created, @Null String createdBy, @Null LocalDateTime lastModified,
			@Null String lastModifiedBy, Boolean isDeleted, String companyName, String ownerName, String phoneNumber,
			String panCard, String address1, String address2, String gstNumber, Long accountNumber, Set<ProductDto> products) {
		super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
		this.companyName = companyName;
		this.ownerName = ownerName;
		this.phoneNumber = phoneNumber;
		this.panCard = panCard;
		this.address1 = address1;
		this.address2 = address2;
		this.gstNumber = gstNumber;
		this.accountNumber=accountNumber;
		this.products = products;
	}

}

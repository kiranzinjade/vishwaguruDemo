package com.techvg.vks.trading.model;

import com.techvg.vks.base.BaseEntityDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ProductVendorDto extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = 3045671664203584634L;

    private Long productId;
    private String prodName;
    private Long vendorId;
    private String vendorName;

    public ProductVendorDto(@Null Long id, @Null LocalDateTime created, @Null String createdBy, @Null LocalDateTime lastModified,
                            @Null String lastModifiedBy, Boolean isDeleted, Long productId,
                            String prodName, Long vendorId, String vendorName) {
        super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
        this.productId = productId;
        this.prodName = prodName;
        this.vendorId = vendorId;
        this.vendorName = vendorName;
    }
}

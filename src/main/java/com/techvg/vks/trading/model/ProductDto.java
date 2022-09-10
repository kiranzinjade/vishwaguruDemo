package com.techvg.vks.trading.model;

import com.techvg.vks.base.BaseEntityDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class ProductDto extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = 3045671664203584634L;

    private String name;
    private Double sellingPrice;
    private Double buyingPrice;
    private Double rateIGST;
    private Double rateCGST;
    private Double rateSGST;
    private Integer unitQty;
    private String prodType;
    private String unit;
    private Long prodTypeId;
    private Long unitId;
    private Long vendorId;
    private List<ProductVendorDto> vendors;

    public ProductDto(@Null Long id, @Null LocalDateTime created, @Null String createdBy, @Null LocalDateTime lastModified,
                      @Null String lastModifiedBy, Boolean isDeleted, String name, Double sellingPrice, Double buyingPrice,
                      Double rateIGST, Double rateCGST, Double rateSGST, Integer unitQty, String prodType,
                      String unit, Long prodTypeId, Long unitId, Long vendorId, List<ProductVendorDto> vendors) {
        super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
        this.name = name;
        this.sellingPrice = sellingPrice;
        this.buyingPrice = buyingPrice;
        this.rateIGST = rateIGST;
        this.rateCGST = rateCGST;
        this.rateSGST = rateSGST;
        this.unitQty = unitQty;
        this.prodType = prodType;
        this.unit = unit;
        this.prodTypeId = prodTypeId;
        this.unitId = unitId;
        this.vendorId= vendorId;
        this.vendors=vendors;
    }
}

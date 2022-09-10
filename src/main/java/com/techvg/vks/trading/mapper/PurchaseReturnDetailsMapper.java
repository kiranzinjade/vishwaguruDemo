package com.techvg.vks.trading.mapper;

import com.techvg.vks.trading.domain.PurchaseReturnDetails;
import com.techvg.vks.trading.domain.StockRegister;
import com.techvg.vks.trading.model.PurchaseReturnDetailsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PurchaseReturnDetailsMapper {

    PurchaseReturnDetailsDto toDto(PurchaseReturnDetails domain);
    PurchaseReturnDetails toDomain(PurchaseReturnDetailsDto dto);

    @Mapping(target = "quantity", source="domain.returnQuantity")
    @Mapping(target = "expiryDate", source="domain.expiryDate")
    @Mapping(target = "batchNo", source="domain.batchNo")
    @Mapping(target = "product", source="domain.product")
    @Mapping(target = "transactionDate", source="domain.purchaseReturn.returnDate")
    StockRegister toStockRegisterDomain(PurchaseReturnDetails domain);
}

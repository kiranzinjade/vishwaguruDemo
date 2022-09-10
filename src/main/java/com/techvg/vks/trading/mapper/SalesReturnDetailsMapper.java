package com.techvg.vks.trading.mapper;

import com.techvg.vks.trading.domain.SalesReturnDetails;
import com.techvg.vks.trading.domain.StockRegister;
import com.techvg.vks.trading.model.SalesReturnDetailsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SalesReturnDetailsMapper {

    SalesReturnDetailsDto toDto(SalesReturnDetails domain);
    SalesReturnDetails toDomain(SalesReturnDetailsDto dto);

    @Mapping(target = "quantity", source="domain.returnQuantity")
    @Mapping(target = "expiryDate", source="domain.expiryDate")
    @Mapping(target = "batchNo", source="domain.batchNo")
    @Mapping(target = "product", source="domain.product")
    @Mapping(target = "transactionDate", source="domain.salesReturn.returnDate")
    StockRegister toStockRegisterDomain(SalesReturnDetails domain);
}

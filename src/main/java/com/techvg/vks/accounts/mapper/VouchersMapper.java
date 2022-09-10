package com.techvg.vks.accounts.mapper;

import com.techvg.vks.accounts.domain.Vouchers;
import com.techvg.vks.accounts.model.VouchersDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {VoucherDetailsMapper.class}, componentModel = "spring")
public interface VouchersMapper {
    Vouchers toDomain(VouchersDto vouchersDto);

    @Mapping( source = "vouchers.voucherType.id", target = "voucherTypeId")
    @Mapping( source = "vouchers.voucherType.name", target = "voucherTypeDesc")
    VouchersDto toDto(Vouchers vouchers);
}

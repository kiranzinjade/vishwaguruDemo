package com.techvg.vks.accounts.mapper;

import com.techvg.vks.accounts.domain.CashBook;
import com.techvg.vks.accounts.domain.GeneralLedger;
import com.techvg.vks.accounts.domain.VoucherTrans;
import com.techvg.vks.accounts.model.VoucherTransDto;
import com.techvg.vks.config.AccountConstants;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VoucherTransMapper {
    VoucherTrans toDomain(VoucherTransDto dto);
    VoucherTransDto toDto(VoucherTrans domain);

    GeneralLedger voucherTransToGeneralLedger(VoucherTrans domain);

    @Mapping(target = "debitAmt", ignore = true )
    @Mapping(target = "creditAmt", ignore = true )
    @Mapping(source = "remark", target = "particulars")
    @Mapping(source = "transDate", target = "transDate")
    @Mapping(source = "voucherNo", target = "voucherNo")
    CashBook voucherTransToCashbook(VoucherTrans domain);

    @AfterMapping
    default void updateCashBook(final VoucherTrans domain,
                                @MappingTarget final CashBook cashBook){
        if(domain.getDebitAmt()!=null) {
            cashBook.setTransType(AccountConstants.CREDIT);
            cashBook.setCreditAmt(domain.getDebitAmt());
        }
        else if(domain.getCreditAmt()!=null) {
            cashBook.setTransType(AccountConstants.DEBIT);
            cashBook.setDebitAmt(domain.getCreditAmt());
        }
    }
}

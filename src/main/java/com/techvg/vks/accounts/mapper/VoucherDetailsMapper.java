 package com.techvg.vks.accounts.mapper;

 import com.techvg.vks.accounts.domain.VoucherDetails;
 import com.techvg.vks.accounts.model.VoucherDetailsDto;
 import org.mapstruct.AfterMapping;
 import org.mapstruct.Mapper;
 import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VoucherDetailsMapper {

    VoucherDetails toDomain(VoucherDetailsDto dto);
    VoucherDetailsDto toDto(VoucherDetails domain);

     @AfterMapping
     default void updateDto(@MappingTarget VoucherDetailsDto dto, VoucherDetails domain) {
        if(domain.getCreditAcc() !=null)
            dto.setCreditAmt(domain.getTransAmount());
         if(domain.getDebitAcc() !=null)
             dto.setDebitAmt(domain.getTransAmount());

     }
}

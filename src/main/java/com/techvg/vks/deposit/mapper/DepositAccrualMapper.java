package com.techvg.vks.deposit.mapper;

import com.techvg.vks.deposit.domain.DepositAccrual;
import com.techvg.vks.deposit.model.DepositAccrualDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DepositAccrualMapper {

    DepositAccrual toDomain(DepositAccrualDto dto);

   // @Mapping(source = "domain.savingAccount.id",target = "savingAccountId")
    @Mapping(source = "domain.deposit.id",target = "depositId")
    DepositAccrualDto toDto(DepositAccrual domain);

    List<DepositAccrualDto> toDtoList(List<DepositAccrual> domainList);
}

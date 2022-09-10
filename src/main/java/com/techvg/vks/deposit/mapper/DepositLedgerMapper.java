package com.techvg.vks.deposit.mapper;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.techvg.vks.deposit.domain.Deposit;
import com.techvg.vks.deposit.domain.DepositLedger;
import com.techvg.vks.deposit.model.DepositDto;
import com.techvg.vks.deposit.model.DepositLedgerDto;

@Mapper(componentModel = "spring")

public interface DepositLedgerMapper {
	
	@Mapping(source = "depositLedger.savingAccount.id",target = "savingAccountId")
	@Mapping(source = "depositLedger.deposit.id",target = "depositId")
	DepositLedgerDto depositLedgerToDepositLedgerDto(DepositLedger depositLedger);

	DepositLedger depositLedgerDtoToDepositLedger(DepositLedgerDto depositLedgerDto);
	
	List<DepositLedgerDto> toDtoList(List<DepositLedger> domainList);
	
	@AfterMapping
	default void getDepositMemberDetails(@MappingTarget DepositLedgerDto depositLedgerDto,DepositLedger depositLedger) {
		//depositLedgerDto.setFullName(depositLedger.getDeposit().getMember().getFirstName()+" "+depositLedger.getDeposit().getMember().getMiddleName()+" "+depositLedger.getDeposit().getMember().getLastName());
		if(depositLedger.getSavingAccount()==null) {
			depositLedgerDto.setFullName(depositLedger.getDeposit().getMember().getFirstName()+" "+depositLedger.getDeposit().getMember().getMiddleName()+" "+depositLedger.getDeposit().getMember().getLastName());
		}
		else {
			depositLedgerDto.setFullName(depositLedger.getSavingAccount().getMember().getFirstName()+" "+depositLedger.getSavingAccount().getMember().getMiddleName()+" "+depositLedger.getSavingAccount().getMember().getLastName());
		}
	}
	

}

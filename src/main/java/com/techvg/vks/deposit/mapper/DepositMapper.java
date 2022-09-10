package com.techvg.vks.deposit.mapper;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.techvg.vks.deposit.domain.Deposit;
import com.techvg.vks.deposit.model.DepositDto;


@Mapper(componentModel = "spring")

public interface DepositMapper {
	@Mapping(source="deposit.member.id" , target= "memberId")
	@Mapping(source="deposit.depositAccount.id" , target="depositAccountId")
	DepositDto depositToDepositDto(Deposit deposit);

	Deposit depositDtoToDeposit(DepositDto depositDto);
	List<DepositDto> toDtoList(List<Deposit> domainList);

	@AfterMapping
	default void getMemberDetails(@MappingTarget DepositDto depositDto,Deposit deposit) {
		depositDto.setFullName(deposit.getMember().getFirstName()+" "+deposit.getMember().getMiddleName()+" "+deposit.getMember().getLastName());
		depositDto.setName(deposit.getDepositAccount().getName());
		depositDto.setLockInPeriod(deposit.getDepositAccount().getLockInPeriod());
		depositDto.setAccountType(deposit.getDepositAccount().getDepositType().getAccountType());
	}

}

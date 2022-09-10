package com.techvg.vks.deposit.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.techvg.vks.deposit.domain.SavingAccount;
import com.techvg.vks.deposit.model.SavingAccountDto;
import com.techvg.vks.society.domain.ExecutiveMember;
import com.techvg.vks.society.model.ExecutiveMembersDto;


@Mapper(componentModel = "spring")
public interface SavingAccountMapper {
	
	@Mapping(source = "savingAccount.depositAccount.id", target = "depositAccountId")
	@Mapping(source="member.id" , target = "memberId")
	SavingAccountDto savingAccountToSavingAccountDto(SavingAccount savingAccount);
	
	SavingAccount savingAccountDtoToSavingAccount(SavingAccountDto savingAccountDto);

	@AfterMapping
	default void getMemberDetails(@MappingTarget SavingAccountDto savingAccountDto,SavingAccount savingAccount) {
		savingAccountDto.setFullName(savingAccount.getMember().getFirstName()+" "+savingAccount.getMember().getMiddleName()+" "+savingAccount.getMember().getLastName());
		
	}
	
}

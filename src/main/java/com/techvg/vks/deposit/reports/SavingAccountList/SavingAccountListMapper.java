package com.techvg.vks.deposit.reports.SavingAccountList;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import com.techvg.vks.deposit.domain.SavingAccount;

@Mapper(componentModel = "spring")
public interface SavingAccountListMapper {

	@IterableMapping(qualifiedByName = "all")
	List<SavingAccountListWrapper> mapAllSavingAccountDataList(List<SavingAccount> savingAccountlist);

	@Named("all")
	@Mappings({

			@Mapping(ignore = true, target = "name"), @Mapping(ignore = true, target = "address"),

	})
	SavingAccountListWrapper mapAllFdData(SavingAccount savingAccount);

	@AfterMapping
	default void fillInProperties(final SavingAccount savingAccount,
			@MappingTarget final SavingAccountListWrapper wrapper) {
		wrapper.setName(savingAccount.getMember().getFirstName() + " " + savingAccount.getMember().getMiddleName() + ""
				+ savingAccount.getMember().getLastName());
		savingAccount.getMember().getHouse().forEach(action -> {
			if (action.getAddressType().contentEquals("PERMANENT")) {
				wrapper.setAddress(action.getAddressLine1() + ", " + action.getAddressLine2() + ", " + action.getCity()
						+ ", " + action.getState() + ", PIN - " + action.getPincode());
			}
		});
	}

}

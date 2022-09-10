package com.techvg.vks.deposit.reports.fdreport;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import com.techvg.vks.common.NumberToWordConversionService;
import com.techvg.vks.deposit.domain.Deposit;

@Mapper(componentModel="spring")
public interface FdReportMapper {

	@IterableMapping(qualifiedByName="all")
    List<FdReportWrapper> mapAllFdDataList(List<Deposit> deposits);

	@Named("all")
	@Mappings({ 
		
		@Mapping(ignore=true,target="name"),
		@Mapping(source="accountNo",target="accountNo"),
		@Mapping(source="deposit.depositAccount.depositType.accountType",target="accountType"),
		@Mapping(source="receiptNo",target="receiptNo"),
		@Mapping(source="deposit.depositAccount.fdDurationDays",target="durationDays"),
		@Mapping(source="depositDate",target="depositDate"),
		@Mapping(source="maturityDate",target="maturityDate"),
		@Mapping(source="deposit.depositAccount.interest",target="interest"),
		@Mapping(source="depositAmount",target="depositAmount"),		
		@Mapping(source="maturityAmount",target="maturityAmount"),
		@Mapping(source="id",target="depositId")
		
		
	})
    FdReportWrapper mapAllFdData(Deposit deposit);
	
	@AfterMapping
	default void fillInProperties(final Deposit deposit,
	      @MappingTarget final FdReportWrapper wrapper ) {
		NumberToWordConversionService numberToWord = new NumberToWordConversionService();
		wrapper.setDepositInWords(numberToWord.convert(deposit.getDepositAmount().intValue()));
		wrapper.setName(deposit.getMember().getFirstName()+" "+deposit.getMember().getMiddleName()+" "+deposit.getMember().getLastName());
	}
		
			
	
}

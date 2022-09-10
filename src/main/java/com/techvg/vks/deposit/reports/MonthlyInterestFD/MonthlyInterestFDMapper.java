package com.techvg.vks.deposit.reports.MonthlyInterestFD;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import com.techvg.vks.common.NumberToWordConversionService;
import com.techvg.vks.deposit.domain.Deposit;
import com.techvg.vks.deposit.reports.fdreport.FdReportWrapper;
import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.membership.reports.MemberRegReport.MemberRegReportwrapper;

@Mapper(componentModel="spring")
public interface MonthlyInterestFDMapper {
	
	@IterableMapping(qualifiedByName="all")
    List<MonthlyInterestFDWrapper> mapAllFdDataList(List<Deposit> deposits);
    MonthlyInterestFDWrapper mapAllFdData(Deposit deposit);


	@Named("all")
	@Mappings({ 
		
		@Mapping(ignore=true,target="name"),
		@Mapping(source="accountNo",target="accountNo"),
		@Mapping(source="deposit.depositAccount.depositType.accountType",target="accountType"),
		@Mapping(source="receiptNo",target="receiptNo"),
		@Mapping(source="depositDate",target="depositDate"),
		@Mapping(source="maturityDate",target="maturityDate"),
		@Mapping(source="interestEarned",target="interestEarned"),
		@Mapping(source="depositAmount",target="depositAmount"),		
		@Mapping(source="id",target="depositId")
		
		
	})
	
	@AfterMapping
	default void fillInProperties(final Deposit deposit,
	      @MappingTarget final MonthlyInterestFDWrapper wrapper ) {
		
	//	wrapper.setName(deposit.getMember().getFirstName()+" "+deposit.getMember().getMiddleName()+" "+deposit.getMember().getLastName());
	}

}

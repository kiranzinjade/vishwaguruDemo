package com.techvg.vks.deposit.reports.MaturityRegisterDeposit;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import com.techvg.vks.config.DepositConstants;
import com.techvg.vks.deposit.domain.Deposit;

@Mapper(componentModel="spring")
public interface MaturityRegisterDepositMapper {
	@IterableMapping(qualifiedByName="all")
  	List<MaturityRegisterDepositWrapper> mapAllMaturityDataList(List<Deposit> depositlist);

	@Named("all")
	
	@Mappings({ 
		@Mapping(source="depositDate",target="depositDate"),
		@Mapping(source="maturityDate",target="maturityDate"),
		@Mapping(source="maturityAmount",target="maturityAmount"),
		@Mapping(source="id",target="depositId"),	
		@Mapping(source="maturityAmount",target="amountPaid"),	
		@Mapping(source="maturityAmount",target="balance")
			
	})
	
	MaturityRegisterDepositWrapper mapAllMaturityData(Deposit deposit);
	
	

@AfterMapping
default void fillInProperties(final Deposit deposit,@MappingTarget final MaturityRegisterDepositWrapper wrapper ) {
 
	if(deposit.getDepositStatus().equals(DepositConstants.DEPOSIT_ACC_CLOSE))
	{
		 wrapper.setAmountPaid(deposit.getMaturityAmount());
		 wrapper.setBalance(0.00);
	}else{
		 wrapper.setAmountPaid(0.00);
		wrapper.setBalance(deposit.getMaturityAmount());
	}
	
  wrapper.setDepositDate(deposit.getDepositDate());
  wrapper.setDepositId(deposit.getId());
  wrapper.setMaturityAmount(deposit.getMaturityAmount());
  wrapper.setMaturityDate(deposit.getMaturityDate());
	
	
}





}

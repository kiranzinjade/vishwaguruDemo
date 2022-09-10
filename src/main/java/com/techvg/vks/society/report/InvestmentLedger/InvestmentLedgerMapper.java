package com.techvg.vks.society.report.InvestmentLedger;

import com.techvg.vks.society.domain.SocietyInvestment;
import com.techvg.vks.society.domain.SocietyInvestmentDetails;

import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel="spring")
public interface InvestmentLedgerMapper {
	@IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
	List<InvestmentLedgerWrapper> mapAllRegisterDataList(List<SocietyInvestmentDetails> societyInvestmentlist);

	InvestmentLedgerWrapper mapAllRegisterData(SocietyInvestmentDetails societyInvestment);

	@AfterMapping
	default void fillInProperties(final SocietyInvestmentDetails societyInvestment, @MappingTarget final InvestmentLedgerWrapper wrapper ) {
	
		
		wrapper.setBoardResolutionDate(societyInvestment.getSocietyInvestment().getBoardResolutionDate());
		wrapper.setBoardResolutionNo(societyInvestment.getSocietyInvestment().getBoardResolutionNo());
		wrapper.setBankName(societyInvestment.getSocietyInvestment().getSocietyInvestmentMaster().getSocietyBank().getBankName());
		wrapper.setNomenclature(societyInvestment.getSocietyInvestment().getSocietyInvestmentMaster().getNomenclature());
		wrapper.setAccountType(societyInvestment.getSocietyInvestment().getSocietyInvestmentMaster().getDepositType().getAccountType());
		wrapper.setPeriod(societyInvestment.getSocietyInvestment().getSocietyInvestmentMaster().getPeriod());
		wrapper.setInterest(societyInvestment.getSocietyInvestment().getSocietyInvestmentMaster().getInterest());
		wrapper.setDepositDate(societyInvestment.getSocietyInvestment().getDepositDate());
		wrapper.setDepositAmount(societyInvestment.getSocietyInvestment().getDepositAmount());
		wrapper.setInterestAmount(societyInvestment.getSocietyInvestment().getInterestAmount());
		wrapper.setMaturityAmount(societyInvestment.getSocietyInvestment().getMaturityAmount());
		wrapper.setMaturityDate(societyInvestment.getSocietyInvestment().getMaturityDate());
		wrapper.setDepositDate1(societyInvestment.getDepositDate());
		wrapper.setInterestAmount1(societyInvestment.getInterestAmount());
		wrapper.setCreditAmount(societyInvestment.getCreditAmount());
		wrapper.setDebitAmount(societyInvestment.getDebitAmount());
		wrapper.setBalanceAmount(societyInvestment.getBalanceAmount());
		wrapper.setInterestDate(societyInvestment.getInterestDate());
	}
}

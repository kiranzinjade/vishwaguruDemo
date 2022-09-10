package com.techvg.vks.loan.reports.loandisbursement;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueMappingStrategy;

import com.techvg.vks.config.LoanConstants;
import com.techvg.vks.loan.domain.LoanDetails;

@Mapper(componentModel = "spring")

public interface LoanDisbursementReportMapper {
	@IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
	public List<LoanDisbursementReportWrapper> mapAllDataList(List<LoanDetails> loanDetails);
	public LoanDisbursementReportWrapper mapAllData(LoanDetails loanDetails);

	@AfterMapping
	default void fillInProperties(final LoanDetails loanDetails, @MappingTarget final LoanDisbursementReportWrapper wrapper) {

		wrapper.setMemberId(loanDetails.member.getId());
		wrapper.setLoanAccountNo(loanDetails.getLoanAccountNo());

		loanDetails.member.getHouse().forEach(action -> {
			if (action.getAddressType().contentEquals("PERMANENT")) {
				wrapper.setAddress(action.getAddressLine1() + ", " + action.getAddressLine2() + ", " + action.getCity()
						+ ", " + action.getState() + ", PIN - " + action.getPincode());
				wrapper.setVillage(action.getCity());
			}
		});
		wrapper.setBorrowerName(loanDetails.member.getLastName() + " " + loanDetails.member.getFirstName() + " "
				+ loanDetails.member.getMiddleName());
		wrapper.setSanctionedLoanAmount(loanDetails.getLoanAmt());
		wrapper.setLoanAmount(loanDetails.getLoanAmt());
		wrapper.setBenefitingArea(loanDetails.getBenefitingArea());
		wrapper.setPurpose(loanDetails.loanProduct.getProductName());
		wrapper.setCostOfInvestment(loanDetails.getCostOfInvestment());
		wrapper.setResolutionNo(loanDetails.getResolutionNo());
		wrapper.setResolutionDate(loanDetails.getResolutionDate());
		wrapper.setDccbLoanNo(loanDetails.getDccbLoanNo());
		wrapper.setMortgageDeedNo(loanDetails.getMortgageDeedNo());
		wrapper.setMortgageDate(loanDetails.getMortgageDate());
		wrapper.setExtentMorgage(loanDetails.getExtentMorgage());
		loanDetails.member.getLand().forEach(action -> {
			wrapper.setAssigneeOfLand(action.getAssigneeOfLand());
			wrapper.setValueOfProperty(action.getValueOfProperty());			
			wrapper.setLandAddress(action.getLandAddress());

			loanDetails.member.getLand().forEach(action1 -> {
				int len = action1.getLandAreaR().toString().length();
				double factor=0.1;
				if(len==2)
					factor=0.01;
				if (action.getLandType().contentEquals(LoanConstants.LAND_TYPE_WET)) {
					wrapper.setWet(0.0 + action1.getLandAreaHector()+ (factor * action1.getLandAreaR()));
					wrapper.setDry(0.0);

				} else {
					wrapper.setDry(0.0 + action1.getLandAreaHector()+ (factor * action1.getLandAreaR()));
					wrapper.setWet(0.0);
				}
			});
		});
	}
}

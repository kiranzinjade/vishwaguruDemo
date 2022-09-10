package com.techvg.vks.loan.reports.SocietykmpReport;

import com.techvg.vks.loan.domain.LoanDemand;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;

@Mapper(componentModel="spring")
public interface SocietyKmpMapper {

	SocietyKmpReportWrapper mapkmp(SocietyKmpReportWrapper societyKmpReportWrapper);
	
	@IterableMapping(qualifiedByName="all") 
	List<SocietyKmpReportWrapper> mapAllKMPDataList(Set<LoanDemand> loanDemandList);
	@Named("all")
	public SocietyKmpReportWrapper mapAllMemberData(LoanDemand loanDemand);
	
	
	@AfterMapping
  default void fillInProperties(final LoanDemand loanDemand, @MappingTarget final SocietyKmpReportWrapper wrapper ) {
		wrapper.setMemberId(loanDemand.getMember().getId());
		wrapper.setMemberFullName(loanDemand.getMember().getLastName()+" "+loanDemand.getMember().getMiddleName()+" "+loanDemand.getMember().getFirstName());

		wrapper.setGatNo(loanDemand.getLand().getLandGatno());

		wrapper.setCropName(loanDemand.getCrop().getCropName());
		wrapper.setCropSeason(loanDemand.getCrop().getSeason());

		wrapper.setCropAreaHector(loanDemand.getCropLandAreaHector());
		wrapper.setCropAreaR(loanDemand.getCropLandAreaR());
		wrapper.setEligibleLoanAmount(loanDemand.getAdjustedDemand());
	}
 
}

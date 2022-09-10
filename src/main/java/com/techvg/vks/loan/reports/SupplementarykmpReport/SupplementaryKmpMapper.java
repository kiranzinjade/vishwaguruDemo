package com.techvg.vks.loan.reports.SupplementarykmpReport;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.membership.reports.VoterListReport.VoterReportWrapper;

@Mapper(componentModel="spring")
public interface SupplementaryKmpMapper {

	SupplementaryKmpReportWrapper mapkmp(SupplementaryKmpReportWrapper supplementaryKmpReportWrapper);
	
 
}

package com.techvg.vks.membership.reports.MemberRegReport;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;
import com.techvg.vks.membership.domain.Member;

public interface MemberRegReportMapper {

	
	public List<MemberRegReportwrapper> mapAllDataList(List<Member> member);
	public MemberRegReportwrapper mapAllData(Member member);
	@AfterMapping
	default void fillInProperties(final Member member,
			@MappingTarget final MemberRegReportwrapper wrapper) {
	}
}

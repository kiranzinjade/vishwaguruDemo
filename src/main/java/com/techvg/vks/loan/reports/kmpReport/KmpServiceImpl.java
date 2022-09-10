package com.techvg.vks.loan.reports.kmpReport;

import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.membership.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class KmpServiceImpl implements KmpService {

	private final MemberRepository memberRepository;
	private final KmpMapper kmpMapper;
	private final KmpSocietyMapper societyKmpMapper;

	@Override
	public List<MemberKmpWrapper> getMemberKmpReport(int year) {

		List<Member> memberList = memberRepository.findByStatus("A");
		List<MemberKmpWrapper> memberKmpWrapperList = new ArrayList<>();
		memberList.forEach(action->{
			MemberKmpWrapper memberKmpWrapper = kmpMapper.mapkmp(action);
			memberKmpWrapperList.add(memberKmpWrapper);
		});
		return memberKmpWrapperList;
	}

	@Override
	public List<SocietyKmpWrapper> getSocietyKmpReport(int year) {
		List<MemberKmpWrapper> memberKmpWrapperList = getMemberKmpReport(year);
		List<SocietyKmpWrapper> societyKmpWrapperList = new ArrayList<>();

		for(MemberKmpWrapper memWrapper: memberKmpWrapperList) {
			Set<KmpReportWrapper> reportWrapperSet = memWrapper.getKmpReportWrapper();

			for (KmpReportWrapper reportWrapper : reportWrapperSet) {
				SocietyKmpWrapper societyKmpWrapper = new SocietyKmpWrapper();

				societyKmpWrapper.setMemberId(memWrapper.getMemberId());
				societyKmpWrapper.setMemberFullName(memWrapper.getMemberFullName());
				societyKmpWrapper.setJindagiPatrakNo(memWrapper.getJindagiPatrakNo());
				societyKmpWrapper.setTotalLand(memWrapper.getTotalLandArea());
				societyKmpWrapper.setSyear(year);
				societyKmpWrapper.setEyear(year+ 1);

				int len = reportWrapper.getTotalAreaR().toString().length();
				double factor=0.1;
				if(len==2)
					factor=0.01;
				societyKmpWrapper.setCropLand(reportWrapper.getTotalAreaHector() + (factor * reportWrapper.getTotalAreaR()));
				societyKmpWrapper.setCropName(reportWrapper.getCropName());
				societyKmpWrapper.setEligibleLoanAmount(reportWrapper.getMaxHectorAmount());

				societyKmpWrapperList.add(societyKmpWrapper);
			}
		}
		return societyKmpWrapperList;
	}

}



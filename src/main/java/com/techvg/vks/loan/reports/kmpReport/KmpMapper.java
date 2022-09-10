package com.techvg.vks.loan.reports.kmpReport;

import com.techvg.vks.membership.domain.Land;
import com.techvg.vks.membership.domain.MemCropReg;
import com.techvg.vks.membership.domain.Member;
import com.techvg.vks.share.domain.Shares;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.*;

@Mapper(componentModel="spring")
public interface KmpMapper {

	MemberKmpWrapper mapkmp(Member member);

	@AfterMapping
	default void fillInProperties(final Member member, @MappingTarget final MemberKmpWrapper wrapper ) {
           Set<Land> landdetails=member.getLand();
           List<Land> landList = new ArrayList<Land>();
           landList.addAll(landdetails);

		wrapper.setMemberId(member.getId());
		wrapper.setMemberFullName(member.getLastName()+" "+member.getMiddleName()+" "+member.getFirstName());
		wrapper.setMemMobileNo(member.getPhoneNumber());
		wrapper.setShareAmount(member.getShares().stream().mapToDouble(Shares::getShareAmount).sum());

		wrapper.setJindagiAmount(landList.get(0).getJindagiAmount());
		wrapper.setJindagiPatrakNo(landList.get(0).getJindagiPatrakNo());

		int year = Calendar.getInstance().get(Calendar.YEAR);
		wrapper.setSyear(year);
		wrapper.setEyear(year+ 1);

		int hector = member.getLand().stream().mapToInt(Land::getLandAreaHector).sum();
		int are = member.getLand().stream().mapToInt(Land::getLandAreaR).sum();
		int totalR = (hector*100) + are;
		double totalArea = totalR * 0.01;
		 double totalArearound = Math.round(totalArea * 100.0) / 100.0;
		wrapper.setTotalLandArea(totalArearound);

		Set<KmpReportWrapper> wrapperSet = new HashSet<>();

		Set<MemCropReg> memCropRegSet = member.getMemCropRegs();

		for(MemCropReg memCropReg: memCropRegSet){
			int len = memCropReg.getLandAreaR().toString().length();
			double factor=0.1;
			if(len==2)
				factor=0.01;

			KmpReportWrapper reportWrapper = new KmpReportWrapper();
			reportWrapper.setCropName(memCropReg.getCrop().getCropName());
			reportWrapper.setSeason(memCropReg.getSeason());
			reportWrapper.setLandType(memCropReg.getLandType());
			reportWrapper.setGatNo(memCropReg.getLandGatno());
			reportWrapper.setTotalAreaHector(memCropReg.getLandAreaHector());
			reportWrapper.setTotalAreaR(memCropReg.getLandAreaR());
			reportWrapper.setMaxHectorAmount(memCropReg.getCrop().getCropLimit()* (memCropReg.getLandAreaHector() + (factor * memCropReg.getLandAreaR())));
			wrapperSet.add(reportWrapper);
		}
		wrapper.setKmpReportWrapper(wrapperSet);
	}

}

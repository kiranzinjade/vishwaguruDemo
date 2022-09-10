package com.techvg.vks.loan.reports.kmpReport;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

@Mapper(componentModel="spring")
public interface KmpSocietyMapper {

    SocietyKmpWrapper mapkmp(MemberKmpWrapper memberKmpWrapper);

    List<SocietyKmpWrapper> mapkmpList(List<MemberKmpWrapper> memberKmpWrapper);

    @AfterMapping
    default void fillInProperties(final MemberKmpWrapper memberKmpWrapper, @MappingTarget final SocietyKmpWrapper wrapper ) {

        wrapper.setMemberId(memberKmpWrapper.getMemberId());
        wrapper.setMemberFullName(memberKmpWrapper.getMemberFullName());

        wrapper.setJindagiPatrakNo(memberKmpWrapper.getJindagiPatrakNo());
        wrapper.setTotalLand(memberKmpWrapper.getTotalLandArea());

        int year = Calendar.getInstance().get(Calendar.YEAR);
        wrapper.setSyear(year);
        wrapper.setEyear(year+ 1);

        Set<KmpReportWrapper> reportWrapperSet = memberKmpWrapper.getKmpReportWrapper();

        for(KmpReportWrapper reportWrapper: reportWrapperSet){
            wrapper.setCropName(reportWrapper.getCropName());
            int len = reportWrapper.getTotalAreaR().toString().length();
            double factor=0.1;
            if(len==2)
                factor=0.01;

 /*            KmpReportWrapper reportWrapper = new KmpReportWrapper();
            reportWrapper.setCropName(memCropReg.getCrop().getCropName());
            reportWrapper.setSeason(memCropReg.getSeason());
            reportWrapper.setLandType(memCropReg.getLandType());
            reportWrapper.setGatNo(memCropReg.getLandGatno());
            reportWrapper.setTotalAreaHector(memCropReg.getLandAreaHector());
            reportWrapper.setTotalAreaR(memCropReg.getLandAreaR());
            reportWrapper.setMaxHectorAmount(memCropReg.getCrop().getCropLimit()* (memCropReg.getLandAreaHector() + (factor * memCropReg.getLandAreaR())));
            wrapperSet.add(reportWrapper);*/
        }
        // wrapper.setKmpReportWrapper(wrapperSet);
    }

}

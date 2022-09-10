package com.techvg.vks.loan.reports.kmpReport;

import java.util.List;

public interface KmpService {
    List<MemberKmpWrapper> getMemberKmpReport(int year);
    List<SocietyKmpWrapper> getSocietyKmpReport(int year);
}
 
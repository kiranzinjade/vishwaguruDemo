package com.techvg.vks.membership.reports.MemberwiseShareReport;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;



public class MemberwiseShareWrapperPageList extends PageImpl<MemberwiseShareReportWrapper>{
	
	public MemberwiseShareWrapperPageList(List<MemberwiseShareReportWrapper> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public MemberwiseShareWrapperPageList(List<MemberwiseShareReportWrapper> content) {
        super(content);
    }


}

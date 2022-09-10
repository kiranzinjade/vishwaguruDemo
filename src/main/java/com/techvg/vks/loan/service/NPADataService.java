package com.techvg.vks.loan.service;

import com.techvg.vks.loan.model.NPADataDto;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface NPADataService {

    List<NPADataDto> getLatestNPAData();
    NPADataDto getLatestNPAData(Date npaDate);
    List<NPADataDto> generateNPAData();
    NPADataDto storeNPAData(NPADataDto npaDataDto);
}

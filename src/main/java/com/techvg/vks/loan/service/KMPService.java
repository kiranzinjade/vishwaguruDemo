package com.techvg.vks.loan.service;

import com.techvg.vks.loan.model.KMPDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface KMPService {
    KMPDto addKMP(KMPDto kmpDto);
    KMPDto getByYearAndApprovalStatus(int year, boolean status);
    KMPDto getByYearAndGenerationStatus(int year, boolean status);
    KMPDto getByKMPId(Long kmpId);
    List<KMPDto> getByApprovalStatus(boolean status);
}

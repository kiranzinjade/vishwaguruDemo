package com.techvg.vks.loan.service;

import com.techvg.vks.loan.model.KMPCropDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface KMPCropService {

    KMPCropDto addKMPCrop(KMPCropDto dto);
    List<KMPCropDto> cropsForKMPByYear(int year);
    List<KMPCropDto> cropsForKMPById(Long kmpId);
}

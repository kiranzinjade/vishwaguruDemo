package com.techvg.vks.loan.service;

import com.techvg.vks.loan.model.KMPSocietyDto;
import org.springframework.stereotype.Service;

@Service
public interface KMPSocietyService {

    KMPSocietyDto addSocietyKmp(KMPSocietyDto dto);
    KMPSocietyDto getKMPByYear(int year);
}

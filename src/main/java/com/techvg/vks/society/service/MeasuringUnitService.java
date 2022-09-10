package com.techvg.vks.society.service;

import com.techvg.vks.society.model.MeasuringUnitDto;
import com.techvg.vks.society.model.MeasuringUnitPageList;
import com.techvg.vks.society.model.ProductTypeDto;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface MeasuringUnitService {

    MeasuringUnitDto addMeasuringUnit(MeasuringUnitDto measuringUnitDto, Authentication authentication);
    MeasuringUnitDto updateMeasuringUnit(Long id, MeasuringUnitDto measuringUnitDto);
    MeasuringUnitDto getMeasuringUnitById(Long id);
    MeasuringUnitDto deleteMeasuringUnitById(Long id);
    MeasuringUnitPageList listMeasuringUnits(Pageable pageable);
    List<MeasuringUnitDto> listAllMeasuringUnits();
}

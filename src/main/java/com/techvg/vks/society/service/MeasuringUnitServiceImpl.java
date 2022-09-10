package com.techvg.vks.society.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.techvg.vks.exceptions.AlreadyExitsException;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.society.domain.MeasuringUnit;
import com.techvg.vks.society.mapper.MeasuringUnitMapper;
import com.techvg.vks.society.model.MeasuringUnitDto;
import com.techvg.vks.society.model.MeasuringUnitPageList;
import com.techvg.vks.society.repository.MeasuringUnitRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MeasuringUnitServiceImpl implements MeasuringUnitService {
    private final MeasuringUnitMapper measuringUnitMapper;
    private final MeasuringUnitRepository measuringUnitRepository;

    @Override
    public MeasuringUnitDto addMeasuringUnit(MeasuringUnitDto measuringUnitDto, Authentication authentication) {
        Optional<MeasuringUnit> measuringUnitOptional = measuringUnitRepository.findByNameAndIsDeleted(measuringUnitDto.getName(), false);
        if (measuringUnitOptional.isPresent()){
            throw new AlreadyExitsException("Measuring Unit already exists : " + measuringUnitDto.getName());
        }
        else {
            return measuringUnitMapper.toDto(measuringUnitRepository.save(measuringUnitMapper.toDomain(measuringUnitDto)));
        }
    }

    @Override
    public MeasuringUnitDto updateMeasuringUnit(Long measuringUnitId, MeasuringUnitDto measuringUnitDto) {

        MeasuringUnit measuringUnit = measuringUnitRepository.findById(measuringUnitId).orElseThrow(NotFoundException::new);
        measuringUnit = measuringUnitMapper.toDomain(measuringUnitDto);
        measuringUnit.setId(measuringUnitId);
        return measuringUnitMapper.toDto(measuringUnitRepository.save(measuringUnit));
    }

    @Override
    public MeasuringUnitDto getMeasuringUnitById(Long measuringUnitId) {

        MeasuringUnit measuringUnit = measuringUnitRepository.findById(measuringUnitId).orElseThrow(
                () -> new NotFoundException("No Measuring Unit found for Id : " +measuringUnitId));
        return measuringUnitMapper.toDto(measuringUnit);
    }

    @Override
    public MeasuringUnitDto deleteMeasuringUnitById(Long measuringUnitId) {

        MeasuringUnit measuringUnit = measuringUnitRepository.findById(measuringUnitId).orElseThrow(NotFoundException::new);
        if (measuringUnit != null) {
            measuringUnit.setIsDeleted(true);
            measuringUnitRepository.save(measuringUnit);
        }
        return measuringUnitMapper.toDto(measuringUnit);
    }

    @Override
    public MeasuringUnitPageList listMeasuringUnits(Pageable pageable) {

        Page<MeasuringUnit> measuringUnitPage;
        measuringUnitPage = measuringUnitRepository.findByisDeleted(pageable,false);

        return new MeasuringUnitPageList(measuringUnitPage
                .getContent()
                .stream()
                .map(measuringUnitMapper::toDto)
                .collect(Collectors.toList()),
                PageRequest
                        .of(measuringUnitPage.getPageable().getPageNumber(),
                                measuringUnitPage.getPageable().getPageSize()),
                measuringUnitPage.getTotalElements());
    }

	@Override
	public List<MeasuringUnitDto> listAllMeasuringUnits() {
		// TODO Auto-generated method stub
		
		return measuringUnitMapper.domainToDtoList(measuringUnitRepository.findByisDeleted(false));
	}
}

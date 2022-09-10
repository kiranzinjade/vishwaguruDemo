package com.techvg.vks.loan.service;

import com.techvg.vks.loan.mapper.KMPCropMapper;
import com.techvg.vks.loan.model.KMPCropDto;
import com.techvg.vks.loan.repository.KMPCropRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class KMPCropServiceImpl implements KMPCropService {
    private final KMPCropMapper mapper;
    private final KMPCropRepository repo;

    @Override
    public KMPCropDto addKMPCrop(KMPCropDto dto) {
        return mapper.toDto(repo.save(mapper.toDomain(dto)));
    }

    @Override
    public List<KMPCropDto> cropsForKMPByYear(int year) {
        return mapper.toDtoList(repo.findByYear(year));
    }

    @Override
    public List<KMPCropDto> cropsForKMPById(Long kmpId) {
        return mapper.toDtoList(repo.findByKmp_Id(kmpId));
    }
}

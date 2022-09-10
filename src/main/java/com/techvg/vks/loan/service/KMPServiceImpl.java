package com.techvg.vks.loan.service;

import com.techvg.vks.loan.mapper.KMPMapper;
import com.techvg.vks.loan.model.KMPDto;
import com.techvg.vks.loan.repository.KMPRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class KMPServiceImpl implements KMPService {
    private final KMPRepository repo;
    private final KMPMapper mapper;

    @Override
    public KMPDto addKMP(KMPDto kmpDto) {
        return mapper.toDto(repo.save(mapper.toDomain(kmpDto)));
    }

    @Override
    public KMPDto getByYearAndApprovalStatus(int year, boolean status) {
        return mapper.toDto(repo.findByYearAndKmpApprovalStatus(year,status));
    }

    @Override
    public KMPDto getByYearAndGenerationStatus(int year, boolean status) {
        return mapper.toDto(repo.findByYearAndKmpGenerationStatus(year,status));
    }

    @Override
    public KMPDto getByKMPId(Long kmpId) {
        return mapper.toDto(repo.getOne(kmpId));
    }

    @Override
    public List<KMPDto> getByApprovalStatus(boolean status) {
        return mapper.toDtoList(repo.findByKmpApprovalStatus(status));
    }
}

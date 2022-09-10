package com.techvg.vks.loan.service;

import com.techvg.vks.loan.mapper.KMPSocietyMapper;
import com.techvg.vks.loan.model.KMPSocietyDto;
import com.techvg.vks.loan.repository.KMPSocietyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KMPSocietyServiceImpl implements KMPSocietyService {
    private final KMPSocietyMapper mapper;
    private final KMPSocietyRepository repo;

    @Override
    public KMPSocietyDto addSocietyKmp(KMPSocietyDto dto) {
        return mapper.toDto(repo.save(mapper.toDomain(dto)));
    }

    @Override
    public KMPSocietyDto getKMPByYear(int year) {
        return mapper.toDto(repo.findByYear(year));
    }
}

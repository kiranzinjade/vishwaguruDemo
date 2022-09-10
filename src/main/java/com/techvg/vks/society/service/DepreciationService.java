package com.techvg.vks.society.service;
import org.springframework.stereotype.Service;
import com.techvg.vks.society.model.DepreciationDto;


@Service
public interface DepreciationService {

	DepreciationDto addDepreciaton(Long assetId);

	
}

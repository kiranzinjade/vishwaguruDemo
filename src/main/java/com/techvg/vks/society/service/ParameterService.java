package com.techvg.vks.society.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import com.techvg.vks.society.model.ParameterDto;
import com.techvg.vks.society.model.ParameterPageList;

public interface ParameterService {

	ParameterDto addParameter(ParameterDto parameterDto, Authentication authentication);

	ParameterPageList listParameters(Pageable pageable);

	ParameterDto deleteParameterById(Long id);

	ParameterDto updateParameter(Long id, ParameterDto parameterDto);

	ParameterDto getParameterById(Long id);


	List<ParameterDto> listAllParameters();

	List<ParameterDto> listParameter(String type);


}

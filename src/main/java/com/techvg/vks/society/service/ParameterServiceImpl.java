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
import com.techvg.vks.society.domain.Parameter;
import com.techvg.vks.society.mapper.ParameterMapper;
import com.techvg.vks.society.model.ParameterDto;
import com.techvg.vks.society.model.ParameterPageList;
import com.techvg.vks.society.repository.ParameterRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Service
@RequiredArgsConstructor
@Slf4j

public class ParameterServiceImpl implements ParameterService{
	
	private final ParameterMapper parameterMapper;
	private final ParameterRepository parameterRepository;
   

	@Override
	public ParameterDto addParameter(ParameterDto parameterDto, Authentication authentication) {
		Optional<Parameter> parameterOptional= parameterRepository.findByValueAndTypeAndIsDeleted(parameterDto.getValue(), parameterDto.getType(), false);
		if (parameterOptional.isPresent()){
			throw new AlreadyExitsException("Parameter value already exists for that type : " + parameterDto.getValue());
		}else {
		
		return parameterMapper.parameterToParameterDto(parameterRepository.save(parameterMapper.parameterDtoToParameter(parameterDto)));
	}
	}

	@Override
	public ParameterPageList listParameters(Pageable pageable) {
		Page<Parameter> parameterPage;
		parameterPage = parameterRepository.findByisDeleted(pageable,false);
       
		
		return new ParameterPageList(parameterPage
										.getContent()
										.stream()
										.map(parameterMapper::parameterToParameterDto)
										.collect(Collectors.toList()),
										PageRequest
											.of(parameterPage.getPageable().getPageNumber(),
													parameterPage.getPageable().getPageSize()),
											parameterPage.getTotalElements());

	}


	@Override
	public ParameterDto deleteParameterById(Long id) {
		Parameter parameter = parameterRepository.findById(id).orElseThrow(NotFoundException::new);
		if (parameter != null) {
			parameter.setIsDeleted(true);
			parameterRepository.save(parameter);
		}
	return parameterMapper.parameterToParameterDto(parameter);
}


	@Override
	public ParameterDto updateParameter(Long id, ParameterDto parameterDto) {
		Parameter parameter = parameterRepository.findById(id).orElseThrow(NotFoundException::new);
		parameterDto.setId(parameter.getId());	
		Parameter parameter1 = parameterMapper.parameterDtoToParameter(parameterDto);
		return parameterMapper.parameterToParameterDto(parameterRepository.save(parameter1));
	}


	@Override
	public ParameterDto getParameterById(Long id) {
		log.debug("REST request to get Parameter : {}", id);
		Parameter parameter = parameterRepository.findById(id).orElseThrow(
				() -> new NotFoundException("No parameter Setting found for Id : " +id));

		return parameterMapper.parameterToParameterDto(parameter);
	}


	@Override

	public List<ParameterDto> listAllParameters() {
		return parameterMapper.domainToDtoList(parameterRepository.findByisDeleted(false));
	}
	
	
	@Override
	public List<ParameterDto> listParameter(String type) {
		return parameterMapper.domainToDtoList(parameterRepository.findByTypeAndIsDeleted(type,false));

	}



}

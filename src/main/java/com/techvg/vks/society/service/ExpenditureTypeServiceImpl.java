
package com.techvg.vks.society.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.techvg.vks.accounts.model.VoucherTypeDto;
import com.techvg.vks.exceptions.AlreadyExitsException;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.society.domain.ExpenditureType;
import com.techvg.vks.society.mapper.ExpenditureTypeMapper;
import com.techvg.vks.society.model.ExpenditureTypeDto;
import com.techvg.vks.society.model.ExpenditureTypePageList;
import com.techvg.vks.society.repository.ExpenditureTypeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExpenditureTypeServiceImpl implements ExpenditureTypeService {
	
	private final ExpenditureTypeMapper expenditureTypeMapper;
    private final ExpenditureTypeRepository expenditureTypeRepository;

    @Override
	public List<ExpenditureTypeDto> listExpenditure() {
		// TODO Auto-generated method stub
		return expenditureTypeMapper.domainToDtoList(expenditureTypeRepository.findAll());
	}

    @Override
	public ExpenditureTypeDto addExpenditureType(ExpenditureTypeDto expenditureTypeDto, Authentication authentication) {
		log.debug("REST request to save Expenditure Type : {}", expenditureTypeDto);
		Optional<ExpenditureType> expenditureTypeOptional = expenditureTypeRepository.findByExpenditureTypeAndIsDeleted(expenditureTypeDto.getExpenditureType(), false);
		if (expenditureTypeOptional.isPresent()) {
			throw new AlreadyExitsException(
					"Expenditure Type already exists : " + expenditureTypeDto.getExpenditureType());
		} else {

			return expenditureTypeMapper.expenditureTypeToExpenditureTypeDto(expenditureTypeRepository
					.save(expenditureTypeMapper.expenditureTypeDtoToExpenditureType(expenditureTypeDto)));

		}
	}



	@Override
	public ExpenditureTypePageList listExpenditureType(Pageable pageable) {

		log.debug("REST request to save Expenditure Type : {}");
		
		Page<ExpenditureType> expenditureTypePage;
		expenditureTypePage = expenditureTypeRepository.findByisDeleted(pageable,false);
       
		
		return new ExpenditureTypePageList(expenditureTypePage
				.getContent()
				.stream()
				.map(expenditureTypeMapper::expenditureTypeToExpenditureTypeDto)
				.collect(Collectors.toList()),
				PageRequest
					.of(expenditureTypePage.getPageable().getPageNumber(),
							expenditureTypePage.getPageable().getPageSize()),
				expenditureTypePage.getTotalElements());

	}

	@Override
	public ExpenditureTypeDto deleteExpenditureTypeById(Long id) {
		ExpenditureType expenditureType = expenditureTypeRepository.findById(id).orElseThrow(NotFoundException::new);
		if(expenditureType != null) {
			expenditureType.setIsDeleted(true);
			expenditureTypeRepository.save(expenditureType);
		}
		  return expenditureTypeMapper.expenditureTypeToExpenditureTypeDto(expenditureType);
	}

	@Override
	public ExpenditureTypeDto updateExpenditureType(Long id, ExpenditureTypeDto expenditureTypeDto) {
		 ExpenditureType expenditureType =expenditureTypeRepository.findById(id).orElseThrow(NotFoundException::new);
		 expenditureTypeDto.setId(expenditureType.getId());	
		 ExpenditureType expenditureType1 =expenditureTypeMapper.expenditureTypeDtoToExpenditureType(expenditureTypeDto);
		return expenditureTypeMapper.expenditureTypeToExpenditureTypeDto(expenditureTypeRepository.save(expenditureType1));

	}

	@Override
	public ExpenditureTypeDto getExpenditureTypeById(Long id) {
		log.debug("REST request to get Expenditure Type : {}", id);
		ExpenditureType expenditureType =expenditureTypeRepository.findById(id).orElseThrow(
				() -> new NotFoundException("No Expenditure Setting found for Id : " +id));

		return expenditureTypeMapper.expenditureTypeToExpenditureTypeDto(expenditureType);
	}

	
}

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
import com.techvg.vks.society.domain.Agm;
import com.techvg.vks.society.domain.DepositType;
import com.techvg.vks.society.mapper.DepositTypeMapper;
import com.techvg.vks.society.model.DepositTypeDto;
import com.techvg.vks.society.model.DepositTypePageList;
import com.techvg.vks.society.repository.DepositTypeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j

public class DepositTypeServiceImpl implements DepositTypeService{
	
	private final DepositTypeMapper depositTypeMapper;
	private final DepositTypeRepository depositTypeRepository;
   
	
	@Override
	
	public DepositTypeDto addDepositType(DepositTypeDto depositTypeDto, Authentication authentication) {
		Optional<DepositType> depositType = depositTypeRepository.findByAccountTypeAndIsDeleted(depositTypeDto.getAccountType(), false);

		if (depositType.isPresent()) {
			throw new AlreadyExitsException("Account Type already exists : " + depositTypeDto.getAccountType());
		}
		log.debug("REST request to save DepositType : {}", depositTypeDto);
		return depositTypeMapper.DepositTypeToDepositTypeDto(depositTypeRepository.save(depositTypeMapper.DepositTypeDtoToDepositType(depositTypeDto)));
	}

	
	@Override
	public DepositTypePageList listDepositType(Pageable pageable) {
		log.debug("Request to get DepositType : {}");

		Page<DepositType> depositTypePage;
		depositTypePage = depositTypeRepository.findByisDeleted(pageable,false);
       
		
		return new DepositTypePageList(depositTypePage
										.getContent()
										.stream()
										.map(depositTypeMapper::DepositTypeToDepositTypeDto)
										.collect(Collectors.toList()),
										PageRequest
											.of(depositTypePage.getPageable().getPageNumber(),
													depositTypePage.getPageable().getPageSize()),
				depositTypePage.getTotalElements());

	}

	@Override
	public DepositTypeDto deleteDepositTypeById(Long id) {
		DepositType depositType = depositTypeRepository.findById(id).orElseThrow(NotFoundException::new);
		if (depositType != null) {
			depositType.setIsDeleted(true);
			depositTypeRepository.save(depositType);
		}
	return depositTypeMapper.DepositTypeToDepositTypeDto(depositType);
}

	@Override
	public DepositTypeDto updateDepositType(Long id, DepositTypeDto depositTypeDto) {
		DepositType depositType = depositTypeRepository.findById(id).orElseThrow(NotFoundException::new);
		depositTypeDto.setId(depositType.getId());	
		DepositType depositType1 = depositTypeMapper.DepositTypeDtoToDepositType(depositTypeDto);
		return depositTypeMapper.DepositTypeToDepositTypeDto(depositTypeRepository.save(depositType1));
	}

	@Override
	public DepositTypeDto getDepositTypeById(Long id) {
		log.debug("REST request to get DepositType : {}", id);
		DepositType depositType = depositTypeRepository.findById(id).orElseThrow(
				() -> new NotFoundException("No Npa Setting found for Id : " +id));

		return depositTypeMapper.DepositTypeToDepositTypeDto(depositType);
	}
	
	public List<DepositTypeDto> listDepositType() {
		// TODO Auto-generated method stub
		return depositTypeMapper.domainToDtoList(depositTypeRepository.findByisDeleted(false));
	}



	

}

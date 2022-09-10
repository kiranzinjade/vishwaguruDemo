package com.techvg.vks.society.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.techvg.vks.exceptions.AlreadyExitsException;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.society.domain.ExpenditureType;
import com.techvg.vks.society.domain.ProfitsAppropriation;
import com.techvg.vks.society.mapper.ProfitsAppropriationMapper;
import com.techvg.vks.society.model.ProfitsAppropriationDto;
import com.techvg.vks.society.model.ProfitsAppropriationPageList;
import com.techvg.vks.society.repository.ProfitsAppropriationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Service
@RequiredArgsConstructor
@Slf4j
public class ProfitsAppropriationServiceImpl implements ProfitsAppropriationService {
	
	
private final ProfitsAppropriationMapper profitAppMapper;
private final ProfitsAppropriationRepository profitAppRepository;


@Override
public ProfitsAppropriationDto addProfitApp(ProfitsAppropriationDto profitAppDto, Authentication authentication) {
	Optional<ProfitsAppropriation> yearOptional = profitAppRepository.findByYearAndIsDeleted(profitAppDto.getYear(), false);
	if (yearOptional.isPresent()) {
		throw new AlreadyExitsException(
				"Year already exists : " + profitAppDto.getYear());
	}else {
	
	return profitAppMapper.toDto(profitAppRepository.save(profitAppMapper.toDomain(profitAppDto)));
	}
}


@Override
public ProfitsAppropriationDto updateProfitApp(Long id, ProfitsAppropriationDto profitAppDto) {
	ProfitsAppropriation profapp= profitAppRepository.findById(id).orElseThrow( () -> new NotFoundException("Cannot Update for Id:" +id));
	profitAppDto.setId(profapp.getId());	
	ProfitsAppropriation profitApp1 = profitAppMapper.toDomain(profitAppDto);
	return profitAppMapper.toDto(profitAppRepository.save(profitApp1));
	
	
	
}


@Override
public ProfitsAppropriationDto deleteProfitAppById(Long id) {
	ProfitsAppropriation profApp = profitAppRepository.findById(id).orElseThrow(NotFoundException::new);
	if (profApp != null) {
		profApp.setIsDeleted(true);
		profitAppRepository.save(profApp);
	}
return profitAppMapper.toDto(profApp);
}


@Override
public ProfitsAppropriationPageList listProfitApp(Pageable pageable) {
	Page<ProfitsAppropriation> profitappPage;
	profitappPage = profitAppRepository.findByisDeleted(pageable,false);
   
	
	return new ProfitsAppropriationPageList(profitappPage
									.getContent()
									.stream()
									.map(profitAppMapper::toDto)
									.collect(Collectors.toList()),
									PageRequest
										.of(profitappPage.getPageable().getPageNumber(),
												profitappPage.getPageable().getPageSize()),
										profitappPage.getTotalElements());
}


@Override
public ProfitsAppropriationDto getProfitAppById(Long id) {
	ProfitsAppropriation profitapp = profitAppRepository.findById(id).orElseThrow(
			() -> new NotFoundException("No Profit Appropriation found for Id : " +id));

	return profitAppMapper.toDto(profitapp);
}


//@Override
//public List<ProfitsAppropriationDto> listAllProfitApp() {
	
	//return profitAppMapper.toDtoList(profitAppRepository.findByisDeleted(false));
	
//}



} 






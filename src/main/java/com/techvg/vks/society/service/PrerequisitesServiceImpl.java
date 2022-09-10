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
import com.techvg.vks.society.domain.Prerequisites;
import com.techvg.vks.society.mapper.PrerequisitesMapper;

import com.techvg.vks.society.model.PrerequisitesDto;
import com.techvg.vks.society.model.PrerequisitesPageList;
import com.techvg.vks.society.repository.PrerequisitesRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j


public class PrerequisitesServiceImpl implements PrerequisitesService{
	
	private final PrerequisitesMapper prerequisitesMapper;
	private final PrerequisitesRepository prerequisitesRepository;
   
	
	@Override
	
	public PrerequisitesDto addPrerequisite(PrerequisitesDto prerequisitesDto, Authentication authentication) {
		log.debug("REST request to save Prerequisite : {}", prerequisitesDto);
		Optional<Prerequisites> prerequisites = prerequisitesRepository.findByDocumentNameAndIsDeleted(prerequisitesDto.getDocumentName(),false);

		 if (prerequisites.isPresent()){
				throw new AlreadyExitsException("Document already exists with Name : " + prerequisitesDto.getDocumentName());
			}
		return prerequisitesMapper.prerequisitesToPrerequisitesDto(prerequisitesRepository.save(prerequisitesMapper.prerequisitesDtoToPrerequisites(prerequisitesDto)));
	}

	
	@Override
	public PrerequisitesPageList listPrerequisites(Pageable pageable) {
		log.debug("Request to get Prerequisite : {}");

		Page<Prerequisites> prerequisitesPage;
		prerequisitesPage = prerequisitesRepository.findByisDeleted(pageable,false);
       
		
		return new PrerequisitesPageList(prerequisitesPage
										.getContent()
										.stream()
										.map(prerequisitesMapper::prerequisitesToPrerequisitesDto)
										.collect(Collectors.toList()),
										PageRequest
											.of(prerequisitesPage.getPageable().getPageNumber(),
													prerequisitesPage.getPageable().getPageSize()),
													prerequisitesPage.getTotalElements());

	}

	@Override
	public PrerequisitesDto deletePrerequisiteById(Long id) {
		Prerequisites prerequisite = prerequisitesRepository.findById(id).orElseThrow(NotFoundException::new);
		if (prerequisite != null) {
			prerequisite.setIsDeleted(true);
			prerequisitesRepository.save(prerequisite);
		}
	return prerequisitesMapper.prerequisitesToPrerequisitesDto(prerequisite);
}

	@Override
	public PrerequisitesDto updatePrereqisite(Long id, PrerequisitesDto prerequisitesDto) {
		Prerequisites prerequisite = prerequisitesRepository.findById(id).orElseThrow(NotFoundException::new);
		prerequisitesDto.setId(prerequisite.getId());	
		Prerequisites prerequisite1 = prerequisitesMapper.prerequisitesDtoToPrerequisites(prerequisitesDto);
		return prerequisitesMapper.prerequisitesToPrerequisitesDto(prerequisitesRepository.save(prerequisite1));
	}

	@Override
	public PrerequisitesDto getPrerequisiteById(Long id) {
		log.debug("REST request to get Prerequisite : {}", id);
		Prerequisites prerequisite = prerequisitesRepository.findById(id).orElseThrow(
				() -> new NotFoundException("No Npa Setting found for Id : " +id));

		return prerequisitesMapper.prerequisitesToPrerequisitesDto(prerequisite);
	}

}

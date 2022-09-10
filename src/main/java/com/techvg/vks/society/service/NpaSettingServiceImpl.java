package com.techvg.vks.society.service;

import com.techvg.vks.exceptions.AlreadyExitsException;
import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.society.domain.NpaSetting;
import com.techvg.vks.society.mapper.NpaSettingMapper;
import com.techvg.vks.society.model.NpaSettingDto;
import com.techvg.vks.society.model.NpaSettingPageList;
import com.techvg.vks.society.repository.NpaSettingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class NpaSettingServiceImpl implements NpaSettingService{
	
	private final NpaSettingMapper npaSettingMapper;
	private final NpaSettingRepository npaSettingRepository;
	
	@Override
	public NpaSettingDto addNpaSetting(NpaSettingDto npaSettingDto, Authentication authentication) {
		Optional<NpaSetting>npaSettingOptional=npaSettingRepository.findByClassificationAndIsDeleted(npaSettingDto.getClassification(),false);
		if(npaSettingOptional.isPresent()) {
			throw new AlreadyExitsException("NPA classification already exists : "+npaSettingDto.getClassification());
		}
		else {
		log.debug("REST request to save Npa Setting : {}", npaSettingDto);
		return npaSettingMapper.npaSettingToNpaSettingDto(npaSettingRepository.save(npaSettingMapper.npaSettingDtoToNpaSetting(npaSettingDto)));
		}
	}

	@Override
	public NpaSettingPageList listNpaSettings(Pageable pageable) {
		log.debug("Request to get Npa Setting : {}");

		Page<NpaSetting> npaSettingPage;
		npaSettingPage = npaSettingRepository.findByisDeleted(pageable,false);

		return new NpaSettingPageList(npaSettingPage
										.getContent()
										.stream()
										.map(npaSettingMapper::npaSettingToNpaSettingDto)
										.collect(Collectors.toList()),
										PageRequest
											.of(npaSettingPage.getPageable().getPageNumber(),
													npaSettingPage.getPageable().getPageSize()),
											npaSettingPage.getTotalElements());

	}

	@Override
	public NpaSettingDto deleteNpaSettingById(Long id) {
		NpaSetting npaSetting = npaSettingRepository.findById(id).orElseThrow(NotFoundException::new);
		if (npaSetting != null) {
			npaSetting.setIsDeleted(true);
			npaSettingRepository.save(npaSetting);
		}
	return npaSettingMapper.npaSettingToNpaSettingDto(npaSetting);
}

	@Override
	public NpaSettingDto updateNpaSetting(Long id, NpaSettingDto npaSettingDto) {
		NpaSetting npaSetting = npaSettingRepository.findById(id).orElseThrow(NotFoundException::new);
		npaSettingDto.setId(npaSetting.getId());	
		Optional<NpaSetting>npaSettingOptional1=npaSettingRepository.findByClassificationAndIsDeleted(npaSettingDto.getClassification(),false);
		Optional<NpaSetting>npaSettingOptional=npaSettingRepository.findByClassificationAndDurationStartAndDurationEndAndIsDeleted(npaSettingDto.getClassification(),npaSettingDto.getDurationStart(),npaSettingDto.getDurationEnd(),false);
		
		if(npaSettingOptional.isPresent() && npaSettingOptional.get().getId()!=npaSetting.getId()) {
			throw new AlreadyExitsException("NPA classification already exists for this year : "+npaSettingDto.getClassification());
		}
		else if(npaSettingOptional1.isPresent() && npaSettingOptional1.get().getId()!=npaSetting.getId()) {
			throw new AlreadyExitsException("NPA classification already exists : "+npaSettingDto.getClassification());
		}
		else {
		NpaSetting npaSetting1 = npaSettingMapper.npaSettingDtoToNpaSetting(npaSettingDto);
		return npaSettingMapper.npaSettingToNpaSettingDto(npaSettingRepository.save(npaSetting1));
		}
	}

	@Override
	public NpaSettingDto getNpaSettingById(Long id) {
		log.debug("REST request to get Npa Setting : {}", id);
		NpaSetting npaSetting = npaSettingRepository.findById(id).orElseThrow(
				() -> new NotFoundException("No Npa Setting found for Id : " +id));

		return npaSettingMapper.npaSettingToNpaSettingDto(npaSetting);
	}

	@Override
	public List<NpaSettingDto> listAllNpa() {
		
		return npaSettingMapper.domainToDtoList(npaSettingRepository.findByisDeleted(false));
	}
	
	
}



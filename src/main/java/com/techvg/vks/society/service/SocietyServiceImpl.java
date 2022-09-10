package com.techvg.vks.society.service;

import com.techvg.vks.exceptions.NotFoundException;
import com.techvg.vks.society.domain.Society;
import com.techvg.vks.society.mapper.SocietyMapper;
import com.techvg.vks.society.model.SocietyDto;
import com.techvg.vks.society.repository.SocietyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SocietyServiceImpl implements SocietyService {

	private final SocietyRepository societyRepository;
	private final SocietyMapper societyMapper;

	@Override
	public SocietyDto addSociety(SocietyDto societyDto, MultipartFile imgFile) {
		Society society = societyMapper.societyDtoToSociety(societyDto);
		try {
			society.setFileData(imgFile.getBytes());
			society.setFileName(imgFile.getOriginalFilename());
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return societyMapper.societyToSocietyDto(societyRepository.save(society));
	}

	@Override
	public SocietyDto updateSociety(SocietyDto societyDto, Long id) {

		Society society = societyRepository.findById(id).orElseThrow(NotFoundException::new);

		societyDto.setId(society.getId());

		Society society1 = societyMapper.societyDtoToSociety(societyDto);

//		try {
//			society1.setFileData(societyDto.getFile().getBytes());
//			society1.setFileName(societyDto.getFile().getOriginalFilename());
//			
//
//		} catch (IOException ex) {
//			ex.printStackTrace();
//		}
		return societyMapper.societyToSocietyDto(societyRepository.save(society1));

	}


	@Override
	public SocietyDto deleteSocietyById(Long id) {
		Society society = societyRepository.findById(id).orElseThrow(NotFoundException::new);
		if (society != null) {
			society.setIsDeleted(true);
			societyRepository.save(society);
		}
		return societyMapper.societyToSocietyDto(society);
	}

	@Override
	public SocietyDto getSocietyById(Long id) {

		log.debug("REST request to getSociety : {}", id);
		Society society = societyRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("No House found for houseId : " + id));
		return societyMapper.societyToSocietyDto(society);
	}


	@Override
	public List<SocietyDto> listSocietyDetails() {
		// TODO Auto-generated method stub
		return societyMapper.domainToDtoList(societyRepository.findAll());

	}

	@Override
	public SocietyDto updateSocietyy(Long id, SocietyDto societyDto, MultipartFile imgFile) {
		
		System.out.println("idddd"+id);
		
		Society society = societyRepository.findById(id).orElseThrow(NotFoundException::new);
		societyDto.setId(society.getId());
		Society society1 = societyMapper.societyDtoToSociety(societyDto);
		try {
			society1.setId(id);
			society1.setFileData(imgFile.getBytes());
			society1.setFileName(imgFile.getOriginalFilename());
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return societyMapper.societyToSocietyDto(societyRepository.save(society1));
	}

}

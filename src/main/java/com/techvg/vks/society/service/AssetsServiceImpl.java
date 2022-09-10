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
import com.techvg.vks.society.domain.Assets;
import com.techvg.vks.society.mapper.AssetsMapper;
import com.techvg.vks.society.model.AssetsDto;
import com.techvg.vks.society.model.AssetsPageList;
import com.techvg.vks.society.repository.AssetsRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AssetsServiceImpl implements AssetsService{
	
	private final AssetsMapper assetsMapper;
	private final AssetsRepository assetsRepository;
	@Override
	public AssetsDto addAssets(AssetsDto assetsDto, Authentication authentication) {
		log.debug("REST request to save Assets : {}", assetsDto);
		Optional<Assets> assets = assetsRepository.findByAssetsNameAndIsDeleted(assetsDto.getAssetsName(),false);
		if (assets.isPresent()) {
			throw new AlreadyExitsException(
					"Asset is already exists with Asset Name : " + assetsDto.getAssetsName());
		} else {
		return assetsMapper.assetsToAssetsDto(assetsRepository.save(assetsMapper.assetsDtoToAssets(assetsDto)));
	}
	}
	@Override
	public AssetsPageList listAssets(Pageable pageable) {
		log.debug("Request to get Assets : {}");

		Page<Assets> assetsPage;
		assetsPage = assetsRepository.findByisDeleted(pageable,false);

		return new AssetsPageList(assetsPage
										.getContent()
										.stream()
										.map(assetsMapper::assetsToAssetsDto)
										.collect(Collectors.toList()),
										PageRequest
											.of(assetsPage.getPageable().getPageNumber(),
													assetsPage.getPageable().getPageSize()),
											assetsPage.getTotalElements());

	}

	@Override
	public AssetsDto deleteAssetsById(long id) {
		Assets assets = assetsRepository.findById(id).orElseThrow(
				() -> new NotFoundException("No Asset found for Id " + id));
		if (assets != null) {
			assets.setIsDeleted(true);
			assetsRepository.save(assets);
		}
	return assetsMapper.assetsToAssetsDto(assets);
}

	@Override
	public AssetsDto updateAssets(Long id, AssetsDto assetsDto) {
		Assets assets = assetsRepository.findById(id).orElseThrow(
				() -> new NotFoundException("No Asset found for Id " + id));
		assetsDto.setId(assets.getId());	
		Assets assets1 = assetsMapper.assetsDtoToAssets(assetsDto);
		return assetsMapper.assetsToAssetsDto(assetsRepository.save(assets1));
	}

	@Override
	public AssetsDto getAssetsById(long id) {
		log.debug("REST request to get Assets : {}", id);
		Assets assets = assetsRepository.findById(id).orElseThrow(
				() -> new NotFoundException("No Assets found for Id : " +id));

		return assetsMapper.assetsToAssetsDto(assets);
	}
	@Override
	public List<AssetsDto> listAssets() {
		// TODO Auto-generated method stub
		return assetsMapper.domainToDtoList(assetsRepository.findAll());
	}

	}




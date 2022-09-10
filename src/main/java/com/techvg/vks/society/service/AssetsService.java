package com.techvg.vks.society.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import com.techvg.vks.society.model.AssetsDto;
import com.techvg.vks.society.model.AssetsPageList;

public interface AssetsService {

	AssetsDto addAssets(AssetsDto assetsDto, Authentication authentication);

	AssetsPageList listAssets(Pageable pageable);

	AssetsDto deleteAssetsById(long id);

	AssetsDto updateAssets(Long id, AssetsDto assetsDto);

	AssetsDto getAssetsById(long id);

	List<AssetsDto> listAssets();

}

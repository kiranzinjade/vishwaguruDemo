package com.techvg.vks.society.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.techvg.vks.society.domain.Assets;
import com.techvg.vks.society.model.AssetsDto;

@Mapper(componentModel = "spring")

public interface AssetsMapper {

	AssetsDto assetsToAssetsDto(Assets assets);
	Assets assetsDtoToAssets(AssetsDto assetsDto);
	List<AssetsDto> domainToDtoList(List<Assets> findAll);
}

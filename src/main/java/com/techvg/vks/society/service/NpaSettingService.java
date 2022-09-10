package com.techvg.vks.society.service;

import com.techvg.vks.society.model.NpaSettingDto;
import com.techvg.vks.society.model.NpaSettingPageList;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service

public interface NpaSettingService {

	NpaSettingDto addNpaSetting(NpaSettingDto npaSettingDto, Authentication authentication);

	NpaSettingPageList listNpaSettings(Pageable pageable);

	NpaSettingDto deleteNpaSettingById(Long id);

	NpaSettingDto updateNpaSetting(Long id, NpaSettingDto npaSettingDto);

	NpaSettingDto getNpaSettingById(Long id);

	List<NpaSettingDto> listAllNpa();

}

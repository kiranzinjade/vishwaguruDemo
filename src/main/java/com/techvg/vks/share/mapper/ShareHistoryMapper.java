package com.techvg.vks.share.mapper;

import org.mapstruct.Mapper;

import com.techvg.vks.share.domain.ShareHistory;
import com.techvg.vks.share.model.ShareHistoryDto;

@Mapper(componentModel = "spring")

public interface ShareHistoryMapper {

	ShareHistoryDto shareHistoryToShareHistoryDto(ShareHistory shareHistory);
	ShareHistory shareHistoryDtoToShareHistory(ShareHistoryDto shareHistoryDto);
}

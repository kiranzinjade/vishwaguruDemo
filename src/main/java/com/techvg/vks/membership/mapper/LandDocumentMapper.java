package com.techvg.vks.membership.mapper;

import org.mapstruct.Mapper;
import com.techvg.vks.membership.domain.LandDocument;
import com.techvg.vks.membership.model.LandDocumentDto;

@Mapper(componentModel = "spring")
public interface LandDocumentMapper {
	LandDocumentDto landDocumentToLandDocumentDto(LandDocument landDocument);
	LandDocument landDocumentDtoToLandDocument(LandDocumentDto landDocumentDto);
    
}
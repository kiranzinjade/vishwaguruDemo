package com.techvg.vks.membership.mapper;

import org.mapstruct.Mapper;

import com.techvg.vks.membership.domain.Document;
import com.techvg.vks.membership.model.DocumentDto;

@Mapper(componentModel = "spring")
public interface DocumentMapper {

	DocumentDto documentToDocumentDto(Document document);

	Document DocumentDtoToDocument(DocumentDto documentDto);

}

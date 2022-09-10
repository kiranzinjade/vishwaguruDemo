package com.techvg.vks.accounts.mapper;

import com.techvg.vks.accounts.domain.Journal;
import com.techvg.vks.accounts.model.JournalDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JournalMapper {
    Journal toDomain(JournalDto dto);
    JournalDto toDto(Journal domain);
}

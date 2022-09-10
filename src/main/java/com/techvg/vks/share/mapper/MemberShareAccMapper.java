package com.techvg.vks.share.mapper;

import com.techvg.vks.share.domain.MemberShareAcc;
import com.techvg.vks.share.model.MemberShareAccDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberShareAccMapper {

    MemberShareAcc toDomain(MemberShareAccDto dto);
    MemberShareAccDto toDto(MemberShareAcc domain);
}

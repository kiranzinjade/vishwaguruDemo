package com.techvg.vks.user.mapper;

import com.techvg.vks.user.bo.User;
import com.techvg.vks.user.model.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toDomain(UserDto dto);
    UserDto toDto(User domain);
}

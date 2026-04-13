package com.allica.user.persistence.entity.mapper;

import com.allica.user.dto.UserDTO;
import com.allica.user.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserEntityMapper extends EntityMapper<UserDTO, UserEntity> {}

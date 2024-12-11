package kr.ac.kku.cs.wp.wsd.user.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import kr.ac.kku.cs.wp.wsd.user.dto.UserDTO;
import kr.ac.kku.cs.wp.wsd.user.entity.User;

/**
 * UserMapper
 * 
 * @author kimjunwoo
 * @since 2024. 12. 11.
 * @version 1.0
 */
@Mapper(componentModel = SPRING, uses = { UserRoleMapper.class })
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "userRoles", source = "userRoles")
    User toEntity(UserDTO userDTO);
    
}
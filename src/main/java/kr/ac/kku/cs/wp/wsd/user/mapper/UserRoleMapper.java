package kr.ac.kku.cs.wp.wsd.user.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING; 
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import kr.ac.kku.cs.wp.wsd.user.dto.UserRoleDTO;
import kr.ac.kku.cs.wp.wsd.user.entity.UserRole;

/**
 * UserRoleMapper
 * 
 * @author kimjunwoo
 * @since 2024. 12. 11.
 * @version 1.0
 */
@Mapper(componentModel = SPRING)
public interface UserRoleMapper {
    UserRoleMapper INSTANCE = Mappers.getMapper(UserRoleMapper.class);

    @Mapping(target = "id.userId", source = "userId")
    @Mapping(target = "id.roleId", source = "roleId")
    @Mapping(target = "roleName", source = "roleName")
    UserRole toEntity(UserRoleDTO userRoleDTO);
    
}
package com.huypq.web.admin.mapper;

import com.huypq.web.admin.domain.User;
import com.huypq.web.admin.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface UserMapper extends EntityMapper<UserDTO, User> {
}

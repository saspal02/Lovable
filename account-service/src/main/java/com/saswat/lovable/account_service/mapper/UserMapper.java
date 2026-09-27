package com.saswat.lovable.account_service.mapper;

import com.saswat.lovable.account_service.dto.auth.SignupRequest;
import com.saswat.lovable.account_service.dto.auth.UserProfileResponse;
import com.saswat.lovable.account_service.entity.User;
import com.saswat.lovable.common_lib.dto.UserDto;
import com.saswat.lovable.common_lib.security.JwtUserPrincipal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(SignupRequest signupRequest);

    @Mapping(source = "userId", target = "id")
    UserProfileResponse toUserProfileResponse(JwtUserPrincipal user);

    UserDto toUserDto(User user);

}

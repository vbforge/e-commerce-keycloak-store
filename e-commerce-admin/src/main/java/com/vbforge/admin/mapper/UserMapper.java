package com.vbforge.admin.mapper;

import com.vbforge.admin.dto.UserDto;
import com.vbforge.admin.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    public UserDto toDto(User user, long orderCount) {
        if (user == null) return null;
        var roles = user.getRoles().stream()
            .map(r -> r.getName().name().replace("ROLE_", ""))
            .sorted()
            .toList();
        return UserDto.builder()
            .id(user.getId())
            .username(user.getUsername())
            .email(user.getEmail())
            .enabled(user.getEnabled())
            .createdAt(user.getCreatedAt())
            .roles(roles)
            .orderCount(orderCount)
            .build();
    }

    public List<UserDto> toDtoList(List<User> users, java.util.function.Function<User, Long> orderCountFn) {
        return users.stream().map(u -> toDto(u, orderCountFn.apply(u))).toList();
    }
}
package org.example.mapper;

import org.example.model.DTO.userDTO.AuthUserCreateDto;
import org.example.model.DTO.userDTO.AuthUserDto;
import org.example.model.DTO.userDTO.AuthUserUpdateDto;
import org.example.model.entity.AuthUser;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class AuthUserMapper {
    public AuthUser fromCreateDto(AuthUserCreateDto dto) {
        AuthUser authUser = new AuthUser();

        authUser.setPhone(dto.getPhone());
        authUser.setEmail(dto.getEmail());
        authUser.setUsername(dto.getUsername());
        authUser.setBirthDate(dto.getBirthDate());
        authUser.setFullName(dto.getFullName());
        authUser.setId(UUID.randomUUID().toString().replace("-", ""));
        authUser.setCreatedAt(LocalDateTime.now());
        authUser.setIsAdmin(false);
        authUser.setPassword(dto.getPassword());
        return authUser;
    }

    public AuthUser fromUpdateDto(AuthUserUpdateDto dto) {
        AuthUser authUser = new AuthUser();

        authUser.setPhone(dto.getPhone());
        authUser.setEmail(dto.getEmail());
        authUser.setUsername(dto.getUsername());
        authUser.setBirthDate(dto.getBirthDate());
        authUser.setFullName(dto.getFullName());
        authUser.setId(dto.getId());
        authUser.setCreatedAt(LocalDateTime.now());
        authUser.setIsAdmin(false);
        authUser.setPassword(dto.getPassword());
        return authUser;
    }

    public List<AuthUserDto> toDto(List<AuthUser> users) {
        return users.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public AuthUserDto toDto(AuthUser users) {
        return AuthUserDto.builder()
                .id(users.getId())
                .phone(users.getPhone())
                .username(users.getUsername())
                .email(users.getEmail())
                .birthDate(users.getBirthDate())
                .fullName(users.getFullName())
                .build();
    }
}

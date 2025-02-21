package org.example.service;

import jakarta.servlet.http.HttpSession;
import org.example.dao.AuthUserDao;
import org.example.mapper.AuthUserMapper;
import org.example.model.DTO.userDTO.AuthUserCreateDto;
import org.example.model.DTO.userDTO.AuthUserDto;
import org.example.model.DTO.userDTO.AuthUserUpdateDto;
import org.example.model.entity.AuthUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthUserService implements AbstractCrudService<AuthUserDto, AuthUser, AuthUserUpdateDto, String, String> {
    private final AuthUserDao dao;
    private final AuthUserMapper mapper;

    public AuthUserService(AuthUserDao dao, AuthUserMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @Override
    public void create(AuthUser authUser, String userId) {
        dao.save(authUser);
    }

    @Override
    public void update(AuthUserUpdateDto dto, String userId) {
        AuthUser authUser = mapper.fromUpdateDto(dto);
        dao.update(authUser);
    }

    @Override
    public AuthUserDto get(String id) {
        return dao.get(id);
    }

    @Override
    public List<AuthUserDto> getAll() {
        List<AuthUser> users = dao.findAll();
        return mapper.toDto(users);
    }

    @Override
    public void delete(String id) {
        dao.delete(id);
    }

    @Override
    public List<AuthUserDto> getById(String id) {
        return List.of();
    }
}

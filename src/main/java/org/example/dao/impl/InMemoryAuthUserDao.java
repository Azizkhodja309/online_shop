package org.example.dao.impl;

import org.example.dao.AuthUserDao;
import org.example.model.DTO.userDTO.AuthUserDto;
import org.example.model.entity.AuthUser;

import java.util.ArrayList;
import java.util.List;

//@Component
public class InMemoryAuthUserDao implements AuthUserDao {

    private final List<AuthUser> AUTH_USERS = new ArrayList<>();

    @Override
    public void save(AuthUser authUser) {
        AUTH_USERS.add(authUser);
    }

    @Override
    public void update(AuthUser authUser) {

    }

    @Override
    public void delete(String id) {
        AUTH_USERS.removeIf(authUser -> authUser.getId().equals(id));
    }

    @Override
    public List<AuthUser> findAll() {
        return AUTH_USERS;
    }

    @Override
    public AuthUserDto get(String id) {
        return null;
    }
}

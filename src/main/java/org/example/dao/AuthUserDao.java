package org.example.dao;

import org.example.model.DTO.userDTO.AuthUserDto;
import org.example.model.entity.AuthUser;

import java.util.List;

public interface AuthUserDao {

    /**
     * save auth user
     *
     * @param authUser auth entity data
     */
    void save(AuthUser authUser);
    void update(AuthUser authUser);
    void delete(String id);

    default List<AuthUser> findAll() {
        return null;
    }
    default AuthUser findByUsername(String username) {
        return null;
    }
    default String findRoleById(Integer id) {
        return null;
    }
    default List<String> findAllPermissionsByRoleId(Integer roleId){
        return null;
    };

    AuthUserDto get(String id);
}

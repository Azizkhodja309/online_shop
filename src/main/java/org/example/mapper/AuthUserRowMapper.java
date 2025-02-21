package org.example.mapper;

import org.example.model.entity.AuthUser;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AuthUserRowMapper implements RowMapper<AuthUser> {
    @Override
    public AuthUser mapRow(ResultSet rs, int rowNum) throws SQLException {
        AuthUser authUser = new AuthUser();
        authUser.setPassword(rs.getString("password"));
        authUser.setUsername(rs.getString("username"));
        authUser.setFullName(rs.getString("full_name"));
        authUser.setId(rs.getString("id"));
        authUser.setPhone(rs.getString("phone"));
        authUser.setIsAdmin(rs.getBoolean("is_admin"));
        authUser.setEmail(rs.getString("email"));
        authUser.setRoleId(rs.getInt("role_id"));
        authUser.setIsLocked(rs.getBoolean("is_locked"));
//                authUser.setBirthDate(rs.getObject("birthdate"));
        return authUser;
    }
}

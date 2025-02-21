package org.example.dao.impl;

//import org.example.config.SecurityConfig;
import org.example.dao.AuthUserDao;
import org.example.mapper.AuthUserRowMapper;
import org.example.model.DTO.userDTO.AuthUserDto;
import org.example.model.entity.AuthUser;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthUserDaoWithJdbc implements AuthUserDao {

    private final JdbcTemplate jdbcTemplate;
    private final AuthUserRowMapper rowMapper;
    private final PasswordEncoder passwordEncoder;

    public AuthUserDaoWithJdbc(JdbcTemplate jdbcTemplate, AuthUserRowMapper rowMapper, PasswordEncoder passwordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void save(AuthUser authUser) {
//        String sql = "insert into users(id, full_name, username, password, birthdate, phone, email) values (:id, :name, :username, :password, :birth, :phone, :email)";
//
//        namedParameterJdbcTemplate.update(sql, Map.of(
//                "id", authUser.getId(),
//                "name", authUser.getFullName(),
//                "username", authUser.getUsername(),
//                "password", authUser.getPassword(),
//                "birth", authUser.getBirthDate(),
//                "phone", authUser.getPhone(),
//                "email", authUser.getEmail())
//        );

        String sql = "insert into users(id, full_name, username, password, birthdate, phone, email) values (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(
                sql,
                authUser.getId(),
                authUser.getFullName(),
                authUser.getUsername(),
                passwordEncoder.encode(authUser.getPassword()),
                authUser.getBirthDate(),
                authUser.getPhone(),
                authUser.getEmail());
    }

    @Override
    public void update(AuthUser authUser) {
        String sql = "update users set full_name = ?, username = ?, password = ?, birthdate = ?, phone = ?, email = ? where id = ?";
        jdbcTemplate.update(
                sql,
                authUser.getFullName(),
                authUser.getUsername(),
                authUser.getPassword(),
                null,
                authUser.getPhone(),
                authUser.getEmail(),
                authUser.getId()
                );
    }

    @Override
    public List<AuthUser> findAll() {
        String sql = "select * from users where deleted = false";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            AuthUser authUser = new AuthUser();
            authUser.setPassword(rs.getString("password"));
            authUser.setUsername(rs.getString("username"));
            authUser.setFullName(rs.getString("full_name"));
            authUser.setId(rs.getString("id"));
            authUser.setPhone(rs.getString("phone"));
            authUser.setIsAdmin(rs.getBoolean("is_admin"));
            authUser.setEmail(rs.getString("email"));
//                authUser.setBirthDate(rs.getObject("birthdate"));
            return authUser;
        });
    }

    @Override
    public AuthUserDto get(String id) {
        String sql = "SELECT * FROM users WHERE id = ? AND deleted = false";

        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                AuthUserDto authUser = new AuthUserDto();
                authUser.setId(rs.getString("id"));
                authUser.setFullName(rs.getString("full_name"));
                authUser.setUsername(rs.getString("username"));
                authUser.setPassword(rs.getString("password"));
                authUser.setPhone(rs.getString("phone"));
                authUser.setEmail(rs.getString("email"));
                authUser.setIsAdmin(rs.getBoolean("is_admin"));
                authUser.setBirthDate(rs.getDate("birthdate").toLocalDate());
                return authUser;
            }, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void delete(String id) {
        String sql = "update users set deleted = true where id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public AuthUser findByUsername(String username) {
        String sql = "select * from users where username like ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, username);
    }

    @Override
    public String findRoleById(Integer id) {
        String sql = "select code from auth_role where id = ?";
        String roleCode = jdbcTemplate.queryForObject(sql, String.class, id);
        return roleCode;
    }

    @Override
    public List<String> findAllPermissionsByRoleId(Integer roleId) {
        String sql = "select p.code from permissions p left join role_permissions rp on p.id = rp.permission_id left join auth_role ar on rp.role_id = ar.id where ar.id = ?";
        return jdbcTemplate.queryForList(sql, String.class, roleId);
    }
}

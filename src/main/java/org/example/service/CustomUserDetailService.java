package org.example.service;

import org.example.config.CustomUserDetails;
import org.example.dao.AuthUserDao;
import org.example.model.entity.AuthUser;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class CustomUserDetailService implements UserDetailsService {

    private final AuthUserDao authUserDao;

    public CustomUserDetailService(@Lazy AuthUserDao authUserDao) {
        this.authUserDao = authUserDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser authUser = authUserDao.findByUsername(username);
        /*String role = authUserDao.findRoleById(authUser.getRoleId());
        List<String> permissions = authUserDao.findAllPermissionsByRoleId(authUser.getRoleId());

        return new CustomUserDetails(authUser, prepareRoles(role, permissions));
*/
        String role = authUserDao.findRoleById(authUser.getRoleId());
//        List<String> permissions = authUserDao.findAllPermissionsByRoleId(authUser.getRoleId());
        return new User(
                authUser.getUsername(),
                authUser.getPassword(),
                List.of()
        );
    }

    private Collection<? extends GrantedAuthority> prepareRoles(String role, List<String> permissions) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        permissions.forEach(p -> authorities.add(new SimpleGrantedAuthority(p)));
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        return authorities;
    }
}

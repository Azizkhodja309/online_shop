package org.example.config;

import org.example.service.CustomUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
        jsr250Enabled = true,
        proxyTargetClass = true,
        securedEnabled = true
)
public class SecurityConfig {
    private final CustomUserDetailService userDetailsService;

    public SecurityConfig(CustomUserDetailService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public DefaultSecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .userDetailsService(userDetailsService)
                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers("/","/auth/login", "/auth/create", "/advertisement/ads", "/auth/users", "/auth/create-page")
                            .permitAll()
                            .anyRequest()
                            .authenticated();
                });

        httpSecurity
                .formLogin(l -> {
                    l.defaultSuccessUrl("/auth/set_authentication")
                            .loginPage("/auth/login")
                            .usernameParameter("username")
                            .passwordParameter("password");
                });

        httpSecurity.logout(
                logoutConfigurer -> {
                    logoutConfigurer.logoutUrl("/auth/logout")
                            .logoutSuccessUrl("/auth/set_authentication")
                            .deleteCookies("JSESSIONID")
                            .clearAuthentication(true)
                            .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout", "POST"));
                }
        ).userDetailsService(userDetailsService);

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncode() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncode());
        return new ProviderManager(provider);
    }
}

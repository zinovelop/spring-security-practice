package com.jinho.spring_security_practice.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth-> auth
                        .requestMatchers("login","/join","/joinProc").permitAll()
//                        .requestMatchers(PathRequest.toH2Console()).permitAll() // H2 설정
                        .requestMatchers("/").hasRole("USER")
//                        .requestMatchers("/admin").authenticated()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
//                .formLogin(login -> login
//                        .loginPage("/login")
//                        .loginProcessingUrl("/loginProc")
//                        .permitAll()
//                )
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session-> session
                        .maximumSessions(1) // 한 개의 아이디당 허용 갯수
                        .maxSessionsPreventsLogin(true) // maximumSessions()에 정의된 다중 로그인 갯수를 초과했을 경우 처리 방법
                )
//                .csrf(AbstractHttpConfigurer::disable)
//                .headers(headers-> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
        ;
        return http.build();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
//        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
//        hierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER\n"
//        + "ROLE_USER > ROLE_GUEST\n");
//
//        return hierarchy;
        // 위 방식에서 6.3.X 부터 변경됨
        // 세가지 방식 중 하나 선택
//        return RoleHierarchyImpl.fromHierarchy("""
//                ROLE_ADMIN > ROLE_USER
//                ROLE_USER > ROLE_GUEST
//                """);
//        return RoleHierarchyImpl.withRolePrefix("ROLE_")
//                .role("ADMIN").implies("USER")
//                .role("USER").implies("GUEST")
//                .build();
        return RoleHierarchyImpl.withDefaultRolePrefix() //기본이 ROLE_
                .role("ADMIN").implies("USER")
                .role("USER").implies("GUEST")
                .build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user1 = User.builder()
                .username("user1")
                .password(passwordEncoder().encode("asd123"))
                .roles("ADMIN")
                .build();
        UserDetails user2 = User.builder()
                .username("user2")
                .password(passwordEncoder().encode("asd123"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user1, user2);
    }
}

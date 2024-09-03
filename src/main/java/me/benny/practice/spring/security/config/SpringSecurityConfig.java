package me.benny.practice.spring.security.config;

import lombok.RequiredArgsConstructor;
import me.benny.practice.spring.security.dto.CustomUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Security 설정 Config
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {

    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // basic authentication
//        http.httpBasic().disable(); // basic authentication filter 비활성화
        http.httpBasic(AbstractHttpConfigurer::disable);
        // csrf
//        http.csrf();
        http.csrf(AbstractHttpConfigurer::disable);
        // remember-me
//        http.rememberMe();
//        http.rememberMe(auth->auth
//                .key("tokenkey")
//                .tokenValiditySeconds(60 * 60 * 24 * 7) //인증토큰 유효시간 (초)
//                .userDetailsService(customUserDetailsService)
//                .rememberMeParameter("remember-me")
//        );
        // authorization
//        http.authorizeRequests()
//                // /와 /home은 모두에게 허용
//                .antMatchers("/", "/home", "/signup").permitAll()
//                // hello 페이지는 USER 롤을 가진 유저에게만 허용
//                .antMatchers("/note").hasRole("USER")
//                .antMatchers("/admin").hasRole("ADMIN")
//                .antMatchers(HttpMethod.POST, "/notice").hasRole("ADMIN")
//                .antMatchers(HttpMethod.DELETE, "/notice").hasRole("ADMIN")
//                .anyRequest().authenticated();
        http.authorizeHttpRequests(auth->auth
                .requestMatchers("/", "/home", "/signup").permitAll()
                .requestMatchers("/note").hasRole("USER")
                .requestMatchers("/admin").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/notice").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/notice").hasRole("ADMIN")
                .anyRequest().authenticated());
        // login
//        http.formLogin()
//                .loginPage("/login")
//                .defaultSuccessUrl("/")
//                .permitAll(); // 모두 허용
        http.formLogin(login->login
                .loginPage("/login")
//                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/")
                .permitAll());
        // logout
//        http.logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                .logoutSuccessUrl("/");
        http.logout(logout->logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/logout"));
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return webSecurity -> {
            webSecurity.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
        };
    }
}

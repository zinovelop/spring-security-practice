package com.study.springsecuritystudy.config

import org.springframework.context.annotation.Bean
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.provisioning.JdbcUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.stereotype.Controller
import javax.sql.DataSource

@Controller
class SecurityConfig {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { auth ->
                auth
                    .anyRequest()
                    .authenticated()
            }
            .sessionManagement { session ->
                session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .httpBasic { }
            .csrf { csrf -> csrf.disable() }
            .headers { headers -> headers.frameOptions { frame -> frame.sameOrigin() } }

        return http.build()
    }

    @Bean
    fun userDetailsService(
        dataSource: DataSource,
        passwordEncoder: PasswordEncoder
    ): UserDetailsService {

        val user1 = User
            .withUsername("jinho")
            .password("asd123")
            .passwordEncoder { passwordEncoder.encode(it) }
            .roles("USER")
            .build()

        val user2 = User
            .withUsername("admin")
            .password("asd123")
            .passwordEncoder { passwordEncoder.encode(it) }
            .roles("ADMIN", "USER")
            .build()

        val jdbcUserDetailsManager: JdbcUserDetailsManager = JdbcUserDetailsManager(dataSource)

        jdbcUserDetailsManager.createUser(user1)
        jdbcUserDetailsManager.createUser(user2)

        return jdbcUserDetailsManager
    }

    @Bean
    fun datasource(): DataSource {
        return EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .addScripts(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
            .build()
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

}
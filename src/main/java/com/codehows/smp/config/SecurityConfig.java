package com.codehows.smp.config;

//import com.codehows.smp.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{

//    private final CustomOAuth2UserService customOAuth2UserService;
    private AjaxAwareAuthenticationEntryPoint ajaxAwareAuthenticationEntryPoint(String url) {
        return new AjaxAwareAuthenticationEntryPoint(url);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.formLogin()
                .loginPage("/member/login")
                .defaultSuccessUrl("/")
                .usernameParameter("email")
                .failureHandler(loginFailHandler())
//                .failureUrl("/member/login/error")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutSuccessUrl("/member/login")
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(ajaxAwareAuthenticationEntryPoint("/member/login"))
        ;

        http.authorizeRequests()
                .mvcMatchers("/").hasAnyRole("USER", "ADMIN")
                .mvcMatchers("/class/**", "/student/**", "/member/roles").hasRole("ADMIN")
                .anyRequest().permitAll()
//                .and()
//                .oauth2Login()
//                .defaultSuccessUrl("/")
//                .userInfoEndpoint() // oauth2 로그인 성공 후 가져올 때의 설정들
//                // 소셜로그인 성공 시 후속 조치를 진행할 UserService 인터페이스 구현체 등록
//                .userService(customOAuth2UserService)
        ;

//        http.exceptionHandling()
//                .authenticationEntryPoint(new CustomAuthenicationEntryPoint())
//        ;

        return http.build();
    }

    @Bean
    public LoginFailHandler loginFailHandler() {
        return new LoginFailHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
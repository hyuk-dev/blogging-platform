package dev.hyuk.blogging.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import dev.hyuk.blogging.service.UserDetailService;
import lombok.RequiredArgsConstructor;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor

public class WebSecurityConfig {
    private final UserDetailService userService;

    // 1)스프링 시큐리티 기능 비활성화
    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
          .requestMatchers(toH2Console())
          .requestMatchers(new AntPathRequestMatcher("/static/**"));
    }

    // 2)특정 HTTP 요청에 대한 웹 기반 보안 구성 
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
          .authorizeRequests(auth -> auth // 3) 인증, 인가 설정 
            .requestMatchers(
                new AntPathRequestMatcher("/login"),
                new AntPathRequestMatcher("/signup"),
                new AntPathRequestMatcher("/user")
            ).permitAll()
              .anyRequest().authenticated())
            .formLogin(formLogin -> formLogin // 4
              .loginPage("/login")
              .defaultSuccessUrl("/articles")
            )
            .logout(logout -> logout // 5
              .logoutSuccessUrl("/login")
              .invalidateHttpSession(true)
            )
            .csrf(AbstractHttpConfigurer::disable) // 6
            .build();
    }

    // 7) 인증 관리자 관련 설정
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailService userDetailService) throws Exception {
      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
      authProvider.setUserDetailsService(userService); // 8
      authProvider.setPasswordEncoder(bCryptPasswordEncoder);
      return new ProviderManager(authProvider);
    }

    @Bean // 9
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
      return new BCryptPasswordEncoder();
    }
    
}

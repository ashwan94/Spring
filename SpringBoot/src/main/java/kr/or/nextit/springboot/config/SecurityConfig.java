package kr.or.nextit.springboot.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {
    // 로그인 및 로그아웃, 권한(Authorization) 처리
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .formLogin(c -> c.loginPage("/login").defaultSuccessUrl("/").permitAll())
                .logout(c -> c.logoutUrl("/logout").logoutSuccessUrl("/").invalidateHttpSession(true))
                // static 폴더에 접근할 필요가 있을 때 사용
//                .authorizeHttpRequests(r -> r.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll())
                .authorizeHttpRequests(
                        r -> r.requestMatchers("/board/list", "/board/view").hasRole("user")
                                .requestMatchers("/board/register").hasRole("member")
                                .requestMatchers("/board/remove").hasRole("manager")
                                .anyRequest().permitAll())
//                .authorizeHttpRequests(
//                        r -> r.requestMatchers(AntPathRequestMatcher.antMatcher("/**/view")).hasRole("admin")
//                )
                .csrf(c -> c.disable())
                .build();
    }
    // 접근(Authentication)과 권한(Authorization) 사용자 관리
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    // 비밀번호 암호가 반드시 필요
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

package saessak.log.user.userConfig;

import antlr.BaseAST;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import saessak.log.jwt.JwtFilter;
import saessak.log.user.service.UserService;

import java.security.Key;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService userService;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic().disable() // ui에서 들어오는 것
                .csrf().disable() // crosssite 기능
                .cors().and()// crosssite 다른 domain 허용
                .authorizeRequests()
                .antMatchers("/user").permitAll() // user 권한 허용
                .antMatchers("/user/join", "/user/login").permitAll() // join, login 허용
//                .antMatchers(HttpMethod.POST, "/coments", "/posts").authenticated()
//                .antMatchers(HttpMethod.GET, "/coments", "/posts").authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt 사용할 경우 사용 가능 할 듯?
                .and()
//                .addFilterBefore(new JwtFilter(userService, Key), UsernamePasswordAuthenticationFilter.class) // 토큰인가 전
                .build();
    }
}

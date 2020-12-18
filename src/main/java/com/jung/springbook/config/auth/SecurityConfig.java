package com.jung.springbook.config.auth;


import com.jung.springbook.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * (1) EnableWebSecurity 는
 * Spring Security 설정들을 활성화시켜 준다.
 *
 * (2) .csrf().disable().headers().frameOptions().disable() 는
 * h2-console 화면을 사용하기 위해 해당 옵션들을 disable 한다.
 *
 * (3) .authorizeRequests() 는
 * URL별 권한관리르 설정하는 옵션의 시작점이다.
 * authorizeRequests 가 선언되어야만 antMatchers 옵션을 사용할 수 있다.
 *
 * (4) antMatchers 는
 * 권한 관리 대상을 지정하는 옵션이다.
 * URL,HTTP 메소드별로 관리가 가능하다.
 *
 * (5) anyRequest 는
 * 설정된 값들 이외 나머지 URL들을 나타낸다.
 * 여기서는 authenticated()을 추가하여 나머지 URL 들은 모두 인증된 사용자(로그인)에게만 허용하게 한다.
 *
 * (6) .logout().logoutSuccessUrl("/") 는
 * 로그아웃 기능에 대한 여러 설정의 진입점이다.
 * 로그아웃 성공 시 / 주소로 이동한다.
 *
 * (7) oauth2Login 는
 * OAuth 2 로그인 기능에 대한 여러 설정의 진입점이다.
 *
 * (8) userInfoEndpoint() 는
 * OAuth 2 로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당한다.
 *
 * (9) userService 는
 * 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체를 동록한다.
 * 리소스 서버(즉, 소셜서비스들)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시가 가능하다.
 */
@RequiredArgsConstructor
@EnableWebSecurity // (1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()// (2)
                .and()
                .authorizeRequests()// (3)
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll() // (4)
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                .anyRequest().authenticated() // (5)
                .and()
                .logout()
                .logoutSuccessUrl("/") // (6)
                .and()
                .oauth2Login() // (7)
                .userInfoEndpoint() // (8)
                .userService(customOAuth2UserService); // (9)
    }
}
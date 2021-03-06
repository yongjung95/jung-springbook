package com.jung.springbook.web;

import com.jung.springbook.config.auth.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

/**
 * (1) @ExtendWith 는
 * 테스트를 진행할 때 JUnit 에 내장된 실행자 외에 다른 실행자를 실행 시킨다.
 * 여기서는 SpringExtension 라는 스프링 실행자를 사용한다.
 * 즉, 스프링 부트 테스트와 JUnit 사이에 연결자 역할을 한다.
 *
 * (2) @WebMvcTest 는
 * 여러 스프링 테스트 어노테이션 중, Web(Spring MVC)에 집중할 수 있는 어노테이션이다.
 * 선언할 경우 @Controller, @ControllerAdvice 등을 사용할 수 있다.
 * 단, @Service, @Component, @Repository 등은 사용할 수 없다. 여기서는 컨트롤러만 사용하기 때문에 선언한다.
 *
 * (3) @Autowired 는
 * 스프링이 관리하는 빈을 주입 받는다.
 *
 * (4) private MockMvc mvc 는
 * 웹 API를 테스트할때 사용한다.
 * 스프링 MVC 테스트의 시작점이다.
 * 이 클래스를 통해 HTTP GET,POST 등에 대한 API 테스트를 할 수 있다.
 *
 * (5) mvc.perform(get("/hello")) 는
 * MockMvc 를 통해 /hello 주소로 HTTP GET 요청을 한다.
 * 체이닝이 지원되어 아래와 같이 여러 검증 기능을 이어서 선언할 수 있다.
 *
 * (6) .andExpect(status().isOk()) 는
 * mvc.perform 의 결과를 검증한다. HTTP Header 의 Status 를 검증한다.
 * 우리가 흔히 알고 있는 200, 404, 500 등의 상태를 검증하고 여기선 OK 즉, 200인지 아닌지를 검증한다.
 *
 * (7) .andExpect(content().string(hello)) 는
 * mvc.perform 의 결과를 검증한다. 응답 본문의 내용을 검증한다.
 * Controller 에서 "hello"를 리턴하기 때문에 이 값이 맞는지 검증한다.
 *
 * (8) param 은
 * API 테스트할 때 사용될 요청 파라미터를 설정한다.
 * 단, 값은 String 만 허용되기 때문에 숫자/날짜 등의 데이터도 등록할 때는 문자열로 변경해야만 사용 가능.
 *
 * (9) jsonPath 는
 * JSON 응답값을 필드별로 검증할 수 있는 메소드이다.
 * $를 기준으로 필드명을 명시한다.
 * 여기서는 name 과 amount 를 검증하니 $.name, $.amount 로 검증한다.
 *
 * (10) @WebMvcTest 는 WebSecurityConfigurerAdapter, WebMvcConfigurer 를 비롯한 @ControllerAdvice, @Controller 를 읽는다.
 * 즉, @Repository, @Service, @Component 는 스캔 대상이 아니다.
 * SecurityConfig 는 읽었지만, SecurityConfig 를 생성하기 위해 필요한 CustomOauth2UserService 는 읽을 수 없어 에러가 발생한다.
 * 이걸 해결하기 위해 스캔 대상에서 SecurityConfig 를 제거한다.
 */
@ExtendWith(SpringExtension.class) // (1)
@WebMvcTest(controllers = HelloController.class, // (2)
        excludeFilters = { @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)}) // (10)
public class HelloControllerTest {

    @Autowired // (3)
    private MockMvc mvc; // (4)

    @WithMockUser(roles="USER")
    @Test
    public void hello가_리턴된다() throws Exception{
        String hello = "hello";

        mvc.perform(get("/hello")) // (5)
                .andExpect(status().isOk()) // (6)
                .andExpect(content().string(hello)); // (7)
    }

    @WithMockUser(roles="USER")
    @Test
    public void helloDto가_리턴된다() throws Exception{
        String name = "hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto")
                .param("name", name) // (8)
                .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name))) // (9)
                .andExpect(jsonPath("$.amount", is(amount)));

    }

}

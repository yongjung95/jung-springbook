package com.jung.springbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @EnableJpaAuditing 를 사용하기 위해선 최소 하나의 @Entity 클래스가 필요하다.
 * 테스트 코드 HelloControllerTest 는 @WebMvcTest 이다 보니 당연히 없다.
 *
 * @EnableJpaAuditing 가 @SpringBootApplication 와 같이 있다보니 @WebMvcTest 에서도 스캔하게 된다.
 * 그래서 @EnableJpaAuditing 와 @SpringBootApplication 둘을 분리해준다. (따로 config 를 생성하여 사용.)
 */
//@EnableJpaAuditing // JPA Auditing 기능 활성화
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

package com.jung.springbook.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * (1) getForObject 는
 * 실제 URL 호출 시 페이지의 내용이 제대로 호출되는지에 대한 테스트
 *
 * (2) contains 는
 * 포함되어 있는지 체크.
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class IndexControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void 메인페이지_로딩(){

        // when
        String body = this.restTemplate.getForObject("/",String.class); // (1)

        // then
        assertThat(body).contains("스프링 부트로 시작하는 웹 서비스"); // (2)
    }
}

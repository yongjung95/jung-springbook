package com.jung.springbook.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


/**
 * config 는 스프링부트를 사용할때 필요한 설정이다.
 */
@Configuration
@EnableJpaAuditing
public class JpaConfig {
}

package com.jung.springbook.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * (1) @Getter 는
 * 선언된 모든 필드의 get 메소드를 생성해 준다.
 *
 * (2) @RequiredArgsConstructor 는
 * 선언된 모든 final 필드가 포함된 생성자를 생성해 준다.
 * final 이 없는 필드는 생성자에 포함되지 않는다.
 */
@Getter // (1)
@RequiredArgsConstructor // (2)
public class HelloResponseDto {

    private final String name;
    private final int amount;
}

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
 *
 * (3) Dto 는 데이터를 DB 에서 가져오는 용도에 맞게 사용된다? (12/18 의문)
 * 계층간의 Controller, View, Business Layer, Persistent Layer 를 말하며 각 계층간 데이터 교환을 위한 객체
 */
@Getter // (1)
@RequiredArgsConstructor // (2)
public class HelloResponseDto {

    private final String name;
    private final int amount;
}

package com.jung.springbook.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * (1) @Target(ElementType.PARAMETER) 는
 * 이 어노테이션이 생성될 수 있는 위치를 지정한다.
 * PARAMETER 로 지정했으니 메소드의 파라미터로 선언된 객체에서만 사용할 수 있다.
 * 이 외에도 클래스 선언문에 쓸 수 있는 TYPE 등이 있다.
 *
 * (2) @Retention 는
 * 해당 어노테이션이 언제까지 유지할 지 알려주는 어노테이션이다.
 * 자기 자신이 어느 시점까지 유효한지를 명시해줘야한다.
 * 3 가지 속성이 있는데, SOURCE, CLASS, RUNTIME
 *
 * - SOURCE 속성은 주석처럼 사용하고 싶을 때 사용한다.
 *   소스 코드까지만 유지되고, 컴파일 후 해당 어노테이션은 사라진다.
 *
 * - CLASS 속성은 @Retention 의 기본값으로 컴파인 한 .class 파일에서도 유지가 된다.
 *   즉, 런타임 시 클래스를 메모리로 읽어오면 해당 정보는 사라진다.
 *
 * - RUNTIME 속성은 클래스를 메모리에 읽어왔을 때까지 유지한다.
 *   코드에서 이 정보를 바탕으로 특정 로직을 실행할 수 있다.
 *
 * (3) @interface 는
 * 이 파일을 어노테이션 클래스로 지정한다.
 * LoginUser 라는 이름을 가진 어노테이션이 생성되었다고 보면 된다.
 */

@Target(ElementType.PARAMETER) // (1)
@Retention(RetentionPolicy.RUNTIME) // (2)
public @interface LoginUser { // (3)
}

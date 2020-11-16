package com.jung.springbook.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 주요 어노테이션 일수록 클래스에 가깝게 둔다.
 * @Entity 는 JPA 의 어노테이션(필수) 이며,
 * @Getter 와 @NoArgsConstructor 는 롬복의 어노테이션이다.
 * 롬복은 코드를 단순화시켜 주지만 필수 어노테이션은 아니다.(코틀린 등의 새 언어 전환으로 롬복이 더이상 필요 없을 경우 쉽게 삭제가능)
 *
 * (1) @Entity 는
 * 테이블과 링크될 클래스임을 나타낸다.
 * 기본값으로 클래스의 카멜케이스 이름을 언더스코어 네이밍으로 테이블 이름을 매칭한다. 예) SalesManager.java -> sales_manager table
 *
 * (2) @Id 는
 * 해당 테이블의 PK 필드를 나타낸다.
 *
 * (3) @GeneratedValue 는
 * PK의 생성 규칙을 나타낸다.
 * 스프링 부트 2.0 에서는 GenerationType.IDENTITY 옵션을 추가해야만 auto_increment(자동으로 1씩 증가)가 된다.
 *
 * (4) @Column 은
 * 테이블의 칼럼을 나타내며 굳이 선언하지 않더라고 해당 클래스의 필드는 모두 칼럼이 된다.
 * 사용하는 이유는, 기본값 외에 추가로 변경이 필요한 옵션이 있으면 사용한다.
 * 문자열의 경우 VARCHAR(255)가 기본값인데, 사이즈를 500으로 늘리고싶거다 타입을 TEXT[예) content] 로 변경하고 싶거나 등의 경우에 사용된다.
 *
 * (5) NoArgsConstructor 는
 * 기본 생성자 자동 추가
 * public Posts(){} 와 같은 효과
 */

@Getter
@NoArgsConstructor // (5)
@Entity // (1)
public class Posts {

    @Id // (2)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // (3)
    private Long id;

    @Column(length = 500, nullable = false) // (4)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder
    public Posts(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

}

package com.jung.springbook.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * (1) @MappedSuperclass 는
 * JPA Entity 클래스들이 BaseTimeEntity 를 상속 할 경우 필드들(createdDate, modifiedDate)도 칼럼으로 인식하도록 한다.
 *
 * (2) @EntityListeners(AuditingEntityListener.class) 는
 * BaseTimeEntity 클래스에 Auditing 기능을 포함한다.
 */

@Getter
@MappedSuperclass // (1)
@EntityListeners(AuditingEntityListener.class) // (2)
public abstract class BaseTimeEntity {

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

}

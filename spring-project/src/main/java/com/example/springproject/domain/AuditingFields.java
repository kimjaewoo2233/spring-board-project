package com.example.springproject.domain;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;


@ToString
@Getter //자동 세팅되는 값들이기에 setter는 놓지 않는다.
@MappedSuperclass
@EntityListeners(value = AuditingEntityListener.class)
public class AuditingFields {

  //  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) //파싱에 대한 룰 지정한다.
    @CreatedDate
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @CreatedBy      //누가 만들었는지 알게해주는 것인데 이거 설정은 JpaConfig에 함
    @Column(nullable = false,updatable = false,length = 100)
    private String createdBy;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedAt;
    @LastModifiedBy
    @Column(nullable = true,length = 100)
    private String modifiedBy;

}

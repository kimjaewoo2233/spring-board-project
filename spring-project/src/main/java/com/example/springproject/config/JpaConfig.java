package com.example.springproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration      //configuration 빈으로 등록 - 이렇게 하면 각종 설정을 잡을떄 쓴다
@EnableJpaAuditing
public class JpaConfig {

    @Bean
    public AuditorAware<String> auditorAware(){
        return () -> Optional.of("uno");    //TODO: 스프링 시큐리티로 인증 기능을 붙이게 될떄 수정
    }   //CreatedBy할떄 누가만든지 넣어주는 거다. 리턴형은 람다식이 들어간다.
}

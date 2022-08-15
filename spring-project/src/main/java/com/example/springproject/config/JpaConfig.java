package com.example.springproject.config;

import com.example.springproject.dto.security.BoardPrincipal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration      //configuration 빈으로 등록 - 이렇게 하면 각종 설정을 잡을떄 쓴다
@EnableJpaAuditing
public class JpaConfig {

    @Bean
    public AuditorAware<String> auditorAware(){
        return () -> Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)    //인증이 됐는지를 먼저 확인 한다.
                .map(Authentication::getPrincipal)  //
                .map(x -> (BoardPrincipal)x)    //타입캐스팅을 람다식으로 적음(BoardPrincipal.class::cast)도 가능
                .map(BoardPrincipal::getUsername);
        // Security Context에 있는 정보를 불러온다 0 거기서 principal을 가져옴

        //값이 없을 수도 있기 떄문에 Nullable로 만든다. 그리고 값은 SecurityContext에서 가져온다
    }   //CreatedBy할떄 누가만든지 넣어주는 거다. 리턴형은 람다식이 들어간다.
}

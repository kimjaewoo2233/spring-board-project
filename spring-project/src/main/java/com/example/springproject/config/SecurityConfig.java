package com.example.springproject.config;


import com.example.springproject.dto.UserAccountDto;
import com.example.springproject.dto.security.BoardPrincipal;
import com.example.springproject.repository.UserAccountRepository;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.management.MXBean;


@Configuration
public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
                return http
                        .authorizeHttpRequests(auth -> auth
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()       //아래 ignore를 시큐리티 보호하에 두게 한 것이다.
                                .mvcMatchers(
                                        HttpMethod.GET,          //특정 HttpMethod를 지정할 수 있다 .GET 요청일때만 안함
                                        "/",            //root페이지를 권한체크 get일떄 안함
                                        "/articles",     //forward되는 페이지도 권한체크안함
                                        "/articles/search-hashtag"
                                ).permitAll()
                                .anyRequest().authenticated()   //나머지는 인증이 되어야한다.
                        )
                        .formLogin().and()
                        .logout()
                                .logoutSuccessUrl("/")
                                .and()  //and를 해줘야 연결이 된다.
                        .build();
                //시쿠리티를 태워서 관리하에 두고 인증과 권한체클를한다.
        }

//        @Bean
//        public WebSecurityCustomizer webSecurityCustomizer(){   //시큐리티 검사에서 완전히 제외한다. static resource가 대표적이다.
//                return (web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations()));
//        } //시큐리티가 제공하는 암호화 모듈을 사용한다. csrf 관리하에 들어가지 않아 보호가 되지 않음 그래서 SecurityFilterChain에서 permitAll 설정하는 것이 좋다

        @Bean                   //Bean 스캐닝을 통해 자연스럽게 빈 주입
        public UserDetailsService userDetailsService(UserAccountRepository userAccountRepository){
                        //return 하는게 loadUserByUsername을 람다식으로 표현한 것이다.
                         return username -> userAccountRepository
                                 .findById(username)
                                 .map(UserAccountDto::from)
                                 .map(BoardPrincipal::from)
                                 .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다 -usernae : "+username));

        }

        @Bean           //시큐리티 인증구현을 할떄 패스워드 인코더도 무조건 등록해줘야한다.
        public PasswordEncoder passwordEncoder(){
                return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        }
}

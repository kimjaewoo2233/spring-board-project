package com.example.springproject.controller;


import com.example.springproject.config.SecurityConfig;
import com.example.springproject.util.FormDataEncoder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("View 컨트롤러 - 인증테스트")
@Import({SecurityConfig.class, FormDataEncoder.class})
@WebMvcTest //시큐리티 적용된 테스트
public class AuthControllerTest {
        //보면 login관련 컨트롤러를 만들지 않았는데 통과한다 시큐리티 때문이다
    private final MockMvc mvc;



    public AuthControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }
    @DisplayName("[view][GET] 로그인 페이지 - 정상호출")
    @Test
    public void givenNoting_whenTryingToLogin_thenReturnLoginView() throws Exception{
            mvc.perform(get("/login"))  //시큐리티에서 자동지정 url
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }
}

package com.example.springproject.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("DATA REST - API 테스트")
@AutoConfigureMockMvc    //SpringBootTest 에서 Mock 을 사용할 수 없기에 이걸 적어줘야 사용간으ㅜ
@Transactional
@SpringBootTest //@WebMvcTest슬라이스 테스트 MockMVC를 사용할 수 있다
@Disabled("Spring Data Rest 통합테스트는 불필요하므로 제외시킴")
public class DataRestTest {

    private final MockMvc mvc;  //WebMVCTest로 주입이 가능해진다

    public DataRestTest(@Autowired MockMvc mockMvc){
        this.mvc = mockMvc;
    }

    @DisplayName("[api] 게시글 리스트 조회")
    @Test
    void givenNoting_whenRequestingArticles_thenReturnsArticleJsonResponse() throws Exception{

        mvc.perform(get("/api/articles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));

    }

    @DisplayName("[api] 게시글 댓글 리스트 조회")
    @Test
    void givenNoting_whenRequestingArticles_thenReturnsArticleCommentsJsonResponse() throws Exception{

        mvc.perform(get("/api/articleComments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));

    }
    }

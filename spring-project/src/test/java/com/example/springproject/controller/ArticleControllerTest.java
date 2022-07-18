package com.example.springproject.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("View 컨트롤러 - 게시글")
@WebMvcTest(ArticleController.class)        //클래스를 지정하지 않으면 모든 컨트롤러를 읽어들인다.
class ArticleControllerTest {


    private final MockMvc mvc;

    public ArticleControllerTest(@Autowired MockMvc mockMvc){
        this.mvc = mockMvc;
    }

    @DisplayName("{view}{get} 게시글 리스트 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingArticleVsView_thenReturnsArticleView() throws Exception{
            //Given

            //when
            mvc.perform(get("/articles"))
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.TEXT_HTML))
                    .andExpect(model().attributeExists("articles"));
            //then

    }

    @DisplayName("{view}{get} 게시글 상세 페이지 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingARticleView_thenReturnArticleView() throws Exception{
        mvc.perform(get("/articles/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.TEXT_HTML))
                .andExpect(model().attributeExists("article"));
    }

    @DisplayName("{view}{GET} 게시글 검색 전용 페이지 - 정상 호출")
    @Test
    public void givenNoting_whenRequestingArticleSerarchView_thenReturnArticleSearch() throws Exception{

        mvc.perform(get("/articles/search"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.TEXT_HTML));
        }
    @DisplayName("{view}[GET] 게시글 해시태그 검색 페이지 - 정상 호출")
    @Test
    public void givenNoting_whenRequestingArticleHashtagSearchView_thenReturningArticleSearchView() throws  Exception{
        mvc.perform(get("/articles/search-hashtag"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.TEXT_HTML));
    }

}
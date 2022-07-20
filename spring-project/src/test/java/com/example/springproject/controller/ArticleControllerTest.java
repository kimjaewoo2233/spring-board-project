package com.example.springproject.controller;

import org.junit.jupiter.api.Disabled;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@DisplayName("View 컨트롤러 - 게시글")
@WebMvcTest(ArticleController.class)        //클래스를 지정하지 않으면 모든 컨트롤러를 읽어들인다.
class ArticleControllerTest {


    private final MockMvc mvc;

    public ArticleControllerTest(@Autowired MockMvc mockMvc){
        this.mvc = mockMvc;
    }

  //  @Disabled("구현중")
    @DisplayName("{view}{get} 게시글 리스트 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingArticleVsView_thenReturnsArticleView() throws Exception{
            //Given

            //when
            mvc.perform(get("/articles"))
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                    .andExpect(view().name("articles/index"))       //view에 이름이 무엇인지 검사
                    .andExpect(model().attributeExists("articles"));
            //then

    }

    @Disabled("구현중")
    @DisplayName("{view}{get} 게시글 상세 페이지 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingARticleView_thenReturnArticleView() throws Exception{
        mvc.perform(get("/articles/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/detail"))
                .andExpect(model().attributeExists("article"))
                .andExpect(model().attributeExists("articleComments"));

    }
    @Disabled("구현중")
    @DisplayName("{view}{GET} 게시글 검색 전용 페이지 - 정상 호출")
    @Test
    public void givenNoting_whenRequestingArticleSerarchView_thenReturnArticleSearch() throws Exception{

        mvc.perform(get("/articles/search"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(model().attributeExists("articles/search"));
        }
    @Disabled("구현중")
    @DisplayName("{view}[GET] 게시글 해시태그 검색 페이지 - 정상 호출")
    @Test
    public void givenNoting_whenRequestingArticleHashtagSearchView_thenReturningArticleSearchView() throws  Exception{
        mvc.perform(get("/articles/search-hashtag"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(model().attributeExists("articles/search-hashtag"));
    }

}
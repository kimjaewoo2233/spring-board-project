package com.example.springproject.controller;

import com.example.springproject.config.SecurityConfig;
import com.example.springproject.dto.ArticleDto;
import com.example.springproject.dto.ArticleWithCommentsDto;
import com.example.springproject.dto.UserAccountDto;
import com.example.springproject.service.ArticleService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@DisplayName("View 컨트롤러 - 게시글")
@Import(SecurityConfig.class)   //Security 접근허용함
@WebMvcTest(ArticleController.class)        //클래스를 지정하지 않으면 모든 컨트롤러를 읽어들인다.
class ArticleControllerTest {


    private final MockMvc mvc;

    @MockBean private ArticleService articleService;
    public ArticleControllerTest(@Autowired MockMvc mockMvc){
        this.mvc = mockMvc;
    }

  //  @Disabled("구현중")
    @DisplayName("{view}{get} 게시글 리스트 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingArticleVsView_thenReturnsArticleView() throws Exception{
            //Given

            given(articleService.searchArticles(eq(null),eq(null),any(Pageable.class))).willReturn(Page.empty());

            //when
            mvc.perform(get("/articles"))
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                    .andExpect(view().name("articles/index"))       //view에 이름이 무엇인지 검사
                    .andExpect(model().attributeExists("articles"))
                     .andExpect(model().attributeExists("searchTypes"));//SearchType을 리스트로 넘겨줘서 뷰에서 어떤 타입이있는지 알게한다
            //then
        then(articleService).should().searchArticles(eq(null), eq(null), any(Pageable.class));

    }



    @DisplayName("{view}{get} 게시글 상세 페이지 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingARticleView_thenReturnArticleView() throws Exception{
        Long articleId = 1L;
        given(articleService.getArticle(articleId)).willReturn(createArticleWithCommentsDto());

        mvc.perform(get("/articles/"+articleId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/detail"))
                .andExpect(model().attributeExists("article"))
                .andExpect(model().attributeExists("articleComments"));

        then(articleService).should().getArticle(articleId);
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

    private ArticleWithCommentsDto createArticleWithCommentsDto() {
        return ArticleWithCommentsDto.of(
                1L,
                createUserAccountDto(),
                Set.of(),
                "titlte",
                "content",
                "#java",
                LocalDateTime.now(),
                "uno",
                LocalDateTime.now(),
                "uno"
        );

    }

    private UserAccountDto createUserAccountDto() {
            return UserAccountDto.of(

                    "uno",
                    "uno",
                    "user@email.com",
                    "UNo",
                    "memo",
                    LocalDateTime.now(),
                    "uno",
                    LocalDateTime.now(),
                    "uno"
            );
    }
    private ArticleDto createArticleDto() {
        return ArticleDto.of(
                createUserAccountDto(),
                "title",
                "content",
                "#java"
        );
    }
}
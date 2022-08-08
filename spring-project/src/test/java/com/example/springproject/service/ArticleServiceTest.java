package com.example.springproject.service;

import com.example.springproject.domain.Article;
import com.example.springproject.domain.type.SearchType;
import com.example.springproject.dto.ArticleDto;
import com.example.springproject.dto.ArticleUpdateDto;
import com.example.springproject.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.doNothing;

import java.time.LocalDateTime;
import java.util.List;

@DisplayName("비즈니스 로직 테스트")
@ExtendWith(MockitoExtension.class)     //테스트를 가볍게 하기 ㅇ위해서 Mockito를 사용
class ArticleServiceTest {

        @InjectMocks        //Mock을 주입해야하는 곳은 InjectMocks 테스트 주체
        private ArticleService sut;
        @Mock   //나머지는 Mock service 클래스에서 이미 빈으로 주입받는다
        private ArticleRepository articleRepository;

        @DisplayName("게시글을 검색하면 게시글 리스트를 반환한다.")
        @Test
        void givenSearchParameter_whenSearchingArticles_thenReturnArticleList(){
            //Given
            Pageable pageable = org.springframework.data.domain.Pageable.ofSize(20);
            given(articleRepository.findAll(pageable)).willReturn(Page.empty());
            //When
            Page<ArticleDto> articles = sut.searchArticles(null,null,pageable);
            //Then
            assertThat(articles).isEmpty();
            then(articleRepository).should().findAll(pageable);
        }

        @DisplayName("검색어와 함께 게시글을 검색하면, 게시글 페이지를 반환한다.")
        @Test
        void givenSearchParameters_whenSearchingArticles_thenReturnsArticlePage(){
            //Given
            SearchType searchType = SearchType.TITLE;
            String searchKeyword = "title";
            Pageable pageable = Pageable.ofSize(20);
            given(articleRepository.findByTitleContaining(searchKeyword,pageable)).willReturn(Page.empty());

            //When
            Page<ArticleDto> articles = sut.searchArticles(searchType,searchKeyword,pageable);
            //Then

            assertThat(articles).isEmpty();
            then(articleRepository).should().findByTitleContaining(searchKeyword,pageable);
        }

       @DisplayName("검색어 없이 게시글을 해시태그 검색하면, 빈페이지를 반환한다.")
       @Test
       void givenNoSearchParameters_whenSearchingArticlesViaHashtag_thenReturnsEmptyPage(){
            //Given
            Pageable pageable = Pageable.ofSize(29);

           //When
            Page<ArticleDto> articles =sut.searchArticlesViaHashtag(null,pageable);

            //then
           assertThat(articles).isEqualTo(Page.empty(pageable));
           then(articleRepository).shouldHaveNoMoreInteractions();
       }

        @DisplayName("게시글의 ID와 수정 정보를 입력하면, 게시글을 수정한다")
        @Test
        void givenArticleIdAndModifiedInfo_whenUpdateingArticle_thenUpdateArticle(){
//
//            given(articleRepository.save(any(Article.class))).willReturn(null);
//
//            sut.updateArticle(1L, ArticleUpdateDto.of("title","content","#java"));
//
//            then(articleRepository).should().save(any(Article.class));
        }

//        @DisplayName("게시글의 ID를 입력하면, 게시글을 삭제한다.")
//        @Test
//        void givenArticleId_whenDeletingArticle_thenDeletesArticle(){
//            // Given
//            willDoNothing().given(articleRepository).delete(any(Article.class));
//
//            // When
//            sut.deleteArticle(1L);
//
//            // Then
//            then(articleRepository).should().delete(any(Article.class));
//        }



}
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
            //When
            Page<ArticleDto> articles = sut.searchArticles(SearchType.TITLE,"search keyword");   //제목, 본문,  Id , 닉네임 , 해시태그
            //Then

            assertThat(articles).isNotNull();

        }

        @DisplayName("게시글을 조회하면 게시글을 반환한다.")
        @Test
        void givenId_whenSearchingArticle_thenReturnArticle(){
            //Given

            //When
            ArticleDto articles = sut.searchArticle(1L);   //제목, 본문,  Id , 닉네임 , 해시태그
            //Then
            assertThat(articles).isNotNull();       //비지 않았다.
        }

        @DisplayName("게시글 정보를 입력하면, 게시글을 생성한다")
        @Test
        void givenArticleInfo_whenSavingArticle_thenSavesArticke(){
                //Given
                ArticleDto dto = ArticleDto.of(LocalDateTime.now(),"Uno","title","content","hashitag");

                given(articleRepository.save(any(Article.class))).willReturn(null);     // void일때는 willDoNoting을 사용
                                //any는 이 타입이면 아무거나 넣을 수도 나올 수도 있다는 뜻임  //return이 있으면 willReturn을 사용용                    //코드에 명시적을 무슨 일이 일어난다는 걸 보여준것이다.
                //When
                    sut.saveArticle(dto);
                //then
                then(articleRepository).should().save(any(Article.class));
                        //save를 호출했는가를 검사하는 것이다.
        }

        @DisplayName("게시글의 ID와 수정 정보를 입력하면, 게시글을 수정한다")
        @Test
        void givenArticleIdAndModifiedInfo_whenUpdateingArticle_thenUpdateArticle(){

            given(articleRepository.save(any(Article.class))).willReturn(null);

            sut.updateArticle(1L, ArticleUpdateDto.of("title","content","#java"));

            then(articleRepository).should().save(any(Article.class));
        }

        @DisplayName("게시글의 ID를 입력하면, 게시글을 삭제한다.")
        @Test
        void givenArticleId_whenDeletingArticle_thenDeletesArticle(){
            // Given
            willDoNothing().given(articleRepository).delete(any(Article.class));

            // When
            sut.deleteArticle(1L);

            // Then
            then(articleRepository).should().delete(any(Article.class));
        }



}
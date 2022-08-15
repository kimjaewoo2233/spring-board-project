package com.example.springproject.repository;

import com.example.springproject.config.JpaConfig;
import com.example.springproject.domain.Article;
import com.example.springproject.domain.ArticleComment;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.AuditorAware;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
//@ActiveProfiles("testdb") yml에서 설정한 test db 사용하기
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Disabled
@DisplayName("JPA 연결 테스트")
@Import(JpaRepositoryTest.TestJpaConfig.class)    //Configuration으로 등록한 파일이 읽히지 않을 수 있어서 이걸 넣는다.
@DataJpaTest    //슬라이스 테스트 할거임  --test메소드들이 전부 Transactional로 묶여있따
class JpaRepositoryTest {

    private final ArticleRepository articleRepository;

    private final ArticleCommentRepository articleCommentRepository;
    //Junit5 부터는 생성자 주입패턴이 가능해졌음
    public JpaRepositoryTest(@Autowired ArticleRepository articleRepository,
                             @Autowired ArticleCommentRepository articleCommentRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }
    @Disabled("데이터가 없어서 꺼놈")
    @DisplayName("select 테스트")
    @Test
    void givenTestData_whenSelecting_thenWorksFine(){
        //Given
        //when
        List<Article> articleList = articleRepository.findAll();
        //then
        assertThat(articleList)
                .isNotNull()
                .hasSize(5)
                .hasSize(2);
    }
    @Disabled("데이터가 없어서 꺼놈")
    @DisplayName("insert 테스트")
    @Test
    void givenTestData_whenInserting_thenWorkFind(){
        long previousCount = articleRepository.count();
        Article article = Article.of("new article","new content","#spring");

        Article savedArticle = articleRepository.save(article);
        assertThat(articleRepository.count())
                .isEqualTo(previousCount+1);

    }
    @Disabled("데이터가 없어서 꺼놈")
    @DisplayName("update 테스트")
    @Test
    void givenTestData_whenUpdating_thenWorkFind(){



        Article articleFirst = articleRepository.findById(2L).orElseThrow();

        String updatedHashing = "#springboot";
        articleFirst.setHashtag(updatedHashing);
        Article updatedArticle = articleRepository.save(articleFirst);
        //transaction으로 묶였기에 update가 진행되지 않는다.  그래서 flush를한다
        articleRepository.flush();
        assertThat(updatedArticle).hasFieldOrPropertyWithValue("hashtag",updatedHashing);
        //hashtag 값이 저 값으로 바뀌었는지 확인
    }
    @Disabled("데이터가 없어서 꺼놈")
    @DisplayName("delete 테스트")
    @Test
    void givenTestData_whenDeleteing_thenWorkFine(){
        List<ArticleComment> comments = new ArrayList<>();



        Article articleFirst = articleRepository.findById(4L).orElseThrow();


//        comments.add(ArticleComment.of(articleFirst,""));
//        comments.add(ArticleComment.of(articleFirst,"댓글2"));

        articleCommentRepository.saveAll(comments);

        articleRepository.findById(2L).get().getArticleComments().forEach( (it) -> {System.out.println(it+"test");});
      //  articleRepository.delete(articleFirst);

    }


    @TestConfiguration  //테스트할떄만 사용하는 설정클래스로 등록한다.
    public static class TestJpaConfig{  //DB 테스트를 하는데 시큐리티 때문에 에러남 그래서 이렇게 설정해서
        // 계정 정보 넣는건 그냥 문자열이 들어가도록 설정이 필요하다다
       @Bean
        public AuditorAware<String> auditorAware(){
            return () -> Optional.of("uno");
        }
    }
}
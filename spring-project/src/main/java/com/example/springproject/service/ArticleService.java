package com.example.springproject.service;

import com.example.springproject.domain.Article;
import com.example.springproject.domain.UserAccount;
import com.example.springproject.domain.type.SearchType;
import com.example.springproject.dto.ArticleDto;
import com.example.springproject.dto.ArticleWithCommentsDto;
import com.example.springproject.repository.ArticleRepository;
import com.example.springproject.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import javax.persistence.EntityNotFoundException;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserAccountRepository userAccountRepository;

    @Transactional(readOnly = true) //변경하지 않고 리드만 한다는 뜻으로 사용
    public Page<ArticleDto> searchArticles(SearchType searchType, String search_keyword, Pageable pageable) {
        if(search_keyword == null || search_keyword.isBlank()){ //빈 문자열이거나 null
            return articleRepository.findAll(pageable).map(ArticleDto::from);   //검색어가 없으면 전부 불러온다
        }

        return switch(searchType){
            case TITLE -> articleRepository.findByTitleContaining(search_keyword,pageable).map(ArticleDto::from);
            case CONTENT -> articleRepository.findByContentContaining(search_keyword,pageable).map(ArticleDto::from);
            case ID -> articleRepository.findByUserAccount_UserIdContaining(search_keyword,pageable).map(ArticleDto::from);
            case NICKNAME -> articleRepository.findByUserAccount_NicknameContaining(search_keyword,pageable).map(ArticleDto::from);
            case HASHTAG -> articleRepository.findByHashtag("#" + search_keyword,pageable).map(ArticleDto::from);
        };

    }

    @Transactional(readOnly = true)
    public ArticleWithCommentsDto getArticleWithComments(Long articleId){
        return articleRepository.findById(articleId)
                .map(ArticleWithCommentsDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: "+articleId));
    }
    @Transactional(readOnly = true)         // 단건조회
    public ArticleDto getArticle(Long articleId){
        return articleRepository.findById(articleId)
                .map(ArticleDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId:" + articleId));
    }

    public void saveArticle(ArticleDto dto){
        UserAccount userAccount = userAccountRepository.getReferenceById(dto.userAccountDto().userId());    //dto와 연관된 아이디를 찾아옴
        //엔티티를 바로조회하지 않고 사용하는 시점에 읽어온다.

        articleRepository.save(dto.toEntity(userAccount));  //userAccount랑 연관관계를 맺은 엔티티가 생성된다.
    }


    public void updateArticle(long articleId, ArticleDto  dto) {
            try{
                Article article = articleRepository.getReferenceById(articleId);    //몇번째 게시물인지
                UserAccount userAccount = userAccountRepository.getReferenceById(dto.userAccountDto().userId());    //바꾸려는 게시물에 아이디를 찾음
                //dto는 record라서 이렇게 가져와서 사용이 가능하다.
                if(article.getUserAccount().equals(userAccount)){       //첫번째로 찾은 게시물과 두번째로 찾은 아이디가 같아야 수정이 가능함 아니면 에러
                    if(dto.title() != null){ article.setTitle(dto.title());}
                    if(dto.content() != null){ article.setContent(dto.content());}
                    article.setHashtag(dto.hashtag());
                }
                //클래스단우ㅢ로 트랜잭션이 묶여있기에 영속성 컨텍스트가 열려있고 커밋할떄 변한것을 감지하여 반영한다. 그래서 save를 안 써도된다.
            } catch (EntityNotFoundException e){
                log.warn("게시글 업데이트 실패. 게시글을 수정하는데 필요한 정보를 찾을 수 없습니다 - {}", e.getLocalizedMessage());
            }
    }

    public void deleteArticle(long articleId,String userId) {
        articleRepository.deleteByIdAndUserAccount_UserId(articleId,userId);
    }
    public long getArticleCount(){
        return articleRepository.count();   // 데이터 갯수를 리턴한다.
    }
    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticlesViaHashtag(String hashtag,Pageable pageable){
        if(hashtag == null || hashtag.isBlank()){
            return Page.empty(pageable);    //비어있는 페이지 정해진 쪽수를ㄹ 리턴
        }
        return articleRepository.findByHashtag(hashtag,pageable).map(ArticleDto::from);
        //해쉬태그로 검색함 그리고 page로 리턴한다. 읽어오는 것이기에 readyOnly
    }
    public List<String> getHashtags(){
        return articleRepository.findAllDistinctHashtags();
    }


}

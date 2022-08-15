package com.example.springproject.service;

import com.example.springproject.domain.Article;
import com.example.springproject.domain.ArticleComment;
import com.example.springproject.domain.UserAccount;
import com.example.springproject.dto.ArticleCommentDto;
import com.example.springproject.repository.ArticleCommentRepository;
import com.example.springproject.repository.ArticleRepository;
import com.example.springproject.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleCommentService {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    private final UserAccountRepository userAccountRepository;


    @Transactional(readOnly = true) //읽는 것이다 읽기만하는 메소드에는 readOnly해주는게 훨씬좋다
    public List<ArticleCommentDto> searchArticleComments(Long articleId) {
        return articleCommentRepository.findByArticle_Id(articleId)
                .stream()
                .map(ArticleCommentDto::from)
                .toList();  //entity를 dto로 바꾼다
    }

    public void saveArticleComment(ArticleCommentDto dto){
                try{
                        Article article = articleRepository.getReferenceById(dto.articleId());
                    UserAccount userAccount = userAccountRepository.getReferenceById(dto.userAccountDto().userId());
                    articleCommentRepository.save(dto.toEntity(article,userAccount));   //댓글이 저장될떄 계정도 연결해야한다.

                }catch (EntityNotFoundException e){
                    log.warn("댓글 저장 실패. 댓글 작성에 필요한 정보를 찾을 수 없습니다 - {}", e.getLocalizedMessage());
                }
    }
    public void updateArticleComment(ArticleCommentDto dto){
            try{
                ArticleComment articleComment = articleCommentRepository.getReferenceById(dto.id());
                if(dto.content() != null) {
                    articleComment.setContent(dto.content());
                }
            }catch (EntityNotFoundException e){
                log.warn("댓글 업데이트 실패. 댓글을 찾을 수 없습니다 - dto: {}", dto);
            }
    }
    public void deleteArticleComment(Long articleCommentId,String userId) {
        articleCommentRepository.deleteByIdAndUserAccount_UserId(articleCommentId,userId);
    }
}

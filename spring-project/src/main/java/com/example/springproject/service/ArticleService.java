package com.example.springproject.service;

import com.example.springproject.domain.type.SearchType;
import com.example.springproject.dto.ArticleDto;
import com.example.springproject.dto.ArticleUpdateDto;
import com.example.springproject.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true) //변경하지 않고 리드만 한다는 뜻으로 사용
    public Page<ArticleDto> searchArticles(SearchType title, String search_keyword) {
        return Page.empty();   //여기 원래 repository.findAll을 사용하는 자리인데 아직 DB연결안함
    }

    @Transactional(readOnly = true)
    public ArticleDto searchArticle(long id){
            return null;
    }

    public void saveArticle(ArticleDto dto) {

    }

    public void updateArticle(long articleId, ArticleUpdateDto dto) {

    }

    public void deleteArticle(long l) {
    }
}

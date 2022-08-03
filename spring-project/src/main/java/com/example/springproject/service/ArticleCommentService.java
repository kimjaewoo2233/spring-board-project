package com.example.springproject.service;

import com.example.springproject.dto.ArticleCommentDto;
import com.example.springproject.repository.ArticleCommentRepository;
import com.example.springproject.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleCommentService {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;


    @Transactional(readOnly = true) //읽는 것이다 읽기만하는 메소드에는 readOnly해주는게 훨씬좋다
    public List<ArticleCommentDto> searchArticleComment(Long articleId) {
        return List.of();

    }
}

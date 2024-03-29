package com.example.springproject.repository.querydsl;

import com.example.springproject.domain.Article;
import com.example.springproject.domain.QArticle;
import com.example.springproject.repository.ArticleRepository;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class ArticleRepositoryCustomImpl
        extends QuerydslRepositorySupport
        implements ArticleRepositoryCustom {

    public ArticleRepositoryCustomImpl(){
        super(Article.class);
    }

    @Override
    public List<String> findAllDistinctHashtags() {
        QArticle article = QArticle.article;

        return from(article)
                .distinct()
                .select(article.hashtag)
                .where(article.hashtag.isNotNull())
                .fetch();
    }
}

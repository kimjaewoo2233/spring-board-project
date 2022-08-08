package com.example.springproject.repository.querydsl;

import java.util.List;
public interface ArticleRepositoryCustom {

    List<String> findAllDistinctHashtags();
}

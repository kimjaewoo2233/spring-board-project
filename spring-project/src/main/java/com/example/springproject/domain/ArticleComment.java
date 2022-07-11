package com.example.springproject.domain;


import java.time.LocalDateTime;

public class ArticleComment {

    private Long id;
    private Article article;
    private String title;

    private LocalDateTime createdAt;
    private String createBy;
    private LocalDateTime modifiedAt;
    private String modifiedBy;
}

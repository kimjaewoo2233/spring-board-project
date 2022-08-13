package com.example.springproject.dto.request;

import com.example.springproject.dto.ArticleCommentDto;
import com.example.springproject.dto.UserAccountDto;

public record ArticleCommentRequest(Long articleId, String content) {
    public static ArticleCommentRequest of(Long articleId, String content) {
        return new ArticleCommentRequest(articleId, content);
    }

    public ArticleCommentDto toDto(UserAccountDto userAccountDto) {
        return ArticleCommentDto.of(
                articleId,
                userAccountDto,
                content
        );
    }

}

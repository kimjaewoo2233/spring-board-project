package com.example.springproject.dto.response;

import com.example.springproject.dto.ArticleDto;

import java.time.LocalDateTime;

public record ArticleResponse(Long id,
                              String title,
                              String content,
                              String hashtag,
                              LocalDateTime createdAt,
                              String email,
                              String nickname) {

    public static ArticleResponse of(Long id, String title, String content, String hashtag, LocalDateTime createdAt, String email, String nickname) {
        return new ArticleResponse(id, title, content, hashtag, createdAt, email, nickname);
    }
    public static ArticleResponse from(ArticleDto dto){//articleDto를 받아 응답으로 보내줄것만 보내주도록함

        String nickname = dto.userAccountDto().nickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = dto.userAccountDto().userId();
        }   //닉네임이 없다면 아이디로 대체한다.
        return new ArticleResponse(
                dto.id(),
                dto.title(),
                dto.content(),
                dto.hashtag(),
                dto.createdAt(),
                dto.userAccountDto().email(),
                nickname
        );

    }
}

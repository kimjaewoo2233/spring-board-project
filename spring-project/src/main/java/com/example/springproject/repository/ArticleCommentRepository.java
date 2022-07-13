package com.example.springproject.repository;

import com.example.springproject.domain.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleCommentRepository extends JpaRepository<ArticleComment,Long> {
}

package com.example.springproject.repository;

import com.example.springproject.domain.ArticleComment;
import com.example.springproject.domain.QArticleComment;
import com.example.springproject.domain.UserAccount;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.List;

@RepositoryRestResource
public interface ArticleCommentRepository
        extends JpaRepository<ArticleComment,Long>,
                QuerydslPredicateExecutor<ArticleComment> ,
            QuerydslBinderCustomizer<QArticleComment> {

    List<ArticleComment> findByArticle_Id(Long articleId);//연관관계 맺은 Id값으로 ArticleComment를 찾는다.

    void deleteByIdAndUserAccount_UserId(Long id, String account_id);
    @Override
    default void customize(QuerydslBindings bindings, QArticleComment root){
            bindings.excludeUnlistedProperties(true);
            bindings.including(root.content,root.createdAt,root.createdBy);
            bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
            bindings.bind(root.createdAt).first(DateTimeExpression::eq);
            bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);

    }



}

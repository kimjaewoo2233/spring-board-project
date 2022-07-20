package com.example.springproject.repository;

import com.example.springproject.domain.Article;
import com.example.springproject.domain.QArticle;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.print.DocFlavor;

@RepositoryRestResource
public interface ArticleRepository extends
        JpaRepository<Article,Long>,
        QuerydslPredicateExecutor<Article>,
        QuerydslBinderCustomizer<QArticle>
{
    @Override   //QuerydslBinderCustomizer 를 사용하려면 이 메소드를 오버라이딩
    default void customize(QuerydslBindings bindings, QArticle root){
        //java 8 부터 인터페이스에 구현이 가능해짐
        bindings.excludeUnlistedProperties(true);   //선택적 검사 -리스팅을 하지않은 프로퍼티는 검색에서 제외
        bindings.including(root.title,root.content,root.hashtag,root.createdAt,root.createdBy);  //이 프로퍼티로만 검색이 가능하게 만든다
     //  bindings.bind(root.title).first(StringExpression::likeIgnoreCase);   //like '${v}'  %는 내가 넣은 장소를 정함
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase);  //like '${%v%}' 둘이 이 차이다    --문자열이라 StringExpression
        //이그젝트 매치로 동작하는데 방식을 바꾼다. 검색 파라미터는 하나만 받는다(first).
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);        //동일검사
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    }


}
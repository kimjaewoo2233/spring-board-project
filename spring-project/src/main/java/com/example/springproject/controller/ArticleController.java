package com.example.springproject.controller;

import com.example.springproject.domain.Article;
import com.example.springproject.domain.type.SearchType;
import com.example.springproject.dto.response.ArticleResponse;
import com.example.springproject.dto.response.ArticleWithCommentsResponse;
import com.example.springproject.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;



/*
*   /articles
*   /articles/{article-id}
*   /articles/search
*   /articles/search-hashtag
* */
@RequiredArgsConstructor
@Controller
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;
    @GetMapping
    public String articles(ModelMap map,
                           @RequestParam(required = false) SearchType searchType,
                           @RequestParam(required = false) String searchValue,
                           @PageableDefault(size = 10,
                                   sort = "createdAt",
                                   direction = Sort.Direction.DESC)Pageable pageable
                           ){
            map.addAttribute("articles", articleService.searchArticles(searchType,searchValue,pageable).map(ArticleResponse::from));
            map.addAttribute("searchTypes",SearchType.values());//enum타입 종류 모두 뷰로뿌림
            return "articles/index";
    }

    @GetMapping("/{articleId}")     //자세히보기
    public String article(@PathVariable Long articleId,ModelMap map){   //getArticle은 댓글도 같이본다ㅣ.
        ArticleWithCommentsResponse articleWithCommentsResponse = ArticleWithCommentsResponse.from(articleService.getArticle(articleId));
        map.addAttribute("article", articleWithCommentsResponse);
        map.addAttribute("articleComments",articleWithCommentsResponse.articleCommentsResponse());
        return "articles/detail";
    }
}

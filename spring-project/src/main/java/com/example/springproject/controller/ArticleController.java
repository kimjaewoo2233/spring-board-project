package com.example.springproject.controller;

import com.example.springproject.domain.type.FormStatus;
import com.example.springproject.domain.type.SearchType;
import com.example.springproject.dto.ArticleDto;
import com.example.springproject.dto.UserAccountDto;
import com.example.springproject.dto.request.ArticleRequest;
import com.example.springproject.dto.response.ArticleResponse;
import com.example.springproject.dto.response.ArticleWithCommentsResponse;
import com.example.springproject.dto.security.BoardPrincipal;
import com.example.springproject.service.ArticleService;
import com.example.springproject.service.PaginationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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


    private final PaginationService paginationService;
    @GetMapping
    public String articles(ModelMap map,
                           @RequestParam(required = false) SearchType searchType,
                           @RequestParam(required = false) String searchValue,
                           @PageableDefault(size = 10,
                                   sort = "createdAt",
                                   direction = Sort.Direction.DESC)Pageable pageable
                           ){
            Page<ArticleResponse> articles = articleService.searchArticles(searchType,searchValue,pageable).map(ArticleResponse::from);
            List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(),articles.getTotalPages());

            map.addAttribute("paginationBarNumbers",barNumbers);
            map.addAttribute("articles", articleService.searchArticles(searchType,searchValue,pageable).map(ArticleResponse::from));
            map.addAttribute("searchTypes",SearchType.values());//enum타입 종류 모두 뷰로뿌림
            return "articles/index";
    }

    @GetMapping("/{articleId}")
    public String article(@PathVariable Long articleId, ModelMap map) {
        ArticleWithCommentsResponse article = ArticleWithCommentsResponse.from(articleService.getArticleWithComments(articleId));

        map.addAttribute("article", article);
        map.addAttribute("articleComments", article.articleCommentsResponse());
        map.addAttribute("totalCount", articleService.getArticleCount());

        return "articles/detail";
    }

    @GetMapping("/search-hashtag")
    public String searchArticleHashtag(
            @RequestParam(required = false) String searchValue,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            ModelMap map
    ) {
        Page<ArticleResponse> articles = articleService.searchArticlesViaHashtag(searchValue, pageable).map(ArticleResponse::from);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), articles.getTotalPages());
        List<String> hashtags = articleService.getHashtags();

        map.addAttribute("articles", articles);
        map.addAttribute("hashtags", hashtags);
        map.addAttribute("paginationBarNumbers", barNumbers);
        map.addAttribute("searchType", SearchType.HASHTAG);

        return "articles/search-hashtag";
    }

    @GetMapping("/form")
    public String articleForm(ModelMap map){
        map.addAttribute("formStatus", FormStatus.CREATE);

        return "articles/form";
    }

    @PostMapping("/form")
    public String postNewArticle(ArticleRequest articleRequest){
        articleService.saveArticle(articleRequest.toDto(UserAccountDto.of(
                "uno","asdf1234","uno@mail.com","Uno","memo",null,null,null, null
        )));

        return "redirect::/articles";
    }
    @GetMapping("/{articleId}/form")
    public String updateArticleForm(@PathVariable Long articleId, ModelMap map) {
        ArticleResponse article = ArticleResponse.from(articleService.getArticle(articleId));

        map.addAttribute("article", article);
        map.addAttribute("formStatus", FormStatus.UPDATE);

        return "articles/form";
    }

    @PostMapping ("/{articleId}/form")
    public String updateArticle(@PathVariable Long articleId, ArticleRequest articleRequest) {
        // TODO: 인증 정보를 넣어줘야 한다.
        articleService.updateArticle(articleId, articleRequest.toDto(UserAccountDto.of(
                "uno", "asdf1234", "uno@mail.com", "Uno", "memo", null, null, null, null
        )));

        return "redirect:/articles/" + articleId;
    }

    @PostMapping ("/{articleId}/delete")
    public String deleteArticle(
            @PathVariable Long articleId,
            @AuthenticationPrincipal BoardPrincipal boardPrincipal
            ) {
        // TODO: 인증 정보를 넣어줘야 한다.
        articleService.deleteArticle(articleId,boardPrincipal.getUsername());

        return "redirect:/articles";
    }

}

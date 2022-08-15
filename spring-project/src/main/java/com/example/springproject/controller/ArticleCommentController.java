package com.example.springproject.controller;


import com.example.springproject.dto.UserAccountDto;
import com.example.springproject.dto.request.ArticleCommentRequest;
import com.example.springproject.dto.security.BoardPrincipal;
import com.example.springproject.service.ArticleCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/comments")
@Controller
public class ArticleCommentController {

    private final ArticleCommentService articleCommentService;

    @PostMapping("/new")
    private String postNewArticleComment(
            ArticleCommentRequest articleCommentRequest
    ){
        //TODO: 인증정보를 넣어준다.
        articleCommentService.saveArticleComment(articleCommentRequest.toDto(UserAccountDto.of(
                "uno","pw","uno@email.com",null,null
        )));
        return "redirect:/articles/"+articleCommentRequest.articleId();
    }
    @PostMapping("/{commentId}/delete")
    public String deleteArticleComment(
            @PathVariable Long commentId,
             Long articleId,
            @AuthenticationPrincipal BoardPrincipal boardPrincipal
            ){   //삭제한뒤 다시 그 게시물로 돌아가야해서 articleId를 파라미터로 받아야한다.
         articleCommentService.deleteArticleComment(commentId,boardPrincipal.getUsername());
        return "redirect:/articles/"+articleId;
    }


}

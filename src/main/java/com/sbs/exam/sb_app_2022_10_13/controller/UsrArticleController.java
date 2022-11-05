package com.sbs.exam.sb_app_2022_10_13.controller;

import com.sbs.exam.sb_app_2022_10_13.service.ArticleService;
import com.sbs.exam.sb_app_2022_10_13.util.Ut;
import com.sbs.exam.sb_app_2022_10_13.vo.Article;
import com.sbs.exam.sb_app_2022_10_13.vo.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UsrArticleController {
  @Autowired
  private ArticleService articleService;


  // 액션 메서드 시작
  @RequestMapping("/usr/article/doAdd")
  @ResponseBody
  public ResultData<Article> doAdd(HttpSession httpSession, String title, String body) {
    boolean isLogined = false;
    int loginedMemberId = 0;

    if (httpSession.getAttribute("loginedMemberId") != null) {
      isLogined = true;
      loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");
    }

    if ( isLogined == false ) {
      return ResultData.from("F-A", "로그인 후 시도해주세요.");
    }

    if( Ut.empty(title)) {
      return ResultData.from("F-1", "title(을)를 입력해주세요.");
    }

    if (Ut.empty(body)) {
      return ResultData.from("F-2", "body(을)를 입력해주세요.");
    }


    ResultData<Integer> writeArticleRd = articleService.writeArticle(loginedMemberId, title, body);

    int id = writeArticleRd.getData1();

    Article article = articleService.getArticle(id);

    return ResultData.newData(writeArticleRd, "article", article);
  }

  @RequestMapping("/usr/article/getArticles")
  @ResponseBody
  public ResultData<List<Article>> getArticles() {
    List<Article> articles = articleService.getArticles();
    return ResultData.from("S-1", "게시물 리스트입니다.", "articles", articles);
  }

  @RequestMapping("/usr/article/getArticle")
  @ResponseBody
  public ResultData<Article> getArticleAction(int id) {
    Article article = articleService.getArticle(id);

    if ( article == null ) {
      return ResultData.from("F-1", Ut.f("%d번 게시물이 존재하지 않습니다.", id));
    }

    return ResultData.from("S-1", Ut.f("%d번 게시물입니다.", id), "article", article);
  }

  @RequestMapping("/usr/article/doDelete")
  @ResponseBody
  public ResultData<Integer> doDelete(HttpSession httpSession, int id) {
    boolean isLogined = false;
    int loginedMemberId = 0;

    if (httpSession.getAttribute("loginedMemberId") != null ) {
      isLogined = true;
      loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");
    }

    if ( isLogined == false ) {
      return ResultData.from("F-A", "로그인 후 이용해주세요.");
    }

    Article article = articleService.getArticle(id);

    if (article.getMemberId() != loginedMemberId) {
      return ResultData.from("F-2", "권한이 없습니다.");
    }

    if ( article == null ) {
      return ResultData.from("F-1", Ut.f("%d번 게시물이 존재하지 않습니다.", id));
    }

    articleService.deleteArticle(id);

    return ResultData.from("S-1", Ut.f("%d번 게시물을 삭제하였습니다.", id), "id", id);
  }

  @RequestMapping("/usr/article/doModify")
  @ResponseBody
  public ResultData<Article> doModify(HttpSession httpSession, int id, String title, String body) {
    boolean isLogined = false;
    int loginedMemberId = 0;

    if (httpSession.getAttribute("loginedMemberId") != null) {
      isLogined = true;
      loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");
    }

    if ( isLogined == false ) {
      return ResultData.from("F-A", "로그인 후 이용해주세요.");
    }

    Article article = articleService.getArticle(id);

    if(article.getMemberId() != loginedMemberId ) {
      return ResultData.from("F-2", "권한이 없습니다.");
    }


    if ( article == null ) {
      return ResultData.from("F-1", Ut.f("%d번 게시물이 존재하지 않습니다.", id));
    }

    ResultData actorCanModifyRd = articleService.actorCanModify(loginedMemberId, article);

    if (actorCanModifyRd.isFail()) {
      return actorCanModifyRd;
    }

    return articleService.modifyArticle(id, title, body);
  }
  // 액션 메서드 끝

}
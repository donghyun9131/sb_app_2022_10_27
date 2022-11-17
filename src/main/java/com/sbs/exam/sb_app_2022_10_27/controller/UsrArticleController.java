package com.sbs.exam.sb_app_2022_10_27.controller;

import com.sbs.exam.sb_app_2022_10_27.service.ArticleService;
import com.sbs.exam.sb_app_2022_10_27.util.Ut;
import com.sbs.exam.sb_app_2022_10_27.vo.Article;
import com.sbs.exam.sb_app_2022_10_27.vo.ResultData;
import com.sbs.exam.sb_app_2022_10_27.vo.Rq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UsrArticleController {
  @Autowired
  private ArticleService articleService;


  @RequestMapping("/usr/article/doAdd")
  @ResponseBody
  public ResultData<Article> doAdd(HttpServletRequest req, String title, String body) {
    Rq rq = (Rq) req.getAttribute("rq");


    if( Ut.empty(title)) {
      return ResultData.from("F-1", "title(을)를 입력해주세요.");
    }

    if (Ut.empty(body)) {
      return ResultData.from("F-2", "body(을)를 입력해주세요.");
    }


    ResultData<Integer> writeArticleRd = articleService.writeArticle(rq.getLoginedMemberId(), title, body);

    int id = writeArticleRd.getData1();

    Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

    return ResultData.newData(writeArticleRd, "article", article);
  }

  @RequestMapping("/usr/article/list")
  public String showList(HttpServletRequest req, Model model) {
    Rq rq = (Rq) req.getAttribute("rq");

    List<Article> articles = articleService.getForPrintArticles(rq.getLoginedMemberId());

    model.addAttribute("articles", articles);

    return "usr/article/list";
  }

  @RequestMapping("/usr/article/detail")
  public String showDetail(HttpServletRequest req, Model model, int id) {
    Rq rq = (Rq) req.getAttribute("rq");

    Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

    model.addAttribute("article", article);

    return "usr/article/detail";
  }

  @RequestMapping("/usr/article/doDelete")
  @ResponseBody
  public Object doDelete(HttpServletRequest req, int id) {
    Rq rq = (Rq) req.getAttribute("rq");                             // BeforeActionInterceptor 에서 Rq 받아오기

    if ( rq.isLogined() == false ) {
      return Ut.jsHistoryBack("로그인 후 이용해주세요.");
    }

    Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

    if ( article == null ) {
      return Ut.jsHistoryBack( Ut.f("%d번 게시물이 존재하지 않습니다.", id));
    }

    if (article.getMemberId() != rq.getLoginedMemberId()) {
      return Ut.jsHistoryBack("권한이 없습니다.");
    }

    articleService.deleteArticle(id);

    return Ut.jsReplace(Ut.f("%d번 게시물을 삭제하였습니다.", id), "../article/list");
  }

  @RequestMapping("/usr/article/modify")
  public String showModify(HttpServletRequest req, int id, String title, String body) {
    Rq rq = (Rq) req.getAttribute("rq");

    Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

    if ( article == null ) {
      return rq.historyBackJsOnView(Ut.f("%d번 게시물이 존재하지 않습니다.", id));
    }

    ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);

    if (actorCanModifyRd.isFail()) {
      return rq.historyBackJsOnView(actorCanModifyRd.getMsg());
    }
    return "usr/article/modify";
  }


  @RequestMapping("/usr/article/doModify")
  @ResponseBody
  public ResultData<Article> doModify(HttpServletRequest req, int id, String title, String body) {
    Rq rq = (Rq) req.getAttribute("rq");

    if ( rq.isLogined() == false ) {
      return ResultData.from("F-A", "로그인 후 이용해주세요.");
    }

    Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

    if(article.getMemberId() != rq.getLoginedMemberId() ) {
      return ResultData.from("F-2", "권한이 없습니다.");
    }


    if ( article == null ) {
      return ResultData.from("F-1", Ut.f("%d번 게시물이 존재하지 않습니다.", id));
    }

    ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);

    if (actorCanModifyRd.isFail()) {
      return actorCanModifyRd;
    }

    return articleService.modifyArticle(id, title, body);
  }
}
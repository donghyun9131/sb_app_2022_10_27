package com.sbs.exam.sb_app_2022_10_27.controller;

import com.sbs.exam.sb_app_2022_10_27.service.ArticleService;
import com.sbs.exam.sb_app_2022_10_27.service.BoardService;
import com.sbs.exam.sb_app_2022_10_27.util.Ut;
import com.sbs.exam.sb_app_2022_10_27.vo.Article;
import com.sbs.exam.sb_app_2022_10_27.vo.Board;
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

  private ArticleService articleService;
  private BoardService boardService;

  public UsrArticleController(ArticleService articleService, BoardService boardService) {
    this.articleService = articleService;
    this.boardService = boardService;
  }



  @RequestMapping("/usr/article/write")

  public String showWrite(HttpServletRequest req) {
    return "usr/article/write";
  }

  @RequestMapping("/usr/article/doWrite")
  @ResponseBody
  public String doWrite(HttpServletRequest req, String title, String body, String replaceUri) {
    Rq rq = (Rq) req.getAttribute("rq");


    if( Ut.empty(title)) {
      return rq.jsHistoryBack("title(을)를 입력해주세요.");
    }

    if (Ut.empty(body)) {
      return rq.jsHistoryBack( "body(을)를 입력해주세요.");
    }


    ResultData<Integer> writeArticleRd = articleService.writeArticle(rq.getLoginedMemberId(), title, body);

    int id = writeArticleRd.getData1();

    if(Ut.empty(replaceUri)) {
      replaceUri = Ut.f("../article/detail?id=%d", id);
    }

    return rq.jsReplace(Ut.f("%d번 게시물이 생성되었습니다.", id), replaceUri);
  }

  @RequestMapping("/usr/article/list")
  public String showList(HttpServletRequest req, Model model, int boardId) {
    Rq rq = (Rq) req.getAttribute("rq");

    Board board = boardService.getBoardById(boardId);

    if (board == null) {
      return rq.historyBackJsOnView(Ut.f("%d번 게시판은 존재하지 않습니다.", boardId));
    }

    int articlesCount = articleService.getArticlesCount(boardId);

    List<Article> articles = articleService.getForPrintArticles(rq.getLoginedMemberId(), boardId);

    model.addAttribute("board", board);
    model.addAttribute("articlesCount", articlesCount);
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
      return rq.jsHistoryBack("로그인 후 이용해주세요.");
    }

    Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

    if ( article == null ) {
      return rq.jsHistoryBack( Ut.f("%d번 게시물이 존재하지 않습니다.", id));
    }

    if (article.getMemberId() != rq.getLoginedMemberId()) {
      return rq.jsHistoryBack("권한이 없습니다.");
    }

    articleService.deleteArticle(id);

    return rq.jsReplace(Ut.f("%d번 게시물을 삭제하였습니다.", id), "../article/list");
  }

  @RequestMapping("/usr/article/modify")
  public String showModify(HttpServletRequest req, Model model, int id) {
    Rq rq = (Rq) req.getAttribute("rq");

    Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

    if ( article == null ) {
      return rq.historyBackJsOnView(Ut.f("%d번 게시물이 존재하지 않습니다.", id));
    }

    ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);

    if (actorCanModifyRd.isFail()) {
      return rq.historyBackJsOnView(actorCanModifyRd.getMsg());
    }

    model.addAttribute("article", article);

    return "usr/article/modify";
  }


  @RequestMapping("/usr/article/doModify")
  @ResponseBody
  public String doModify(HttpServletRequest req, int id, String title, String body) {
    Rq rq = (Rq) req.getAttribute("rq");

    if ( rq.isLogined() == false ) {
      return rq.jsHistoryBack("로그인 후 이용해주세요.");
    }

    Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

    if(article.getMemberId() != rq.getLoginedMemberId() ) {
      return rq.jsHistoryBack("권한이 없습니다.");
    }


    if ( article == null ) {
      return rq.jsHistoryBack(Ut.f("%d번 게시물이 존재하지 않습니다.", id));
    }

    ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);

    if (actorCanModifyRd.isFail()) {
      return rq.jsHistoryBack(actorCanModifyRd.getMsg());
    }

    articleService.modifyArticle(id, title, body);
    return rq.jsReplace(Ut.f("%d번 게시물이 수정 되었습니다.", id), Ut.f("../article/detail?id=%d", id));
  }
}
package com.sbs.exam.sb_app_2022_10_27.controller;

import com.sbs.exam.sb_app_2022_10_27.service.ArticleService;
import com.sbs.exam.sb_app_2022_10_27.service.BoardService;
import com.sbs.exam.sb_app_2022_10_27.util.Ut;
import com.sbs.exam.sb_app_2022_10_27.vo.Article;
import com.sbs.exam.sb_app_2022_10_27.vo.Board;
import com.sbs.exam.sb_app_2022_10_27.vo.ResultData;
import com.sbs.exam.sb_app_2022_10_27.vo.Rq;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class UsrArticleController {

  private ArticleService articleService;
  private BoardService boardService;
  private Rq rq;

  public UsrArticleController(ArticleService articleService, BoardService boardService, Rq rq) {
    this.articleService = articleService;
    this.boardService = boardService;
    this.rq = rq;
  }


  @RequestMapping("/usr/article/write")

  public String showWrite(HttpServletRequest req) {
    return "usr/article/write";
  }

  @RequestMapping("/usr/article/doWrite")
  @ResponseBody
  public String doWrite(int boardId, String title, String body, String replaceUri) {

    if( Ut.empty(title)) {
      return rq.jsHistoryBack("title(을)를 입력해주세요.");
    }

    if (Ut.empty(body)) {
      return rq.jsHistoryBack( "body(을)를 입력해주세요.");
    }


    ResultData<Integer> writeArticleRd = articleService.writeArticle(rq.getLoginedMemberId(), boardId, title, body);

    int id = writeArticleRd.getData1();

    if(Ut.empty(replaceUri)) {
      replaceUri = Ut.f("../article/detail?id=%d", id);
    }

    return rq.jsReplace(Ut.f("%d번 게시물이 생성되었습니다.", id), replaceUri);
  }

  @RequestMapping("/usr/article/list")
  public String showList(Model model, @RequestParam(defaultValue = "1") int boardId, @RequestParam(defaultValue = "1") int page,
                         @RequestParam(defaultValue = "title,body") String searchKeywordTypeCode, @RequestParam(defaultValue = "") String searchKeyword) {
    Board board = boardService.getBoardById(boardId);

    if (board == null) {
      return rq.historyBackJsOnView(Ut.f("%d번 게시판은 존재하지 않습니다.", boardId));
    }

    int articlesCount = articleService.getArticlesCount(boardId, searchKeywordTypeCode, searchKeyword);

    int itemsCountInAPage = 10;
    int pagesCount = (int)Math.ceil((double) articlesCount / itemsCountInAPage);
    List<Article> articles = articleService.getForPrintArticles(rq.getLoginedMemberId(), boardId, searchKeywordTypeCode, searchKeyword, itemsCountInAPage, page);

    model.addAttribute("boardId", boardId);
    model.addAttribute("pagesCount", pagesCount);
    model.addAttribute("page", page);
    model.addAttribute("articlesCount", articlesCount);
    model.addAttribute("articles", articles);

    return "usr/article/list";
  }

  @RequestMapping("/usr/article/detail")
  public String showDetail(Model model, int id) {

    Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

    model.addAttribute("article", article);

    return "usr/article/detail";
  }

  @RequestMapping("/usr/article/doIncreaseHitCountRd")
  @ResponseBody
  public ResultData<Integer> doIncreaseHitCountRd(int id) {
    ResultData<Integer> increaseHitCountRd = articleService.increaseHitCount(id);

    if (increaseHitCountRd.isFail()) {
      return increaseHitCountRd;
    }

    ResultData<Integer> rd = ResultData.newData(increaseHitCountRd, "hitCount", articleService.getArticleHitCount(id));

    rd.setData2("id", id);

    return rd;
  }

  @RequestMapping("/usr/article/doDelete")
  @ResponseBody
  public Object doDelete(int id) {

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
  public String showModify(Model model, int id) {
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
  public String doModify(int id, String title, String body) {
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
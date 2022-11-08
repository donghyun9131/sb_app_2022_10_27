package com.sbs.exam.sb_app_2022_10_27.service;

import com.sbs.exam.sb_app_2022_10_27.repository.ArticleRepository;
import com.sbs.exam.sb_app_2022_10_27.util.Ut;
import com.sbs.exam.sb_app_2022_10_27.vo.Article;
import com.sbs.exam.sb_app_2022_10_27.vo.ResultData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

  private ArticleRepository articleRepository;

  public ArticleService(ArticleRepository articleRepository) {           // 생성자 주입
    this.articleRepository = articleRepository;
  }


  public ResultData writeArticle(int memberId, String title, String body) {
    articleRepository.writeArticle(memberId, title, body);
    int id = articleRepository.getLastInsertId();

    return ResultData.from("S-1", Ut.f("%d번 게시물이 생성되었습니다.", id), "id", id);
  }

  public List<Article> getForPrintArticles() {
    return articleRepository.getForPrintArticles();
  }

  public Article getForPrintArticle(int id) {
    return articleRepository.getForPrintArticle(id);
  }

  public void deleteArticle(int id) {
    articleRepository.deleteArticle(id);
  }

  public ResultData<Article> modifyArticle(int id, String title, String body) {
    articleRepository.modifyArticle(id, title, body);

    Article article = getForPrintArticle(id);
    return ResultData.from("S-1", Ut.f("%d번 게시물을 수정했습니다.", id), "article", article);
  }

  public ResultData actorCanModify (int actorId, Article article) {
    if ( article == null ) {
      return ResultData.from("F-1", "권한이 없습니다.");
    }

    if ( article.getMemberId() != actorId ) {
      return ResultData.from("F-2", "권한이 없습니다.");
    }

    return ResultData.from("S-1", "수정 가능.");
  }
}
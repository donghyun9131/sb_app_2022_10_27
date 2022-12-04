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

  public ArticleService(ArticleRepository articleRepository) {
    this.articleRepository = articleRepository;
  }

  public ResultData<Integer> writeArticle(int memberId, int boardId, String title, String body) {
    articleRepository.writeArticle(memberId, boardId, title, body);
    int id = articleRepository.getLastInsertId();

    return ResultData.from("S-1", Ut.f("%d번 게시물이 생성되었습니다.", id), "id", id);
  }

  public List<Article> getForPrintArticles(int actorId, int boardId, String searchKeywordTypeCode,
                                           String searchKeyword, int itemsCountInAPage, int page) {
    int limitStart = (page - 1) * itemsCountInAPage;
    int limitTake = itemsCountInAPage;

    List<Article> articles = articleRepository.getFroPrintArticles(boardId, searchKeywordTypeCode, searchKeyword,
        limitStart, limitTake);

    for (Article article : articles) {
      updateForPrintData(actorId, article);
    }

    return articles;
  }

  public Article getForPrintArticle(int actorId, int id) {
    Article article = articleRepository.getForPrintArticle(id);

    updateForPrintData(actorId, article);

    return article;
  }

  private void updateForPrintData(int actorId, Article article) {
    if (article == null) {
      return;
    }

    ResultData actorCanDeleteRd = actorCanDelete(actorId, article);
    article.setExtra__actorCanDelete(actorCanDeleteRd.isSuccess());

    ResultData actorCanModifyRd = actorCanModify(actorId, article);
    article.setExtra__actorCanModify(actorCanModifyRd.isSuccess());
  }

  public void deleteArticle(int id) {
    articleRepository.deleteArticle(id);
  }

  public ResultData<Article> modifyArticle(int id, String title, String body) {
    articleRepository.modifyArticle(id, title, body);

    Article article = getForPrintArticle(0, id);

    return ResultData.from("S-1", Ut.f("%d번 게시물이 수정되었습니다.", id), "article", article);
  }

  public ResultData actorCanModify(int actorId, Article article) {
    if (article == null) {
      return ResultData.from("F-1", "게시물이 존재하지 않습니다.");
    }

    if (article.getMemberId() != actorId) {
      return ResultData.from("F-2", "권한이 없습니다.");
    }

    return ResultData.from("S-1", "게시물 수정이 가능합니다.");
  }

  public ResultData actorCanDelete(int actorId, Article article) {
    if (article == null) {
      return ResultData.from("F-1", "게시물이 존재하지 않습니다.");
    }

    if (article.getMemberId() != actorId) {
      return ResultData.from("F-2", "권한이 없습니다.");
    }

    return ResultData.from("S-1", "게시물 삭제가 가능합니다.");
  }

  public int getArticlesCount(int boardId, String searchKeywordTypeCode, String searchKeyword) {
    return articleRepository.getArticlesCount(boardId, searchKeywordTypeCode, searchKeyword);
  }

  public ResultData<Integer> increaseHitCount(int id) {
    int affectedRowsCount = articleRepository.increaseHitCount(id);

    if (affectedRowsCount == 0) {
      return ResultData.from("F-1", "해당 게시물이 존재하지 않습니다.", "affectedRowsCount", affectedRowsCount);
    }

    return ResultData.from("S-1", "조회수가 증가되었습니다.", "affectedRowsCount", affectedRowsCount);
  }

  public int getArticleHitCount(int id) {
    return articleRepository.getArticleHitCount(id);
  }

  public ResultData increaseGoodReactionPoint(int relId) {
    int affectedRowsCount = articleRepository.increaseGoodReactionPoint(relId);

    if (affectedRowsCount == 0) {
      return ResultData.from("F-1", "해당 게시물이 존재하지 않습니다.", "affectedRowsCount", affectedRowsCount);
    }

    return ResultData.from("S-1", "좋아요 수가 증가되었습니다.", "affectedRowsCount", affectedRowsCount);
  }

  public ResultData increaseBadReactionPoint(int relId) {
    int affectedRowsCount = articleRepository.increaseBadReactionPoint(relId);

    if (affectedRowsCount == 0) {
      return ResultData.from("F-1", "해당 게시물이 존재하지 않습니다.", "affectedRowsCount", affectedRowsCount);
    }

    return ResultData.from("S-1", "싫어요 수가 증가되었습니다.", "affectedRowsCount", affectedRowsCount);
  }
}
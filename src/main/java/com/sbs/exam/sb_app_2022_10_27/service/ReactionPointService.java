package com.sbs.exam.sb_app_2022_10_27.service;

import com.sbs.exam.sb_app_2022_10_27.repository.ReactionPointRepository;
import com.sbs.exam.sb_app_2022_10_27.vo.ResultData;
import org.springframework.stereotype.Service;

@Service
public class ReactionPointService {
  private ReactionPointRepository reactionPointRepository;
  private ArticleService articleService;

  public ReactionPointService(ReactionPointRepository reactionPointRepository, ArticleService articleService) {
    this.reactionPointRepository = reactionPointRepository;
    this.articleService = articleService;
  }

  // 자격 확인
  public ResultData actorCanMakeReactionPoint(int actorId, String relTypeCode, int relId) {
    if (actorId == 0) {
      return ResultData.from("F-1", "로그인 후 이용해주세요.");
    }

    int sumReactionPointByMemberId = reactionPointRepository.getSumReactionPointByMemberId(relTypeCode, relId, actorId);

    if (sumReactionPointByMemberId != 0) {
      return ResultData.from("F-2", "리액션이 불가능합니다.", "sumReactionPointByMemberId", sumReactionPointByMemberId);
    }

    return ResultData.from("S-1", "리액션이 가능합니다.", "sumReactionPointByMemberId", sumReactionPointByMemberId);
  }

  public ResultData addGoodReactionPoint(int actorId, String relTypeCode, int relId) {
    reactionPointRepository.addGoodReactionPoint(actorId, relTypeCode, relId);

    switch (relTypeCode) {
      case "article":
        articleService.increaseGoodReactionPoint(relId);
        break;
    }

    return ResultData.from("S-1", "좋아요를 누르셨습니다.");
  }

  public ResultData addBadReactionPoint(int actorId, String relTypeCode, int relId) {
    // 현재 사용자가 어느 게시판에 몇번째 글에 좋아요 취소를 했는 지 저장.
    reactionPointRepository.addBadReactionPoint(actorId, relTypeCode, relId);

    // 해당 게시글에 리액션 포인트 감소
    switch (relTypeCode) {
      case "article":
        articleService.increaseBadReactionPoint(relId);
        break;
    }

    return ResultData.from("S-1", "싫어요를 누르셨습니다.");
  }

  public ResultData deleteGoodReactionPoint(int actorId, String relTypeCode, int relId) {
    // 현재 사용자가 어느 게시판에 몇번째 글에 좋아요 취소를 했는지 저장
    reactionPointRepository.deleteReactionPoint(actorId, relTypeCode, relId);

    // 해당 게시글에 리액션 포인트 감소
    switch (relTypeCode) {
      case "article":
        articleService.decreaseGoodReactionPoint(relId);
        break;
    }

    return ResultData.from("S-1", "좋아요가 취소 처리 됬습니다.");
  }

  public ResultData deleteBadReactionPoint(int actorId, String relTypeCode, int relId) {
    reactionPointRepository.deleteReactionPoint(actorId, relTypeCode, relId);

    switch (relTypeCode) {
      case "article":
        articleService.decreaseBadReactionPoint(relId);
        break;
    }

    return ResultData.from("S-1", "싫어요가 취소 처리 됬습니다.");
  }
}

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="pageTitle" value="게시물 내용"/>
<%@ include file="../common/head.jspf" %>

<script>
  const params = {};
  params.id = parseInt(${param.id});
</script>

<script>
  function ArticleDetail__increaseHitCount() {
    const localStorageKey = 'article__' + ${param.id} + '__viewDone';

    if( localStorage.getItem(localStorageKey) ) {
      return;
    }

    localStorage.setItem(localStorageKey, true);

    $.get('/usr/article/doIncreaseHitCountRd',
      {
        id : params.id,
        ajaxMode: 'Y'
      }, function(data) {
        $('.article-detail__hit-count').empty().html(data.data1);
      }, 'json');
  }

  $(function() {
    // 실전코드
    // ArticleDetail__increaseHitCount();

    // 임시코드
    setTimeout(ArticleDetail__increaseHitCount(), 500);
  })
</script>

<section class="mt-5 con-min-width">
  <div class="con mx-auto px-3">
    <div class="table-box-type-1">
      <table border="1">
        <colgroup>
          <col width="200"/>
        </colgroup>
        <tbody>
        <tr>
          <th>번호</th>
          <td>
            <div class="badge badge-primary">
              ${article.id}
            </div>
          </td>
        </tr>
        <tr>
          <th>작성날짜</th>
          <td>${article.forPrintType2RegDate}</td>
        </tr>
        <tr>
          <th>수정날짜</th>
          <td>${article.forPrintType2UpdateDate}</td>
        </tr>
        <tr>
          <th>작성자</th>
          <td>${article.extra__writerName}</td>
        </tr>
        <tr>
          <th>조회</th>
          <td>
            <span class="badge badge-primary article-detail__hit-count">${article.hitCount}</span>
          </td>
        </tr>
        <tr>
          <th>추천</th>
          <td>
            <div class="flex items-center">
              <span class="badge badge-primary">${article.goodReactionPoint}</span>
              &nbsp;
              <c:if test="${actorCanMakeReaction}">
                <a href="/usr/reactionPoint/doGoodReaction?relTypeCode=article&relId=${param.id}&replaceUri=${rq.encodedCurrentUri}" class="btn btn-xs btn-primary btn-outline">
                  좋아요 👍
                </a>
                &nbsp;
                <a href="/usr/reactionPoint/doBadReaction?relTypeCode=article&relId=${param.id}&replaceUri=${rq.encodedCurrentUri}" class="btn btn-xs btn-secondary btn-outline">
                  싫어요 👎
                </a>
              </c:if>

              <c:if test="${actorCanCancelGoodReaction}">
                <a href="/usr/reactionPoint/doCancelGoodReaction?relTypeCode=article&relId=${param.id}&replaceUri=${rq.encodedCurrentUri}" class="btn btn-xs btn-primary">
                  좋아요 👍
                </a>
                &nbsp;
                <a onclick="alert(this.title); return false;" title="먼저 좋아요를 취소해주세요." href="#" class="btn btn-xs btn-secondary btn-outline">
                  싫어요 👎
                </a>
              </c:if>

              <c:if test="${actorCanCancelBadReaction}">
                <a onclick="alert(this.title); return false;" title="먼저 싫어요를 취소해주세요."  href="#" class="btn btn-xs btn-primary btn-outline">
                  좋아요 👍
                </a>
                &nbsp;
                <a href="/usr/reactionPoint/doCancelBadReaction?relTypeCode=article&relId=${param.id}&replaceUri=${rq.encodedCurrentUri}" class="btn btn-xs btn-secondary">
                  싫어요 👎
                </a>
              </c:if>
            </div>
          </td>
        </tr>
        <tr>
          <th>제목</th>
          <td>${article.title}</td>
        </tr>
        <tr>
          <th>내용</th>
          <td>${article.body}</td>
        </tr>
        </tbody>
      </table>
    </div>

    <div class="btns">
      <button class="btn btn-link" type="button" onclick="history.back()">뒤로가기</button>
      <c:if test="${article.extra__actorCanModify}">
        <a class="btn btn-link" href="../article/modify?id=${article.id}">게시물 수정</a>
      </c:if>
      <c:if test="${article.extra__actorCanDelete}">
        <a class="btn btn-link" onclick="if( confirm('정말 삭제하시겠습니까?') == false ) return false"
           href="../article/doDelete?id=${article.id}">게시물 삭제</a>
      </c:if>
    </div>

  </div>
</section>

<%@ include file="../common/foot.jspf" %>
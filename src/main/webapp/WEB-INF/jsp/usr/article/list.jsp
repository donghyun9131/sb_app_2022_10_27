<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="pageTitle" value="${board.name} 게시물 리스트" />
<%@ include file="../common/head.jspf" %>

<section class="mt-5 con-min-width">
  <div class="con mx-auto px-3">
    <div class="flex">
      <div>
        게시물 개수 : <span class="badge badge-primary">${articlesCount}</span>건
      </div>
      <div class="flex-grow"></div>
      <form>
        <input type="hidden" name="boardId" value="${param.boardId}">

        <select data-value="${param.searchKeywordTypeCode}" name="searchKeywordTypeCode" class="select select-bordered">
          <option disabled="disabled">검색타입</option>
          <option value="title">제목</option>
          <option value="body">내용</option>
          <option value="title,body">제목,내용</option>

          <input name="searchKeyword" type="text" class="ml-2 w-72 input input-bordered" placeholder="검색어" maxlength="20" value="${param.searchKeyword}">
          <button type="submit" class="ml-2 btn btn-primary">검색</button>
        </select>
      </form>
    </div>
    <div class="table-box-type-1 mt-3">
      <table class="table table-fixed">
        <colgroup>
          <col width="80"/>
          <col width="150"/>
          <col width="150"/>
          <col width="150"/>
          <col />
        </colgroup>
        <thead>
        <tr>
          <th>번호</th>
          <th>작성날짜</th>
          <th>수정날짜</th>
          <th>작성자</th>
          <th>제목</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="article" items="${articles}">
          <tr>
            <th>${article.id}</th>
            <th>${article.regDate.substring(2, 16)}</th>
            <th>${article.updateDate.substring(2, 16)}</th>
            <th>${article.extra__writerName}</th>
            <th>
              <a class="btn-text-link block w-full truncate" href="../article/detail?id=${article.id}" >
                ${article.title}
              </a>
            </th>
          </tr>
        </c:forEach>
        </tbody>
      </table>
    </div>
    <div class="page-menu mt-3 flex justify-center">
      <div class="btn-group">
        <c:set var="pageMenuArmLen" value="5"/>
        <c:set var="startPage" value="${page - pageMenuArmLen >= 1 ? page - pageMenuArmLen : 1}" />
        <c:set var="endPage" value="${page + pageMenuArmLen <= pagesCount ? page + pageMenuArmLen : pagesCount}" />

        <c:set var="pageBaseUri" value="?boardId=${boardId}" />
        <c:set var="pageBaseUri" value="${pageBaseUri}&searchKeywordTypeCode=${searchKeywordTypeCode}" />
        <c:set var="pageBaseUri" value="${pageBaseUri}&searchKeyword=${param.searchKeyword}" />

        <c:if test="${startPage > 1}">
          <a class="btn btn-sm" href="${pageBaseUri}&page=1">1</a>
          <c:if test="${startPage > 2}">
            <a href="btn btn-disabled">...</a>
          </c:if>
        </c:if>

        <c:forEach begin="${startPage}" end="${endPage}" var="i">
          <a class="btn btn-sm ${page == i ? 'btn-active' : ''}" href="${pageBaseUri}&page=${i}">${i}</a>
        </c:forEach>

        <c:if test="${endPage < pagesCount}">
          <c:if test="${endPage < pagesCount - 1}">
            <a href="btn btn-disabled">...</a>
          </c:if>
          <a class="btn btn-sm" href="${pageBaseUri}&page=${pagesCount}">${pagesCount}</a>
        </c:if>
      </div>
    </div>
  </div>
</section>

<%@ include file="../common/foot.jspf" %>
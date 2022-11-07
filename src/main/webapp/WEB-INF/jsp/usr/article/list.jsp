<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="pageTitle" value="게시물 리스트" />
<%@ include file="../common/head.jspf" %>
<section class="mt-5">
  <div class="container mx-auto px-3">
    <div class="table-box-type-1">
      <table border="1">
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
            <th>${article.memberId}</th>
            <th>
              <a href="../article/detail?id=${article.id}" class="hover:underline">${article.title}</a>
            </th>
          </tr>
        </c:forEach>
        </tbody>
      </table>
    </div>
  </div>
</section>

<%@ include file="../common/foot.jspf" %>
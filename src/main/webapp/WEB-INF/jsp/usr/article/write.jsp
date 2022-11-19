<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="pageTitle" value="게시물 작성" />
<%@ include file="../common/head.jspf" %>

<section class="mt-5">
  <div class="container mx-auto px-3">
    <form class="table-box-type-1" method="POST" action="../article/doWrite">
      <input type="hidden" name="id" value="${article.id}">

      <table border="1">
        <colgroup>
          <col width="200"/>
        </colgroup>
        <tbody>
        <tr>
          <th>작성자</th>
          <td>
            ${rq.loginedMember.nickname}
          </td>
        </tr>
        <tr>
          <th>제목</th>
          <td>
            <input name="title" class="input input-bordered w-96 max-w-xs" type="text" placeholder="제목" >
          </td>
        </tr>
        <tr>
          <th>내용</th>
          <td>
            <textarea class="textarea textarea-bordered w-full" name="body" rows="10" placeholder="내용"></textarea>
          </td>
        </tr>
        <tr>
          <th>수정</th>
          <td>
            <button type="submit" class="btn btn-primary">작성<button>
            <button type="button" onclick="history.back();" class="btn btn-outline btn-secondary">뒤로가기</button>
          </td>
        </tr>
        </tbody>
      </table>
    </form>

    <div class="btns">
    </div>

  </div>
</section>

<%@ include file="../common/foot.jspf" %>
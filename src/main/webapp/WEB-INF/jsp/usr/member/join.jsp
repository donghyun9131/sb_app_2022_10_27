<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="pageTitle" value="회원가입" />
<%@ include file="../common/head.jspf" %>


<section class="mt-5">
  <div class="container mx-auto px-3">
    <form class="table-box-type-1" method="post" action="../member/doJoin">
      <table border="1">
        <colgroup>
          <col width="200"/>
        </colgroup>
        <tbody>
        <tr>
          <th>로그인아이디</th>
          <td>
            <input name="loginId" class="input input-bordered w-96 max-w-xs" type="text" placeholder="로그인아이디를 입력해주세요." />
          </td>
        </tr>
        <tr>
          <th>로그인비밀번호</th>
          <td>
            <input name="loginPw" class="input input-bordered w-96 max-w-xs" type="password" placeholder="로그인비밀번호를 입력해주세요." />
          </td>
        </tr>
        <tr>
          <th>로그인비밀번호 확인</th>
          <td>
            <input name="loginPwConfirm" class="input input-bordered w-96 max-w-xs" type="password" placeholder="로그인비밀번호를 한번 더 입력해주세요." />
          </td>
        </tr>
        <tr>
          <th>이름</th>
          <td>
            <input name="name" class="input input-bordered w-96 max-w-xs" type="text" placeholder="이름을 입력해주세요." />
          </td>
        </tr>
        <tr>
          <th>닉네임</th>
          <td>
            <input name="nickname" class="input input-bordered w-96 max-w-xs" type="text" placeholder="닉네임을 입력해주세요." />
          </td>
        </tr>
        <tr>
          <th>휴대폰 번호</th>
          <td>
            <input name="cellphoneNo" class="input input-bordered w-96 max-w-xs" type="text" placeholder="휴대폰 번호를 입력해주세요." />
          </td>
        </tr>
        <tr>
          <th>이메일</th>
          <td>
            <input name="email" class="input input-bordered w-96 max-w-xs" type="text" placeholder="이메일을 입력해주세요." />
            <button type="submit" class="btn btn-primary">가입<button>
            <button type="button" onclick="history.back();" class="btn btn-outline btn-secondary">홈</button>
          </td>
        </tr>
        </tbody>
      </table>
    </form>
  </div>
</section>
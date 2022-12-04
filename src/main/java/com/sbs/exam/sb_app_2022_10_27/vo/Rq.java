package com.sbs.exam.sb_app_2022_10_27.vo;

import com.sbs.exam.sb_app_2022_10_27.service.MemberService;
import com.sbs.exam.sb_app_2022_10_27.util.Ut;
import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)           // request마다 Rq가 생성.
public class Rq {

  @Getter
  private boolean isLogined;

  @Getter
  private int loginedMemberId;

  @Getter
  private Member loginedMember;

  private HttpServletRequest req;
  private HttpServletResponse resp;
  private HttpSession session;

  public Rq(HttpServletRequest req, HttpServletResponse resp, MemberService memberService) {
    this.req = req;
    this.resp = resp;
    this.session = req.getSession();
    
    HttpSession httpSession = req.getSession();

    boolean isLogined = false;
    int loginedMemberId = 0;

    if (httpSession.getAttribute("loginedMemberId") != null) {
      isLogined = true;
      loginedMemberId = (int) session.getAttribute("loginedMemberId");
      loginedMember = memberService.getMemberById(loginedMemberId);
    }

    this.isLogined = isLogined;
    this.loginedMemberId = loginedMemberId;
    this.loginedMember = loginedMember;
    this.req.setAttribute("rq", this);
  }

  public void printHistoryBackJs(String msg) {
    resp.setContentType("text/html; charset=UTF-8");
    print(Ut.jsHistoryBack(msg));
  }

  public void print(String str) {
    try {
      resp.getWriter().append(str);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void println(String str) {
    print(str + "\n");
  }

  public void login(Member member) {
    session.setAttribute("loginedMemberId", member.getId());
  }

  public void logout() {
    session.removeAttribute("loginedMemberId");
  }

  public String historyBackJsOnView(String msg) {
    req.setAttribute("msg", msg);
    req.setAttribute("historyBack", true);
    return "common/js";
  }

  public String jsHistoryBack(String msg) {
    return Ut.jsHistoryBack(msg);
  }

  public String jsReplace(String msg, String uri) {
    return Ut.jsReplace(msg, uri);
  }

  // 이 메서드는 Rq 객체가 자연스럽게 생성되도록 유도하는 역할을 한다.
  // 지우면 안되고,
  // 편의를 위해 beforeActionInterceptor 에서 꼭 호출을 해줘야 한다.
  public void initOnBeforeActionInterceptor() {

  }

  public String getCurrentUri() {
    String currentUri = (String)req.getAttribute("javax.servlet.forward.request_uri");
    String queryString = req.getQueryString();

    if (queryString != null && queryString.length() > 0) {
      currentUri += "?" + queryString;
    }

    return currentUri;
  }

  public String getEncodedCurrentUri() {
    return Ut.getUriEncoded(getCurrentUri());
  }
}

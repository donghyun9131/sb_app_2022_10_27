package com.sbs.exam.sb_app_2022_10_27.interceptor;

import com.sbs.exam.sb_app_2022_10_27.vo.Rq;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class NeedLoginInterceptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handle) throws Exception {
    Rq rq = (Rq) req.getAttribute("rq");

    if ( !rq.isLogined() ) {
      rq.printHistoryBackJs("로그인 후 이용해주세요.");
      return false;
    }

    return HandlerInterceptor.super.preHandle(req, resp, handle);
  }
}

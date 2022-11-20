package com.sbs.exam.sb_app_2022_10_27.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class BeforeActionInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handle) throws Exception {
    // 이제는 Rq 객체가 자동으로 만들어지기 때문에 필요 없음

    return HandlerInterceptor.super.preHandle(req, resp, handle);
  }
}

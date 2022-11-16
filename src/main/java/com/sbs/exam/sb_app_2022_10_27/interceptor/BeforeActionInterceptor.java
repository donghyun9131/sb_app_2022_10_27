package com.sbs.exam.sb_app_2022_10_27.interceptor;

import com.sbs.exam.sb_app_2022_10_27.vo.Rq;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class BeforeActionInterceptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handle) throws Exception {
    Rq rq = new Rq(req, resp);                                     // Rq를 컨트롤러 단에 전달
    req.setAttribute("rq", rq);                             // Rq를 컨트롤러 단에 전달, Rq가 jsp까지 전달 (파급 효과가 크다.)

    return HandlerInterceptor.super.preHandle(req, resp, handle);
  }
}

package com.sbs.exam.sb_app_2022_10_27.interceptor;

import com.sbs.exam.sb_app_2022_10_27.vo.Rq;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class BeforeActionInterceptor implements HandlerInterceptor {
  private Rq rq;

  public BeforeActionInterceptor(Rq rq) {
    this.rq = rq;
  }



  @Override
  public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handle) throws Exception {
    rq.initOnBeforeActionInterceptor();

    return HandlerInterceptor.super.preHandle(req, resp, handle);
  }
}

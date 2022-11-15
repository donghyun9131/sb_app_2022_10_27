package com.sbs.exam.sb_app_2022_10_27.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {
  // beforeActionInterceptor 인터셉터 불러오기
  @Autowired
  BeforeActionInterceptor beforeActionInterceptor;

  // 이 함수는 인터셉터를 적용하는 역할을 합니다.
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(beforeActionInterceptor)
        .addPathPatterns("/**")
        .excludePathPatterns("/css/**")
        .excludePathPatterns("/js/**")
        .excludePathPatterns("/error");
  }
}

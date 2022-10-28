package com.sbs.exam.sb_app_2022_10_13.article.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {
  private int id;
  private String title;
  private String body;
}

package com.sbs.exam.sb_app_2022_10_27.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {
  private int id;
  private String regDate;
  private String updateDate;
  private int memberId;
  private String title;
  private String body;

  private String extra_writerName;
  private boolean extra__actorCanModify;
  private boolean extra__actorCanDelete;

  public String getRegDateForPrint() {
    return regDate.substring(2, 16);
  }
  public String getUpdateDateForPrint() {
    return updateDate.substring(2, 16);
  }
}

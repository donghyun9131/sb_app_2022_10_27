package com.sbs.exam.sb_app_2022_10_13.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
  private int id;
  private String regDate;
  private String updateDate;
  private String loginId;
  private String loginPw;
  private int authLevel;
  private String name;
  private String nickname;
  private String cellphoneNo;
  private String email;
  private boolean delStatus;
  private String delDate;
}
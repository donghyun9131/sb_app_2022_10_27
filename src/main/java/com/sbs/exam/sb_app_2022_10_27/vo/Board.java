package com.sbs.exam.sb_app_2022_10_27.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Board {
  private int id;
  private String regDate;
  private String updateDate;
  private String code;
  private String name;
  private boolean delStatus;
  private String delDate;
}

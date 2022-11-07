package com.sbs.exam.sb_app_2022_10_27.vo;

import lombok.Getter;

public class ResultData<DT> {
  // S-1 (성공)
  // F-1 (실패)
  @Getter
  private String resultCode;
  @Getter
  private String msg;
  @Getter
  private String data1Name;
  @Getter
  private DT data1;

  private ResultData() {

  }

  public static ResultData from(String resultCode, String msg) {
    return from(resultCode, msg, null, null);
  }

  public static <DT> ResultData<DT> from(String resultCode, String msg, String data1Name , DT data1) {
    ResultData<DT> rd = new ResultData<DT>();
    rd.resultCode = resultCode;
    rd.msg = msg;
    rd.data1Name = data1Name;
    rd.data1 = data1;

    return rd;
  }

  public boolean isSuccess() {
    return resultCode.startsWith("S-");
  }

  public boolean isFail() {
    return isSuccess() == false;
  }

  public static <DT>ResultData<DT> newData(ResultData oldRd, String data1Name, DT data1) {
    return from(oldRd.getResultCode(), oldRd.getMsg(), data1Name, data1);
  }
}
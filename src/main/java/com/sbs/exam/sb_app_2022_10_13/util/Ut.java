package com.sbs.exam.sb_app_2022_10_13.util;

public class Ut {
  public static boolean empty(Object obj) {
    if(obj == null) {
      return true;
    }

    if( obj instanceof String == false ) {
      return true;
    }

    String str = (String) obj;

    return str.trim().length() == 0;
  }

  public static Object f(String format, Object... args) {
    return String.format(format, args);
  }
}
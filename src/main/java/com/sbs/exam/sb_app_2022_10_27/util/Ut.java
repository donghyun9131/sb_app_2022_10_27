package com.sbs.exam.sb_app_2022_10_27.util;

import java.net.URLEncoder;
import java.security.MessageDigest;

public class Ut {
  public static boolean empty(Object obj) {
    if(obj == null) {
      return true;
    }

    if(obj instanceof Integer) {
      return ((int)obj) == 0;
    }

    if(obj instanceof Long) {
      return ((Long)obj) == 0;
    }

    if( obj instanceof String == false ) {
      return true;
    }

    String str = (String) obj;

    return str.trim().length() == 0;
  }

  public static String f(String format, Object... args) {
    return String.format(format, args);
  }

  public static String jsHistoryBack(String msg) {
    if (msg == null) {
      msg = "";
    }

    return Ut.f("""
           <script>
           const msg = '%s'.trim();
           if( msg.length > 0) {
              alert(msg);
           }
           history.back();
           </script> 
           """, msg);
  }

  public static String jsReplace(String msg, String uri) {
    if (msg == null) {
      msg = "";
    }

    if( uri == null ) {
      uri = "";
    }

    return Ut.f("""
           <script>
           const msg = '%s'.trim();
           if( msg.length > 0) {
              alert(msg);
           }
           location.replace('%s');
           </script> 
           """, msg, uri);
  }

  public static String getUriEncoded(String str) {
    try {
      return URLEncoder.encode(str, "UTF-8");
    } catch (Exception e) {
      return str;
    }
  }
}
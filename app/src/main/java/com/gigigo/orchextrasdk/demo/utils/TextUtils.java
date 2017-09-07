package com.gigigo.orchextrasdk.demo.utils;

public class TextUtils {
  public static String normalize(String text) {
    return text.replace("_"," ");
  }

  public static String capitalize(String text) {
    return text.substring(0,1).toUpperCase() + text.substring(1).toLowerCase();
  }
}

package com.ppamorim.calculator.utils;

/**
 * Created by pedro on 2/16/15.
 */
public class DebugUtil {

  public static final boolean DEBUG = true;

  public static void log(String message) {
    if(DebugUtil.DEBUG) {
      System.out.println("LOG: " + message);
    }
  }

}

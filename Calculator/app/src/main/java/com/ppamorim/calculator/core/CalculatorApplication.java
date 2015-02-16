package com.ppamorim.calculator.core;

import android.app.Application;
import com.activeandroid.ActiveAndroid;

/**
 * Created by pedro on 2/16/15.
 */
public class CalculatorApplication extends Application {

  public CalculatorApplication() {
    super();
  }

  @Override
  public void onCreate() {
    super.onCreate();
    ActiveAndroid.initialize(this);
  }
  @Override
  public void onTerminate() {
    super.onTerminate();
    ActiveAndroid.dispose();
  }

}

package com.ppamorim.calculator.utils;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.ValidationError;
import java.util.List;

public final class Common {
  public static String getFailedFieldNames(List<ValidationError> errors) {
    StringBuilder stringBuilder = new StringBuilder();
    for (ValidationError error : errors) {
      View view = error.getView();
      TextView textView = (TextView) view;
      List<Rule> failedRules = error.getFailedRules();
      String fieldName = textView.getHint().toString().toUpperCase().replaceAll(" ", "_");
      for (Rule failedRule : failedRules) {
        stringBuilder.append(fieldName).append(" ");
        Log.i(Rule.class.getSimpleName(), failedRule.toString());
      }
    }
    return stringBuilder.toString();
  }
}

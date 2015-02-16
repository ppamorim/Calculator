package com.ppamorim.calculator.core.service;

import com.activeandroid.query.Select;
import com.ppamorim.calculator.core.model.BaseModel;
import java.util.List;

/**
 * Created by pedro on 2/16/15.
 */
public class BaseQuery {

  public static List<? extends BaseModel> getAllFromModel(Class<? extends BaseModel> clazz) {
    return new Select().from(clazz).execute();
  }

  public static Object getItemFromModel(Class<? extends BaseModel> clazz, int position) {
    return new Select().from(clazz).where(BaseModel._RID, position).executeSingle();
  }

}

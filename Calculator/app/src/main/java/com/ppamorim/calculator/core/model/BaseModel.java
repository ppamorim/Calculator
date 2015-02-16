package com.ppamorim.calculator.core.model;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Cache;
import com.activeandroid.Model;
import com.activeandroid.TableInfo;
import com.ppamorim.calculator.utils.DebugUtil;

public abstract class BaseModel extends Model {

  public static final String _RID = "remote_id";

  public int getRemoteId() {
    return -1;
  }

  public static void truncate(Class<? extends Model> type) {
    try {
      TableInfo tableInfo = Cache.getTableInfo(type);
      ActiveAndroid.execSQL("delete from " + tableInfo.getTableName() + ";");
      ActiveAndroid.execSQL("delete from sqlite_sequence where name='" + tableInfo.getTableName() + "';");
    } catch (Exception e) {
      if(DebugUtil.DEBUG) {
        e.printStackTrace();
      }
    }
  }

}
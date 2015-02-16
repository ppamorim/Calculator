package com.ppamorim.calculator.model;

import android.provider.BaseColumns;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.ppamorim.calculator.core.model.BaseModel;

@Table(name = "History", id = BaseColumns._ID)
public class History extends BaseModel {

  public static final String TITLE = "title";

  @Column(name = TITLE)
  private float mCount = 0f;

  public History() {}

  public History(float count) {
    this.mCount = count;
  }

  public float getCount() {
    return mCount;
  }

}

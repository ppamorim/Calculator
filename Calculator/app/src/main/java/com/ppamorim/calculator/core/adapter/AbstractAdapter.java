package com.ppamorim.calculator.core.adapter;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.Filterable;
import java.util.List;
public abstract class AbstractAdapter<T> extends BaseAdapter {

  public final Context mContext;
  public List<T> mDataAll;
  public List<T> mData;

  public AbstractAdapter(Context context, List<T> list) {
    mContext = context;
    mData = list;
    if (this instanceof Filterable) {
      mDataAll = list;
    }
  }

  public final Context getContext(){
    return mContext;
  }

  @Override
  public int getCount() {
    return mData != null ? mData.size() : -1;
  }

  @Override
  public T getItem(int i) {
    return mData.get(i);
  }

  public List<T> getDataAll() {
    return mDataAll;
  }

  public int getCountAll() {
    return mDataAll.size();
  }

  public void setData(List<T> mData) {
    this.mData = mData;
  }

  @Override
  public long getItemId(int position) {
    return getItem(position).hashCode();
  }

}

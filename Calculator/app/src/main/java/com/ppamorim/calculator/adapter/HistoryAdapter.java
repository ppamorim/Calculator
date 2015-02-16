package com.ppamorim.calculator.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo.UndoAdapter;
import com.ppamorim.calculator.R;
import com.ppamorim.calculator.core.adapter.AbstractAdapter;
import com.ppamorim.calculator.model.History;
import java.util.List;

public class HistoryAdapter extends AbstractAdapter<History> implements UndoAdapter {

  private LayoutInflater mLayoutInflater;

  public HistoryAdapter(Context context, List<History> list) {
    super(context, list);
    mLayoutInflater = LayoutInflater.from(mContext);
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder holder;
    if (convertView != null) {
      holder = (ViewHolder) convertView.getTag();
    } else {
      convertView = mLayoutInflater.inflate(R.layout.adapter_history, parent, false);
      holder = new ViewHolder(convertView);
      convertView.setTag(holder);
    }

    History history = getItem(position);
    if(history != null) {
      holder.title.setText(String.valueOf(history.getCount()));
    }

    return convertView;
  }

  static class ViewHolder {
    @InjectView(R.id.history_title) TextView title;

    public ViewHolder(View view) {
      ButterKnife.inject(this, view);
    }
  }

  @Override
  public boolean hasStableIds() {
    return true;
  }

  @NonNull @Override public View getUndoView(int i, @Nullable View convertView, @NonNull ViewGroup viewGroup) {
    View view = convertView;
    if (view == null) {
      view = LayoutInflater.from(mContext).inflate(R.layout.undo_row, viewGroup, false);
    }
    return view;
  }

  @NonNull @Override public View getUndoClickView(@NonNull View view) {
    return view.findViewById(R.id.undo_row_undobutton);
  }
}

package com.ppamorim.calculator.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.joanzapata.android.asyncservice.api.annotation.InjectService;
import com.joanzapata.android.asyncservice.api.annotation.OnMessage;
import com.joanzapata.android.asyncservice.api.internal.AsyncService;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo.SimpleSwipeUndoAdapter;
import com.ppamorim.calculator.R;
import com.ppamorim.calculator.adapter.HistoryAdapter;
import com.ppamorim.calculator.core.view.FloatingEditText;
import com.ppamorim.calculator.core.view.PaperButton;
import com.ppamorim.calculator.model.History;
import com.ppamorim.calculator.service.internal.HistoryQuery;
import com.ppamorim.calculator.utils.event.UserEvent;
import java.util.ArrayList;
import java.util.Iterator;

import static com.joanzapata.android.asyncservice.api.annotation.OnMessage.Sender.ALL;

public class HistoryFragment extends Fragment {

  public static final String TAG = "MainActivity";
  private static final int INITIAL_DELAY_MILLIS = 300;

  private ArrayList<History> mHistories = new ArrayList<>();
  private HistoryAdapter mHistoryAdapter;

  @InjectService HistoryQuery service;

  @InjectView(R.id.edittext_result) FloatingEditText mEditResult;
  @InjectView(R.id.list_history) DynamicListView mListHistory;
  @InjectView(R.id.text_result) TextView mResult;
  @InjectView(R.id.calculate) PaperButton mCalculate;

  @OnClick(R.id.calculate) void onCalculateClick() {
    calculateMedium();
  }

  @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_history, container, false);
    ButterKnife.inject(this, view);
    AsyncService.inject(this);
    return view;
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    configFooter();
    configAdapter();
  }

  @Override public void setUserVisibleHint(boolean isVisibleToUser) {
    super.setUserVisibleHint(isVisibleToUser);
    if(isAdded() && isVisibleToUser) {
      service.getHistoryAsync(1l);
    }
  }

  private void configFooter() {
    mResult.setVisibility(View.GONE);
    mCalculate.setText(getResources().getString(R.string.calculate_medium));
  }

  private void configAdapter() {
    mHistoryAdapter = new HistoryAdapter(getActivity(), mHistories);
    SimpleSwipeUndoAdapter simpleSwipeUndoAdapter = new SimpleSwipeUndoAdapter(mHistoryAdapter, getActivity(), onDismissCallback);
    AlphaInAnimationAdapter animAdapter = new AlphaInAnimationAdapter(simpleSwipeUndoAdapter);
    animAdapter.setAbsListView(mListHistory);
    animAdapter.getViewAnimator().setInitialDelayMillis(INITIAL_DELAY_MILLIS);
    mListHistory.setAdapter(animAdapter);
    mListHistory.enableSimpleSwipeUndo();
  }

  private void calculateMedium() {
    if(mHistories != null && mHistories.size() > 0) {
      float total = 0f;
      for(History history : mHistories) {
        total += history.getCount();
      }
      mEditResult.setText(String.valueOf(total/mHistories.size()));
    }
  }

  @OnMessage
  public void onUserFetched(UserEvent response) {
    if(isAdded()) {
      mHistories.clear();
      ArrayList<History> list = new ArrayList<>((ArrayList<History>) response.getItems());
      Iterator<History> commentsInterator = list.iterator();
      while (commentsInterator.hasNext()) {
        final History comment = commentsInterator.next();
        if (comment.getCount() == 0f) {
          commentsInterator.remove();
        }
      }

      mHistories.addAll(list);
      mHistoryAdapter.notifyDataSetChanged();
    }
  }

  private OnDismissCallback onDismissCallback = new OnDismissCallback() {
    @Override public void onDismiss(@NonNull ViewGroup viewGroup, @NonNull int[] ints) {
      for(int position : ints) {
        service.removeHistoryAsync(mHistories.get(position).getRemoteId());
        mHistories.remove(position);
        mHistoryAdapter.notifyDataSetChanged();
      }
    }
  };

  public static HistoryFragment newInstance() {
    return new HistoryFragment();
  }

}

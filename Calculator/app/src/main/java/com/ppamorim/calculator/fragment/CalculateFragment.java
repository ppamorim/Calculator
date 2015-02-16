package com.ppamorim.calculator.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.joanzapata.android.asyncservice.api.annotation.InjectService;
import com.joanzapata.android.asyncservice.api.annotation.OnMessage;
import com.joanzapata.android.asyncservice.api.internal.AsyncService;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.ppamorim.calculator.MainActivity;
import com.ppamorim.calculator.R;
import com.ppamorim.calculator.core.view.FloatingEditText;
import com.ppamorim.calculator.model.History;
import com.ppamorim.calculator.service.internal.HistoryQuery;
import com.ppamorim.calculator.utils.Common;
import com.ppamorim.calculator.utils.event.UserEvent;
import java.util.ArrayList;
import java.util.List;

import static com.joanzapata.android.asyncservice.api.annotation.OnMessage.Sender.ALL;

public class CalculateFragment extends Fragment implements Validator.ValidationListener {

  private Validator mValidator;

  @InjectService HistoryQuery service;

  @NotEmpty(messageResId = R.string.empty_field)
  @InjectView(R.id.edittext_one) FloatingEditText mEditOne;

  @NotEmpty(messageResId = R.string.empty_field)
  @InjectView(R.id.edittext_two) FloatingEditText mEditTwo;

  @InjectView(R.id.edittext_result) EditText mEditResult;

  @OnClick(R.id.calculate) void onCalculateClick() {
    mValidator.validate();
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    configValidator();
  }

  @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_calculate, container, false);
    ButterKnife.inject(this, view);
    AsyncService.inject(this);
    return view;
  }

  @Override public void onValidationSucceeded() {
    calculate();
  }

  @Override public void onValidationFailed(List<ValidationError> errors) {
    //mEditResult.setText(Common.getFailedFieldNames(errors));
  }

  public void configValidator() {
    mValidator = new Validator(this);
    mValidator.setValidationListener(this);
  }

  public void calculate() {
    ((MainActivity)getActivity()).hideKeyboard();
    float value1 = Float.valueOf(mEditOne.getText().toString());
    float value2 = Float.valueOf(mEditTwo.getText().toString());
    float result = value1 / value2;
    save(result);
    mEditResult.setText(String.valueOf(result));
  }

  public void save(float result) {
    if(isEditTextEmpty(mEditOne) && isEditTextEmpty(mEditTwo) && isEditTextEmpty(mEditResult)) {
      service.addHistoryAsync(result);
    }
  }

  private boolean isEditTextEmpty(EditText editText) {
    return editText != null && editText.getText() != null && editText.getText().length() > 0;
  }

  @OnMessage
  public void onUserFetched(UserEvent response) {
    //mHistories.clear();
    //mHistories.addAll((ArrayList<History>)response.getItems());
    //mHistoryAdapter.notifyDataSetChanged();
    //text.setText(e.getName() + " " + e.getAge());
  }

  @OnMessage({UserEvent.class, UserEvent.class})
  public void onUserFetched() {
  }
  @OnMessage(UserEvent.class)
  public void onUserFetchedTest() {
  }

  @OnMessage(from = ALL)
  public void onUserFetchedFromAnywhere(UserEvent e) {
  }

  public static CalculateFragment newInstance() {
    return new CalculateFragment();
  }

}

package com.ppamorim.calculator;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.inputmethod.InputMethodManager;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.joanzapata.android.asyncservice.api.internal.AsyncService;
import com.ppamorim.calculator.adapter.TabsPagerAdapter;
import com.ppamorim.calculator.core.view.CustomSlidingTabStrip;
import com.ppamorim.calculator.fragment.CalculateFragment;
import com.ppamorim.calculator.fragment.HistoryFragment;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {

  private List<Fragment> arrayFragments = generateFragments();
  private TabsPagerAdapter mTabsPagerAdapter;

  @InjectView(R.id.toolbar) Toolbar mToolbar;
  @InjectView(R.id.sliding_tabs) CustomSlidingTabStrip mSlidingTabLayout;
  @InjectView(R.id.viewpager) ViewPager mViewPager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.inject(this);
  }

  @Override protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    configToolbar();
    configPagerView();
  }

  public void configToolbar() {
    mToolbar.setTitle(getResources().getString(R.string.app_name));
  }

  public void configPagerView() {
    if(arrayFragments.size() > 0) {
      mTabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager(), arrayFragments);
    }
    mViewPager.setOffscreenPageLimit(1);
    mViewPager.setAdapter(mTabsPagerAdapter);
    mSlidingTabLayout.setViewPager(mViewPager);
  }

  public ArrayList<Fragment> generateFragments() {
    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    fragmentArrayList.add(CalculateFragment.newInstance());
    fragmentArrayList.add(HistoryFragment.newInstance());
    return fragmentArrayList;
  }

  public void hideKeyboard() {
    runOnUiThread(new Runnable() {
      @Override public void run() {
        if(getCurrentFocus() != null) {
          InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
          inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
      }
    });
  }

}

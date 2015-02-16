package com.ppamorim.calculator.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;
import java.util.List;

public class TabsPagerAdapter extends FragmentPagerAdapter {
  public List<Fragment> mFragments;
  private List<String> mTagFragment;
  public TabsPagerAdapter(FragmentManager fragmentManager, List<Fragment> fragments, List<String> fragmentTag) {
    super(fragmentManager);
    mFragments = fragments;
    mTagFragment = fragmentTag;
  }
  public TabsPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
    super(fm);
    this.mFragments = fragments;
  }
  @Override
  public Fragment getItem(int position) {
    return mFragments.get(position);
  }
  @Override
  public int getCount() {
    return mFragments.size();
  }
  @Override
  public CharSequence getPageTitle(int position) {
    if(mTagFragment.size() > 0) {
      return mTagFragment.get(position);
    } else {
      return "ERRROR";
    }
  }
  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
    if (position >= getCount()) {
      FragmentManager manager = ((Fragment) object).getFragmentManager();
      FragmentTransaction trans = manager.beginTransaction();
      trans.remove((Fragment) object);
      trans.commitAllowingStateLoss();
    }
  }
  public void updateAllTags(String... fragmentsTag) {
    mTagFragment.clear();
    for(String value : fragmentsTag) {
      mTagFragment.add(value);
    }
    notifyDataSetChanged();
  }
}

package com.example.haiminhtran.alarm.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {


    List<Fragment> fragments;
    public void setFragments(List<Fragment> fragments) {
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public int getCount() {
        return fragments.size();
    }


}

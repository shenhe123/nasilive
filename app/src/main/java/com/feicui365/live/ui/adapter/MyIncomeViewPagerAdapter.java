package com.feicui365.live.ui.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.feicui365.live.ui.fragment.MyIncomeFragment;

import java.util.List;

public class MyIncomeViewPagerAdapter extends FragmentStatePagerAdapter {
List<MyIncomeFragment> fragments;
    List<String> title;
    public MyIncomeViewPagerAdapter(@NonNull FragmentManager fm, List<MyIncomeFragment> fragments, List<String> title) {
        super(fm);
        this.fragments=fragments;
        this.title=title;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position);
    }
}

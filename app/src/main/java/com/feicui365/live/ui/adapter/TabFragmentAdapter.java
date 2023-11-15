package com.feicui365.live.ui.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/*import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;*/

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.feicui365.live.R;
import com.feicui365.live.model.entity.LiveCategory;


import java.util.ArrayList;
import java.util.List;

public class TabFragmentAdapter extends FragmentPagerAdapter {

    private ArrayList<LiveCategory> mTitles;
    private Context mContext;
    private List<Fragment> mFragments;

    public TabFragmentAdapter(List<Fragment> fragments, ArrayList<LiveCategory> titles, FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
        this.mFragments = fragments;
        this.mTitles = titles;
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
        return mTitles.get(position).getTitle();
    }

    public View getTabView(int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_header, null);
        TextView textView=view.findViewById(R.id.tv_header);
        textView.setText(mTitles.get(position).getTitle());
        return view;
    }
}

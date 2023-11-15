package com.feicui365.live.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.feicui365.live.R;
import com.feicui365.live.widget.WheelListView;

import java.util.ArrayList;
import java.util.List;

public class WheelAdapter extends BaseAdapter {

    private List<String> mData = new ArrayList<>();

  public  void update(List<String> mWheelLabels) {
        mData.clear();
        mData.addAll(mWheelLabels);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        //这里多加了4个辅助的item是为了前后的item都能滚动到两线之间。
        return mData.size() + WheelListView.WHEEL_SIZE - 1;
    }

    @Override
    public String getItem(int position) {
        return mData.get(position - WheelListView.WHEEL_SIZE / 2);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.widget_wheel_item, null);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.tv_label_item_wheel);

        int start = WheelListView.WHEEL_SIZE / 2;
        int end = mData.size() + WheelListView.WHEEL_SIZE / 2 - 1;
        if (position < start || position > end) {
            //范围外的是辅助的item，不显示
            convertView.setVisibility(View.INVISIBLE);
        } else {
            textView.setText(getItem(position));
            convertView.setVisibility(View.VISIBLE);
        }
        return convertView;
    }
}
package cn.tillusory.tiui.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class TiViewPagerAdapter extends PagerAdapter {

    private List<? extends View> tiViewList;

    public TiViewPagerAdapter(List<? extends View> list) {
        this.tiViewList = list;
    }

    @Override
    public int getCount() {
        return tiViewList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = tiViewList.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(tiViewList.get(position));
    }
}

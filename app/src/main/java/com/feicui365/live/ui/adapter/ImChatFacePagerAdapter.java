package com.feicui365.live.ui.adapter;

import android.content.Context;

import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.annotation.Nullable;
/*import android.support.v7.widget.GridLayoutManager;*/
import android.support.v7.widget.RecyclerView;
/*import android.support.v4.view.PagerAdapter;*/

import com.feicui365.live.R;
import com.feicui365.live.interfaces.OnFaceClickListener;
import com.feicui365.live.util.FaceUtil;

import java.util.ArrayList;
import java.util.List;


public class ImChatFacePagerAdapter extends PagerAdapter {

    private static final int FACE_COUNT = 20;//每页20个表情
    private List<View> mViewList;

    public ImChatFacePagerAdapter(Context context, OnFaceClickListener onFaceClickListener) {
        LayoutInflater inflater = LayoutInflater.from(context);
        mViewList = new ArrayList<>();
        List<String> faceList = FaceUtil.getFaceList();
        int fromIndex = 0;
        int size = faceList.size();
        int pageCount = size / FACE_COUNT;
        if (size % FACE_COUNT > 0) {
            pageCount++;
            for (int i = 0, count = pageCount * FACE_COUNT - size; i < count; i++) {
                faceList.add("");
            }
        }
        for (int i = 0; i < pageCount; i++) {
            RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.view_chat_face_page, null, false);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(context, 7, GridLayoutManager.VERTICAL, false));
            int endIndex = fromIndex + FACE_COUNT;
            List<String> list = new ArrayList<>();
            for (int j = fromIndex; j < endIndex; j++) {
                list.add(faceList.get(j));
            }
            list.add("<");
            recyclerView.setAdapter(new ImChatFaceAdapter(list, inflater, onFaceClickListener));
            mViewList.add(recyclerView);
            fromIndex = endIndex;
        }
    }

    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(@Nullable View view, @Nullable Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(@Nullable ViewGroup container, int position) {
        View view = mViewList.get(position);
        container.addView(view);
        return view;
    }


    @Override
    public void destroyItem(@Nullable ViewGroup container, int position, @Nullable Object object) {
        container.removeView(mViewList.get(position));
    }
}

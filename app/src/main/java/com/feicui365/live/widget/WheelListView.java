package com.feicui365.live.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.feicui365.live.R;
import com.feicui365.live.ui.adapter.WheelAdapter;

import java.util.List;

public class WheelListView extends ListView implements AbsListView.OnScrollListener {

    public final static int WHEEL_SIZE = 5;

    private int mItemHeight;
    private int mCurrentPosition;
    private List<String> mLabels;
    private WheelAdapter mWheelAdapter;

    public WheelListView(Context context) {
        this(context, null);
    }

    public WheelListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WheelListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        refreshState(scrollState);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        refreshItems();
    }

    private void init() {
        mWheelAdapter = new WheelAdapter();
        setAdapter(mWheelAdapter);
        setVerticalScrollBarEnabled(false);
        setScrollingCacheEnabled(false);
        setCacheColorHint(Color.TRANSPARENT);
        setFadingEdgeLength(0);
        setOverScrollMode(OVER_SCROLL_NEVER);
        setDividerHeight(0);
        setOnScrollListener(this);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        Paint dividerPaint = new Paint();
        dividerPaint.setColor(Color.parseColor("#c1c1c1"));
        dividerPaint.setStrokeWidth(2);
        int viewWidth = getWidth();
        int startY = mItemHeight * (WHEEL_SIZE / 2);
        int endY = mItemHeight * (WHEEL_SIZE / 2 + 1);
        canvas.drawLine(0, startY, viewWidth, startY, dividerPaint);
        canvas.drawLine(0, endY, viewWidth, endY, dividerPaint);
        super.dispatchDraw(canvas);
    }

    /**
     * 设置滚轮的刻度列表
     */
    public void setLabels(List<String> labels) {
        mLabels = labels;
        mWheelAdapter.update(mLabels);
        initView();
    }

    /**
     * 获取当前滚轮位置的标签
     */
    public String getSelectLabel() {
        return mLabels.get(mCurrentPosition);
    }

    /**
     * 获取当前滚轮位置
     */
    public int getSelectIndex() {
        return mCurrentPosition;
    }

    private void initView() {
        View itemView = LayoutInflater.from(getContext())
                .inflate(R.layout.widget_wheel_item, null);
        itemView.measure(0, 0);
        mItemHeight = itemView.getMeasuredHeight();
        getLayoutParams().height = mItemHeight * WHEEL_SIZE;
        mWheelAdapter.update(mLabels);
    }

    private void refreshItems() {
        if (getChildAt(0) == null) {
            return;
        }
        int offset = WHEEL_SIZE / 2;
        int firstPosition = getFirstVisiblePosition();
        int position = firstPosition + offset;
        //判断第一个条目与父布局的距离,若大于条目高度的一半则加一
        if (Math.abs(getChildAt(0).getY()) > mItemHeight / 2) {
            position = firstPosition + offset + 1;
        }
        //记录选择的条目
        int temp = position - WHEEL_SIZE / 2;
        mCurrentPosition = temp < 0 ? 0 : temp;

        //只对position前后几个条目操作
        for (int i = position - offset; i <= position + offset; i++) {
            View itemView = getChildAt(i - firstPosition);
            if (itemView == null) continue;
            if (position == i) {
                itemView.setAlpha(1f);
            } else {
                int delta = Math.abs(i - position);
                //离position越远透明度却高
                itemView.setAlpha((float) Math.pow(0.4f, delta));
            }
        }
    }

    /**
     * 在onScrollStateChanged中调用,监听滚动事件
     */
    private void refreshState(int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE) {
            View itemView = getChildAt(0);
            if (itemView == null) {
                return;
            }
            float deltaY = itemView.getY();
            if (deltaY == 0) {
                return;
            }
            //控制item滚动到两线之间
            if (Math.abs(deltaY) < mItemHeight / 2) {
                smoothScrollBy(getDistance(deltaY), 50);
            } else {
                smoothScrollBy(getDistance(mItemHeight + deltaY), 50);
            }
        }
    }

    /**
     * 用于逐渐滑动到目标位置，参数scrollDistance越小返回值越小，滑动越慢。
     * 直到返回0，那么smoothScrollBy(0)就不会再引起refreshState的循环调用。
     */
    private int getDistance(float scrollDistance) {
        if (Math.abs(scrollDistance) <= 2) {
            return (int) scrollDistance;
        } else if (Math.abs(scrollDistance) < 12) {
            return scrollDistance > 0 ? 2 : -2;
        } else {
            return (int) (scrollDistance / 6);
        }
    }


}
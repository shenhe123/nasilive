package com.feicui365.live.live.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

//import android.support.annotation.NonNull;


import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.lxj.xpopup.core.BottomPopupView;

import com.feicui365.live.R;
import com.feicui365.live.interfaces.CommonPickerListener;

import java.util.ArrayList;
import java.util.List;


/**
 *

 */
public class CommonPickerPopup extends BottomPopupView {

    private int itemsVisibleCount = 7;
    private int itemTextSize = 18;
    public int dividerColor = 0xFFd5d5d5; //分割线的颜色
    public float lineSpace = 2.4f; // 条目间距倍数 默认2

    private WheelView wheelView;
    public CommonPickerPopup(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_xpopup_ext_common_picker;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        wheelView = findViewById(R.id.commonWheel);
        findViewById(R.id.btnCancel).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        TextView btnConfirm = findViewById(R.id.btnConfirm);

        btnConfirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = wheelView.getCurrentItem();
                if(commonPickerListener!=null) commonPickerListener.onItemSelected(index, list.get(index));
                dismiss();
            }
        });
        initWheelData();
    }

    private void initWheelData() {
        wheelView.setItemsVisibleCount(itemsVisibleCount);
        wheelView.setAlphaGradient(true);
        wheelView.setTextSize(itemTextSize);
        wheelView.setCyclic(false);
        wheelView.setDividerColor(dividerColor);
        wheelView.setDividerType(WheelView.DividerType.FILL);
        wheelView.setLineSpacingMultiplier(lineSpace);
        wheelView.setTextColorOut(getContext().getResources().getColor(R.color.black));
        wheelView.setTextColorCenter(getContext().getResources().getColor(R.color.black));
        wheelView.isCenterLabel(false);
        wheelView.setCurrentItem(currentItem);
        wheelView.setAdapter(new ArrayWheelAdapter<>(list));
        wheelView.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
            }
        });
    }
    private CommonPickerListener commonPickerListener;
    public CommonPickerPopup setCommonPickerListener(CommonPickerListener commonPickerListener){
        this.commonPickerListener = commonPickerListener;
        return this;
    }
    public CommonPickerPopup setItemTextSize(int textSize){
        this.itemTextSize = textSize;
        return this;
    }

    public CommonPickerPopup setItemsVisibleCount(int itemsVisibleCount){
        this.itemsVisibleCount = itemsVisibleCount;
        return this;
    }
    public CommonPickerPopup setLineSpace(float lineSpace){
        this.lineSpace = lineSpace;
        return this;
    }
    List<String> list = new ArrayList<>();
    /**
     * 设置选项数据
     */
    public CommonPickerPopup setPickerData(List<String> list) {
        this.list = list;
        return this;
    }

    int currentItem = 0;
    /**
     * 设置默认选中
     */
    public CommonPickerPopup setCurrentItem(int currentItem) {
        this.currentItem = currentItem;
        return this;
    }


}

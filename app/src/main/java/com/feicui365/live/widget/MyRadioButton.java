package com.feicui365.live.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioButton;



public class MyRadioButton extends RadioButton {

    public MyRadioButton(Context context) {
        super(context);
    }

    public MyRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setChecked(boolean checked) {
        //super.setChecked(checked);
    }

    public void doChecked(boolean checked){
        super.setChecked(checked);
    }

}

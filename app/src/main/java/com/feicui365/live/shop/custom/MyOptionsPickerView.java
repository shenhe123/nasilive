package com.feicui365.live.shop.custom;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.bigkoo.pickerview.configure.PickerOptions;
import com.bigkoo.pickerview.view.OptionsPickerView;

public class MyOptionsPickerView extends OptionsPickerView {
    public MyOptionsPickerView(PickerOptions pickerOptions) {
        super(pickerOptions);
    }



    @Override
    public void createDialog() {
        super.createDialog();
        Window window=getDialog().getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.argb(0, 0, 0, 0)));
            WindowManager.LayoutParams params = window.getAttributes();
            params.gravity = Gravity.CENTER_HORIZONTAL;
            window.getDecorView().setPadding(0, 0, 0, 0);
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(params);
        }
    }
}

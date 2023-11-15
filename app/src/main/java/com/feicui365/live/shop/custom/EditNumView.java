package com.feicui365.live.shop.custom;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.feicui365.live.R;

public class EditNumView extends LinearLayout implements View.OnClickListener {

    private int result;
    private ImageView iv_subtract;
    private TextView tv_result;
    private ImageView iv_plus;
    private int default_result = 0;


    GetNumListener getNumListener;

    public interface GetNumListener {
        void getNum(int num);
    }

    public void setDefault_result(int default_result) {
        this.default_result = default_result;
        iv_subtract.setImageResource(R.mipmap.btn_delete_pre);
    }

    public GetNumListener getGetNumListener() {
        return getNumListener;
    }

    public void setGetNumListener(GetNumListener getNumListener) {
        this.getNumListener = getNumListener;
    }

    public EditNumView(Context context) {
        super(context);

    }

    public EditNumView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_edit_num, this);
        iv_subtract = findViewById(R.id.iv_subtract);
        tv_result = findViewById(R.id.tv_result);
        iv_plus = findViewById(R.id.iv_plus);
        iv_subtract.setOnClickListener(this);
        iv_plus.setOnClickListener(this);
    }

    public int getResult() {
        return result;
    }

    public void setResult(int results) {
        result = results;
        tv_result.setText(String.valueOf(result));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_subtract:
                if (result == default_result) {
                    return;
                } else {
                    result--;
                    if (result == default_result) {
                        iv_subtract.setImageResource(R.mipmap.btn_delete);
                    } else {
                        iv_subtract.setImageResource(R.mipmap.btn_delete_pre);
                    }
                    tv_result.setText(String.valueOf(result));
                }
                if (getNumListener != null) {
                    getNumListener.getNum(result);
                }
                break;
            case R.id.iv_plus:
                result++;
                if (result > default_result) {
                    iv_subtract.setImageResource(R.mipmap.btn_delete_pre);
                }
                tv_result.setText(String.valueOf(result));
                if (getNumListener != null) {
                    getNumListener.getNum(result);
                }
                break;
        }
    }
}

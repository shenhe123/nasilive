package com.feicui365.live.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.feicui365.live.R;
import com.feicui365.live.model.entity.LiveAudienceListBean;
import com.feicui365.live.ui.adapter.LiveAudienceDetailAdapter;

import java.util.ArrayList;

public class AudienceListDialog extends BaseDialog {

    private Context mContext;

    private int type = 1;
    private RecyclerView rv_live_detail;
    private LinearLayout ll_nodata;

    public AudienceListDialog(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    public static AudienceListDialog create(Context context) {
        AudienceListDialog dialog = new AudienceListDialog(context);
        dialog.show();
        return dialog;
    }

    @Override
    protected boolean isBottomPop() {
        return true;
    }


    @Override
    protected int getContentView() {
        return R.layout.dialog_audience;
    }

    @Override
    protected void initView() {
        super.initView();
        rv_live_detail = findViewById(R.id.rv_live_detail);
        ll_nodata = findViewById(R.id.ll_nodata);
    }

    @Override
    protected void initEvents() {
        super.initEvents();

    }

    public void addData(ArrayList<LiveAudienceListBean> bean) {


        if (bean != null && bean.size() > 0) {
            LiveAudienceDetailAdapter liveAudienceDetailAdapter = new LiveAudienceDetailAdapter(bean, mContext);
            rv_live_detail.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
            rv_live_detail.setAdapter(liveAudienceDetailAdapter);
            ll_nodata.setVisibility(View.GONE);
            rv_live_detail.setVisibility(View.VISIBLE);
        } else {
            ll_nodata.setVisibility(View.VISIBLE);
            rv_live_detail.setVisibility(View.GONE);
        }
    }
}

package com.feicui365.live.ui.act;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.feicui365.live.R;
import com.feicui365.live.base.OthrBase2Activity;
import com.feicui365.live.ui.adapter.ReportItemAdapter;

import java.util.ArrayList;

public class ReportItemActivity extends OthrBase2Activity {
    RecyclerView rv_my_income;
    ArrayList<String> report_itmes=new ArrayList<>();
    ReportItemAdapter reportItemAdapter;
    String report_id="";
    String report_type="";
    @Override
    protected int getLayoutId() {
        return R.layout.report_item_activity;
    }

    @Override
    protected void initData() {
        report_id=getIntent().getStringExtra("REPORT_ID");
        if(report_id==null||report_id.equals("")){
            return;
        }
        report_type=getIntent().getStringExtra("REPORT_TYPE");
        if(report_type==null||report_type.equals("")){
            return;
        }

        report_itmes.add("色情低俗");
        report_itmes.add("政治敏感");
        report_itmes.add("违法犯罪");
        report_itmes.add("发布垃圾广告、售卖假货等");
        report_itmes.add("非原创内容");
        report_itmes.add("冒充官方");
        report_itmes.add("头像、昵称、签名违规");
        report_itmes.add("侵犯权益");
        report_itmes.add("涉嫌诈骗");
        report_itmes.add("疑似自我伤害");
        report_itmes.add("侮辱谩骂");

        setTitle("用户举报");
        rv_my_income=findViewById(R.id.rv_my_income);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ReportItemActivity.this);
        rv_my_income.setLayoutManager(linearLayoutManager);
        reportItemAdapter=new ReportItemAdapter(report_itmes, ReportItemActivity.this,report_id,report_type);
        rv_my_income.setAdapter(reportItemAdapter);


    }
}

package com.feicui365.live.ui.act;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.feicui365.live.R;
import com.feicui365.live.base.OthrBase2Activity;
import com.feicui365.live.util.HttpUtils;
import com.feicui365.live.util.LevelUtils;
import com.feicui365.live.util.MyUserInstance;

import java.math.BigDecimal;

import de.hdodenhof.circleimageview.CircleImageView;


public class MyLevelActivity extends OthrBase2Activity {

    CircleImageView civ_avatar;
    ImageView iv_level;
    TextView tv_name, tv_level_now, tv_level_next,tv_level_num_now,tv_level_num_next;
    ProgressBar progressbar;
    RelativeLayout rl_back;

    @Override
    protected int getLayoutId() {
        return R.layout.my_level_activity;
    }

    @Override
    protected void initData() {
        hideTitle(true);
        initView();
    }

    private void initView() {
        civ_avatar = findViewById(R.id.civ_avatar);
        iv_level = findViewById(R.id.iv_level);
        tv_name = findViewById(R.id.tv_name);
        tv_level_now = findViewById(R.id.tv_level_now);
        tv_level_next = findViewById(R.id.tv_level_next);
        progressbar = findViewById(R.id.progressbar);
        tv_level_num_now=findViewById(R.id.tv_level_num_now);
        tv_level_num_next=findViewById(R.id.tv_level_num_next);
        rl_back=findViewById(R.id.rl_back2);
        Glide.with(this).applyDefaultRequestOptions(new RequestOptions().placeholder(R.mipmap.moren)).load(MyUserInstance.getInstance().getUserinfo().getAvatar()).into(civ_avatar);
        Glide.with(this).load(LevelUtils.getUserLevel(MyUserInstance.getInstance().getUserinfo().getUser_level())).into(iv_level);
        tv_name.setText(MyUserInstance.getInstance().getUserinfo().getNick_name());



        HttpUtils.getInstance().getuserlevelinfo(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                JSONObject jsonObject = JSON.parseObject(response.body());
                if( HttpUtils.getInstance().swtichStatus(jsonObject)){
                    JSONObject data = jsonObject.getJSONObject("data");
                    JSONObject level = data.getJSONObject("level");
                    JSONObject next = data.getJSONObject("next");
                    if(level==null){
                        finish();
                    }

                    if(level.getString("level").equals("20")){
                        int now_level_point=Integer.parseInt(level.getString("point"));
                        tv_level_now.setText("LV"+level.getString("level"));
                        tv_level_next.setText("LV"+level.getString("level"));
                        tv_level_num_now.setText("经验值"+now_level_point);
                        tv_level_num_next.setText("距离升级"+0);
                        progressbar.setProgress(100);
                    }else {
                        int now_point=Integer.parseInt(MyUserInstance.getInstance().getUserinfo().getPoint());
                        int now_level_point=Integer.parseInt(level.getString("point"));
                        int next_level_point=Integer.parseInt(next.getString("point"));
                        tv_level_now.setText("LV"+level.getString("level"));
                        tv_level_next.setText("LV"+next.getString("level"));
                        tv_level_num_now.setText("经验值"+now_point);
                        tv_level_num_next.setText("距离升级"+(next_level_point-now_point));
                        double i= div(now_point-now_level_point,next_level_point-now_level_point,2);
                        int ii=new Double(i*100).intValue();

                        progressbar.setProgress(ii);
                    }


                }
            }
        });

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public  double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}

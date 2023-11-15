package com.feicui365.live.ui.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.feicui365.live.R;
import com.feicui365.live.base.OthrBase2Activity;
import com.feicui365.live.dialog.WheelDialog;
import com.feicui365.live.model.entity.QCloudData;
import com.feicui365.live.util.HttpUtils;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.live.util.ToastUtils;
import com.feicui365.live.util.WordUtil;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MySettingActivity extends OthrBase2Activity implements View.OnClickListener {

    RequestOptions options;
    CircleImageView civ_avatar;
    EditText tv_nickname, tv_qianming, tv_age, tv_high, tv_weight, tv_city, tv_work;
    TextView tv_phone, tv_sex, tv_star, tv_photos;
    WheelDialog wheelDialog_start, wheelDialog_sex;
    ArrayList<String> startlist = new ArrayList<>();
    ArrayList<String> sexlist = new ArrayList<>();
    RelativeLayout rl_sex;
    String temp_pic = "";
    String temp_pic_web = "";
    int type = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(WordUtil.getString(R.string.Edit_User_Info));
        tv_other.setText(WordUtil.getString(R.string.Save));
        tv_other.setVisibility(View.VISIBLE);
        initView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.my_setting_activty;
    }

    private void initView() {
        startlist.addAll(MyUserInstance.getInstance().Starts());
        sexlist.addAll(MyUserInstance.getInstance().Sex());
        civ_avatar = findViewById(R.id.civ_avatar);
        tv_nickname = findViewById(R.id.tv_nickname);
        tv_qianming = findViewById(R.id.tv_qianming);
        tv_phone = findViewById(R.id.tv_phone);
        tv_age = findViewById(R.id.tv_age);
        tv_high = findViewById(R.id.tv_high);
        tv_weight = findViewById(R.id.tv_weight);
        tv_city = findViewById(R.id.tv_city);
        tv_work = findViewById(R.id.tv_work);
        tv_sex = findViewById(R.id.tv_sex);
        tv_star = findViewById(R.id.tv_star);
        tv_photos = findViewById(R.id.tv_photos);
        rl_sex = findViewById(R.id.rl_sex);
        tv_qianming.setText(MyUserInstance.getInstance().getUserinfo().getProfile().getSignature());
        tv_nickname.setText(MyUserInstance.getInstance().getUserinfo().getNick_name());
        thumb = MyUserInstance.getInstance().getUserinfo().getAvatar();
        tv_age.setText(MyUserInstance.getInstance().getUserinfo().getProfile().getAge());
        tv_high.setText(MyUserInstance.getInstance().getUserinfo().getProfile().getHeight());
        tv_weight.setText(MyUserInstance.getInstance().getUserinfo().getProfile().getWeight());
        tv_city.setText(MyUserInstance.getInstance().getUserinfo().getProfile().getCity());
        tv_work.setText(MyUserInstance.getInstance().getUserinfo().getProfile().getCareer());


            tv_sex.setTextColor(getResources().getColor(R.color.black));
            if (MyUserInstance.getInstance().getUserinfo().getProfile().getGender()==1) {
                tv_sex.setText("男");
            } else {
                tv_sex.setText("女");
            }

        if (MyUserInstance.getInstance().getUserinfo().getProfile().getConstellation() != null) {
            if (MyUserInstance.getInstance().getUserinfo().getProfile().getConstellation().equals("")) {
                tv_star.setText("请选择星座");
            } else {
                tv_star.setTextColor(getResources().getColor(R.color.black));
                tv_star.setText(MyUserInstance.getInstance().getUserinfo().getProfile().getConstellation());
            }

        }


        Glide.with(this).applyDefaultRequestOptions(options).load(MyUserInstance.getInstance().getUserinfo().getAvatar()).into(civ_avatar);

        civ_avatar.setOnClickListener(this);


        if (MyUserInstance.getInstance().getUserinfo().getAccount().equals("")) {
            tv_phone.setText("绑定手机号");
            tv_phone.setOnClickListener(this);

        } else {
            tv_sex.setTextColor(getResources().getColor(R.color.black));
            tv_phone.setText(MyUserInstance.getInstance().getUserinfo().getAccount());
        }

        tv_sex.setOnClickListener(this);
        rl_sex.setOnClickListener(this);
        tv_star.setOnClickListener(this);
        tv_other.setOnClickListener(this);
        tv_photos.setOnClickListener(this);


        //头像监听
        setOnUploadFinshListener(new OnUploadFinshListener() {
            @Override
            public void onFinish(String url) {
                if (url == null) {
                    temp_pic_web = MyUserInstance.getInstance().getUserinfo().getAvatar();
                    saveType(type);
                    return;
                }

                if (url.equals("")) {
                    temp_pic_web = MyUserInstance.getInstance().getUserinfo().getAvatar();
                    saveType(type);
                    return;
                }
                temp_pic_web = url;
                saveType(type);
            }
        });

    }

    private void saveType(int i) {

        switch (i) {
            case 2:
                save("");
                break;
            case 3:
                save(temp_pic_web);
                break;
        }

    }

    private void uploadAvatar(String pic) {
        HttpUtils.getInstance().getTempKeysForCos(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                JSONObject jsonObject = HttpUtils.getInstance().check(response);
                if (HttpUtils.getInstance().swtichStatus(jsonObject)) {
                    JSONObject data = jsonObject.getJSONObject("data");
                    if (null != data) {
                        QCloudData qCloudData = (QCloudData) JSON.toJavaObject(data, QCloudData.class);
                        upLoadImage(qCloudData, pic);
                    }
                }

            }
        });
    }




    @Override
    protected void initData() {
        options = new RequestOptions()
                .placeholder(R.mipmap.moren)
                .centerCrop();
    }

    private void save(String url) {
        if (url.equals("")) {
            url = MyUserInstance.getInstance().getUserinfo().getAvatar();
        }
        String avatar = url;
        String nick_name = tv_nickname.getText().toString();
        String signature = tv_qianming.getText().toString();
        String gender = "";
        if (!tv_sex.getText().toString().equals("")) {
            if (tv_sex.getText().toString().equals("男")) {
                gender = "1";
            } else {
                gender = "0";
            }
        }

        String height = tv_high.getText().toString();
        String weight = tv_weight.getText().toString();
        String constellation = tv_star.getText().toString();
        String city = tv_city.getText().toString();
        String age = tv_age.getText().toString();
        String career = tv_work.getText().toString();


        HttpUtils.getInstance().editUserInfo(avatar, nick_name, signature, gender, height, weight, constellation, city, age, career, MyUserInstance.getInstance().getUserinfo().getProfile().getPhotos(), new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                JSONObject jsonObject = JSON.parseObject(response.body());
                hideLoading();
                if (jsonObject.getString("status").equals("0")) {
                    ToastUtils.showT("保存成功");
                    finish();
                }
            }
        });
    }


    private void showStar() {
        wheelDialog_start = new WheelDialog(this);
        wheelDialog_start.setLabels(startlist);
        wheelDialog_start.setOnWheelSelectListener(new WheelDialog.OnWheelSelectListener() {
            @Override
            public void onClickOk(int index, String selectLabel) {

                tv_star.setText(selectLabel);
                wheelDialog_start.cancel();
            }
        });
        wheelDialog_start.show();
    }

    private void showSex() {
        wheelDialog_sex = new WheelDialog(this);
        wheelDialog_sex.setLabels(sexlist);
        wheelDialog_sex.setOnWheelSelectListener(new WheelDialog.OnWheelSelectListener() {
            @Override
            public void onClickOk(int index, String selectLabel) {
                tv_sex.setText(selectLabel);
                wheelDialog_sex.cancel();
            }
        });
        wheelDialog_sex.show();
    }


    private void openPicChoose() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .selectionMode(PictureConfig.SINGLE)
                .enableCrop(true)
                .freeStyleCropEnabled(true)
                .showCropGrid(true)
                .forResult(PictureConfig.CHOOSE_REQUEST);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    ArrayList<String> temp = new ArrayList();
                    for (int i = 0; i < selectList.size(); i++) {
                        temp.add(selectList.get(i).getCutPath());
                    }
                    temp_pic = temp.get(0);
                    Glide.with(this).applyDefaultRequestOptions(new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)).load(temp_pic).into(civ_avatar);
                    break;

            }
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (MyUserInstance.getInstance().getUserinfo().getAccount() == null || MyUserInstance.getInstance().getUserinfo().getAccount().equals("")) {
            tv_phone.setText("绑定手机号");
            tv_phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(context, BindPhoneActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            tv_sex.setTextColor(getResources().getColor(R.color.black));
            tv_phone.setText(MyUserInstance.getInstance().getUserinfo().getAccount());
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.civ_avatar:
                openPicChoose();
                break;
            case R.id.tv_phone:
                Intent intent = new Intent();
                intent.setClass(context, BindPhoneActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_sex:
                showSex();
                break;
            case R.id.rl_sex:
                showSex();
                break;
            case R.id.tv_star:
                showStar();
                break;
            case R.id.tv_other:
                showLoading();

                //3,没换头像,没换照片
                if (temp_pic.equals("") ) {
                    type = 2;
                    saveType(type);
                }

                //4,换了头像,没换照片
                if (!temp_pic.equals("")) {
                    type = 3;
                    uploadAvatar(temp_pic);
                }
                break;
            case R.id.tv_photos:
                startActivity(new Intent(MySettingActivity.this, PhotoWallActivity.class));
            break;

        }
    }
}

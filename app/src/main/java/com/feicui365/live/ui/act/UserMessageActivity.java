package com.feicui365.live.ui.act;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.androidkun.xtablayout.XTabLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.base.Constants;
import com.feicui365.live.base.OthrBase2Activity;
import com.feicui365.live.contract.HomeContract;
import com.feicui365.live.interfaces.OnPicUpLoadPicsListener;
import com.feicui365.live.live.utils.ArmsUtils;
import com.feicui365.live.live.utils.GlideUtils;
import com.feicui365.live.model.entity.QCloudData;
import com.feicui365.live.net.APIService;
import com.feicui365.live.presenter.HomePresenter;
import com.feicui365.live.ui.fragment.DetailFragment;
import com.feicui365.live.util.HttpUtils;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.live.util.ToastUtils;
import com.feicui365.live.util.UploadUtils;
import com.feicui365.live.util.WordUtil;
import com.feicui365.nasinet.utils.AppManager;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.live2.V2TXLiveDef;
import com.tencent.live2.V2TXLivePusherObserver;
import com.tencent.qcloud.tuicore.component.imageEngine.impl.GlideEngine;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.tillusory.sdk.TiSDKManager;
import cn.tillusory.sdk.bean.TiRotation;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.functions.Consumer;

public class UserMessageActivity extends OthrBase2Activity implements View.OnClickListener {
    CircleImageView iv_user_img;
    TextView tv_nick_num;
    EditText et_nicknume;
    TextView tv_change_pic;
    Button bt_save;
    String temp_pic = "";
    int type = 0;
    String temp_pic_web = "";
    RequestOptions options;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_message;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("个人中心");
        tv_other.setVisibility(View.GONE);
        initView();
    }

    @Override
    protected void initData() {
    }

    protected void initView() {
        tv_nick_num = findViewById(R.id.tv_nick_num);
        iv_user_img = findViewById(R.id.iv_user_img);
        et_nicknume = findViewById(R.id.et_nicknume);
        tv_change_pic = findViewById(R.id.tv_change_pic);
        bt_save = findViewById(R.id.bt_save);
        String nickname = MyUserInstance.getInstance().getUserinfo().getNick_name();
        et_nicknume.setText(nickname);
        if (TextUtils.isEmpty(nickname)) {
            tv_nick_num.setText(0 + "/10");
        } else {
            tv_nick_num.setText(nickname.length() + "/10");
        }
        et_nicknume.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String value = s.toString();
                if (TextUtils.isEmpty(value)) {
                    tv_nick_num.setText(0 + "/10");
                } else {
                    tv_nick_num.setText(value.length() + "/10");
                }
            }
        });

        //头像监听
        setOnUploadFinshListener(new OthrBase2Activity.OnUploadFinshListener() {
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
        options = new RequestOptions()
                .placeholder(R.mipmap.moren)
                .centerCrop();

        thumb = MyUserInstance.getInstance().getUserinfo().getAvatar();
        Glide.with(this).applyDefaultRequestOptions(options).load(MyUserInstance.getInstance().getUserinfo().getAvatar()).into(iv_user_img);
        tv_change_pic.setOnClickListener(this);
        bt_save.setOnClickListener(this);
        iv_user_img.setOnClickListener(this);
    }


    @OnClick({R.id.tv_change_pic, R.id.bt_save, R.id.iv_user_img})
    public void onClick(View view) {
        if (isFastClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.iv_user_img:
            case R.id.tv_change_pic:
                cameraPhotoPop();
                break;
            case R.id.bt_save:
                showLoading();

                //3,没换头像,没换照片
                if (temp_pic.equals("")) {
                    type = 2;
                    saveType(type);
                }

                //4,换了头像,没换照片
                if (!temp_pic.equals("")) {
                    type = 3;
                    uploadAvatar(temp_pic);
                }
                break;
        }
    }


    public void cameraPhotoPop() {
        showPopDialog(this, context.getResources().getString(R.string.selec_upload_method));
    }


    public void showPopDialog(Activity context, String message) {
        View view = context.getLayoutInflater().inflate(R.layout.select_pop, null);
        final Dialog dialog = new Dialog(context, R.style.FullScreenDialogStyle);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.getDecorView().setPadding(0, 0, 0, 0);

        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        TextView titleTv = view.findViewById(R.id.title_tv);
        TextView tv_photo = view.findViewById(R.id.tv_photo);
        TextView tv_pic = view.findViewById(R.id.tv_pic);


        if (message.equals("")) {
            titleTv.setVisibility(View.GONE);
        } else {
            titleTv.setVisibility(View.VISIBLE);
            titleTv.setText(message);
        }

        tv_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                takePhoto();
//                onRvItemClickListener.onNegativeButtonClick(dialog);
            }
        });
        tv_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                openPictures();
//                onRvItemClickListener.OnRvItemClickListener(dialog);
            }
        });
    }


    private static OnRvItemClickListener onRvItemClickListener;

    public interface OnRvItemClickListener {
        void onNegativeButtonClick(Dialog dialog);

        void OnRvItemClickListener(Dialog dialog);
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


    private void save(String url) {
        if (url.equals("")) {
            url = MyUserInstance.getInstance().getUserinfo().getAvatar();
        }
        String avatar = url;
        String nick_name = et_nicknume.getText().toString();
        ;

        String signature = MyUserInstance.getInstance().getUserinfo().getProfile().getSignature();
        String gender = "";
        String height = MyUserInstance.getInstance().getUserinfo().getProfile().getHeight();
        String weight = MyUserInstance.getInstance().getUserinfo().getProfile().getWeight();
        String constellation = "";
        String city = MyUserInstance.getInstance().getUserinfo().getProfile().getCity();
        String age = MyUserInstance.getInstance().getUserinfo().getProfile().getAge();
        String career = MyUserInstance.getInstance().getUserinfo().getProfile().getCareer();
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

    private int mPerCount;

    private void takePhoto() {
        mPerCount = 0;
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.requestEach(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {

                        if (permission.granted) {
                            mPerCount++;
                            if (mPerCount == 3) {
                                PictureSelector.create((Activity) context)
                                        .openCamera(PictureMimeType.ofImage())
                                        .compress(true)//是否压缩
                                        .enableCrop(true)
                                        .freeStyleCropEnabled(true)
                                        .showCropGrid(true)
                                        .forResult(Constants.TAKE_PHOTO);

                            }
                        } else {
                            ToastUtils.showT("没有读写权限");
                        }

                    }
                });

    }

    private void openPictures() {
        mPerCount = 0;
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.requestEach(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {

                        if (permission.granted) {
                            mPerCount++;
                            if (mPerCount == 3) {
                                PictureSelector.create((Activity) context)
                                        .openGallery(PictureMimeType.ofImage())
                                        .maxSelectNum(1)
                                        .enableCrop(true)
                                        .freeStyleCropEnabled(true)
                                        .showCropGrid(true)
                                        .forResult(Constants.CHOSE_PICS);
                            }
                        } else {
                            ToastUtils.showT("没有读写权限");
                        }

                    }
                });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case Constants.CHOSE_PICS:
                temp_pic = PictureSelector.obtainMultipleResult(data).get(0).getCutPath();
                Glide.with(this).applyDefaultRequestOptions(new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)).load(temp_pic).into(iv_user_img);
                break;
            case Constants.TAKE_PHOTO:
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                if (selectList.get(0).isCut()) {
                    temp_pic = selectList.get(0).getCutPath();
                } else {
                    temp_pic = selectList.get(0).getPath();
                }
                Glide.with(this).applyDefaultRequestOptions(new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)).load(temp_pic).into(iv_user_img);
                break;
        }
    }


}

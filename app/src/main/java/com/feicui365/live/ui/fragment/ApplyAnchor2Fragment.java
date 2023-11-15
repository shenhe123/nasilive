package com.feicui365.live.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.feicui365.live.live.adapter.SwiftMessageAdapter;
import com.feicui365.live.model.entity.SwiftMessageBean;
import com.feicui365.live.model.entity.UserAuthInfoBean;
import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.ui.act.RejectActivity;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.feicui365.live.R;
import com.feicui365.live.base.OtherBaseFragment;
import com.feicui365.live.model.entity.QCloudData;
import com.feicui365.live.ui.act.ApplyAnchorActivity;
import com.feicui365.live.util.HttpUtils;
import com.feicui365.live.util.ToastUtils;
import com.feicui365.live.widget.NoSrcollViewPager;
import com.feicui365.live.util.MyUserInstance;

import java.util.List;

import static android.app.Activity.RESULT_OK;


public class ApplyAnchor2Fragment extends OtherBaseFragment implements View.OnClickListener {


    TextView tv_submit,tv_1;
    EditText et_realname, et_id, et_ali_account, et_ali_num;
    ImageView iv_front, iv_back, iv_hand, iv_binded_id;
    ApplyAnchorActivity act;
    int type = 0;
    LinearLayout ll_edit;
    ApplyAnchor2Fragment context;
    RequestOptions options = new RequestOptions()
            .centerCrop();

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Glide.with(context).applyDefaultRequestOptions(options).load(msg.obj.toString()).into(iv_front);
                    act.id_font = msg.obj.toString();
                    break;
                case 2:
                    Glide.with(context).applyDefaultRequestOptions(options).load(msg.obj.toString()).into(iv_back);
                    act.id_back = msg.obj.toString();
                    break;
                case 3:
                    Glide.with(context).applyDefaultRequestOptions(options).load(msg.obj.toString()).into(iv_hand);
                    act.id_all = msg.obj.toString();
                    break;
            }
        }
    };

    public ApplyAnchor2Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.appply_anchor_2_fragment, container, false);
        context = this;
        initView(view);
        return view;
    }

    private void initView(View view) {
        act = (ApplyAnchorActivity) getActivity();
        tv_submit = view.findViewById(R.id.tv_submit);
        tv_submit.setOnClickListener(this);
        et_realname = view.findViewById(R.id.et_realname);
        et_realname.setFocusable(true);
        et_realname.setFocusableInTouchMode(true);
        et_realname.requestFocus();
        et_id = view.findViewById(R.id.et_id);
        et_ali_account = view.findViewById(R.id.et_ali_account);
        et_ali_num = view.findViewById(R.id.et_ali_num);
        iv_front = view.findViewById(R.id.iv_front);
        iv_front.setOnClickListener(this);
        iv_back = view.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        iv_hand = view.findViewById(R.id.iv_hand);
        iv_hand.setOnClickListener(this);
        iv_binded_id = view.findViewById(R.id.iv_binded_id);
        ll_edit = view.findViewById(R.id.ll_edit);
        tv_1=view.findViewById(R.id.tv_1);
        setOnItemListener(new OnUploadFinshListener() {
            @Override
            public void onFinish(String url) {
                Message message = Message.obtain();//message.obj=bundle  传值也行
                message.what = type;
                message.obj = url;
                handler.sendMessage(message);
            }
        });

        if (MyUserInstance.getInstance().getUserinfo().getIs_anchor().equals("1")) {
            ll_edit.setVisibility(View.GONE);
            iv_binded_id.setVisibility(View.VISIBLE);
            tv_1.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_submit:
                if (MyUserInstance.getInstance().getUserinfo().getIs_anchor().equals("1")) {

//                    act.setFragmentSkipInterface(new ApplyAnchorActivity.FragmentSkipInterface() {
//                        @Override
//                        public void gotoFragment(NoSrcollViewPager viewPager) {
//                            /** 跳转到第二个页面的逻辑 */
//                            viewPager.setCurrentItem(2);
//                        }
//                    });
//                    /** 进行跳转 */
//                    act.skipToFragment();
                    HttpUtils.getInstance().identityAuth(act.real_name, act.user_id, act.id_font + "," + act.id_back , new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            JSONObject jsonObject = JSON.parseObject(response.body());
                            if (jsonObject.getString("status").equals("0")) {
                                startActivity(new Intent(getContext(),RejectActivity.class));
                                getActivity().finish();
//                                HttpUtils.getInstance().editUserInfo2(sex, age, height, weight, constellation, career, city, signature, new StringCallback() {
//                                    @Override
//                                    public void onSuccess(Response<String> response) {
//                                        JSONObject jsonObject = JSON.parseObject(response.body());
//                                        if (jsonObject.getString("status").equals("0")) {
//                                            initDialog();
//                                            updateUserInfo();
//                                        }
//                                    }
//                                });

                            }
                        }


                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                        }
                    });


                    startActivity(new Intent(getContext(), RejectActivity.class));
                } else {

                    ll_edit.setVisibility(View.VISIBLE);
                    iv_binded_id.setVisibility(View.GONE);



                    if (et_id.getText().toString().equals("")) {

                        ToastUtils.showT("请输入身份证号码");

                        return;
                    }

                    if (et_realname.getText().toString().equals("")) {
                        ToastUtils.showT("请输入真实姓名");
                        return;
                    }

                    if (act.id_font.equals("")) {
                        ToastUtils.showT("请上传身份证正面照片");
                        return;
                    }


                    if (act.id_back.equals("")) {
                        ToastUtils.showT("请上传身份证背面照片");
                        return;
                    }

//                    if (act.id_all.equals("")) {
//                        ToastUtils.showT("请上传手持身份证照片");
//                        return;
//                    }
                    act.real_name = et_realname.getText().toString();
                    act.user_id = et_id.getText().toString();
                    act.ali_account = et_ali_account.getText().toString();
                    act.ali_num = et_ali_num.getText().toString();

//                    act.setFragmentSkipInterface(new ApplyAnchorActivity.FragmentSkipInterface() {
//                        @Override
//                        public void gotoFragment(NoSrcollViewPager viewPager) {
//                            /** 跳转到第二个页面的逻辑 */
//                            viewPager.setCurrentItem(2);
//                        }
//                    });
//                    /** 进行跳转 */
//                    act.skipToFragment();

                    HttpUtils.getInstance().identityAuth(act.real_name, act.user_id, act.id_font + "," + act.id_back , new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            JSONObject jsonObject = JSON.parseObject(response.body());
                            if (jsonObject.getString("status").equals("0")) {
                                startActivity(new Intent(getContext(),RejectActivity.class));
                            }
                        }


                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                        }
                    });

                }
                break;
            case R.id.iv_front:
                type = 1;
                openPicChoose();
                break;
            case R.id.iv_back:
                type = 2;
                openPicChoose();
                break;
            case R.id.iv_hand:
                type = 3;
                openPicChoose();
                break;

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        HttpUtils.getInstance().getUserAuthInfo(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                hideLoading();
                try {
                    // JSONArray data = (JSONArray) HttpUtils.getInstance().check(response).getJSONArray("data");
                    JSONObject jsonObject = HttpUtils.getInstance().check(response);
                    if (HttpUtils.getInstance().swtichStatus(jsonObject)) {
                        JSONArray data = jsonObject.getJSONArray("data");

                        if (null != data) {
                            UserAuthInfoBean userAuthInfoBean = (UserAuthInfoBean) JSONObject.parseObject(data.toString(), UserAuthInfoBean.class);
                            et_realname.setText(userAuthInfoBean.getReal_name());
                            et_id.setText(userAuthInfoBean.getId_num());
                            String id_card_url = userAuthInfoBean.getId_card_url();
                            String front = id_card_url.substring(0, id_card_url.indexOf(","));
                            String back = id_card_url.substring(id_card_url.indexOf(","));
                            Glide.with(context).applyDefaultRequestOptions(options).load(front).into(iv_front);
                            Glide.with(context).applyDefaultRequestOptions(options).load(back).into(iv_back);


                            hideLoading();
                        }
                    }
                } catch (Exception e) {
                    Log.e("Exception", e.toString());
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
            }
        });
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
                    // 图片选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);

                    HttpUtils.getInstance().getTempKeysForCos(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            JSONObject data = HttpUtils.getInstance().check(response).getJSONObject("data");
                            if (null != data) {
                                QCloudData qCloudData = (QCloudData) JSON.toJavaObject(data, QCloudData.class);
                                if (selectList.get(0).isCut()) {
                                    upLoadImage(qCloudData, selectList.get(0).getCutPath());
                                } else {
                                    upLoadImage(qCloudData, selectList.get(0).getPath());
                                }
                            }
                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                        }
                    });

                    break;
            }
        }

    }


}

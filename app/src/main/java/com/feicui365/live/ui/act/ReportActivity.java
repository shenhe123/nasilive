package com.feicui365.live.ui.act;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.feicui365.live.R;
import com.feicui365.live.base.OthrBase2Activity;
import com.feicui365.live.util.ToastUtils;
import com.feicui365.live.model.entity.QCloudData;
import com.feicui365.live.util.HttpUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;

public class ReportActivity extends OthrBase2Activity implements OthrBase2Activity.OnUploadFinshListener, View.OnClickListener, BGASortableNinePhotoLayout.Delegate {
    BGASortableNinePhotoLayout npl_item_report_photos;
    TextView tv_report_pic_num, tv_3, tv_report, tv_sumbit;
    EditText et_report;
    private static final int RC_PHOTO_PREVIEW = 2;
    String report_item = "";
    String report_id = "";
    String report_type = "";
    String urls = "";
    ArrayList<String> upload_urls = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.report_activity;
    }

    @Override
    protected void initData() {
        initView();
    }

    private void initView() {
        setTitle("用户举报");
        setOnUploadFinshListener(this);
        report_item = getIntent().getStringExtra("REPORT_ITEM");
        report_id = getIntent().getStringExtra("REPORT_ID");
        report_type = getIntent().getStringExtra("REPORT_TYPE");
        tv_report = findViewById(R.id.tv_report);
        tv_report.setText(report_item);
        tv_3 = findViewById(R.id.tv_3);
        npl_item_report_photos = findViewById(R.id.npl_item_report_photos);
        npl_item_report_photos.setDelegate(this);
        tv_report_pic_num = findViewById(R.id.tv_report_pic_num);
        et_report = findViewById(R.id.et_report);
        et_report.setFilters(new InputFilter[]{new InputFilter.LengthFilter(200)});
        et_report.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                tv_3.setText((s.length() + 1) + "/200");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tv_sumbit = findViewById(R.id.tv_sumbit);
        tv_sumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading();
                if (npl_item_report_photos.getData().size() > 0) {
                    for (int i = 0; i < npl_item_report_photos.getData().size(); i++) {
                        int finalI = i;
                        HttpUtils.getInstance().getTempKeysForCos(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                JSONObject jsonObject = HttpUtils.getInstance().check(response);
                                if (HttpUtils.getInstance().swtichStatus(jsonObject)) {
                                    JSONObject data = jsonObject.getJSONObject("data");
                                    if (null != data) {
                                        QCloudData qCloudData = (QCloudData) JSON.toJavaObject(data, QCloudData.class);
                                        upLoadImage(qCloudData, npl_item_report_photos.getData().get(finalI));
                                    }
                                }

                            }
                        });

                    }
                } else {

                    switch (report_type) {
                        case "1":
                            HttpUtils.getInstance().reoprtAnchor(report_id, et_report.getText().toString(), report_item, urls, new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    JSONObject jsonObject = JSON.parseObject(response.body());
                                    if (HttpUtils.getInstance().swtichStatus(jsonObject)) {
                                        ToastUtils.showT("举报成功");
                                    }
                                    finish();
                                    hideLoading();
                                }

                                @Override
                                public void onError(Response<String> response) {
                                    super.onError(response);
                                    ToastUtils.showT("举报失败");
                                    hideLoading();
                                }
                            });
                            break;
                        case "2":
                            HttpUtils.getInstance().reoprtMoment(report_id, et_report.getText().toString(), report_item, urls, new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    JSONObject jsonObject = JSON.parseObject(response.body());
                                    if (HttpUtils.getInstance().swtichStatus(jsonObject)) {
                                        ToastUtils.showT("举报成功");
                                    }
                                    finish();
                                    hideLoading();
                                }

                                @Override
                                public void onError(Response<String> response) {
                                    super.onError(response);
                                    ToastUtils.showT("举报失败");
                                    hideLoading();
                                }
                            });
                            break;
                        case "3":
                            HttpUtils.getInstance().reoprtShortvideo(report_id, et_report.getText().toString(), report_item, urls, new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    JSONObject jsonObject = JSON.parseObject(response.body());
                                    if (HttpUtils.getInstance().swtichStatus(jsonObject)) {
                                        ToastUtils.showT("举报成功");
                                    }
                                    finish();
                                    hideLoading();
                                }

                                @Override
                                public void onError(Response<String> response) {
                                    super.onError(response);
                                    ToastUtils.showT("举报失败");
                                    hideLoading();
                                }
                            });
                            break;
                    }


                }


            }
        });
    }

    private void openPicChoose() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .selectionMode(PictureConfig.MULTIPLE)
                .maxSelectNum(4)
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
                    ArrayList<String> temp = new ArrayList();
                    for (int i = 0; i < selectList.size(); i++) {
                        temp.add(selectList.get(i).getCutPath());
                    }
                    npl_item_report_photos.setData(temp);
                    tv_report_pic_num.setText("上传图片"+" " + npl_item_report_photos.getItemCount() + "/4");
                    break;
            }
        }

    }

    @Override
    public void onFinish(String url) {
        upload_urls.add(url);

        if (upload_urls.size() == npl_item_report_photos.getData().size()) {

            for (int i = 0; i < upload_urls.size(); i++) {
                if (i == 0) {
                    urls = upload_urls.get(0);
                } else if (i > 0) {
                    urls = urls + "," + upload_urls.get(i);
                }
            }


            switch (report_type) {
                case "1":
                    HttpUtils.getInstance().reoprtAnchor(report_id, et_report.getText().toString(), report_item, urls, new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            JSONObject jsonObject = JSON.parseObject(response.body());

                            if (HttpUtils.getInstance().swtichStatus(jsonObject)) {
                                ToastUtils.showT("举报成功");
                            }
                            finish();
                            hideLoading();
                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);

                            ToastUtils.showT("举报失败");

                            hideLoading();
                        }
                    });
                    break;
                case "2":
                    HttpUtils.getInstance().reoprtMoment(report_id, et_report.getText().toString(), report_item, urls, new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            JSONObject jsonObject = JSON.parseObject(response.body());

                            if (HttpUtils.getInstance().swtichStatus(jsonObject)) {
                                ToastUtils.showT("举报成功");
                            }
                            finish();
                            hideLoading();
                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);

                            ToastUtils.showT("举报失败");

                            hideLoading();
                        }
                    });
                    break;
                case "3":
                    HttpUtils.getInstance().reoprtShortvideo(report_id, et_report.getText().toString(), report_item, urls, new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            JSONObject jsonObject = JSON.parseObject(response.body());

                            if (HttpUtils.getInstance().swtichStatus(jsonObject)) {
                                ToastUtils.showT("举报成功");
                            }
                            finish();
                            hideLoading();
                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);

                            ToastUtils.showT("举报失败");

                            hideLoading();
                        }
                    });
                    break;
            }


        }

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onClickAddNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {
        openPicChoose();
    }

    @Override
    public void onClickDeleteNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        npl_item_report_photos.removeItem(position);
        tv_report_pic_num.setText("上传图片 " + npl_item_report_photos.getData().size() + "/4");
    }

    @Override
    public void onClickNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        Intent photoPickerPreviewIntent = new BGAPhotoPickerPreviewActivity.IntentBuilder(this)
                .previewPhotos(models) // 当前预览的图片路径集合
                .selectedPhotos(models) // 当前已选中的图片路径集合
                .maxChooseCount(npl_item_report_photos.getMaxItemCount()) // 图片选择张数的最大值
                .currentPosition(position) // 当前预览图片的索引
                .isFromTakePhoto(false) // 是否是拍完照后跳转过来
                .build();
        startActivityForResult(photoPickerPreviewIntent, RC_PHOTO_PREVIEW);
    }

    @Override
    public void onNinePhotoItemExchanged(BGASortableNinePhotoLayout sortableNinePhotoLayout, int fromPosition, int toPosition, ArrayList<String> models) {

    }
}

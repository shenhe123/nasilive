package com.feicui365.live.ui.act;

import android.Manifest;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.view.View;
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
import com.feicui365.live.model.entity.QCloudData;
import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.util.HttpUtils;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.live.util.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class PhotoWallActivity extends OthrBase2Activity implements BGASortableNinePhotoLayout.Delegate, EasyPermissions.PermissionCallbacks {
    BGASortableNinePhotoLayout mPhotosSnpl;
    TextView tv_save;
    private static final int PRC_PHOTO_PICKER = 1;
    private static final int RC_CHOOSE_PHOTO = 1;
    private static final int RC_PHOTO_PREVIEW = 2;
    ArrayList<String> photos_income = new ArrayList<>();
    ArrayList<String> photos_news = new ArrayList<>();
    ArrayList<String> photos_webs = new ArrayList<>();
    ArrayList<String> photos_news_web = new ArrayList<>();
    int upload=0;
    @Override
    protected int getLayoutId() {
        return R.layout.photo_wall_activity;
    }

    @Override
    protected void initData() {
        setTitle("照片墙");
        tv_save = findViewById(R.id.tv_save);
        mPhotosSnpl = findViewById(R.id.snpl_moment_add_photos);
        mPhotosSnpl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choicePhotoWrapper();
            }
        });
        mPhotosSnpl.setMaxItemCount(9);
        mPhotosSnpl.setDelegate(this);
        String photos = MyUserInstance.getInstance().getUserinfo().getProfile().getPhotos();
        if (photos != null) {
            if (!photos.equals("")) {
                String[] images = photos.split(",");
                for (int i = 0; i < images.length; i++) {
                    photos_income.add(images[i]);
                }
                mPhotosSnpl.setData(photos_income);
            }
        }


        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查看现有的照片,确定区分哪些是网络图片哪些不是
                if(mPhotosSnpl.getItemCount()==0){
                    ToastUtils.showT("最少需要保存一张照片");
                    return;
                }
                initSavePhoto(mPhotosSnpl.getData());

            }
        });

        //图片上传监听
        //照片墙监听
        setOnUploadFinshListener2(new OnUploadFinshListener() {
            @Override
            public void onFinish(String url) {
                upload++;
                if (url == null) {
                    return;
                }
                if (url.equals("")) {
                    return;
                }
                photos_news_web.add(url);
                if (upload == photos_news.size()) {
                    photos_webs.addAll(photos_news_web);
                    saveInfo(photos_webs);
                    return;
                }
            }
        });

    }

    private void initSavePhoto(ArrayList<String> pics){
        showLoading();
        upload=0;
        photos_webs.clear();
        photos_news.clear();
        photos_news_web.clear();
        for (int i = 0; i < pics.size(); i++) {
            if (pics.get(i).contains("http")) {
                photos_webs.add(pics.get(i));
            } else {
                photos_news.add(pics.get(i));
            }
        }

        //如果有新图就表示有本地图片,相反全是网络图片
        if (photos_news.size() > 0) {
            for (int i = 0; i < photos_news.size(); i++) {
                int finalI = i;
                HttpUtils.getInstance().getTempKeysForCos(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject data = HttpUtils.getInstance().check(response).getJSONObject("data");
                        if (null != data) {
                            QCloudData qCloudData = (QCloudData) JSON.toJavaObject(data, QCloudData.class);
                          //  upLoadImage2(qCloudData, photos_news.get(finalI));

                        }

                    }
                });
            }
        } else {
            saveInfo(photos_webs);
        }

    }


    private void saveInfo(ArrayList<String> pics){
        String picUrl="";
        for (int i = 0; i < pics.size(); i++) {
            if (i < pics.size() - 1) {
                picUrl = picUrl + pics.get(i) + ",";
            } else {
                picUrl = picUrl + pics.get(i);
            }
        }


        UserRegist user= MyUserInstance.getInstance().getUserinfo();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showLoading();
            }
            });


        String finalPicUrl = picUrl;
        HttpUtils.getInstance().editUserInfo(user.getAvatar(), user.getNick_name(), user.getSignature(), user.getGender(), user.getHeight(), user.getWeight(), user.getConstellation(), user.getCity(), user.getAge(), user.getCareer(), picUrl, new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                JSONObject jsonObject = JSON.parseObject(response.body());
                hideLoading();
                if (jsonObject.getString("status").equals("0")) {
                    ToastUtils.showT("保存成功");
                    MyUserInstance.getInstance().getUserinfo().getProfile().setPhotos(finalPicUrl);
                    finish();
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                hideLoading();
                ToastUtils.showT("保存失败");
            }
        });

    }


    @AfterPermissionGranted(PRC_PHOTO_PICKER)
    private void choicePhotoWrapper() {

        if (null == mPhotosSnpl) {
            return;
        }

        if (mPhotosSnpl.getData().size() >= 9) {

            ToastUtils.showT("最多只能选择9张图片");
            return;
        }

        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
            File takePhotoDir = new File(Environment.getExternalStorageDirectory(), "TakePhoto");
            int temp_count = 9 - mPhotosSnpl.getData().size();

            PictureSelector.create(this)
                    .openGallery(PictureMimeType.ofImage())
                    .selectionMode(PictureConfig.MULTIPLE)
                    .maxSelectNum(temp_count)
                    .enableCrop(true)
                    .freeStyleCropEnabled(true)
                    .showCropGrid(true)
                    .forResult(RC_CHOOSE_PHOTO);


        } else {
            EasyPermissions.requestPermissions(this, "图片选择需要以下权限:\n\n1.访问设备上的照片\n\n2.拍照", PRC_PHOTO_PICKER, perms);
        }
    }

    @Override
    public void onClickAddNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {
        choicePhotoWrapper();
    }

    @Override
    public void onClickDeleteNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        mPhotosSnpl.removeItem(position);
    }

    @Override
    public void onClickNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        Intent photoPickerPreviewIntent = new BGAPhotoPickerPreviewActivity.IntentBuilder(this)
                .previewPhotos(models) // 当前预览的图片路径集合
                .selectedPhotos(models) // 当前已选中的图片路径集合
                .maxChooseCount(mPhotosSnpl.getMaxItemCount()) // 图片选择张数的最大值
                .currentPosition(position) // 当前预览图片的索引
                .isFromTakePhoto(false) // 是否是拍完照后跳转过来
                .build();
        startActivityForResult(photoPickerPreviewIntent, RC_PHOTO_PREVIEW);
    }

    @Override
    public void onNinePhotoItemExchanged(BGASortableNinePhotoLayout sortableNinePhotoLayout, int fromPosition, int toPosition, ArrayList<String> models) {

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == PRC_PHOTO_PICKER) {

            ToastUtils.showT("您拒绝了「图片选择」所需要的相关权限!");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            switch (requestCode) {
                case RC_CHOOSE_PHOTO:
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    if (selectList == null) {
                        return;
                    }
                    if (selectList.size() == 0) {
                        return;
                    }
                    ArrayList<String> temp = new ArrayList();
                    for (int i = 0; i < selectList.size(); i++) {
                        temp.add(selectList.get(i).getCutPath());
                    }
                    mPhotosSnpl.addMoreData(temp);
                    break;
                case RC_PHOTO_PREVIEW:
                    mPhotosSnpl.setData(BGAPhotoPickerPreviewActivity.getSelectedPhotos(data));
                    break;
            }
        }

    }

}

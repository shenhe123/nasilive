package com.feicui365.live.shop.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.base.Constants;
import com.feicui365.live.contract.ShopContract;
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.model.entity.QCloudData;
import com.feicui365.live.presenter.ShopPresenter;
import com.feicui365.live.shop.adapter.StarAdapter;
import com.feicui365.live.shop.entity.OrderGoods;
import com.feicui365.live.shop.entity.Star;
import com.feicui365.live.ui.act.PhotoInfoActivity;
import com.feicui365.live.util.MyCredentialProvider;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.live.util.ToastUtils;
import com.feicui365.nasinet.userconfig.AppStatusManager;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.cos.xml.CosXmlService;
import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.listener.CosXmlResultListener;
import com.tencent.cos.xml.model.CosXmlRequest;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.object.PutObjectRequest;
import com.tencent.cos.xml.model.object.PutObjectResult;
import com.tencent.qcloud.core.auth.QCloudCredentialProvider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class PublishGoodsCommentActivity extends BaseMvpActivity<ShopPresenter> implements ShopContract.View, EasyPermissions.PermissionCallbacks, BGASortableNinePhotoLayout.Delegate {
    private final int PRC_PHOTO_PICKER = 1;
    private final int RC_CHOOSE_PHOTO = 1;
    private final int RC_PHOTO_PREVIEW = 2;
    @BindView(R.id.snpl_moment_add_photos)
    public BGASortableNinePhotoLayout mPhotosSnpl;

    @BindView(R.id.iv_pic)
    ImageView iv_pic;
    @BindView(R.id.tv_color)
    TextView tv_color;
    @BindView(R.id.tv_good_title)
    TextView tv_good_title;
    @BindView(R.id.et_comment)
    EditText et_comment;
    @BindView(R.id.rv_star)
    RecyclerView rv_star;

    OrderGoods orderGoods;
    private CosXmlService cosXmlService;
    StarAdapter starAdapter;

    String goods_comment_url = "";
    int upload_count;
    int upload_fail_count;
    final int PUBLISH_COMMIT = 100000;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PUBLISH_COMMIT:
                    mPresenter.submitComment(String.valueOf(orderGoods.getId())
                            , String.valueOf(orderGoods.getGoodsid())
                            , String.valueOf(starAdapter.getStar())
                            , et_comment.getText().toString()
                            , goods_comment_url);
                    break;


            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_goods_comment_publish;
    }

    @Override
    protected void initData() {
        mPresenter.getTempKeysForCos();
    }

    @Override
    protected void initView() {
        AppStatusManager.getInstance().setAppStatus(1);
        mPresenter = new ShopPresenter();
        mPresenter.attachView(this);
        setTitle("发布评价");
        setOther("发布");
        hideOther(false);
        setOtherTextColor(R.color.color_FD255C);
        mPhotosSnpl.setDelegate(this);
        Bundle bundleObject = getIntent().getExtras();
        if (bundleObject == null) {
            finish();
            return;
        }
        if (bundleObject.getSerializable(Constants.ORDER_GOODS) == null) {
            finish();
            return;
        }

        orderGoods = (OrderGoods) bundleObject.getSerializable(Constants.ORDER_GOODS);
        initPage();
    }


    private void initPage() {
        String[] pic = orderGoods.getGoods().getThumb_urls().split(",");
        Glide.with(this).applyDefaultRequestOptions(new RequestOptions().centerCrop())
                .load(pic[0]).into(iv_pic);


        tv_good_title.setText(orderGoods.getGoods().getTitle());
        if (orderGoods.getSize() != null) {
            tv_color.setText(orderGoods.getColor() + orderGoods.getSize());
        } else {
            tv_color.setText(orderGoods.getColor());
        }
        ArrayList<Star> list = new ArrayList<>();
        Star s = new Star();
        s.setCheck(true);
        list.add(s);
        list.add(new Star());
        list.add(new Star());
        list.add(new Star());
        list.add(new Star());
        starAdapter = new StarAdapter(list);
        rv_star.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rv_star.setAdapter(starAdapter);

    }

    @Override
    public void onError(Throwable throwable) {

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
        startActivity(new Intent(context, PhotoInfoActivity.class).putExtra("data", models));
    }

    @Override
    public void onNinePhotoItemExchanged(BGASortableNinePhotoLayout sortableNinePhotoLayout, int fromPosition, int toPosition, ArrayList<String> models) {

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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(PRC_PHOTO_PICKER)
    private void choicePhotoWrapper() {

        int temp_count = 9 - mPhotosSnpl.getData().size();
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {

                        PictureSelector.create(this)
                                .openGallery(PictureMimeType.ofImage())
                                .selectionMode(PictureConfig.MULTIPLE)
                                .maxSelectNum(temp_count)
                                .enableCrop(true)
                                .freeStyleCropEnabled(true)
                                .showCropGrid(true)
                                .forResult(RC_CHOOSE_PHOTO);

                    } else {
                        ToastUtils.showT("图片选择需要以下权限:\\n\\n1.访问设备上的照片\\n\\n2.拍照");
                    }
                });


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


    private void initQCloud(QCloudData data) {
        String appid = MyUserInstance.getInstance().getUserConfig().getConfig().getQcloud_appid();
        String region = MyUserInstance.getInstance().getUserConfig().getConfig().getCos_region();

        // 创建 CosXmlServiceConfig 对象，根据需要修改默认的配置参数
        CosXmlServiceConfig serviceConfig = new CosXmlServiceConfig.Builder()
                .setAppidAndRegion(appid, region)
                .isHttps(true) // 使用 HTTPS 请求, 默认为 HTTP 请求
                .builder();

        QCloudCredentialProvider credentialProvider = new MyCredentialProvider(data.getCredentials().getTmpSecretId(),
                data.getCredentials().getTmpSecretKey(), data.getCredentials().getSessionToken(), data.getExpiredTime(), data.getStartTime());
        cosXmlService = new CosXmlService(this, serviceConfig, credentialProvider);
    }


    public void upLoadImage(String imagePath, CosXmlResultListener cosXmlResultListener) {
        if (cosXmlService == null) {
            ToastUtils.showT("上传组件初始化失败");
            return;
        }
        String name_temp = "IMG_ANDROID_" + getTime() + "_" + new Random().nextInt(99999);
        String bucket = MyUserInstance.getInstance().getUserConfig().getConfig().getCos_bucket(); //存储桶，格式：BucketName-APPID
        String cosPath = MyUserInstance.getInstance().getUserConfig().getConfig().getCos_folder_image() + "/" + name_temp; //对象在存储桶中的位置标识符，即称对象键
        String srcPath = imagePath; //本地文件的绝对路径
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, cosPath, srcPath);
        Set<String> headerKeys = new HashSet<>();
        headerKeys.add("Host");
        putObjectRequest.setSignParamsAndHeaders(null, headerKeys);
        cosXmlService.putObjectAsync(putObjectRequest, cosXmlResultListener);
    }

    @Override
    public void setTempKeysForCos(QCloudData data) {
        initQCloud(data);

    }


    @OnClick({R.id.tv_other})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_other:

                if (et_comment.getText().toString().equals("")) {
                    ToastUtils.showT("给想买的小伙伴们分享一下商品评价吧~");
                    return;
                }

                goods_comment_url = "";
                upload_count = 0;
                upload_fail_count = 0;
                Log.e("mPhotosSnpl", mPhotosSnpl.getData().size() + "");
                if (mPhotosSnpl.getData().size() > 0) {
                    for (int i = 0; i < mPhotosSnpl.getData().size(); i++) {

                        upLoadImage(mPhotosSnpl.getData().get(i), new CosXmlResultListener() {
                            @Override
                            public void onSuccess(CosXmlRequest request, CosXmlResult result) {

                                upload_count++;
                                hideLoading();
                                PutObjectResult putObjectResult = (PutObjectResult) result;
                                String url = putObjectResult.accessUrl;
                                goods_comment_url = goods_comment_url + url + ",";
                                checkCanPublish();
                            }

                            @Override
                            public void onFail(CosXmlRequest request, CosXmlClientException exception, CosXmlServiceException serviceException) {
                                upload_count++;
                                hideLoading();
                                ToastUtils.showT("图片上传失败");
                                upload_fail_count++;
                                checkCanPublish();
                            }
                        });
                    }
                } else {
                    mHandler.sendEmptyMessage(PUBLISH_COMMIT);
                }


                break;

        }
    }


    private void checkCanPublish() {

        //通过给定的次数判断
        if (upload_count != mPhotosSnpl.getData().size()) {

            return;
        }
        //如果次数相同,要看有没有上传失败的例子
        if (upload_count == mPhotosSnpl.getData().size()) {
            if (upload_fail_count > 0) {
                ToastUtils.showT("部分图片上传失败");
            } else {
                //如果等于0表示全部图片正常上传

                StringBuilder sb_title = new StringBuilder(goods_comment_url);
                sb_title.replace(sb_title.length() - 1, sb_title.length(), "");
                goods_comment_url = sb_title.toString();
                mHandler.sendEmptyMessage(PUBLISH_COMMIT);

            }
        }

    }

    @Override
    public void submitComment(BaseResponse baseResponse) {
      ToastUtils.showT("评价成功");
      finish();
    }
}

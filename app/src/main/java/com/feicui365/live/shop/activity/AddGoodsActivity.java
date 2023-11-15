package com.feicui365.live.shop.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.kyleduo.switchbutton.SwitchButton;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.base.Constants;
import com.feicui365.live.contract.ShopContract;
import com.feicui365.live.model.entity.QCloudData;
import com.feicui365.live.presenter.ShopPresenter;
import com.feicui365.live.shop.adapter.AddColorAdapter;
import com.feicui365.live.shop.adapter.AddSizeAdapter;
import com.feicui365.live.shop.adapter.AddSpeceAdapter;
import com.feicui365.live.shop.entity.AddColor;
import com.feicui365.live.shop.entity.AddInventory;
import com.feicui365.live.shop.entity.AddSize;
import com.feicui365.live.shop.entity.JsonBean;
import com.feicui365.live.shop.entity.Subcates;
import com.feicui365.live.shop.utils.GetJsonDataUtil;

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

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;

public class AddGoodsActivity extends BaseMvpActivity<ShopPresenter> implements ShopContract.View {

    @BindView(R.id.rv_goods_color)
    RecyclerView rv_goods_color;


    @BindView(R.id.rv_goods_sizes)
    RecyclerView rv_goods_sizes;


    @BindView(R.id.rv_goods_specs)
    RecyclerView rv_goods_specs;


    @BindView(R.id.rl_add_color)
    RelativeLayout rl_add_color;

    @BindView(R.id.rl_add_size)
    RelativeLayout rl_add_size;


    @BindView(R.id.tv_class_name)
    TextView tv_class_name;

    @BindView(R.id.bga_goods_pic)
    BGASortableNinePhotoLayout bga_goods_pic;//type1,标题图片

    @BindView(R.id.bga_goods_desc)
    BGASortableNinePhotoLayout bga_goods_desc; //type2,详情图片

    @BindView(R.id.rl_address_start)
    RelativeLayout rl_address_start;
    @BindView(R.id.tv_address_start)
    TextView tv_address_start;

    @BindView(R.id.et_delivery_time)
    EditText et_delivery_time;

    @BindView(R.id.et_good_desc)
    EditText et_good_desc;

    @BindView(R.id.sb_freight)
    SwitchButton sb_freight;


    @BindView(R.id.et_freight)
    EditText et_freight;

    @BindView(R.id.et_good_title)
    EditText et_good_title;


    AddColorAdapter addColorAdapter;
    List<AddColor> list_add_color = new ArrayList<>();


    AddSizeAdapter addSizeAdapter;
    List<AddSize> list_add_size = new ArrayList<>();

    AddSpeceAdapter addSpeceAdapter;
    ArrayList<AddInventory> list_add_spece = new ArrayList<>();

    private CosXmlService cosXmlService;
    //1,标题,2详情
    private int color_pic_postion;
    //商品类别
    private Subcates chose_category;

    private String province = "";//省
    private String city = "";//市
    private String district = "";//区
    private List<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private final int MSG_LOAD_DATA = 0x0001;
    private final int MSG_LOAD_SUCCESS = 0x0002;
    private final int MSG_LOAD_FAILED = 0x0003;
    private final int MSG_UPDATE_PIC = 0x0004;
    private final int MSG_SUMBIT = 0x0005;
    private Thread thread;
    private static boolean isLoaded = false;
    String goods_title_url="";

    String goods_desc_url="";
    int upload_count=0;
    int upload_all_count=0;
    int upload_fail_count=0;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 子线程中解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
                    isLoaded = true;
                    break;
                case MSG_UPDATE_PIC:
                    if(addColorAdapter!=null){
                        addColorAdapter.setImage(getApplicationContext(), color_pic_postion, list_add_color.get(color_pic_postion).getImg_url());
                    }

                    break;
                case MSG_SUMBIT:
                    submitGoods();
                    break;

            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_goods;
    }

    @Override
    protected void initData() {
        mPresenter.getTempKeysForCos();
    }

    @Override
    protected void initView() {
        mPresenter = new ShopPresenter();
        mPresenter.attachView(this);
        setTitle("添加商品");
        AppStatusManager.getInstance().setAppStatus(1);


        initAddColor();
        initAddSize();
        initAddSpece();
        initNinePhoto();
        initJsonData();
        initSwtich();
    }

    private void initSwtich() {
        sb_freight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    et_freight.setVisibility(View.GONE);
                } else {


                    et_freight.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    private void initNinePhoto() {
        bga_goods_pic.setDelegate(new BGASortableNinePhotoLayout.Delegate() {
            @Override
            public void onClickAddNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {

                openPicChoose(10 - models.size(), Constants.GET_PIC_TITLE);
            }

            @Override
            public void onClickDeleteNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
                sortableNinePhotoLayout.removeItem(position);
            }

            @Override
            public void onClickNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
                startActivity(new Intent(context, PhotoInfoActivity.class).putExtra("data", models));
            }

            @Override
            public void onNinePhotoItemExchanged(BGASortableNinePhotoLayout sortableNinePhotoLayout, int fromPosition, int toPosition, ArrayList<String> models) {

            }
        });

        bga_goods_desc.setDelegate(new BGASortableNinePhotoLayout.Delegate() {
            @Override
            public void onClickAddNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {

                openPicChoose(10 - models.size(), Constants.GET_PIC_DESC);
            }

            @Override
            public void onClickDeleteNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
                sortableNinePhotoLayout.removeItem(position);
            }

            @Override
            public void onClickNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
                startActivity(new Intent(context, PhotoInfoActivity.class).putExtra("data", models));
            }

            @Override
            public void onNinePhotoItemExchanged(BGASortableNinePhotoLayout sortableNinePhotoLayout, int fromPosition, int toPosition, ArrayList<String> models) {

            }
        });
    }

    private void initAddColor() {
        addColorAdapter = new AddColorAdapter(list_add_color);
        rv_goods_color.setLayoutManager(new LinearLayoutManager(this));
        rv_goods_color.setAdapter(addColorAdapter);
        addColorAdapter.bindToRecyclerView(rv_goods_color);
        addColorAdapter.setOnAddPicListener(new AddColorAdapter.OnAddPicListener() {
            @Override
            public void chosePic(int postion) {
                color_pic_postion = postion;
                openPicChoose(1, Constants.GET_COLOR_PIC);
            }

            @Override
            public void sumbitPic() {
                changeAddSpece();
            }
        });
    }


    private void initAddSize() {

        addSizeAdapter = new AddSizeAdapter(list_add_size);
        rv_goods_sizes.setLayoutManager(new LinearLayoutManager(this));
        rv_goods_sizes.setAdapter(addSizeAdapter);
        addSizeAdapter.setOnAddSizeListener(new AddSizeAdapter.OnAddSizeListener() {
            @Override
            public void sumbitSize() {
                changeAddSpece();
            }
        });

    }


    private void initAddSpece() {
        if (addSpeceAdapter == null) {
            addSpeceAdapter = new AddSpeceAdapter(list_add_spece);
            rv_goods_specs.setLayoutManager(new LinearLayoutManager(this));
            rv_goods_specs.setAdapter(addSpeceAdapter);
        } else {
            addSpeceAdapter.notifyDataSetChanged();
        }
    }


    private void changeAddSpece() {
        List<AddInventory> list_add_spece_temp = new ArrayList<>();

        for (int i = 0; i < list_add_color.size(); i++) {
            if (list_add_color.get(i).getColor() == null) {
                break;
            }
            if ("".equals(list_add_color.get(i).getColor())) {
                break;
            }

            if (list_add_size.size() == 0) {
                AddInventory add_inventory = new AddInventory();
                add_inventory.setColor(list_add_color.get(i).getColor());
                list_add_spece_temp.add(add_inventory);
            } else {

                for (int y = 0; y < list_add_size.size(); y++) {
                    AddInventory add_inventory = new AddInventory();
                    add_inventory.setColor(list_add_color.get(i).getColor());
                    add_inventory.setSize(list_add_size.get(y).getSize());
                    list_add_spece_temp.add(add_inventory);
                }
            }
        }

        for (int i = 0; i < list_add_spece_temp.size(); i++) {
            AddInventory add_inventory = list_add_spece_temp.get(i);
            for (int y = 0; y < list_add_spece.size(); y++) {

                if (list_add_spece.get(y).getSize().equals(add_inventory.getSize())
                        & list_add_spece.get(y).getColor().equals(add_inventory.getColor())) {
                    add_inventory.setPrice(list_add_spece.get(y).getPrice());
                    add_inventory.setLeft_count(list_add_spece.get(y).getLeft_count());
                }

            }

        }

        list_add_spece.clear();
        list_add_spece.addAll(list_add_spece_temp);


        initAddSpece();

    }


    @Override
    public void onError(Throwable throwable) {

    }


    private void openPicChoose(int num, int result_code) {

        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {

                        PictureSelector.create(this)
                                .openGallery(PictureMimeType.ofImage())
                                .selectionMode(PictureConfig.MULTIPLE)
                                .maxSelectNum(num)
                                .enableCrop(true)
                                .freeStyleCropEnabled(true)
                                .showCropGrid(true)
                                .forResult(result_code);

                    } else {
                        ToastUtils.showT("图片选择需要以下权限:\\n\\n1.访问设备上的照片\\n\\n2.拍照");
                    }
                });


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


    public void upLoadImage(String imagePath ,CosXmlResultListener cosXmlResultListener) {
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
        cosXmlService.putObjectAsync(putObjectRequest,cosXmlResultListener);
    }


    private void showPickerView() {// 弹出选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                province = options1Items.size() > 0 ?
                        options1Items.get(options1).getPickerViewText() : "";

                city = options2Items.size() > 0
                        && options2Items.get(options1).size() > 0 ?
                        options2Items.get(options1).get(options2) : "";

                district = options2Items.size() > 0
                        && options3Items.get(options1).size() > 0
                        && options3Items.get(options1).get(options2).size() > 0 ?
                        options3Items.get(options1).get(options2).get(options3) : "";

                tv_address_start.setText(province + city + district);
                tv_address_start.setTextColor(getResources().getColor(R.color.black));
            }
        }).setTitleText("城市选择")
                .setDecorView((ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content))
                .setDividerColor(android.graphics.Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器


        pvOptions.show();

    }

    private void initJsonData() {//解析数据
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据
        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）
            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String cityName = jsonBean.get(i).getCityList().get(c).getName();
                cityList.add(cityName);//添加城市
                ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表
                city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                province_AreaList.add(city_AreaList);//添加该省所有地区数据
            }

            options2Items.add(cityList);
            options3Items.add(province_AreaList);
        }
        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }


    @OnClick({R.id.rl_add_color, R.id.rl_add_size, R.id.rl_goods_class, R.id.rl_address_start, R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_add_color:
                addColorAdapter.addData(new AddColor());
                rv_goods_color.setFocusable(true);
                rv_goods_color.setFocusableInTouchMode(true);
                rv_goods_color.requestFocus();
                break;
            case R.id.rl_add_size:
                addSizeAdapter.addData(new AddSize());
                rv_goods_sizes.setFocusable(true);
                rv_goods_sizes.setFocusableInTouchMode(true);
                rv_goods_sizes.requestFocus();
                break;


            case R.id.rl_goods_class:

                Intent intent = new Intent(this, GoodClassifyActivity.class);
                startActivityForResult(intent, Constants.GET_CLASSIFY);
                break;

            case R.id.rl_address_start:
                showPickerView();
                break;
            case R.id.tv_submit:



                if(bga_goods_pic.getData().size()==0){
                    ToastUtils.showT("请先上传商品标题图");
                    return;
                }

                if(bga_goods_desc.getData().size()==0){
                    ToastUtils.showT("请先上传商品详情图");
                    return;
                }
                //先初始化两个地址
                goods_title_url="";
                goods_desc_url="";
                upload_fail_count=0;
                upload_count=0;
                upload_all_count=bga_goods_pic.getData().size()+bga_goods_desc.getData().size();
                //先上传详情图
                showLoading();
                for (int i=0;i<bga_goods_pic.getData().size();i++){

                    upLoadImage(bga_goods_pic.getData().get(i), new CosXmlResultListener() {
                        @Override
                        public void onSuccess(CosXmlRequest request, CosXmlResult result) {
                            upload_count++;
                            hideLoading();
                            PutObjectResult putObjectResult = (PutObjectResult) result;
                            String url = putObjectResult.accessUrl;
                            goods_title_url=goods_title_url+url+",";
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
                //在上传分类图
                for (int i=0;i<bga_goods_desc.getData().size();i++){

                    upLoadImage(bga_goods_desc.getData().get(i), new CosXmlResultListener() {
                        @Override
                        public void onSuccess(CosXmlRequest request, CosXmlResult result) {
                            upload_count++;
                            hideLoading();
                            PutObjectResult putObjectResult = (PutObjectResult) result;
                            String url = putObjectResult.accessUrl;
                            goods_desc_url=goods_desc_url+url+",";
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
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {


            switch (requestCode) {
                case Constants.GET_PIC_TITLE:
                    // 图片选择结果回调
                    List<LocalMedia> selectList_title = PictureSelector.obtainMultipleResult(data);
                    ArrayList<String> temp_title = new ArrayList();
                    for (int i = 0; i < selectList_title.size(); i++) {
                        temp_title.add(selectList_title.get(i).getCutPath());
                    }
                    bga_goods_pic.addMoreData(temp_title);

                    break;


                case Constants.GET_PIC_DESC:
                    // 图片选择结果回调
                    List<LocalMedia> selectList_desc = PictureSelector.obtainMultipleResult(data);
                    ArrayList<String> temp_desc = new ArrayList();
                    for (int i = 0; i < selectList_desc.size(); i++) {
                        temp_desc.add(selectList_desc.get(i).getCutPath());
                    }
                    bga_goods_desc.addMoreData(temp_desc);

                    break;

                case Constants.GET_COLOR_PIC:
                    // 图片选择结果回调
                    List<LocalMedia> selectList_color_pic = PictureSelector.obtainMultipleResult(data);

                    showLoading();
                    upLoadImage(selectList_color_pic.get(0).getCutPath(), new CosXmlResultListener() {
                        @Override
                        public void onSuccess(CosXmlRequest request, CosXmlResult result) {
                            hideLoading();
                            PutObjectResult putObjectResult = (PutObjectResult) result;
                            String url = putObjectResult.accessUrl;
                            list_add_color.get(color_pic_postion).setImg_url(url);
                            mHandler.sendEmptyMessage(MSG_UPDATE_PIC);
                        }

                        @Override
                        public void onFail(CosXmlRequest request, CosXmlClientException exception, CosXmlServiceException serviceException) {
                            hideLoading();
                            ToastUtils.showT("图片添加失败");
                        }
                    });
                    break;

                case Constants.GET_CLASSIFY:
                    // 图片选择结果回调
                    Subcates address = (Subcates) data.getSerializableExtra("data");
                    if (address != null) {
                        chose_category = address;
                        tv_class_name.setText(chose_category.getTitle());
                    }
                    break;


            }


        }
    }

    @Override
    public void setTempKeysForCos(QCloudData data) {
        initQCloud(data);

    }

    private void submitGoods(){

        if (chose_category == null) {
            ToastUtils.showT("请选择商品类别");
            hideLoading();
            return;
        }

        //color 数据
        if (list_add_color.size() == 0) {
            ToastUtils.showT("请添加颜色分类");
            hideLoading();
            return;
        }
        ArrayList<AddColor> temp_color = new ArrayList<>();
        for (int i = 0; i < list_add_color.size(); i++) {
            if (list_add_color.get(i).getColor() != null) {
                if (!list_add_color.get(i).getColor().equals("")) {
                    temp_color.add(new AddColor(list_add_color.get(i).getColor(), list_add_color.get(i).getImg_url()));
                }
            }
        }
        if (temp_color.size() == 0) {
            ToastUtils.showT("请添加颜色分类");
            hideLoading();
            return;
        }
        //发货时间
        if (et_delivery_time.getText().toString().equals("")) {
            ToastUtils.showT("请输入发货时长");
            hideLoading();
            return;
        }


        //商品详情说明
        if (et_good_desc.getText().toString().equals("")) {
            ToastUtils.showT("请输入商品详情描述");
            hideLoading();
            return;
        }
        //运费
        if (!sb_freight.isChecked()) {

            if (et_freight.getText().toString().equals("")) {
                ToastUtils.showT("请输入运费");
                hideLoading();
                return;
            }
        }

        //商品价格库存

        for (int i = 0; i < list_add_spece.size(); i++) {
            AddInventory addInventory = list_add_spece.get(i);
            if (addInventory.getPrice() == null || addInventory.getLeft_count() == 0) {
                ToastUtils.showT("请检查所有类别的价格跟库存");
                return;
            }
        }

        //size 数据(非必填)
        ArrayList<AddSize> temp_size = new ArrayList<>();
        if (list_add_color.size() != 0) {

            temp_size = new ArrayList<>();
            for (int i = 0; i < list_add_size.size(); i++) {
                if (list_add_size.get(i).getSize() != null) {
                    if (!list_add_size.get(i).getSize().equals("")) {
                        temp_size.add(new AddSize(list_add_size.get(i).getSize()));
                    }
                }
            }

        }

        //商品标题
        if (et_good_title.getText().toString().equals("")) {
            ToastUtils.showT("请输入商品标题");
            return;
        }
        //商品标题
        if (tv_address_start.getText().toString().equals("")) {
            ToastUtils.showT("请选择发货地");
            return;
        }
        String freight="0";
        if(!sb_freight.isChecked()){
            if(et_freight.getText().toString().equals("")){
                freight="0";
            }else{
                freight=et_freight.getText().toString();
            }
        }


        mPresenter.publishGoods(String.valueOf(chose_category.getId())
                , temp_color
                , et_delivery_time.getText().toString()
                , et_good_desc.getText().toString()
                , goods_desc_url
                ,freight
                , list_add_spece
                , temp_size
                , goods_title_url
                , et_good_title.getText().toString()
                , tv_address_start.getText().toString()
        );
    }

    private void checkCanPublish(){
        //通过给定的次数判断
        if(upload_count!=upload_all_count){
            Log.e("checkCanPublish","还没上传完");
            return;
        }
        //如果次数相同,要看有没有上传失败的例子
        if(upload_count==upload_all_count){
          if(upload_fail_count>0){
              hideLoading();
              ToastUtils.showT("部分图片上传失败");
          }else {
              //如果等于0表示全部图片正常上传
              StringBuilder sb_title=new StringBuilder(goods_title_url);
              sb_title.replace(sb_title.length()-1,sb_title.length(),"");
              goods_title_url=sb_title.toString();

              StringBuilder sb_desc=new StringBuilder(goods_desc_url);
              sb_desc.replace(sb_desc.length()-1,sb_desc.length(),"");
              goods_desc_url=sb_desc.toString();
              mHandler.sendEmptyMessage(MSG_SUMBIT);
             // submitGoods();
          }
        }

    }


    @Override
    public void publishGoods(JSONObject baseResponse) {
        ToastUtils.showT("发布成功,请等待审核");
        finish();
    }
}

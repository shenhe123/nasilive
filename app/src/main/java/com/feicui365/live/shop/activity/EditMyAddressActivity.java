package com.feicui365.live.shop.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.kyleduo.switchbutton.SwitchButton;
import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.base.Constants;
import com.feicui365.live.contract.ShopContract;
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.presenter.ShopPresenter;
import com.feicui365.live.shop.entity.Address;
import com.feicui365.live.shop.entity.JsonBean;
import com.feicui365.live.shop.utils.GetJsonDataUtil;
import com.feicui365.live.util.ToastUtils;
import com.feicui365.nasinet.userconfig.AppStatusManager;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class EditMyAddressActivity extends BaseMvpActivity<ShopPresenter> implements ShopContract.View {
    private List<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private static boolean isLoaded = false;

    @BindView(R.id.tv_area)
    TextView tv_area;

    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_address)
    EditText et_address;
    @BindView(R.id.sb_default)
    SwitchButton sb_default;


    String province = "";//省
    String city = "";//市
    String district = "";//区
    String address = "";//详细地址
    String name = "";//名字
    String mobile = "";//电话
    int is_default;//是否默认

    Address chose_address;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_address;
    }

    @Override
    protected void initData() {
        if(chose_address!=null){
            initInfo();
        }
    }

    private void initInfo() {
        province=chose_address.getProvince();
        city=chose_address.getCity();
        district=chose_address.getDistrict();
        address=chose_address.getAddress();
        name=chose_address.getName();
        mobile=chose_address.getMobile();
        is_default=chose_address.getIs_default();

        et_name.setText(name);
        et_phone.setText(mobile);
        tv_area.setText(province + " " + city + " " + district);
        tv_area.setTextColor(getResources().getColor(R.color.black));
        et_address.setText(address);

        if(is_default==1){
            sb_default.setChecked(true);
        }else{
            sb_default.setChecked(false);
        }
    }

    @Override
    protected void initView() {
        mPresenter = new ShopPresenter();
        mPresenter.attachView(this);
        setTitle("新建收获地址");
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
        AppStatusManager.getInstance().setAppStatus(1);
        if(getIntent().getSerializableExtra(Constants.EDIT_ADDRESS)!=null){
            chose_address= (Address) getIntent().getSerializableExtra(Constants.EDIT_ADDRESS);
        }
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void addAddress(BaseResponse baseResponse) {
        Log.e("BaseResponse", baseResponse.toString());
        ToastUtils.showT("保存成功");
        finish();
    }

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

            }
        }
    };

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

                tv_area.setText(province + " " + city + " " + district);
                tv_area.setTextColor(getResources().getColor(R.color.black));
            }
        }).setTitleText("城市选择")
                .setDecorView((ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content))
                .setDividerColor(Color.BLACK)
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }


    @OnClick({R.id.tv_area, R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_area:
                if (isLoaded) {
                    showPickerView();
                } else {
                    ToastUtils.showT("城市初始化失败");
                }

                break;
            case R.id.tv_submit:
                name = et_name.getText().toString();
                mobile = et_phone.getText().toString();
                address = et_address.getText().toString();

                if (name.equals("") | mobile.equals("") | address.equals("") |
                        province.equals("") | city.equals("") | district.equals("")) {
                    ToastUtils.showT("请填写完整资料,再提交");
                    return;
                }
                if (sb_default.isChecked()) {
                    is_default=1;
                }else {
                    is_default=0;
                }
                if(chose_address==null){
                    mPresenter.addAddress(province, city, district, address, name, mobile, String.valueOf(is_default));
                }else{
                    mPresenter.editAddress(chose_address.getId(),province, city, district, address, name, mobile, String.valueOf(is_default));
                }


                break;
        }
    }
}

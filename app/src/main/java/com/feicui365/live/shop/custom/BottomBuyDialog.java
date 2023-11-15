package com.feicui365.live.shop.custom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lxj.xpopup.core.BottomPopupView;
import com.feicui365.live.R;
import com.feicui365.live.shop.adapter.GoodColorAdapter;
import com.feicui365.live.shop.adapter.GoodSizeAdapter;
import com.feicui365.live.shop.entity.Color;
import com.feicui365.live.shop.entity.GoodInfo;
import com.feicui365.live.shop.entity.Inventory;
import com.feicui365.live.shop.entity.Size;
import com.feicui365.live.shop.interfaces.OnChoseListener;
import com.feicui365.live.util.SpacingItemDecoration;
import com.feicui365.live.util.ToastUtils;


import java.util.ArrayList;

//待完善
public class BottomBuyDialog extends BottomPopupView implements View.OnClickListener {

    private TextView tv_submit, tv_price, tv_left_count;
    private ImageView iv_close2, iv_pic;
    private GoodInfo myGoodinfo = new GoodInfo();
    private RecyclerView rv_color, rv_size;
    private EditNumView env_num;
    private GoodColorAdapter goodColorAdapter;
    private GoodSizeAdapter goodSizeAdapter;
    private LinearLayout ll_size;

    private ArrayList<Size> list_size = new ArrayList<>();
    private ArrayList<Color> list_color = new ArrayList<>();
    private ArrayList<Inventory> list_inventory = new ArrayList<>();


    private int all_left_count = 0;
    private int chose_color = -1;
    private int chose_size = -1;
    private Inventory chose_inventory;
    private SubmitListener submitListener;

    public SubmitListener getSubmitListener() {
        return submitListener;
    }

    public void setSubmitListener(SubmitListener submitListener) {
        this.submitListener = submitListener;
    }

    public BottomBuyDialog(@NonNull Context context) {
        super(context);
    }

    public BottomBuyDialog(@NonNull Context context, GoodInfo myGoodinfo) {
        super(context);
        this.myGoodinfo = myGoodinfo;
    }

    public interface SubmitListener {
        void submit(GoodInfo chose_goodinfo, Inventory chose_inventory, int count);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.bottom_buy_dialog;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        tv_submit = findViewById(R.id.tv_submit);
        iv_close2 = findViewById(R.id.iv_close2);
        tv_price = findViewById(R.id.tv_price);
        tv_left_count = findViewById(R.id.tv_left_count);
        iv_pic = findViewById(R.id.iv_pic);
        rv_color = findViewById(R.id.rv_color);
        rv_size = findViewById(R.id.rv_size);
        env_num = findViewById(R.id.env_num);
        ll_size=findViewById(R.id.ll_size);
        tv_submit.setOnClickListener(this);
        iv_close2.setOnClickListener(this);
        //price
        tv_price.setText("￥" + myGoodinfo.getGoods().getPrice());
        //图
        String[] images = myGoodinfo.getGoods().getThumb_urls().split(",");
        Glide.with(getContext())
                .applyDefaultRequestOptions(new RequestOptions().centerCrop())
                .load(images[0])
                .into(iv_pic);


        /**
         * 数据处理
         * 1，初始化颜色(键值对,键为颜色ID),初始化尺寸(键值对,键为尺寸ID)
         * 2,拿到所有的库存,并显示未选状态下的总库存
         * 3,优先确定所有颜色是否对应当前的库存,如果有颜色没有库存,点击时候提醒没有库存,并且做透明度处理
         * 4,如果有颜色有库存,可点击,点击后通过键值对获取对应的尺寸,总库存(尺寸)
         * 5,获取对应库存的所有尺寸,没有的尺寸透明度处理,到这一步就可以确定选择的最终结果
         * **/

        initData();


        initColor();

        if(list_size.size()==0){
            workDataWithOutSize();
            ll_size.setVisibility(GONE);
        }else{
            ll_size.setVisibility(VISIBLE);
            initSize();
            workDataSize();

        }

        initEditNum();
    }

    private void initEditNum() {
        env_num.setGetNumListener(new EditNumView.GetNumListener() {
            @Override
            public void getNum(int num) {
                if (num >= all_left_count) {
                    env_num.setResult(all_left_count);
                }
            }
        });
    }

    //先初始化颜色
    private void initColor() {
        if (myGoodinfo.getColors() == null) {
            return;
        }
        if (myGoodinfo.getColors().size() == 0) {
            return;
        }

        list_color.get(0).setChose(true);
        chose_color=list_color.get(0).getId();
        goodColorAdapter = new GoodColorAdapter(list_color);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 5, GridLayoutManager.VERTICAL, false);
        rv_color.addItemDecoration(new SpacingItemDecoration(0, 3));
        rv_color.setLayoutManager(layoutManager);
        rv_color.setAdapter(goodColorAdapter);
        goodColorAdapter.setOnChoseColorListener(new OnChoseListener() {
            @Override
            public void onChose(int id) {
                chose_color = id;
                if(list_size.size()==0){
                    workDataWithOutSize();
                }else{
                    workDataSize();
                }
            }

            @Override
            public void onColorChose(Color color) {
                chose_color = color.getId();
                if(list_size.size()==0){
                    workDataWithOutSize();
                }else{
                    workDataSize();
                }
                if(color.getImg_url()!=null){
                    if("".equals(color.getImg_url())){
                        Glide.with(getContext())
                                .applyDefaultRequestOptions(new RequestOptions().centerCrop())
                                .load(color.getImg_url())
                                .into(iv_pic);
                    }
                }
            }
        });

    }

    //初始化尺码,对应的商品尺码
    private void initSize() {

        if (myGoodinfo.getSizes() == null) {
            return;
        }
        if (myGoodinfo.getSizes().size() == 0) {
            return;
        }
        list_size.get(0).setChose(true);
        chose_size=list_size.get(0).getId();
        goodSizeAdapter = new GoodSizeAdapter(list_size);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 5, GridLayoutManager.VERTICAL, false);
        rv_size.addItemDecoration(new SpacingItemDecoration(0, 3));
        rv_size.setLayoutManager(layoutManager);
        rv_size.setAdapter(goodSizeAdapter);

        goodSizeAdapter.setOnChoseSizeListener(new OnChoseListener() {
            @Override
            public void onChose(int id) {
                chose_size = id;
                workDataSize();
            }

            @Override
            public void onColorChose(Color goodsid) {

            }
        });
    }


    private void initData() {

        if (myGoodinfo.getSizes() == null && myGoodinfo.getSizes().size() == 0) {
            ToastUtils.showT("当前货物暂无库存");
            dismiss();
        }
        if (myGoodinfo.getColors() == null && myGoodinfo.getColors().size() == 0) {
            ToastUtils.showT("当前货物暂无库存");
            dismiss();
        }
        if (myGoodinfo.getInventory() == null && myGoodinfo.getInventory().size() == 0) {
            ToastUtils.showT("当前货物暂无库存");
            dismiss();
        }
        list_size.addAll(myGoodinfo.getSizes());
        list_color.addAll(myGoodinfo.getColors());
        list_inventory.addAll(myGoodinfo.getInventory());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_submit:

                if (env_num.getResult() == 0) {
                    ToastUtils.showT("选择数量为0");
                    return;
                }
                if (submitListener != null) {
                    if(chose_inventory==null){
                        ToastUtils.showT("请选择商品");
                        return;
                    }
                    submitListener.submit(myGoodinfo, chose_inventory, env_num.getResult());
                }

                dismiss();
                break;
            case R.id.iv_close2:
                dismiss();
                break;

        }
    }

    //数据拆检(有SIZE的时候)
    private void workDataSize() {
        all_left_count = 0;
        //分类,1,啥都没选,2,只选了颜色,3只选了SIZE,4全选
        int type = 0;
        if (chose_color != -1 & chose_size == -1) {
            type = 1;
        } else if (chose_color == -1 & chose_size != -1) {
            type = 2;
        } else if (chose_color != -1 & chose_size != -1) {
            type = 3;
        }

        switch (type) {
            //全没选

            case 0:

                for (int i = 0; i < list_inventory.size(); i++) {
                    all_left_count = all_left_count + list_inventory.get(i).getLeft_count();
                }

                break;
            //只选了颜色
            case 1:
                for (int i = 0; i < list_inventory.size(); i++) {
                    if (list_inventory.get(i).getColorid() == chose_color) {
                        all_left_count = all_left_count + list_inventory.get(i).getLeft_count();
                    }

                }


                break;
            //只选了尺寸
            case 2:
                for (int i = 0; i < list_inventory.size(); i++) {
                    if (list_inventory.get(i).getSizeid() == chose_size) {
                        all_left_count = all_left_count + list_inventory.get(i).getLeft_count();
                    }

                }


                break;
            //全选
            case 3:
                for (int i = 0; i < list_inventory.size(); i++) {
                    if (list_inventory.get(i).getSizeid() == chose_size &
                            list_inventory.get(i).getColorid() == chose_color) {
                        all_left_count = all_left_count + list_inventory.get(i).getLeft_count();
                        if (all_left_count != 0) {
                            chose_inventory = list_inventory.get(i);
                            tv_price.setText("￥" + chose_inventory.getPrice());
                        }

                        break;
                    }

                }


                break;


        }

        if (all_left_count == 0) {
            ToastUtils.showT("当前货物没有库存");
            tv_price.setText("￥" + myGoodinfo.getGoods().getPrice());
        }

        tv_left_count.setText("库存 " + all_left_count + "件");


        if (type != 3) {
            chose_inventory = null;
        }

    }


    //数据拆检(没有SIZE的时候)
    private void workDataWithOutSize() {
        all_left_count = 0;
        //分类,1,啥都没选,2,只选了颜色,3只选了SIZE,4全选
        int type = 0;
        if (chose_color != -1 & chose_size == -1) {
            type = 1;
        }

        switch (type) {
            //全没选

            case 0:

                for (int i = 0; i < list_inventory.size(); i++) {
                    all_left_count = all_left_count + list_inventory.get(i).getLeft_count();
                }

                break;
            //只选了颜色
            case 1:
                for (int i = 0; i < list_inventory.size(); i++) {
                    if (list_inventory.get(i).getColorid() == chose_color) {
                        all_left_count = all_left_count + list_inventory.get(i).getLeft_count();
                        if (all_left_count != 0) {
                            chose_inventory = list_inventory.get(i);
                            tv_price.setText("￥" + chose_inventory.getPrice());
                        }
                    }

                }


                break;




        }

        if (all_left_count == 0) {
            ToastUtils.showT("当前货物没有库存");
            tv_price.setText("￥" + myGoodinfo.getGoods().getPrice());
        }

        tv_left_count.setText("库存 " + all_left_count + "件");


        if (type != 1) {
            chose_inventory = null;
        }

    }


}

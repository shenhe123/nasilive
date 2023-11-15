package com.feicui365.live.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lxj.xpopup.XPopup;
import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.base.Constants;
import com.feicui365.live.contract.ShopContract;
import com.feicui365.live.im.TxImUtils;
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.presenter.ShopPresenter;
import com.feicui365.live.shop.adapter.GoodsInfoAdapter;
import com.feicui365.live.shop.custom.BottomBuyDialog;
import com.feicui365.live.shop.custom.BottomServiceDialog;
import com.feicui365.live.shop.entity.Good;
import com.feicui365.live.shop.entity.GoodInfo;
import com.feicui365.live.shop.entity.Inventory;
import com.feicui365.live.util.ToastUtils;
import com.feicui365.live.widget.MyRecyclerView;
import com.feicui365.nasinet.utils.AppManager;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class GoodsInfoActivity extends BaseMvpActivity<ShopPresenter> implements ShopContract.View {

    @BindView(R.id.xb_good)
    XBanner xb_good;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.tv_good_title)
    TextView tv_good_title;
    @BindView(R.id.tv_freight)
    TextView tv_freight;
    @BindView(R.id.tv_sale_count)
    TextView tv_sale_count;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.rl_service)
    RelativeLayout rl_service;
    @BindView(R.id.tv_comment_count)
    TextView tv_comment_count;
    @BindView(R.id.tv_comment_show)
    TextView tv_comment_show;
    @BindView(R.id.fl_comment)
    FrameLayout fl_comment;
    @BindView(R.id.iv_shop)
    ImageView iv_shop;
    @BindView(R.id.tv_shoper)
    TextView tv_shoper;
    @BindView(R.id.rv_goods_pic)
    MyRecyclerView rv_goods_pic;

    @BindView(R.id.rl_comment)
    RelativeLayout rl_comment;



    private Good myGood;
    private GoodInfo myGoodinfo;
    private GoodsInfoAdapter goodsInfoAdapter;
    private BottomBuyDialog add_cart,buy_now;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_goods_info;
    }

    @Override
    protected void initData() {


    }

    @Override
    protected void initView() {
        mPresenter = new ShopPresenter();
        mPresenter.attachView(this);
        setTitle("商品详情");
        showImageOther(20, 20, R.mipmap.ic_shop_gouwuche);
        //获取传过来的类
        myGood = (Good) getIntent().getSerializableExtra(Constants.GOODS_INFO);
        if (myGood == null) {
            finish();
        }

        initDate();

    }

    private void initDate() {
        //banner
        if (myGood.getThumb_urls() != null && !"".equals(myGood.getThumb_urls())) {
            List<String> banners = new ArrayList<>();
            String[] images = myGood.getThumb_urls().split(",");
            for (int i = 0; i < images.length; i++) {
                banners.add(images[i]);
            }
            if (banners.size() != 0) {
                xb_good.setData(R.layout.image_fresco2, banners, null);
                xb_good.loadImage(new XBanner.XBannerAdapter() {
                    @Override
                    public void loadBanner(XBanner banner, Object model, View view, int position) {
                        ImageView mSimpleView = view.findViewById(R.id.my_image_view);
                        Glide.with(GoodsInfoActivity.this)
                                .applyDefaultRequestOptions(new RequestOptions().centerCrop().placeholder(R.mipmap.zhanwei))
                                .load(banners.get(position)).into(mSimpleView);
                    }
                });
            }
        }

        //price
        tv_price.setText("￥"+myGood.getPrice());
        //标题
        tv_good_title.setText(myGood.getTitle());

    }

    @Override
    public void getGoodsInfo(GoodInfo baseResponse) {
        if (baseResponse != null) {
            myGoodinfo = baseResponse;
            updateDate();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(myGood!=null){
            mPresenter.getGoodsInfo(myGood.getId());
        }

    }

    private void updateDate() {
        //运费
        tv_freight.setText("运费:"+myGoodinfo.getGoods().getFreight()+"元");
        //卖出数
        tv_sale_count.setText("已售:"+myGoodinfo.getGoods().getSale_count()+"件");
        //地址
        if(myGoodinfo.getGoods().getAddress()!=null){
            tv_address.setText(myGoodinfo.getGoods().getAddress());
        }
        //服务

        //评论数
        if(myGoodinfo.getEvaluate_count()>0){
            tv_comment_count.setText("商品评价("+myGoodinfo.getEvaluate_count()+")");
            tv_comment_show.setVisibility(View.VISIBLE);
            initComment();
        }else{
            tv_comment_show.setVisibility(View.GONE);
        }


        //商家商店
        Glide.with(this)
                .applyDefaultRequestOptions(new RequestOptions().centerCrop())
                .load(myGoodinfo.getGoods().getShop().getUser().getAvatar())
                .into(iv_shop);
        tv_shoper.setText(myGoodinfo.getGoods().getShop().getUser().getNick_name());

        //商品详情
        if (myGoodinfo.getGoods().getDesc_img_urls() != null && !"".equals(myGoodinfo.getGoods().getDesc_img_urls())) {
            initList();
        }

    }

    private void initList() {
        List<String> goodsInfoPic = new ArrayList<>();
        String[] images = myGood.getDesc_img_urls().split(",");
        for (int i = 0; i < images.length; i++) {
            goodsInfoPic.add(images[i]);
        }
        LinearLayoutManager lm=new LinearLayoutManager(this);
        if(goodsInfoAdapter==null){
            goodsInfoAdapter=new GoodsInfoAdapter(goodsInfoPic);
        }
        rv_goods_pic.setNestedScrollingEnabled(false);
        rv_goods_pic.setLayoutManager(lm);
        rv_goods_pic.setAdapter(goodsInfoAdapter);
    }

    //填充评论
    private void initComment() {
        View v = getLayoutInflater().inflate(R.layout.goods_info_comment, fl_comment, false);
        Glide.with(this).applyDefaultRequestOptions(new RequestOptions().centerCrop().placeholder(R.mipmap.zhanwei))
                .load(myGoodinfo.getEvaluate().getUser().getAvatar())
                .into((CircleImageView)v.findViewById(R.id.civ_avatar));
        ((TextView)v.findViewById(R.id.tv_nickname)).setText(myGoodinfo.getEvaluate().getUser().getNick_name());
        ((TextView)v.findViewById(R.id.tv_comment)).setText(myGoodinfo.getEvaluate().getContent());
        fl_comment.setVisibility(View.VISIBLE);
        fl_comment.addView(v);

    }

    @Override
    public void onError(Throwable throwable) {

    }


    @OnClick({R.id.rl_service,R.id.iv_buy_now,R.id.iv_other,R.id.iv_add,R.id.tv_shop,R.id.iv_go_shop,
    R.id.tv_help,R.id.rl_comment})
    public void onClick(View view) {
        if (isFastClick()){
            return;
        }
        switch (view.getId()){
            case R.id.rl_service:
                BottomServiceDialog bottomServiceDialog=new BottomServiceDialog(context);
                new XPopup.Builder(context)
                        .dismissOnBackPressed(true)
                        .dismissOnTouchOutside(true)
                        .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                        .asCustom(bottomServiceDialog/*.enableDrag(false)*/)

                        .show();
                break;

            case R.id.iv_buy_now:
                if(myGoodinfo.getInventory()==null){
                    ToastUtils.showT("当前货物没有库存");
                    return;
                }
                if(myGoodinfo.getInventory().size()==0){
                    ToastUtils.showT("当前货物没有库存");
                    return;
                }

                if(buy_now==null){
                    buy_now=new BottomBuyDialog(context,myGoodinfo);
                    buy_now.setSubmitListener(new BottomBuyDialog.SubmitListener() {
                        @Override
                        public void submit(GoodInfo chose_goodinfo, Inventory chose_inventory, int count) {

                            Intent intent = new Intent(context, ConfirmOrderToBuyActivity.class);
                            Bundle bundleObject = new Bundle();
                            bundleObject.putSerializable(Constants.GOODS_INFO, chose_goodinfo);
                            bundleObject.putSerializable(Constants.INVENTORY_INFO, chose_inventory);
                            bundleObject.putInt(Constants.GOODS_COUNT, count);
                            intent.putExtras(bundleObject);
                            startActivity(intent);
                        }
                    });
                }
                new XPopup.Builder(context)
                        .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                        .asCustom(buy_now)
                        .show();


                break;
            case R.id.iv_add:
                if(myGoodinfo.getInventory()==null){
                    ToastUtils.showT("当前货物没有库存");
                    return;
                }
                if(myGoodinfo.getInventory().size()==0){
                    ToastUtils.showT("当前货物没有库存");
                    return;
                }
                if(add_cart==null){
                    add_cart=new BottomBuyDialog(context,myGoodinfo);
                    add_cart.setSubmitListener(new BottomBuyDialog.SubmitListener() {
                        @Override
                        public void submit(GoodInfo chose_goodinfo, Inventory chose_inventory, int count) {
                            mPresenter.addCartGoods(String.valueOf(chose_inventory.getId()),chose_goodinfo.getGoods().getShopid(),String.valueOf(count));
                        }
                    });
                }

                new XPopup.Builder(context)
                        .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                        .asCustom(add_cart)
                        .show();


                break;
            case R.id.iv_other:
                AppManager.getAppManager().startActivity(ShopCarActivity.class);
                break;
            case R.id.tv_shop:
                Intent intent_shop=new Intent(this,ShopActivity.class);
                intent_shop.putExtra(Constants.SHOP_ID,myGood.getShopid());
                AppManager.getAppManager().startActivity(intent_shop);
                break;
            case R.id.iv_go_shop:
                Intent intent_shop_2=new Intent(this,ShopActivity.class);
                intent_shop_2.putExtra(Constants.SHOP_ID,myGood.getShopid());
                AppManager.getAppManager().startActivity(intent_shop_2);
                break;
            case R.id.tv_help:

                TxImUtils.getSocketManager().startChat(myGood.getShopid(),myGoodinfo.getGoods().getShop().getUser().getNick_name(),myGoodinfo.getGoods().getShop().getUser().getAvatar());

                break;
            case R.id.rl_comment:
                if(myGoodinfo==null){
                    return;
                }

                if(myGoodinfo.getEvaluate_count()==0){
                    return;
                }
                Intent intent_good=new Intent(this,GoodsCommentActivity.class);
                intent_good.putExtra(Constants.GOODS_ID,myGood.getId());
                AppManager.getAppManager().startActivity(intent_good);
                break;

        }
    }

    @Override
    public void addCartGoods(BaseResponse baseResponse) {
       ToastUtils.showT("添加成功");
    }
}

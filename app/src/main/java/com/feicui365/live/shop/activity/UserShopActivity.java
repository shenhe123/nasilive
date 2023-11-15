package com.feicui365.live.shop.activity;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.base.Constants;
import com.feicui365.live.contract.ShopContract;
import com.feicui365.live.presenter.ShopPresenter;
import com.feicui365.live.shop.entity.ShopUserInfo;
import com.feicui365.live.util.ToastUtils;
import com.feicui365.nasinet.userconfig.AppStatusManager;
import com.feicui365.nasinet.utils.AppManager;

import butterknife.BindView;
import butterknife.OnClick;

public class UserShopActivity extends BaseMvpActivity<ShopPresenter> implements ShopContract.View {
    @BindView(R.id.tv_page_title)
    TextView tv_page_title;
    @BindView(R.id.rl_back_2)
    RelativeLayout rl_back_2;
    @BindView(R.id.rl_change)
    RelativeLayout rl_change;
    @BindView(R.id.rl_open_shop)
    RelativeLayout rl_open_shop;
    @BindView(R.id.rl_footmark)
    RelativeLayout rl_footmark;


    //购物车
    @BindView(R.id.tv_shopcart_num)
    TextView tv_shopcart_num;

    //待支付
    @BindView(R.id.tv_forpay_num)
    TextView tv_forpay_num;

    //待发货
    @BindView(R.id.tv_forsend_num)
    TextView tv_forsend_num;

    //待收获
    @BindView(R.id.tv_forget_num)
    TextView tv_forget_num;

    //待评价
    @BindView(R.id.tv_forcommit_num)
    TextView tv_forcommit_num;

    //待退款
    @BindView(R.id.tv_forrefund_num)
    TextView tv_forrefund_num;


    int open_type=0;
    ShopUserInfo shopUserInfo;
    boolean is_change=false;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_customer_shop;
    }

    @Override
    protected void initData() {
        showLoading();
        mPresenter.getShopUserInfo();
    }


    @Override
    protected void initView() {
        mPresenter = new ShopPresenter();
        mPresenter.attachView(this);
        hideTitle(true);
        tv_page_title.setText("直播小店(买家)");
        mPresenter = new ShopPresenter();
        mPresenter.attachView(this);
        AppStatusManager.getInstance().setAppStatus(1);

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @OnClick({R.id.rl_back_2, R.id.rl_change,R.id.rl_open_shop,R.id.rl_shop_cart,R.id.rL_shop_address
    ,R.id.ll_wait_pay,R.id.ll_all_order,R.id.rl_footmark,R.id.ll_wait_send,R.id.ll_wait_get,R.id.ll_refund})
    public void onClick(View view) {
        if (isFastClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.rl_back_2:
                finish();
                break;

            case R.id.rl_change:
                if(shopUserInfo!=null){
                    if(shopUserInfo.getIsShop()==1){
                        AppManager.getAppManager().startActivity2(SellerShopActivity.class);
                        overridePendingTransition(0,0);
                        is_change=true;
                        finish();
                    }
                }
                break;
            case R.id.rl_open_shop:
                if(open_type==0){
                    ToastUtils.showT("请成为主播后再申请开店");
                }else {
                    AppManager.getAppManager().startActivity(DepositActivity.class);
                }
                break;
            case R.id.rl_shop_cart:
                AppManager.getAppManager().startActivity(ShopCarActivity.class);
                break;
            case R.id.rL_shop_address:
                AppManager.getAppManager().startActivity(MyAddressActivity.class);
                break;

            case R.id.rl_footmark:
                AppManager.getAppManager().startActivity(FootMarkActivity.class);
                break;
            case R.id.ll_all_order:
                Intent i_all=new Intent(this,UserOrderActivity.class);
                i_all.putExtra(Constants.ORDER_TYPE,0);
                startActivity(i_all);
                break;
            case R.id.ll_wait_pay:
                Intent i_pay=new Intent(this,UserOrderActivity.class);
                i_pay.putExtra(Constants.ORDER_TYPE,1);
                startActivity(i_pay);
                break;
            case R.id.ll_wait_send:
                Intent i_send=new Intent(this,UserOrderActivity.class);
                i_send.putExtra(Constants.ORDER_TYPE,2);
                startActivity(i_send);
                break;
            case R.id.ll_wait_get:
                Intent i_get=new Intent(this,UserOrderActivity.class);
                i_get.putExtra(Constants.ORDER_TYPE,3);
                startActivity(i_get);
                break;
            case R.id.ll_refund:
                Intent i_refund=new Intent(this,UserOrderActivity.class);
                i_refund.putExtra(Constants.ORDER_TYPE,4);
                startActivity(i_refund);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void getShopUserInfo(ShopUserInfo bean) {
        //开店显示
        if(bean==null){
            return;
        }
        shopUserInfo=bean;
        if(bean.getIsShop()==1){
            rl_open_shop.setVisibility(View.GONE);
        }else{
            rl_change.setVisibility(View.GONE);
            if(bean.getIs_anchor()==0){
                open_type=0;
            }else{
                open_type=1;
            }
        }
        //付款数字
        if(bean.getUnpay_count()==0){
            tv_forpay_num.setVisibility(View.GONE);
        }else{
            tv_forpay_num.setVisibility(View.VISIBLE);
            if(bean.getUnpay_count()<=99){
                tv_forpay_num.setText(String.valueOf(bean.getUnpay_count()));
            }else{
                tv_forpay_num.setText(String.valueOf(99));
            }

        }
        //发货数字
        if(bean.getUndelivery_count()==0){
            tv_forsend_num.setVisibility(View.GONE);
        }else{
            tv_forsend_num.setVisibility(View.VISIBLE);
            if(bean.getUndelivery_count()<=99){
                tv_forsend_num.setText(String.valueOf(bean.getUndelivery_count()));
            }else{
                tv_forsend_num.setText(String.valueOf(99));
            }

        }
        //收获数字
        if(bean.getUnreceive_count()==0){
            tv_forget_num.setVisibility(View.GONE);
        }else{
            tv_forget_num.setVisibility(View.VISIBLE);
            if(bean.getUnpay_count()<=99){
                tv_forget_num.setText(String.valueOf(bean.getUnreceive_count()));
            }else{
                tv_forget_num.setText(String.valueOf(99));
            }

        }
        //评价数字
        if(bean.getUnevaluate_count()==0){
            tv_forcommit_num.setVisibility(View.GONE);
        }else{
            tv_forcommit_num.setVisibility(View.VISIBLE);
            if(bean.getUnpay_count()<=99){
                tv_forcommit_num.setText(String.valueOf(bean.getUnevaluate_count()));
            }else{
                tv_forcommit_num.setText(String.valueOf(99));
            }

        }

    }

    @Override
    public void finish() {

        super.finish();
        if(is_change){
            overridePendingTransition(0,0);
        }else{
            overridePendingTransition(0,R.anim.activity_anim_out_left);
        }

    }
}

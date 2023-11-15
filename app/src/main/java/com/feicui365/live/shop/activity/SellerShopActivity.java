package com.feicui365.live.shop.activity;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.base.Constants;
import com.feicui365.live.contract.ShopContract;
import com.feicui365.live.eventbus.MessageBus;
import com.feicui365.live.presenter.ShopPresenter;
import com.feicui365.live.shop.entity.ShopSellerInfo;
import com.feicui365.live.ui.act.AllMessageActivity;
import com.feicui365.nasinet.userconfig.AppStatusManager;
import com.feicui365.nasinet.utils.AppManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class SellerShopActivity extends BaseMvpActivity<ShopPresenter> implements ShopContract.View {

    @BindView(R.id.tv_page_title)
    TextView tv_page_title;

    @BindView(R.id.iv_avatar)
    CircleImageView iv_avatar;

    @BindView(R.id.tv_shoper)
    TextView tv_shoper;
    @BindView(R.id.tv_good_num)
    TextView tv_good_num;
    @BindView(R.id.tv_remainder_num)
    TextView tv_remainder_num;
    @BindView(R.id.tv_income_num)
    TextView tv_income_num;
    @BindView(R.id.tv_remainder_all)
    TextView tv_remainder_all;
    @BindView(R.id.rl_order)
    RelativeLayout rl_order;
    @BindView(R.id.tv_unpay_num)
    TextView tv_unpay_num;
    @BindView(R.id.tv_unsend)
    TextView tv_unsend;
    @BindView(R.id.tv_refund)
    TextView tv_refund;
    @BindView(R.id.rl_add_good)
    RelativeLayout rl_add_good;
    @BindView(R.id.rl_good_manage)
    RelativeLayout rl_good_manage;

    @BindView(R.id.rl_shop_withdraw)
    RelativeLayout rl_shop_withdraw;


    @BindView(R.id.tv_forsend_num)
    TextView tv_forsend_num;


    boolean is_change;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_seller_shop;
    }

    @Override
    protected void initData() {
        showLoading();
        mPresenter.shopHomePageData();
    }

    @Override
    protected void initView() {
        AppStatusManager.getInstance().setAppStatus(1);
        mPresenter = new ShopPresenter();
        mPresenter.attachView(this);
        hideTitle(true);
        tv_page_title.setText("直播小店(卖家)");
       /* if (getAllUnReadMsgCount().equals("0")) {
            tv_forsend_num.setVisibility(View.GONE);
        } else {
            tv_forsend_num.setText(getAllUnReadMsgCount());
        }
*/
        EventBus.getDefault().register(this);
    }

    @Override
    public void shopHomePageData(ShopSellerInfo bean) {
        if (bean == null) {
            return;
        }


        Glide.with(this).applyDefaultRequestOptions(new RequestOptions().centerCrop().placeholder(R.mipmap.moren))
                .load(bean.getUser().getAvatar())
                .into(iv_avatar);
        tv_shoper.setText(bean.getUser().getNick_name());
        tv_good_num.setText("共" + bean.getGoods_count() + "件商品");
        tv_remainder_num.setText(bean.getProfit());
        tv_income_num.setText(String.valueOf(bean.getIncome_today()));
        tv_remainder_all.setText(String.valueOf(bean.getTotal_profit()));
        tv_unpay_num.setText(String.valueOf(bean.getOrder_count_unpay()));
        tv_unsend.setText(String.valueOf(bean.getOrder_count_undelivery()));
        tv_refund.setText(String.valueOf(bean.getOrder_count_refund()));


    }

    @Override
    public void onError(Throwable throwable) {

    }

    @OnClick({R.id.rl_order, R.id.rl_add_good, R.id.rl_good_manage, R.id.rl_shop_withdraw,
            R.id.ll_wait_pay, R.id.ll_wait_send, R.id.ll_refund, R.id.rl_change, R.id.rl_chat})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_order:
                AppManager.getAppManager().startActivity(SellerOrderActivity.class);
                break;
            case R.id.rl_add_good:
                AppManager.getAppManager().startActivity(AddGoodsActivity.class);
                break;
            case R.id.rl_good_manage:
                AppManager.getAppManager().startActivity(GoodsManagerActivity.class);
                break;

            case R.id.rl_shop_withdraw:
                AppManager.getAppManager().startActivity(SellerWithDrawActivity.class);

                break;

            case R.id.ll_wait_pay:
                Intent i_pay = new Intent(this, SellerOrderActivity.class);
                i_pay.putExtra(Constants.ORDER_TYPE, 0);
                startActivity(i_pay);
                break;
            case R.id.ll_wait_send:
                Intent i_send = new Intent(this, SellerOrderActivity.class);
                i_send.putExtra(Constants.ORDER_TYPE, 1);
                startActivity(i_send);
                break;
            case R.id.ll_refund:
                Intent i_refund = new Intent(this, SellerOrderActivity.class);
                i_refund.putExtra(Constants.ORDER_TYPE, 4);
                startActivity(i_refund);
                break;
            case R.id.rl_change:

                AppManager.getAppManager().startActivity2(UserShopActivity.class);
                overridePendingTransition(0, 0);
                is_change = true;
                finish();
                break;
            case R.id.rl_chat:
                AppManager.getAppManager().startActivity(AllMessageActivity.class);
                break;
        }
    }


    @Override
    public void finish() {

        super.finish();
        if (is_change) {
            overridePendingTransition(0, 0);
        } else {
            overridePendingTransition(0, R.anim.activity_anim_out_left);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMessage(MessageBus message) {
        if (!this.isFinishing()) {

            int num = Integer.valueOf(message.message);
            String result = "0";
            if (num > 99) {
                result = "99+";
            } else {
                result = String.valueOf(num);
            }
            if (!result.equals("0")) {
                tv_forsend_num.setVisibility(View.VISIBLE);
                tv_forsend_num.setText(result);
            } else {
                tv_forsend_num.setVisibility(View.GONE);
            }
        }


    }


}

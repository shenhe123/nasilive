package com.feicui365.live.ui.fragment;

import android.app.Dialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpFragment;
import com.feicui365.live.contract.HomeContract;
import com.feicui365.live.dialog.LivePriceDialog;
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.model.entity.BuyGuard;
import com.feicui365.live.model.entity.GuardianInfo;
import com.feicui365.live.presenter.HomePresenter;
import com.feicui365.live.ui.act.ToPayActivity;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.live.util.ToastUtils;
import com.feicui365.live.util.WordUtil;
import com.feicui365.live.widget.Dialogs;
import com.feicui365.nasinet.utils.AppManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class BuyGuardianFragment extends BaseMvpFragment<HomePresenter> implements HomeContract.View {

    @BindView(R.id.rl_week)
    RelativeLayout rl_week;
    @BindView(R.id.rl_month)
    RelativeLayout rl_month;
    @BindView(R.id.rl_year)
    RelativeLayout rl_year;

    @BindView(R.id.tv_week_1)
    TextView tv_week_1;
    @BindView(R.id.tv_week_2)
    TextView tv_week_2;

    @BindView(R.id.tv_week_4)
    TextView tv_week_4;
    @BindView(R.id.tv_month_1)
    TextView tv_month_1;
    @BindView(R.id.tv_month_2)
    TextView tv_month_2;
    @BindView(R.id.tv_month_4)
    TextView tv_month_4;

    @BindView(R.id.tv_year_1)
    TextView tv_year_1;
    @BindView(R.id.tv_year_2)
    TextView tv_year_2;

    @BindView(R.id.tv_year_4)
    TextView tv_year_4;

    @BindView(R.id.iv_gift)
    ImageView iv_gift;

    @BindView(R.id.iv_tequan)
    ImageView iv_tequan;

    @BindView(R.id.tv_gift_1)
    TextView tv_gift_1;
    @BindView(R.id.tv_gift_2)
    TextView tv_gift_2;
    @BindView(R.id.tv_tequan_1)
    TextView tv_tequan_1;
    @BindView(R.id.tv_tequan_2)
    TextView tv_tequan_2;

    @BindView(R.id.tv_year_3)
    TextView tv_year_coin;
    @BindView(R.id.tv_month_3)
    TextView tv_month_coin;
    @BindView(R.id.tv_week_3)
    TextView tv_week_coin;
    @BindView(R.id.tv_buy)
    TextView tv_buy;
    @BindView(R.id.tv_over_time)
    TextView tv_over_time;
    @BindView(R.id.tv_my_coin)
    TextView tv_my_coin;


    String coin_year = MyUserInstance.getInstance().getUserConfig().getGuard_price().getYear_price();
    String coin_week = MyUserInstance.getInstance().getUserConfig().getGuard_price().getWeek_price();
    String coin_month = MyUserInstance.getInstance().getUserConfig().getGuard_price().getMonth_price();
    GuardianInfo guardianInfo;
    String anchorid;
    String type = "0";
    BuyFinish buyFinish;
    private Dialog dialog;

    public void setBuyFinish(BuyFinish buyFinish) {
        this.buyFinish = buyFinish;
    }

    public interface BuyFinish {
        void BuySuccess(GuardianInfo guardianInfo);
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initView(View view) {
        mPresenter = new HomePresenter();
        mPresenter.attachView(this);
        if (getArguments() != null) {
            anchorid = getArguments().getString("anchorid");
            if (getArguments().getSerializable("GuardianInfo") != null) {
                guardianInfo = (GuardianInfo) getArguments().getSerializable("GuardianInfo");
                tv_buy.setText("续费(9折)");
            }
        }

        if (guardianInfo != null) {
            tv_over_time.setText(guardianInfo.getExpire_time() + " 到期");
        }

        if (Integer.parseInt(coin_year) > 10000) {
            tv_year_coin.setText(String.valueOf(Integer.parseInt(coin_year) / 10000));
            tv_year_4.setText(" 万金币");
        } else {
            tv_year_coin.setText(coin_year);
            tv_year_4.setText(" 金币");
        }

        if (Integer.parseInt(coin_week) > 10000) {
            tv_week_coin.setText(String.valueOf(Integer.parseInt(coin_week) / 10000));
            tv_week_4.setText(" 万金币");
        } else {
            tv_week_coin.setText(coin_week);
            tv_week_4.setText(" 金币");
        }

        if (Integer.parseInt(coin_month) > 10000) {
            tv_month_coin.setText(String.valueOf(Integer.parseInt(coin_month) / 10000));
            tv_month_4.setText(" 万金币");
        } else {
            tv_month_coin.setText(coin_month);
            tv_month_4.setText(" 金币");
        }
        tv_my_coin.setText("我的金币: " + MyUserInstance.getInstance().getUserinfo().getGold());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_guardian_buy;
    }

    @Override
    public void showLoading() {
        hideLoading();
        dialog = Dialogs.createLoadingDialog(getContext());
        dialog.show();
    }

    @Override
    public void hideLoading() {
        if (null != dialog) {
            dialog.dismiss();
        }
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @OnClick({R.id.rl_week, R.id.rl_month, R.id.rl_year, R.id.tv_buy,R.id.tv_charge})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_week:
                initView(1);
                type = "2";
                break;

            case R.id.rl_month:
                initView(2);
                type = "1";
                break;
            case R.id.rl_year:
                initView(3);
                type = "0";
                break;
            case R.id.tv_buy:


                if (guardianInfo == null) {
                    if (Integer.parseInt(MyUserInstance.getInstance().getUserinfo().getGold()) > Integer.parseInt(getPrice())) {
                        mPresenter.buyGuard(anchorid, type, "0");
                    } else {
                        initPayDialog();
                    }

                } else {
                    Double price = Integer.parseInt(getPrice()) * 0.9;
                    if (Integer.parseInt(MyUserInstance.getInstance().getUserinfo().getGold()) > price.intValue()) {
                        mPresenter.buyGuard(anchorid, type, "1");
                    } else {
                        initPayDialog();
                    }

                }

                break;
            case R.id.tv_charge:
                AppManager.getAppManager().startActivity(ToPayActivity.class);
                break;
        }
    }

    @Override
    public void buyGuard(BaseResponse<BuyGuard> response) {
        ToastUtils.showT("购买成功");
        if (response.getData() == null) {
            return;
        }
        guardianInfo = response.getData().getGuard();
        tv_my_coin.setText("我的金币: " + response.getData().getGold());
        tv_over_time.setText(guardianInfo.getExpire_time() + " 到期");
        MyUserInstance.getInstance().getUserinfo().setGold(response.getData().getGold());
        if( MyUserInstance.getInstance().getUserinfo().getGuard_anchors()==null){
            ArrayList<String> arrayList=new ArrayList<>();
            arrayList.add(guardianInfo.getAnchorid());
            MyUserInstance.getInstance().getUserinfo().setGuard_anchors(arrayList);
        }else{
            ArrayList<String> arrayList=new ArrayList<>();
            arrayList.add(guardianInfo.getAnchorid());
            MyUserInstance.getInstance().getUserinfo().getGuard_anchors().addAll(arrayList);
        }

        if (buyFinish != null) {
            buyFinish.BuySuccess(guardianInfo);
        }

    }

    private void initView(int type) {
        switch (type) {
            case 1:
                //周
                rl_week.setBackgroundResource(R.mipmap.bg_guardian_red);
                rl_month.setBackgroundResource(R.mipmap.bg_guardian_white);
                rl_year.setBackgroundResource(R.mipmap.bg_guardian_white);

                tv_week_1.setTextColor(getColor(R.color.white));
                tv_week_2.setTextColor(getColor(R.color.white));
                tv_week_coin.setTextColor(getColor(R.color.white));
                tv_week_4.setTextColor(getColor(R.color.white));

                tv_month_1.setTextColor(getColor(R.color.black));
                tv_month_2.setTextColor(getColor(R.color.color_A2A2A2));
                tv_month_coin.setTextColor(getColor(R.color.color_FF8B02));
                tv_month_4.setTextColor(getColor(R.color.color_FF8B02));

                tv_year_1.setTextColor(getColor(R.color.black));
                tv_year_2.setTextColor(getColor(R.color.color_A2A2A2));
                tv_year_coin.setTextColor(getColor(R.color.color_FF8B02));
                tv_year_4.setTextColor(getColor(R.color.color_FF8B02));

                iv_gift.setImageAlpha(125);
                iv_tequan.setImageAlpha(125);
                tv_gift_1.setTextColor(0x80000000);
                tv_gift_2.setTextColor(0x80939393);
                tv_tequan_1.setTextColor(0x80000000);
                tv_tequan_2.setTextColor(0x80939393);
                break;
            case 2:
                //月
                rl_week.setBackgroundResource(R.mipmap.bg_guardian_white);
                rl_month.setBackgroundResource(R.mipmap.bg_guardian_red);
                rl_year.setBackgroundResource(R.mipmap.bg_guardian_white);

                tv_week_1.setTextColor(getColor(R.color.black));
                tv_week_2.setTextColor(getColor(R.color.color_A2A2A2));
                tv_week_coin.setTextColor(getColor(R.color.color_FF8B02));
                tv_week_4.setTextColor(getColor(R.color.color_FF8B02));

                tv_month_1.setTextColor(getColor(R.color.white));
                tv_month_2.setTextColor(getColor(R.color.white));
                tv_month_coin.setTextColor(getColor(R.color.white));
                tv_month_4.setTextColor(getColor(R.color.white));


                tv_year_1.setTextColor(getColor(R.color.black));
                tv_year_2.setTextColor(getColor(R.color.color_A2A2A2));
                tv_year_coin.setTextColor(getColor(R.color.color_FF8B02));
                tv_year_4.setTextColor(getColor(R.color.color_FF8B02));

                iv_gift.setImageAlpha(255);
                iv_tequan.setImageAlpha(125);

                tv_gift_1.setTextColor(0xff000000);
                tv_gift_2.setTextColor(0xff939393);
                tv_tequan_1.setTextColor(0x80000000);
                tv_tequan_2.setTextColor(0x80939393);
                break;
            case 3:
                //年
                rl_week.setBackgroundResource(R.mipmap.bg_guardian_white);
                rl_month.setBackgroundResource(R.mipmap.bg_guardian_white);
                rl_year.setBackgroundResource(R.mipmap.bg_guardian_red);


                tv_year_1.setTextColor(getColor(R.color.white));
                tv_year_2.setTextColor(getColor(R.color.white));
                tv_year_coin.setTextColor(getColor(R.color.white));
                tv_year_4.setTextColor(getColor(R.color.white));

                tv_month_1.setTextColor(getColor(R.color.black));
                tv_month_2.setTextColor(getColor(R.color.color_A2A2A2));
                tv_month_coin.setTextColor(getColor(R.color.color_FF8B02));
                tv_month_4.setTextColor(getColor(R.color.color_FF8B02));

                tv_week_1.setTextColor(getColor(R.color.black));
                tv_week_2.setTextColor(getColor(R.color.color_A2A2A2));
                tv_week_coin.setTextColor(getColor(R.color.color_FF8B02));
                tv_week_4.setTextColor(getColor(R.color.color_FF8B02));

                iv_gift.setImageAlpha(255);
                iv_tequan.setImageAlpha(255);
                tv_gift_1.setTextColor(0xff000000);
                tv_gift_2.setTextColor(0xff939393);
                tv_tequan_1.setTextColor(0xff000000);
                tv_tequan_2.setTextColor(0xff939393);
                break;

        }

    }

    private int getColor(int res) {
        return getContext().getResources().getColor(res);
    }

    private String getPrice() {
        String price = "";
        switch (type) {
            case "0":
                price = coin_year;
                break;
            case "1":
                price = coin_month;
                break;
            case "2":
                price = coin_week;
                break;
        }
        return price;
    }

    private void initPayDialog() {
        LivePriceDialog.Builder builder = new LivePriceDialog.Builder(getContext());
        builder.create();

        builder.setContent("您的金币不足,是否前往充值?");
        builder.setTitle("金币不足");
        builder.setSubmitText(WordUtil.getString(R.string.Submit));
        builder.setOnSubmit(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.livePriceDialog.dismiss();
                AppManager.getAppManager().startActivity(ToPayActivity.class);
            }
        });
        builder.setCanCancel(true);
        builder.livePriceDialog.show();
    }
}

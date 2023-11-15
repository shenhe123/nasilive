package com.feicui365.live.presenter;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.feicui365.live.contract.ShopContract;
import com.feicui365.live.model.ShopModel;
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.model.entity.QCloudData;
import com.feicui365.live.net.APIService;
import com.feicui365.live.net.RxScheduler;
import com.feicui365.live.shop.entity.Address;
import com.feicui365.live.shop.entity.CartGoodInfo;
import com.feicui365.live.shop.entity.ClassIfy;
import com.feicui365.live.shop.entity.EvaluateList;
import com.feicui365.live.shop.entity.GoodInfo;
import com.feicui365.live.shop.entity.GoodManager;
import com.feicui365.live.shop.entity.LogisticsInfo;
import com.feicui365.live.shop.entity.OrderDetails;
import com.feicui365.live.shop.entity.OrderGoods;
import com.feicui365.live.shop.entity.SellerCashOut;
import com.feicui365.live.shop.entity.SellerOrderInfo;
import com.feicui365.live.shop.entity.SellerWalletInfo;
import com.feicui365.live.shop.entity.UserOrderInfo;
import com.feicui365.live.shop.entity.RefundInfo;
import com.feicui365.live.shop.entity.RefundOrderGoods;
import com.feicui365.live.shop.entity.ShopGoodList;
import com.feicui365.live.shop.entity.ShopSellerInfo;
import com.feicui365.live.shop.entity.ShopUserInfo;
import com.feicui365.live.util.HttpUtils;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.live.util.ToastUtils;

import java.util.ArrayList;

import io.reactivex.functions.Consumer;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ShopPresenter extends BasePresenter<ShopContract.View> implements ShopContract.Presenter {


    private ShopContract.Model model;

    public ShopPresenter() {
        model = new ShopModel();
    }

    @Override
    public void getGoodsInfo(String goodsid) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.getGoodsInfo(new FormBody.Builder().add("platform", "2")
                .add("goodsid", goodsid)

                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken()).build())
                .compose(RxScheduler.<BaseResponse<GoodInfo>>Flo_io_main())
                .as(mView.<BaseResponse<GoodInfo>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<GoodInfo>>() {
                    @Override
                    public void accept(BaseResponse<GoodInfo> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.getGoodsInfo(bean.getData());
                        }

                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void goodsEvaluateList(String goodsid, String page) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.goodsEvaluateList(new FormBody.Builder().add("platform", "2")
                .add("goodsid", goodsid)
                .add("type", "0")
                .add("page", page)
                .add("size", "20")
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken()).build())
                .compose(RxScheduler.<BaseResponse<EvaluateList>>Flo_io_main())
                .as(mView.<BaseResponse<EvaluateList>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<EvaluateList>>() {
                    @Override
                    public void accept(BaseResponse<EvaluateList> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.goodsEvaluateList(bean.getData());
                        }

                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void getShopGoodsList(String shopid, String page) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.getShopGoodsList(new FormBody.Builder().add("platform", "2")
                .add("shopid", shopid)
                .add("page", page)
                .add("size", "20")
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken()).build())
                .compose(RxScheduler.<BaseResponse<ShopGoodList>>Flo_io_main())
                .as(mView.<BaseResponse<ShopGoodList>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ShopGoodList>>() {
                    @Override
                    public void accept(BaseResponse<ShopGoodList> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.getShopGoodsList(bean.getData());
                        }

                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void getCartGoodsList(String page) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.getCartGoodsList(new FormBody.Builder().add("platform", "2")

                .add("page", page)
                .add("size", "20")
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken()).build())
                .compose(RxScheduler.<BaseResponse<ArrayList<CartGoodInfo>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<CartGoodInfo>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<CartGoodInfo>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<CartGoodInfo>> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.getCartGoodsList(bean.getData());
                        }

                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void addAddress(String province, String city, String district, String address, String name, String mobile, String is_default) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.addAddress(new FormBody.Builder().add("platform", "2")

                .add("province", province)
                .add("city", city)
                .add("district", district)
                .add("address", address)
                .add("name", name)
                .add("mobile", mobile)
                .add("is_default", is_default)
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken()).build())
                .compose(RxScheduler.<BaseResponse>Flo_io_main())
                .as(mView.<BaseResponse>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.addAddress(bean);
                        }

                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void editAddress(String addressid, String province, String city, String district, String address, String name, String mobile, String is_default) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.editAddress(new FormBody.Builder().add("platform", "2")
                .add("addressid", addressid)
                .add("province", province)
                .add("city", city)
                .add("district", district)
                .add("address", address)
                .add("name", name)
                .add("mobile", mobile)
                .add("is_default", is_default)
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken()).build())
                .compose(RxScheduler.<BaseResponse>Flo_io_main())
                .as(mView.<BaseResponse>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.addAddress(bean);
                        }

                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void delCartGoods(String cartgoodsids) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.delCartGoods(new FormBody.Builder().add("platform", "2")

                .add("cartgoodsids", cartgoodsids)
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken()).build())
                .compose(RxScheduler.<BaseResponse>Flo_io_main())
                .as(mView.<BaseResponse>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.delCartGoods(bean);
                        }

                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void addCartGoods(String inventoryid, String shopid, String count) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.addCartGoods(new FormBody.Builder().add("platform", "2")

                .add("inventoryid", inventoryid)
                .add("shopid", shopid)
                .add("count", count)
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken()).build())
                .compose(RxScheduler.<BaseResponse>Flo_io_main())
                .as(mView.<BaseResponse>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.addCartGoods(bean);
                        }

                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void getUserAddressList() {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.getUserAddressList(new FormBody.Builder().add("platform", "2")

                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken()).build())
                .compose(RxScheduler.<BaseResponse<ArrayList<Address>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<Address>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<Address>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<Address>> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.getUserAddressList(bean.getData());
                        }

                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void submitOrder(String addressid, ArrayList lists, String total_price) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现

        JSONObject json_result = new JSONObject();
        json_result.put("addressid", addressid);
        json_result.put("lists", JSON.toJSON(lists));
        json_result.put("total_price", total_price);
        json_result.put("uid", MyUserInstance.getInstance().getUserinfo().getId());
        json_result.put("token", MyUserInstance.getInstance().getUserinfo().getToken());
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, String.valueOf(json_result));
        Log.e("body", String.valueOf(json_result));

        OkGo.<String>post(APIService.baseUrl + APIService.SubmitOrder)
                .upRequestBody(body)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = HttpUtils.getInstance().check(response);
                        if (HttpUtils.getInstance().swtichStatus(jsonObject)) {
                            mView.submitOrder(jsonObject);
                        }


                        mView.hideLoading();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        mView.hideLoading();
                    }
                });

    }

    @Override
    public void publishGoods(String categoryid, ArrayList colors, String delivery, String desc, String desc_img_urls,
                             String freight, ArrayList inventorys, ArrayList sizes, String thumb_urls, String title, String address) {


        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现

        JSONObject json_result = new JSONObject();
        json_result.put("categoryid", categoryid);
        json_result.put("colors", JSON.toJSON(colors));
        json_result.put("delivery", delivery);
        json_result.put("desc", desc);
        json_result.put("desc_img_urls", desc_img_urls);
        json_result.put("freight", freight);
        json_result.put("inventorys", JSON.toJSON(inventorys));
        if (sizes.size() != 0) {

            json_result.put("sizes", JSON.toJSON(sizes));
        }

        json_result.put("thumb_urls", thumb_urls);
        json_result.put("title", title);
        json_result.put("address", address);
        json_result.put("uid", MyUserInstance.getInstance().getUserinfo().getId());
        json_result.put("token", MyUserInstance.getInstance().getUserinfo().getToken());
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, String.valueOf(json_result));
        Log.e("body", String.valueOf(json_result));

        OkGo.<String>post(APIService.baseUrl + APIService.PublishGoods)
                .upRequestBody(body)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = HttpUtils.getInstance().check(response);
                        if (HttpUtils.getInstance().swtichStatus(jsonObject)) {
                            mView.publishGoods(jsonObject);
                        }
                        mView.hideLoading();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void payDeposit(String pay_channel) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.payDeposit(new FormBody.Builder().add("platform", "2")
                .add("pay_channel", pay_channel)
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken()).build())
                .compose(RxScheduler.<BaseResponse>Flo_io_main())
                .as(mView.<BaseResponse>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.payDeposit(bean);
                        }

                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void getShopUserInfo() {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.getShopUserInfo(new FormBody.Builder().add("platform", "2")

                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken()).build())
                .compose(RxScheduler.<BaseResponse<ShopUserInfo>>Flo_io_main())
                .as(mView.<BaseResponse<ShopUserInfo>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ShopUserInfo>>() {
                    @Override
                    public void accept(BaseResponse<ShopUserInfo> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.getShopUserInfo(bean.getData());
                        }

                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void getOrderList(String type, String page) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.getOrderList(new FormBody.Builder().add("platform", "2")
                .add("type", type)
                .add("page", page)
                .add("size", "20")
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken()).build())
                .compose(RxScheduler.<BaseResponse<ArrayList<UserOrderInfo>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<UserOrderInfo>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<UserOrderInfo>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<UserOrderInfo>> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.getOrderList(bean.getData());
                        }

                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void shopOrderList(String type, String page) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.shopOrderList(new FormBody.Builder().add("platform", "2")
                .add("type", type)
                .add("page", page)
                .add("size", "20")
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken()).build())
                .compose(RxScheduler.<BaseResponse<ArrayList<SellerOrderInfo>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<SellerOrderInfo>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<SellerOrderInfo>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<SellerOrderInfo>> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.shopOrderList(bean.getData());
                        }

                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void getRefundOrderList(String type, String page) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.getRefundOrderList(new FormBody.Builder().add("platform", "2")
                .add("type", type)
                .add("page", page)
                .add("size", "20")
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken()).build())
                .compose(RxScheduler.<BaseResponse<ArrayList<RefundOrderGoods>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<RefundOrderGoods>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<RefundOrderGoods>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<RefundOrderGoods>> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.getRefundOrderList(bean.getData());
                        }

                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void getSellerRefundOrderList(String type, String page) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.getSellerRefundOrderList(new FormBody.Builder().add("platform", "2")
                .add("type", type)
                .add("page", page)
                .add("size", "20")
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken()).build())
                .compose(RxScheduler.<BaseResponse<ArrayList<RefundOrderGoods>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<RefundOrderGoods>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<RefundOrderGoods>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<RefundOrderGoods>> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.getRefundOrderList(bean.getData());
                        }

                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void getOrderInfo(String suborderid) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        mView.showLoading();
        //具体实现
        model.getOrderInfo(new FormBody.Builder().add("platform", "2")

                .add("suborderid", suborderid)
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken()).build())
                .compose(RxScheduler.<BaseResponse<OrderDetails>>Flo_io_main())
                .as(mView.<BaseResponse<OrderDetails>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<OrderDetails>>() {
                    @Override
                    public void accept(BaseResponse<OrderDetails> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.getOrderInfo(bean.getData());
                        }

                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void getExpressInfo(String suborderid) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        mView.showLoading();
        //具体实现
        model.getExpressInfo(new FormBody.Builder().add("platform", "2")

                .add("suborderid", suborderid)
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken()).build())
                .compose(RxScheduler.<BaseResponse<LogisticsInfo>>Flo_io_main())
                .as(mView.<BaseResponse<LogisticsInfo>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<LogisticsInfo>>() {
                    @Override
                    public void accept(BaseResponse<LogisticsInfo> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.getExpressInfo(bean.getData());
                        } else {
                            mView.getExpressInfo(null);
                        }

                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void applyReturnGoods(String ordergoodsid, String reason, String amount, String desc) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        mView.showLoading();
        //具体实现
        model.applyReturnGoods(new FormBody.Builder().add("platform", "2")
                .add("reason", reason)
                .add("amount", amount)
                .add("desc", desc)
                .add("ordergoodsid", ordergoodsid)
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken()).build())
                .compose(RxScheduler.<BaseResponse>Flo_io_main())
                .as(mView.<BaseResponse>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.applyReturnGoods(bean);
                        }

                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void refundOrderInfo(String ordergoodsid) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        mView.showLoading();
        //具体实现
        model.refundOrderInfo(new FormBody.Builder().add("platform", "2")

                .add("ordergoodsid", ordergoodsid)
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken()).build())
                .compose(RxScheduler.<BaseResponse<RefundInfo>>Flo_io_main())
                .as(mView.<BaseResponse<RefundInfo>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<RefundInfo>>() {
                    @Override
                    public void accept(BaseResponse<RefundInfo> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.refundOrderInfo(bean.getData());
                        }

                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void shopHomePageData() {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        mView.showLoading();
        //具体实现
        model.shopHomePageData(new FormBody.Builder().add("platform", "2")

                .add("shopid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken()).build())
                .compose(RxScheduler.<BaseResponse<ShopSellerInfo>>Flo_io_main())
                .as(mView.<BaseResponse<ShopSellerInfo>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ShopSellerInfo>>() {
                    @Override
                    public void accept(BaseResponse<ShopSellerInfo> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.shopHomePageData(bean.getData());
                        }

                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void operateReturn(String operate, String addressid, String need_return, String returnid) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        mView.showLoading();
        //具体实现
        model.operateReturn(new FormBody.Builder().add("platform", "2")
                .add("returnid", returnid)
                .add("operate", operate)
                .add("addressid", addressid)
                .add("need_return", need_return)
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken()).build())
                .compose(RxScheduler.<BaseResponse>Flo_io_main())
                .as(mView.<BaseResponse>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.operateReturn(bean);
                        }

                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void getShopCategoryList() {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        mView.showLoading();
        //具体实现
        model.getShopCategoryList(new FormBody.Builder().add("platform", "2")

                /*               .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                               .add("token", MyUserInstance.getInstance().getUserinfo().getToken())*/.build())
                .compose(RxScheduler.<BaseResponse<ArrayList<ClassIfy>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<ClassIfy>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<ClassIfy>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<ClassIfy>> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.getShopCategoryList(bean.getData());
                        }

                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }


    public void getTempKeysForCos() {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现

        model.getTempKeysForCos(new FormBody.Builder()
                .add("platform", "2")
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId()).add("token", MyUserInstance.getInstance().getUserinfo().getToken()).build())
                .compose(RxScheduler.<BaseResponse<QCloudData>>Flo_io_main())
                .as(mView.<BaseResponse<QCloudData>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<QCloudData>>() {
                    @Override
                    public void accept(BaseResponse<QCloudData> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.setTempKeysForCos(bean.getData());
                        }
                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void deliveryOrder(String suborderid, String express_no, String express_company) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现

        model.deliveryOrder(new FormBody.Builder()
                .add("platform", "2")
                .add("suborderid", suborderid)
                .add("express_no", express_no)
                .add("express_company", express_company)
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken()).build())
                .compose(RxScheduler.<BaseResponse>Flo_io_main())
                .as(mView.<BaseResponse>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.deliveryOrder(bean);
                        } else {
                            ToastUtils.showT(bean.getMsg());
                        }
                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void getShopGoods(String type, String page) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现

        model.getShopGoods(new FormBody.Builder()
                .add("platform", "2")
                .add("type", type)
                .add("page", page)
                .add("size", "20")
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken()).build())
                .compose(RxScheduler.<BaseResponse<ArrayList<GoodManager>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<GoodManager>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<GoodManager>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<GoodManager>> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.getShopGoods(bean.getData());
                        }
                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void setGoodsSaleStatus(String status, String goodsid) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现

        model.setGoodsSaleStatus(new FormBody.Builder()
                .add("platform", "2")
                .add("status", status)
                .add("goodsid", goodsid)

                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken()).build())
                .compose(RxScheduler.<BaseResponse>Flo_io_main())
                .as(mView.<BaseResponse>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.setGoodsSaleStatus(bean);
                        }
                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void walletInfo() {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现

        model.walletInfo(new FormBody.Builder()
                .add("platform", "2")
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken()).build())
                .compose(RxScheduler.<BaseResponse<SellerWalletInfo>>Flo_io_main())
                .as(mView.<BaseResponse<SellerWalletInfo>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<SellerWalletInfo>>() {
                    @Override
                    public void accept(BaseResponse<SellerWalletInfo> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.walletInfo(bean.getData());
                        }
                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void SellerwithdrawLog(String page) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现

        model.SellerwithdrawLog(new FormBody.Builder()
                .add("platform", "2")
                .add("page", page)
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken()).build())
                .compose(RxScheduler.<BaseResponse<ArrayList<SellerCashOut>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<SellerCashOut>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<SellerCashOut>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<SellerCashOut>> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.SellerwithdrawLog(bean.getData());
                        }
                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void Sellerwithdraw(String alipay_account, String alipay_name, String apply_cash) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现

        model.Sellerwithdraw(new FormBody.Builder()
                .add("platform", "2")
                .add("alipay_account", alipay_account)
                .add("alipay_name", alipay_name)
                .add("apply_cash", apply_cash)
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken()).build())
                .compose(RxScheduler.<BaseResponse>Flo_io_main())
                .as(mView.<BaseResponse>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.Sellerwithdraw(bean);
                        }
                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void getUserVisits(String page) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现

        model.getUserVisits(new FormBody.Builder()
                .add("platform", "2")
                .add("page", page)
                .add("size", "20")

                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken()).build())
                .compose(RxScheduler.<BaseResponse<ArrayList<OrderGoods>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<OrderGoods>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<OrderGoods>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<OrderGoods>> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.getUserVisits(bean.getData());
                        }
                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void delUserVisits(String visitsids) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现

        model.delUserVisits(new FormBody.Builder()
                .add("platform", "2")
                .add("visitsids", visitsids)
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken()).build())
                .compose(RxScheduler.<BaseResponse>Flo_io_main())
                .as(mView.<BaseResponse>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.delUserVisits(bean);
                        }
                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void cancelOrder(String suborderid) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现

        model.cancelOrder(new FormBody.Builder()
                .add("platform", "2")
                .add("suborderid", suborderid)
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken()).build())
                .compose(RxScheduler.<BaseResponse>Flo_io_main())
                .as(mView.<BaseResponse>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.cancelOrder(bean);
                        }
                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });

    }

    @Override
    public void confirmReceipt(String suborderid) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现

        model.confirmReceipt(new FormBody.Builder()
                .add("platform", "2")
                .add("suborderid", suborderid)
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken()).build())
                .compose(RxScheduler.<BaseResponse>Flo_io_main())
                .as(mView.<BaseResponse>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.confirmReceipt(bean);
                        }
                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void submitComment(String ordergoodsid, String goodsid, String score, String content, String img_urls) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现

        FormBody.Builder builder = new FormBody.Builder()
                .add("platform", "2")
                .add("ordergoodsid", ordergoodsid)
                .add("goodsid", goodsid)
                .add("score", score)
                .add("content", content)

                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken());
        if (img_urls != null & !"".equals(img_urls)) {
            builder.add("img_urls", img_urls);
        }


        model.submitComment(builder.build())
                .compose(RxScheduler.<BaseResponse>Flo_io_main())
                .as(mView.<BaseResponse>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {

                        if (bean.isSuccess()) {
                            mView.submitComment(bean);
                        } else {
                            ToastUtils.showT(bean.getMsg());
                        }
                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void submitReturnExpress(String returnid, String express_no) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现

        model.submitReturnExpress(new FormBody.Builder()
                .add("platform", "2")
                .add("returnid", returnid)
                .add("express_no", express_no)

                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken()).build())
                .compose(RxScheduler.<BaseResponse>Flo_io_main())
                .as(mView.<BaseResponse>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {

                        if (bean.isSuccess()) {
                            mView.submitReturnExpress(bean);
                        } else {
                            ToastUtils.showT(bean.getMsg());
                        }
                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void shopReceiveReturn(String returnid) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现

        model.shopReceiveReturn(new FormBody.Builder()
                .add("platform", "2")
                .add("returnid", returnid)
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken()).build())
                .compose(RxScheduler.<BaseResponse>Flo_io_main())
                .as(mView.<BaseResponse>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {

                        if (bean.isSuccess()) {
                            mView.shopReceiveReturn(bean);
                        } else {
                            ToastUtils.showT(bean.getMsg());
                        }
                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }
}

package com.feicui365.live.model;

import com.feicui365.live.contract.ShopContract;
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.model.entity.QCloudData;
import com.feicui365.live.net.RetrofitClient;
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

import java.util.ArrayList;

import io.reactivex.Flowable;
import okhttp3.FormBody;

public class ShopModel implements ShopContract.Model {


    @Override
    public Flowable<BaseResponse<GoodInfo>> getGoodsInfo(FormBody body) {
        return RetrofitClient.getInstance().getApi().getGoodsInfo(body);
    }

    @Override
    public Flowable<BaseResponse<EvaluateList>> goodsEvaluateList(FormBody body) {
        return RetrofitClient.getInstance().getApi().goodsEvaluateList(body);
    }

    @Override
    public Flowable<BaseResponse<ShopGoodList>> getShopGoodsList(FormBody body) {
        return RetrofitClient.getInstance().getApi().getShopGoodsList(body);
    }

    @Override
    public Flowable<BaseResponse<ArrayList<CartGoodInfo>>> getCartGoodsList(FormBody body) {
        return RetrofitClient.getInstance().getApi().getCartGoodsList(body);
    }

    @Override
    public Flowable<BaseResponse> addAddress(FormBody body) {
        return RetrofitClient.getInstance().getApi().addAddress(body);
    }

    @Override
    public Flowable<BaseResponse> editAddress(FormBody body) {
        return RetrofitClient.getInstance().getApi().editAddress(body);
    }

    @Override
    public Flowable<BaseResponse> delCartGoods(FormBody body) {
        return RetrofitClient.getInstance().getApi().delCartGoods(body);
    }

    @Override
    public Flowable<BaseResponse> addCartGoods(FormBody body) {
        return RetrofitClient.getInstance().getApi().addCartGoods(body);
    }

    @Override
    public Flowable<BaseResponse> payDeposit(FormBody body) {
        return RetrofitClient.getInstance().getApi().payDeposit(body);
    }



    @Override
    public Flowable<BaseResponse<ArrayList<Address>>> getUserAddressList(FormBody body) {
         return RetrofitClient.getInstance().getApi().getUserAddressList(body);
    }

    @Override
    public Flowable<BaseResponse<ShopUserInfo>> getShopUserInfo(FormBody body) {
        return RetrofitClient.getInstance().getApi().getShopUserInfo(body);
    }

    @Override
    public Flowable<BaseResponse<ArrayList<UserOrderInfo>>> getOrderList(FormBody body) {
        return RetrofitClient.getInstance().getApi().getOrderList(body);
    }

    @Override
    public Flowable<BaseResponse<ArrayList<SellerOrderInfo>>> shopOrderList(FormBody body) {
        return RetrofitClient.getInstance().getApi().shopOrderList(body);
    }

    @Override
    public Flowable<BaseResponse<ArrayList<RefundOrderGoods>>> getRefundOrderList(FormBody body) {
        return RetrofitClient.getInstance().getApi().getRefundOrderList(body);
    }

    @Override
    public Flowable<BaseResponse<ArrayList<RefundOrderGoods>>> getSellerRefundOrderList(FormBody body) {
        return RetrofitClient.getInstance().getApi().getSellerRefundOrderList(body);
    }

    @Override
    public Flowable<BaseResponse<OrderDetails>> getOrderInfo(FormBody body) {
        return RetrofitClient.getInstance().getApi().getOrderInfo(body);
    }

    @Override
    public Flowable<BaseResponse<LogisticsInfo>> getExpressInfo(FormBody body) {
        return RetrofitClient.getInstance().getApi().getExpressInfo(body);
    }

    @Override
    public Flowable<BaseResponse> applyReturnGoods(FormBody body) {
        return RetrofitClient.getInstance().getApi().applyReturnGoods(body);
    }

    @Override
    public Flowable<BaseResponse<RefundInfo>> refundOrderInfo(FormBody body) {
        return RetrofitClient.getInstance().getApi().returnOrderInfo(body);
    }

    @Override
    public Flowable<BaseResponse<ShopSellerInfo>> shopHomePageData(FormBody body) {
        return RetrofitClient.getInstance().getApi().shopHomePageData(body);
    }

    @Override
    public Flowable<BaseResponse> operateReturn(FormBody body) {
        return RetrofitClient.getInstance().getApi().operateReturn(body);
    }

    @Override
    public Flowable<BaseResponse<ArrayList<ClassIfy>>> getShopCategoryList(FormBody body) {
        return RetrofitClient.getInstance().getApi().getShopCategoryList(body);
    }

    @Override
    public Flowable<BaseResponse<QCloudData>> getTempKeysForCos(FormBody body) {
        return RetrofitClient.getInstance().getApi().getTempKeysForCos(body);
    }

    @Override
    public Flowable<BaseResponse> deliveryOrder(FormBody body) {
        return RetrofitClient.getInstance().getApi().deliveryOrder(body);
    }

    @Override
    public Flowable<BaseResponse<ArrayList<GoodManager>>> getShopGoods(FormBody body) {
        return RetrofitClient.getInstance().getApi().getShopGoods(body);
    }

    @Override
    public Flowable<BaseResponse> setGoodsSaleStatus(FormBody body) {
        return RetrofitClient.getInstance().getApi().setGoodsSaleStatus(body);
    }

    @Override
    public Flowable<BaseResponse<SellerWalletInfo>> walletInfo(FormBody body) {
        return RetrofitClient.getInstance().getApi().walletInfo(body);
    }



    @Override
    public Flowable<BaseResponse> Sellerwithdraw(FormBody body) {
        return RetrofitClient.getInstance().getApi().Sellerwithdraw(body);
    }

    @Override
    public Flowable<BaseResponse<ArrayList<SellerCashOut>>> SellerwithdrawLog(FormBody body) {
        return RetrofitClient.getInstance().getApi().SellerwithdrawLog(body);
    }

    @Override
    public Flowable<BaseResponse<ArrayList<OrderGoods>>> getUserVisits(FormBody body) {
        return RetrofitClient.getInstance().getApi().getUserVisits(body);
    }

    @Override
    public Flowable<BaseResponse> delUserVisits(FormBody body) {
        return RetrofitClient.getInstance().getApi().delUserVisits(body);
    }

    @Override
    public Flowable<BaseResponse> cancelOrder(FormBody body) {
        return RetrofitClient.getInstance().getApi().cancelOrder(body);
    }

    @Override
    public Flowable<BaseResponse> confirmReceipt(FormBody body) {
        return RetrofitClient.getInstance().getApi().confirmReceipt(body);
    }

    @Override
    public Flowable<BaseResponse> submitComment(FormBody body) {
        return RetrofitClient.getInstance().getApi().submitComment(body);
    }

    @Override
    public Flowable<BaseResponse> submitReturnExpress(FormBody body) {
        return RetrofitClient.getInstance().getApi().submitReturnExpress(body);
    }

    @Override
    public Flowable<BaseResponse> shopReceiveReturn(FormBody body) {
        return RetrofitClient.getInstance().getApi().shopReceiveReturn(body);
    }


}

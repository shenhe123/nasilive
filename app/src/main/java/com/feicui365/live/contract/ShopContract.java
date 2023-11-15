package com.feicui365.live.contract;

import com.alibaba.fastjson.JSONObject;
import com.feicui365.live.base.BaseView;
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.model.entity.QCloudData;
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

public interface ShopContract {
    interface Model {
        Flowable<BaseResponse<GoodInfo>> getGoodsInfo(FormBody body);
        Flowable<BaseResponse<EvaluateList>> goodsEvaluateList(FormBody body);
        Flowable<BaseResponse<ShopGoodList>> getShopGoodsList(FormBody body);
        Flowable<BaseResponse<ArrayList<CartGoodInfo>>> getCartGoodsList(FormBody body);
        Flowable<BaseResponse> addAddress(FormBody body);
        Flowable<BaseResponse> editAddress(FormBody body);
        Flowable<BaseResponse> delCartGoods(FormBody body);
        Flowable<BaseResponse> addCartGoods(FormBody body);
        Flowable<BaseResponse> payDeposit(FormBody body);
        Flowable<BaseResponse<ArrayList<Address>>> getUserAddressList(FormBody body);
        Flowable<BaseResponse<ShopUserInfo>> getShopUserInfo(FormBody body);

        Flowable<BaseResponse<ArrayList<UserOrderInfo>>> getOrderList(FormBody body);
        Flowable<BaseResponse<ArrayList<SellerOrderInfo>>> shopOrderList(FormBody body);


        Flowable<BaseResponse<ArrayList<RefundOrderGoods>>> getRefundOrderList(FormBody body);
        Flowable<BaseResponse<ArrayList<RefundOrderGoods>>> getSellerRefundOrderList(FormBody body);

        Flowable<BaseResponse<OrderDetails>> getOrderInfo(FormBody body);
        Flowable<BaseResponse<LogisticsInfo>> getExpressInfo(FormBody body);

        Flowable<BaseResponse> applyReturnGoods(FormBody body);

        Flowable<BaseResponse<RefundInfo>> refundOrderInfo(FormBody body);
        Flowable<BaseResponse<ShopSellerInfo>> shopHomePageData(FormBody body);

        Flowable<BaseResponse> operateReturn(FormBody body);

        Flowable<BaseResponse<ArrayList<ClassIfy>>> getShopCategoryList(FormBody body);

        Flowable<BaseResponse<QCloudData>> getTempKeysForCos(FormBody body);

        Flowable<BaseResponse> deliveryOrder(FormBody body);

        Flowable<BaseResponse<ArrayList<GoodManager>>> getShopGoods(FormBody body);

        Flowable<BaseResponse> setGoodsSaleStatus(FormBody body);

        Flowable<BaseResponse<SellerWalletInfo>> walletInfo(FormBody body);

        Flowable<BaseResponse> Sellerwithdraw(FormBody body);

        Flowable<BaseResponse<ArrayList<SellerCashOut>>> SellerwithdrawLog(FormBody body);

        Flowable<BaseResponse<ArrayList<OrderGoods>>> getUserVisits(FormBody body);

        Flowable<BaseResponse> delUserVisits(FormBody body);


        Flowable<BaseResponse> cancelOrder(FormBody body);

        Flowable<BaseResponse> confirmReceipt(FormBody body);


        Flowable<BaseResponse> submitComment(FormBody body);

        Flowable<BaseResponse> submitReturnExpress(FormBody body);

        Flowable<BaseResponse> shopReceiveReturn(FormBody body);



    }

    interface View extends BaseView {
        @Override
        void showLoading();

        @Override
        void hideLoading();

        @Override
        void onError(Throwable throwable);

        default void getGoodsInfo(GoodInfo baseResponse){

        }
        default void setTempKeysForCos(QCloudData data){

        }
        default void goodsEvaluateList(EvaluateList baseResponse){

        }

        default void getShopGoodsList(ShopGoodList baseResponse){

        }
        default void getCartGoodsList(ArrayList<CartGoodInfo> baseResponse){

        }

        default void addAddress(BaseResponse baseResponse){

        }
        default void editAddress(BaseResponse baseResponse){

        }
        default void delCartGoods(BaseResponse baseResponse){

        }
        default void addCartGoods(BaseResponse baseResponse){

        }
        default void getUserAddressList(ArrayList<Address> baseResponse){

        }
        default void payDeposit(BaseResponse baseResponse){

        }

        default void submitOrder(JSONObject baseResponse){

        }
        default void publishGoods(JSONObject baseResponse){

        }
        default void getShopUserInfo(ShopUserInfo baseResponse){

        }
        default void getOrderList(ArrayList<UserOrderInfo> baseResponse){

        }

        default void shopOrderList(ArrayList<SellerOrderInfo> baseResponse){

        }
        default void getRefundOrderList(ArrayList<RefundOrderGoods> baseResponse){

        }
        default void getSellerRefundOrderList(ArrayList<RefundOrderGoods> baseResponse){

        }
        default void getOrderInfo(OrderDetails baseResponse){

        }
        default void getExpressInfo(LogisticsInfo baseResponse){

        }

        default void applyReturnGoods(BaseResponse baseResponse){

        }
        default void refundOrderInfo(RefundInfo baseResponse){

        }
        default void shopHomePageData(ShopSellerInfo baseResponse){

        }
        default void operateReturn(BaseResponse baseResponse){

        }
        default void getShopCategoryList(ArrayList<ClassIfy> baseResponse){

        }

        default void deliveryOrder(BaseResponse baseResponse){

        }
        default void setGoodsSaleStatus(BaseResponse baseResponse){

        }


        default void getShopGoods(ArrayList<GoodManager> baseResponse){

        }

        default void walletInfo(SellerWalletInfo baseResponse){

        }

        default void SellerwithdrawLog(ArrayList<SellerCashOut> baseResponse){

        }
        default void Sellerwithdraw(BaseResponse baseResponse){

        }

        default void getUserVisits(ArrayList<OrderGoods> baseResponse){

        }

        default void delUserVisits(BaseResponse baseResponse){

        }
        default void cancelOrder(BaseResponse baseResponse){

        }
        default void confirmReceipt(BaseResponse baseResponse){

        }



        default void submitComment(BaseResponse baseResponse){

        }

        default void submitReturnExpress(BaseResponse baseResponse){

        }

        default void shopReceiveReturn(BaseResponse baseResponse){

        }


    }

    interface Presenter {
        void getGoodsInfo(String goodsid);
        void goodsEvaluateList(String goodsid,String page);
        void getShopGoodsList(String shopid,String page);
        void getCartGoodsList(String page);
        void addAddress(String province,String city,String district,String address,String name,String mobile,String is_default);
        void editAddress(String addressid,String province,String city,String district,String address,String name,String mobile,String is_default);
        void delCartGoods(String cartgoodsids);
        void addCartGoods(String inventoryid,String shopid,String count);
        void getUserAddressList();
        void submitOrder(String addressid,ArrayList lists,String total_price);
        //商家发布商品
        void publishGoods(String categoryid,ArrayList colors,String delivery,String desc,String desc_img_urls,String freight,ArrayList inventorys,ArrayList sizes,String thumb_urls,String title,String address);
        void payDeposit(String pay_channel);
        void getShopUserInfo();
        void getOrderList(String type,String page);
        void shopOrderList(String type,String page);


        void getRefundOrderList(String type,String page);
        void getSellerRefundOrderList(String type,String page);

        void getOrderInfo(String suborderid);
        void getExpressInfo(String suborderid);
        void applyReturnGoods(String ordergoodsid,String reason,String amount,String desc);
        void refundOrderInfo(String ordergoodsid);
        void shopHomePageData();
        void operateReturn(String operate,String addressid,String need_return,String returnid);
        void getShopCategoryList();
        void getTempKeysForCos();
        void deliveryOrder(String suborderid,String express_no,String express_company);
        void getShopGoods(String type,String page);
        void setGoodsSaleStatus(String status,String goodsid);

        void walletInfo();
        void SellerwithdrawLog(String page);
        void Sellerwithdraw(String alipay_account,String alipay_name,String apply_cash);
        void getUserVisits(String page);
        void delUserVisits(String visitsids);
        void cancelOrder(String suborderid);

        void confirmReceipt(String suborderid);

        void submitComment(String ordergoodsid,String goodsid,String score,String content,String img_urls);

        void submitReturnExpress(String returnid,String express_no);

        void shopReceiveReturn(String returnid);
    }
}

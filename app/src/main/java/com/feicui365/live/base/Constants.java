package com.feicui365.live.base;


import android.os.Environment;

public class Constants {
    public static final String CHAT_INFO = "chatInfo";
    public static final String VIDEO = "VIDEO";
    public static final String VIDEO_CACHE = "VIDEO_CACHE";
    public static final String VIDEO_MODEL = "VIDEO_MODEL";
    public static final String VIDEO_CATEGORY_ID = "Videocategoryid";
    public static final String VIDEO_CATEGORY_TITLE = "Videocategorytitle";
    public static final String UMENG_APP_KEY = "";
    //外部sd卡
    public static final String DCMI_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
    //内部存储 /data/data/<application package>/files目录
    public static final String INNER_PATH = MyApp.getInstance().getFilesDir().getAbsolutePath();
    //文件夹名字
    public static final String PIC_PATH = INNER_PATH + "/temp/";
    public static final String DOWNLOAD_GIF = "downloadGif";

    public static final String GIF_PATH = INNER_PATH + "/gif/";
    private static final String DIR_NAME = "nasi";
    public static final String VIDEO_PATH_2 = DCMI_PATH + "/" + DIR_NAME + "/video/";

    public static final String PAYLOAD = "payload";


    public static final String PAY_ALI_NOT_ENABLE = "支付宝未接入";
    public static final String PAY_WX_NOT_ENABLE = "微信支付未接入";
    public static final String PAY_ALL_NOT_ENABLE = "未开启支付";
    public static final String GIF_GIFT_PREFIX = "gif_gift_";



    public static final String VIDEO_FACE_OPEN = "videoOpenFace";
    public static final String VIDEO_COMMENT = "videoComment";
    public static final String VIDEO_CHOSE_COMMENT = "videoChoseComment";
    public static final String VIDEO_PATH = "videoPath";
    public static final String VIDEO_RECORD_DURATION = "VIDEO_RECORD_DURATION";


    public static final int SETTING_PHOTO_CODE = 1006;

    public static final int GUEST_LOGIN = 1111;

    public static final int CIRCLE_CHOSE = 1009;

    public static final int TAKE_PHOTO = 0x0001;

    public static final int CHOSE_PICS = 10002;

    public static final String LIVE_INFO = "LIVE_INFO";
    public static final String LIVE_MEMBER = "LIVE_MEMBER";
    public static final String ANCHOR_ID = "ANCHOR_ID";
    public static final String ORDER_DETAILS = "orderdetails";
    public static final String SELLER_ORDER_TYPE = "sellerordertype";
    public static final String ORDER_GOODS = "ordergoods";
    public static final String EXPRESS_NO = "expressno";
    public static final String EXPRESS_COMPANY = "expresscompany";
    public static final String INVENTORY_INFO = "Inventoryinfo";
    public static final String GOODS_COUNT = "goodscount";
    public static final String SHOP_ID = "shopid";
    public static final String ORDER_ID = "orderid";

    public static final String PIC_URL = "pic_url";

    public static final int GET_CLASSIFY = 10085;
    public static final int GET_PIC_TITLE = 10084;
    public static final int GET_PIC_DESC = 10083;
    public static final int GET_COLOR_PIC = 10082;
    public static final int GET_REFUND = 10080;


    public static final String REFUND_INFO = "refundinfo";
    public static final String DELIVERY_INFO = "delivery_info";

    public static final String EDIT_ADDRESS = "editaddress";
    public static final String SHOP_CART_GOODS = "shopcartgoods";
    public static final String ORDER_TYPE = "ordertype";

    public static final String GOODS_INFO = "goodsinfo";
    public static final String GOODS_ID = "goodsid";
    public static final int GET_ZXING = 10086;
    public static final int GET_ADDRESS = 10087;
}

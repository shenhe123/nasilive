package com.feicui365.live.shop.utils;

public class DeliveryNameUtils {

    public static String getName(String code) {


        switch (code) {
            case "yuantong":
                return "圆通速递";
            case "yunda":
                return "韵达快递";
            case "zhongtong":
                return "中通快递";
            case "shunfeng":
                return "顺丰速运";
            case "youzhengguonei":
                return "邮政快递包裹";
            case "huitongkuaidi":
                return "百世快递";
            case "jd":
                return "京东物流";
            case "shentong":
                return "申通快递";
            case "tiantian":
                return "天天快递";

            case "ems":
                return "EMS";
            case "youzhengbk":
                return "邮政标准快递";
            case "debangwuliu":
                return "德邦";
            case "debangkuaidi":
                return "德邦快递";
            case "yundakuaiyun":
                return "韵达快运";
            case "baishiwuliu":
                return "百世快运";
            case "zhongtongkuaiyun":
                return "中通快运";
            case "yuantongkuaiyun":
                return "圆通快运";



            default:
                return "其他";
        }


    }

/*    $ExpressCompanyCodes =["圆通速递"=>"yuantong",
            "韵达快递"=>"yunda",
            "中通快递"=>"zhongtong",
            "顺丰速运"=>"shunfeng",
            "邮政快递包裹"=>"youzhengguonei",
            "百世快递"=>"huitongkuaidi",
            "京东物流"=>"jd",
            "申通快递"=>"shentong",
            "天天快递"=>"tiantian",
            "EMS"=>"ems",
            "邮政标准快递"=>"youzhengbk",
            "德邦"=>"debangwuliu",
            "德邦快递"=>"debangkuaidi",
            "韵达快运"=>"yundakuaiyun",
            "百世快运"=>"baishiwuliu",
            "中通快运"=>"zhongtongkuaiyun",
            "圆通快运"=>"yuantongkuaiyun"];*/
}

package com.feicui365.live.util;

import android.icu.text.DecimalFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ComputerUtils {
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double plus(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String getintimacy(Float f) {
        String data = "";
        DecimalFormat format = new DecimalFormat(".0");
        data=format.format(f);
        return data;
    }

    public static String divide(int size_1,int size_2,int point) {

        BigDecimal num_1 = new BigDecimal(String.valueOf(size_1));
        BigDecimal num_2 =new BigDecimal(String.valueOf(size_2));
        BigDecimal total =  num_1.divide(num_2).setScale(point, RoundingMode.HALF_UP) ;
        return total.toString();
    }

    public static String divide(long size_1,long size_2,int point) {

        BigDecimal num_1 = new BigDecimal(String.valueOf(size_1));
        BigDecimal num_2 =new BigDecimal(String.valueOf(size_2));
        BigDecimal total =  num_1.divide(num_2).setScale(point, RoundingMode.HALF_UP) ;
        return total.toString();
    }

    public static long computerTime(String createTime,String updateTime) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date mDate = sdf.parse(createTime);
            Date mUpdateDate = sdf.parse(updateTime);
            long into_time = mDate.getTime();
            long now_time = mUpdateDate.getTime();
            return now_time - into_time;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static int div(int v1, Double v2) {

        BigDecimal b1 = new BigDecimal(Integer.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, 0, BigDecimal.ROUND_HALF_UP).intValue();
    }

    public static int divUp(int v1, int v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_UP).intValue();
    }

}

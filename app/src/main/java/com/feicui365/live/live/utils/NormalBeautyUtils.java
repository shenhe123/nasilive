package com.feicui365.live.live.utils;

import android.graphics.Bitmap;


import com.feicui365.live.R;
import com.feicui365.live.base.MyApp;
import com.feicui365.live.model.entity.Filters;

import java.util.ArrayList;

import static android.graphics.BitmapFactory.decodeResource;

/**
 *
 */
public class NormalBeautyUtils {
    public static ArrayList<Filters> initFilterList() {
        ArrayList<Filters> filters = new ArrayList<>();

        Filters filter = new Filters();
        filter.setTag(1);
        filter.setFilterBitmap(null);
        filter.setFilterSrc(R.mipmap.yuantu);
        filter.setUnFilterSrc(R.mipmap.yuantu_pre);
        filter.setSpecialRatio(0f);
        filters.add(filter);

        Filters filter1 = new Filters();
        filter1.setTag(2);
        Bitmap bitmap1 = decodeResource(MyApp.getInstance().getResources(), R.mipmap.filter_bailan);
        filter1.setFilterBitmap(bitmap1);
        filter1.setFilterSrc(R.mipmap.bailan);
        filter1.setUnFilterSrc(R.mipmap.bailan_pre);
        filters.add(filter1);

        Filters filter2 = new Filters();
        filter2.setTag(2);
        Bitmap bitmap2 = decodeResource(MyApp.getInstance().getResources(), R.mipmap.filter_biaozhun);
        filter2.setFilterBitmap(bitmap2);
        filter2.setFilterSrc(R.mipmap.biaozhun);
        filter2.setUnFilterSrc(R.mipmap.biaozhun_pre);
        filters.add(filter2);

        Filters filter3 = new Filters();
        filter3.setTag(2);
        Bitmap bitmap3 = decodeResource(MyApp.getInstance().getResources(), R.mipmap.filter_chaotuo);
        filter3.setFilterBitmap(bitmap3);
        filter3.setFilterSrc(R.mipmap.chaotuo);
        filter3.setUnFilterSrc(R.mipmap.chaotuo_pre);
        filters.add(filter3);

        Filters filter4 = new Filters();
        filter4.setTag(2);
        Bitmap bitmap4 = decodeResource(MyApp.getInstance().getResources(), R.mipmap.filter_chunzhen);
        filter4.setFilterBitmap(bitmap4);
        filter4.setFilterSrc(R.mipmap.chunzhen);
        filter4.setUnFilterSrc(R.mipmap.chunzhen_pre);
        filters.add(filter4);

        Filters filter5 = new Filters();
        filter5.setTag(2);
        Bitmap bitmap5 = decodeResource(MyApp.getInstance().getResources(), R.mipmap.filter_fennen);
        filter5.setFilterBitmap(bitmap5);
        filter5.setFilterSrc(R.mipmap.fennen);
        filter5.setUnFilterSrc(R.mipmap.fennen_pre);
        filters.add(filter5);

        Filters filter6 = new Filters();
        filter6.setTag(2);
        Bitmap bitmap6 = decodeResource(MyApp.getInstance().getResources(), R.mipmap.filter_huaijiu);
        filter6.setFilterBitmap(bitmap6);
        filter6.setFilterSrc(R.mipmap.huaijiu);
        filter6.setUnFilterSrc(R.mipmap.huaijiu_pre);
        filters.add(filter6);

        Filters filter7 = new Filters();
        filter7.setTag(2);
        Bitmap bitmap7 = decodeResource(MyApp.getInstance().getResources(), R.mipmap.filter_landiao);
        filter7.setFilterBitmap(bitmap7);
        filter7.setFilterSrc(R.mipmap.landiao);
        filter7.setUnFilterSrc(R.mipmap.landiao_pre);
        filters.add(filter7);

        Filters filter8 = new Filters();
        filter8.setTag(2);
        Bitmap bitmap8 = decodeResource(MyApp.getInstance().getResources(), R.mipmap.filter_langman);
        filter8.setFilterBitmap(bitmap8);
        filter8.setFilterSrc(R.mipmap.langman);
        filter8.setUnFilterSrc(R.mipmap.langman_pre);
        filters.add(filter8);

        Filters filter9 = new Filters();
        filter9.setTag(2);
        Bitmap bitmap9 = decodeResource(MyApp.getInstance().getResources(), R.mipmap.filter_white);
        filter9.setFilterBitmap(bitmap9);
        filter9.setFilterSrc(R.mipmap.meibai);
        filter9.setUnFilterSrc(R.mipmap.meibai_pre);
        filters.add(filter9);

        Filters filter10 = new Filters();
        filter10.setTag(2);
        Bitmap bitmap10 = decodeResource(MyApp.getInstance().getResources(), R.mipmap.filter_qingliang);
        filter10.setFilterBitmap(bitmap10);
        filter10.setFilterSrc(R.mipmap.qingliang);
        filter10.setUnFilterSrc(R.mipmap.qingliang_pre);
        filters.add(filter10);

        Filters filter11 = new Filters();
        filter11.setTag(2);
        Bitmap bitmap11 = decodeResource(MyApp.getInstance().getResources(), R.mipmap.filter_qingxin);
        filter11.setFilterBitmap(bitmap11);
        filter11.setFilterSrc(R.mipmap.qingxin);
        filter11.setUnFilterSrc(R.mipmap.qingxin_pre);
        filters.add(filter11);

        Filters filter12 = new Filters();
        filter12.setTag(2);
        Bitmap bitmap12 = decodeResource(MyApp.getInstance().getResources(), R.mipmap.filter_rixi);
        filter12.setFilterBitmap(bitmap12);
        filter12.setFilterSrc(R.mipmap.rixi);
        filter12.setUnFilterSrc(R.mipmap.rixi_pre);
        filters.add(filter12);

        Filters filter13 = new Filters();
        filter13.setTag(2);
        Bitmap bitmap13 = decodeResource(MyApp.getInstance().getResources(), R.mipmap.filter_weimei);
        filter13.setFilterBitmap(bitmap13);
        filter13.setFilterSrc(R.mipmap.weimei);
        filter13.setUnFilterSrc(R.mipmap.weimei_pre);
        filters.add(filter13);

        Filters filter14 = new Filters();
        filter14.setTag(2);
        Bitmap bitmap14 = decodeResource(MyApp.getInstance().getResources(), R.mipmap.filter_xiangfen);
        filter14.setFilterBitmap(bitmap14);
        filter14.setFilterSrc(R.mipmap.xiangfen);
        filter14.setUnFilterSrc(R.mipmap.xiangfen_pre);
        filters.add(filter14);

        Filters filter15 = new Filters();
        filter15.setTag(2);
        Bitmap bitmap15 = decodeResource(MyApp.getInstance().getResources(), R.mipmap.filter_yinghong);
        filter15.setFilterBitmap(bitmap15);
        filter15.setFilterSrc(R.mipmap.yinghong);
        filter15.setUnFilterSrc(R.mipmap.yinghong_p);
        filters.add(filter15);

        Filters filter16 = new Filters();
        filter16.setTag(2);
        Bitmap bitmap16 = decodeResource(MyApp.getInstance().getResources(), R.mipmap.filter_yuanqi);
        filter16.setFilterBitmap(bitmap16);
        filter16.setFilterSrc(R.mipmap.yuanqi);
        filter16.setUnFilterSrc(R.mipmap.yuanqi_pre);
        filters.add(filter16);

        Filters filter17 = new Filters();
        filter17.setTag(1);
        Bitmap bitmap17 = decodeResource(MyApp.getInstance().getResources(), R.mipmap.filter_yunshang);
        filter17.setFilterBitmap(bitmap17);
        filter17.setFilterSrc(R.mipmap.yunshang);
        filter17.setUnFilterSrc(R.mipmap.yunshang_pre);
        filters.add(filter17);

        return filters;
    }


}

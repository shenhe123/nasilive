package com.feicui365.live.dialog;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxj.xpopup.core.BottomPopupView;
import com.feicui365.live.R;
import com.feicui365.live.model.entity.ShareBean;
import com.feicui365.live.ui.adapter.ShareAdapter;
import com.feicui365.live.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;


public class ShareDialog2 extends BottomPopupView {

    RecyclerView rv_top, rv_bottom;
    TextView tv_close;
    ShareAdapter share_adapter_top, share_adapter_bottom;
    List<ShareBean> list_top = new ArrayList<>();
    List<ShareBean> list_bottom = new ArrayList<>();
    private String share_url = "";
    private String id = "";
    private String video_url = "";
    private String title = "";
    private String image_url = "";
    private String is_collect = "";
    private String video_id = "";
    private String content = "";
    private Bitmap tumb;
    private String type = "1";
    private boolean hide_collect,hide_down,hide_bottom;

    public void setHide_collect(boolean hide_collect) {
        this.hide_collect = hide_collect;
    }

    public void setHide_down(boolean hide_down) {
        this.hide_down = hide_down;
    }

    public void setHide_bottom(boolean hide_bottom) {
        this.hide_bottom = hide_bottom;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setIs_collect(String is_collect) {
        this.is_collect = is_collect;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTumb(Bitmap tumb) {
        this.tumb = tumb;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ShareDialog2(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_share_layout_2;
    }


    @Override
    protected void onCreate() {
        super.onCreate();
        initView();
        initRecycle();
        initData();
        initClick();
    }

    private void initView() {
        tv_close = findViewById(R.id.tv_close);
        rv_top = findViewById(R.id.rv_top);
        rv_bottom = findViewById(R.id.rv_bottom);
    }

    private void initRecycle() {
        LinearLayoutManager lm_top = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        share_adapter_top = new ShareAdapter(list_top);
        rv_top.setLayoutManager(lm_top);
        rv_top.setAdapter(share_adapter_top);


        LinearLayoutManager lm_bottom = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        share_adapter_bottom = new ShareAdapter(list_bottom);
        rv_bottom.setLayoutManager(lm_bottom);
        rv_bottom.setAdapter(share_adapter_bottom);
    }

    private void initData() {
        list_top.add(new ShareBean("微信", "id_wechat", R.mipmap.ic_wechat));
        list_top.add(new ShareBean("朋友圈", "id_friend", R.mipmap.ic_pengyouq));
       // list_top.add(new ShareBean("QQ", "id_qq", R.mipmap.ic_qq));
      //  list_top.add(new ShareBean("QQ空间", "id_qzone", R.mipmap.ic_qzone));
       // list_top.add(new ShareBean("微博", "id_weibo", R.mipmap.ic_sina));


        list_bottom.add(new ShareBean("举报", "id_report", R.mipmap.ic_jubao));
        list_bottom.add(new ShareBean("复制链接", "id_copy", R.mipmap.ic_fuzhi));
        if(hide_collect!=true){
            list_bottom.add(new ShareBean("收藏", "id_collect", R.mipmap.ic_shoucang));
        }

        if(hide_down!=true){
            list_bottom.add(new ShareBean("下载", "id_download", R.mipmap.ic_baocun));
        }


        share_adapter_top.notifyDataSetChanged();
        share_adapter_bottom.notifyDataSetChanged();

    }

    private void initClick() {
        tv_close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        share_adapter_top.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ShareBean shareBean= (ShareBean) adapter.getData().get(position);
                switch (shareBean.getId()){



                }
            }
        });

        share_adapter_bottom.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtils.showT(((ShareBean) adapter.getData().get(position)).getTitle());
            }
        });

    }
}
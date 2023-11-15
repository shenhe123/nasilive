package com.feicui365.live.dialog;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.feicui365.live.R;
import com.feicui365.live.ui.act.ReportItemActivity;
import com.feicui365.live.util.DownloadUtil2;
import com.feicui365.live.util.HttpUtils;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.live.util.ShareUtils;
import com.feicui365.live.util.StringUtil;
import com.feicui365.live.util.ToastUtils;

import com.tencent.qcloud.tuicore.util.BackgroundTasks;
import com.yanzhenjie.permission.AndPermission;

import java.io.File;

import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;


public class ShareDialog extends Dialog {

    public ShareDialog(Context context) {
        super(context);
    }

    public ShareDialog(Context context, int theme) {
        super(context, theme);
    }


    public interface OnCollectListener {
        void collect(String type);
    }

    public static class Builder implements View.OnClickListener {
        private ProgressDialog mDownloadProgressDialog;
        private Context context;
        private String share_url = "";
        private View contentView;
        private OnCollectListener onCollectListener;
        RelativeLayout rl_wechat, rl_down, rl_10, rl_wechat_friend, rl_qq, rl_qqzone, rl_weibo, rl_save, rl_jubao, rl_collect, rl_fuzhi;
        LinearLayout ll_bottom_2;
        private String id = "";
        String video_url = "";

        public void setVideo_url(String video_url) {
            this.video_url = video_url;
        }

        private String title = "";
        private String image_url = "";
        private String is_collect = "";
        private String video_id = "";
        TextView tv_close;

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }

        private TextView tv_8;
        private ImageView iv_8;

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        private String content = "";
        private Bitmap tumb;
        private String type = "1";

        public Builder(Context context) {
            this.context = context;
        }

        public void setOnCollectListener(OnCollectListener onCollectListener) {
            this.onCollectListener = onCollectListener;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        public void setShare_url(String share_url) {
            this.share_url = share_url;
        }



        public void setType(String type) {
            this.type = type;
        }

        public ShareDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final ShareDialog dialog = new ShareDialog(context, R.style.Dialog);
            View layout = inflater.inflate(R.layout.dialog_share_layout, null);
            dialog.addContentView(layout, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            layout.findViewById(R.id.rl_other).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            mDownloadProgressDialog = new ProgressDialog(context);
            mDownloadProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL); // 设置进度条的形式为圆形转动的进度条
            mDownloadProgressDialog.setCancelable(false);                           // 设置是否可以通过点击Back键取消
            mDownloadProgressDialog.setCanceledOnTouchOutside(false);
            rl_wechat = layout.findViewById(R.id.rl_wechat);
            rl_wechat_friend = layout.findViewById(R.id.rl_wechat_friend);
            rl_qq = layout.findViewById(R.id.rl_qq);
            rl_qqzone = layout.findViewById(R.id.rl_qqzone);
            rl_weibo = layout.findViewById(R.id.rl_weibo);
            rl_save = layout.findViewById(R.id.rl_save);
            tv_close = layout.findViewById(R.id.tv_close);
            rl_down = layout.findViewById(R.id.rl_down);
            rl_10 = layout.findViewById(R.id.rl_10);

            rl_wechat.setOnClickListener(this);
            rl_wechat_friend.setOnClickListener(this);
            rl_qq.setOnClickListener(this);
            rl_qqzone.setOnClickListener(this);
            rl_weibo.setOnClickListener(this);
            rl_save.setOnClickListener(this);
            tv_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            ll_bottom_2 = layout.findViewById(R.id.ll_bottom_2);
            rl_jubao = layout.findViewById(R.id.rl_jubao);
            rl_collect = layout.findViewById(R.id.rl_collect);
            rl_fuzhi = layout.findViewById(R.id.rl_fuzhi);

            rl_jubao.setOnClickListener(this);
            rl_collect.setOnClickListener(this);
            rl_fuzhi.setOnClickListener(this);


            tv_8 = layout.findViewById(R.id.tv_8);
            iv_8 = layout.findViewById(R.id.iv_8);

            dialog.setContentView(layout);
            hideDown(true);

            return dialog;
        }


        public void hideCollect() {
            tv_8.setVisibility(View.GONE);
            iv_8.setVisibility(View.GONE);
        }

        public void hideDown(boolean is_hide) {
            if (is_hide) {
                rl_down.setOnClickListener(null);
                rl_10.setVisibility(View.GONE);
            } else {
                rl_down.setOnClickListener(this);
                rl_10.setVisibility(View.VISIBLE);
            }

        }

        public void showBottom2() {
            ll_bottom_2.setVisibility(View.VISIBLE);
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setTumb(Bitmap tumb) {
            this.tumb = tumb;
        }

        public void setIs_collect(String is_collect) {
            this.is_collect = is_collect;
        }

        @Override
        public void onClick(View v) {
            if (type.equals("3") & !video_id.equals("")) {
                ShareUtils.getInstance().setShort_video_id(video_id);
            }
            switch (v.getId()) {
                case R.id.rl_wechat:

                    ShareUtils.getInstance().ShareWechat(share_url, title, content, tumb, image_url, Wechat.NAME);

                    break;
                case R.id.rl_wechat_friend:
                    ShareUtils.getInstance().ShareWechat(share_url, title, content, tumb, image_url, WechatMoments.NAME);
                    break;
                case R.id.rl_qq:
                 //   ShareUtils.getInstance().ShareWechat(share_url, title, content, tumb, image_url, QQ.NAME);
                    break;
                case R.id.rl_qqzone:
                 //   ShareUtils.getInstance().ShareWechat(share_url, title, content, tumb, image_url, QZone.NAME);
                    break;
                case R.id.rl_weibo:
                    break;
                case R.id.rl_save:
                    break;
                case R.id.rl_jubao:
                    if (id.equals("")) {
                        return;
                    }

                    Intent i = new Intent(context, ReportItemActivity.class);
                    i.putExtra("REPORT_ID", id);
                    i.putExtra("REPORT_TYPE", type);
                    context.startActivity(i);
                    break;
                case R.id.rl_collect:
                    if (is_collect.equals("")) {
                        return;
                    }

                    String type = "";
                    if (is_collect.equals("0")) {
                        type = "1";
                    } else {
                        type = "0";
                    }

                    HttpUtils.getInstance().shortCollect(id, type, new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            JSONObject data = HttpUtils.getInstance().check(response);
                            if (data.get("status").toString().equals("0")) {
                                if (onCollectListener != null) {
                                    if (is_collect.equals("0")) {
                                        onCollectListener.collect("1");
                                    } else {
                                        onCollectListener.collect("0");
                                    }
                                }
                            }
                        }
                    });
                    break;
                case R.id.rl_fuzhi:
                    //获取剪贴板管理器：
                    ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    // 创建普通字符型
                    ClipData mClipData = ClipData.newPlainText("Label", share_url);
                    // 将ClipData内容放到系统剪贴板里。
                    cm.setPrimaryClip(mClipData);
                    ToastUtils.showT("复制成功");

                    break;
                case R.id.rl_down:

                    if(MyUserInstance.getInstance().getUserinfo().getVip_date()==null){
                        ToastUtils.showT("当前用户不是会员,无法使用此功能");
                        return;

                    }

                    if(!MyUserInstance.getInstance().OverTime(MyUserInstance.getInstance().getUserinfo().getVip_date())){
                        ToastUtils.showT("当前用户不是会员,无法使用此功能");
                        return;

                    }
                    AndPermission.with(context)
                            .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            // 准备方法，和 okhttp 的拦截器一样，在请求权限之前先运行改方法，已经拥有权限不会触发该方法
                            .rationale((context, permissions, executor) -> {
                                // 此处可以选择显示提示弹窗
                                executor.execute();
                            })
                            // 用户给权限了
                            .onGranted(permissions -> {
                                mDownloadProgressDialog.show();


                                DownloadUtil2.download("SHORT", DownloadUtil2.createFile("Nasinet"), StringUtil.generateVideoFileName(), video_url, new DownloadUtil2.Callback() {
                                    @Override
                                    public void onSuccess(File file) {
                                        mDownloadProgressDialog.dismiss();
                                        ToastUtils.showT("下载成功");
                                    }

                                    @Override
                                    public void onProgress(int progress) {
                                        BackgroundTasks.getInstance().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                mDownloadProgressDialog.setProgress(progress);
                                            }
                                        });
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        ToastUtils.showT("下载失败");
                                        mDownloadProgressDialog.dismiss();
                                    }
                                });
                            })
                            // 用户拒绝权限，包括不再显示权限弹窗也在此列
                            .onDenied(permissions -> {
                                // 判断用户是不是不再显示权限弹窗了，若不再显示的话进入权限设置页
                                ToastUtils.showT("没有写入权限,无法下载视频");

                            })
                            .start();
                    break;

            }


        }


    }


    public Dialog getDialog() {
        return this;
    }

    @Override
    public void show() {
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
        getWindow().setWindowAnimations(R.style.share_animation);
    }


}
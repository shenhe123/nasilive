package com.feicui365.live.dialog;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.feicui365.live.R;
import com.feicui365.live.util.EncodingUtils;
import com.feicui365.live.util.ToastUtils;
import com.google.zxing.qrcode.encoder.QRCode;

public class ShareLiveDialog extends BaseDialog implements View.OnClickListener {

    private Context mContext;
    private ImageView head_img;
    private TextView nick_name;
    private TextView introduce;
    private ImageView thumb;
    private ImageView ewm;
    private TextView desc;
    private LinearLayout copy_link;
    private LinearLayout save_img;
    private ConstraintLayout live_top;

    public ShareLiveDialog(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    public static ShareLiveDialog create(Context context) {
        ShareLiveDialog dialog = new ShareLiveDialog(context);
        dialog.show();
        return dialog;
    }

    @Override
    protected void initView() {
        super.initView();
        head_img = findViewById(R.id.share_head_img);
        nick_name = findViewById(R.id.share_nick_name);
        introduce = findViewById(R.id.share_introduce);
        thumb = findViewById(R.id.share_thumb);
        ewm = findViewById(R.id.share_ewm);
        desc = findViewById(R.id.share_desc);
        copy_link = findViewById(R.id.share_copy_link);
        save_img = findViewById(R.id.share_save_img);
        live_top = findViewById(R.id.share_live_top);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        copy_link.setOnClickListener(this);
        save_img.setOnClickListener(this);
    }

    @Override
    protected float setDialogWith() {
        return 1f;
    }

    public ShareLiveDialog setNickName(String firstText) {
        setText(nick_name, firstText);
        return this;
    }

    public ShareLiveDialog setIntroduce(String firstText) {
        setText(introduce, firstText);
        return this;
    }

    public ShareLiveDialog setDesc(String firstText) {
        setText(desc, firstText);
        return this;
    }



    private void setText(TextView textView, String text) {
        if (textView == null) {
            return;
        }
        textView.setText(!TextUtils.isEmpty(text) ? text : "");
    }

    /**
     * 设置图片url
     * */
    public ShareLiveDialog setThumbUrl(String url){
        setThumbUrl(thumb,url);
        return this;
    }

    private void setThumbUrl(ImageView qrWechatIv, String url) {
        if (qrWechatIv == null){
            return;
        }
        Glide.with(mContext).load(url).into(qrWechatIv);
    }


    public ShareLiveDialog setHeadUrl(String url) {
        setUrl(head_img, url);
        return this;
    }

    private void setUrl(ImageView qrWechatIv, String url) {
        if (qrWechatIv == null) {
            return;
        }
        Glide.with(mContext).load(url).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(qrWechatIv);
    }

    public ShareLiveDialog setQrCode(String url) {
        setThumbUrl(ewm, url);
        return this;
    }

    private void setQrUrl(ImageView qrWechatIv, String url) {
        if (qrWechatIv == null) {
            return;
        }
//        Bitmap qrCode = EncodingUtils.createQRCode(url, 87, 87, null);
//        ewm.setImageBitmap(qrCode);
    }





    @Override
    protected int getContentView() {
        return R.layout.dialog_share_live;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.share_save_img:
                dismiss();
                if (onClickCallback != null) {
                    onClickCallback.onClickType(BaseDialog.SELECT_FIRST);
                }
                Bitmap live_top_img  = EncodingUtils.getViewBitmap(live_top);
               // EncodingUtils.savePhotoToSDCard(live_top_img,"/sdcard/file","img");
                EncodingUtils.SaveImage(live_top_img,mContext);
                break;
            case R.id.share_copy_link:
                dismiss();
                if (onClickCallback != null) {
                    onClickCallback.onClickType(BaseDialog.SELECT_SECOND);
                }
                break;

        }
    }
}

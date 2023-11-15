package com.feicui365.live.shop.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feicui365.live.R;
import com.feicui365.live.shop.entity.AddColor;
import com.feicui365.live.util.ToastUtils;

import java.util.List;

public class AddColorAdapter extends BaseQuickAdapter<AddColor, BaseViewHolder> {

   private OnAddPicListener onAddPicListener;


    public interface OnAddPicListener{
        void chosePic(int postion);

        void sumbitPic();
    }

    public OnAddPicListener getOnAddPicListener() {
        return onAddPicListener;
    }

    public void setOnAddPicListener(OnAddPicListener onAddPicListener) {
        this.onAddPicListener = onAddPicListener;
    }

    public AddColorAdapter(@Nullable List<AddColor> data) {
        super(R.layout.add_good_color_item, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, AddColor item) {


        //区分是否已经添加
        if(item.getTag()==null){
            helper.setGone(R.id.iv_submit,true);
            helper.setGone(R.id.iv_cancel,false);
        }else{
            helper.setGone(R.id.iv_submit,false);
            helper.setGone(R.id.iv_cancel,true);
        }

        if(item.getColor()!=null){

            helper.setText(R.id.et_color,item.getColor());
        }else{
            helper.setText(R.id.et_color,"");
        }

        if(item.getImg_url()==null){
            helper.setImageResource(R.id.iv_good_color,R.mipmap.ic_add_color);
        }else{
            Glide.with(helper.itemView).applyDefaultRequestOptions(new RequestOptions().centerCrop())
                    .load(item.getImg_url()).into((ImageView) helper.getView(R.id.iv_good_color));
        }



        helper.getView(R.id.iv_good_color).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onAddPicListener!=null){
                    onAddPicListener.chosePic(helper.getLayoutPosition());
                }
            }
        });

        helper.getView(R.id.iv_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((((EditText)helper.getView(R.id.et_color)).getText().toString()).equals("")){
                    ToastUtils.showT("请先上传颜色图片");
                    return;
                }

                if(item.getImg_url()==null){
                    ToastUtils.showT("请先上传颜色图片");
                    return;
                }


                helper.setGone(R.id.iv_submit,false);
                helper.getView(R.id.iv_good_color).setClickable(false);
                helper.getView(R.id.et_color).setEnabled(false);
                helper.setGone(R.id.iv_cancel,true);
                item.setColor(((EditText)helper.getView(R.id.et_color)).getText().toString());
                item.setTag("1");
                if(onAddPicListener!=null){
                    onAddPicListener.sumbitPic();
                }
            }
        });

        helper.getView(R.id.iv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              remove(helper.getLayoutPosition());
                if(onAddPicListener!=null){
                    onAddPicListener.sumbitPic();
                }

            }
        });


    }


    public void setImage(Context context,int postion, String url){

       ImageView iv_good_color= (ImageView) getViewByPosition(postion,R.id.iv_good_color);
       if(iv_good_color!=null){
           Glide.with(context).applyDefaultRequestOptions(new RequestOptions().centerCrop())
                   .load(url).into(new SimpleTarget<Drawable>() {
               @Override
               public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                   iv_good_color.setImageDrawable(resource);
               }

               @Override
               public void onLoadFailed(@Nullable Drawable errorDrawable) {
                   super.onLoadFailed(errorDrawable);
               }
           });
       }

    }
}

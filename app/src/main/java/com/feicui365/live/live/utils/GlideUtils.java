package com.feicui365.live.live.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import org.jetbrains.annotations.NotNull;


/**
 * Glide 工具
 */
public class GlideUtils {

    public static void setImageCenterInside(Context context, String url, int res, ImageView view) {
        if(!ArmsUtils.isStringEmpty(url)){
            return;
        }
        Glide.with(context)
                .applyDefaultRequestOptions(new RequestOptions().centerInside().placeholder(res).skipMemoryCache(true))
                .load(url)
                .into(view);
    }

    public static void setImage(Context context, String url, int res, ImageView view) {


        if(!ArmsUtils.isStringEmpty(url)){
            Glide.with(context)
                    .applyDefaultRequestOptions(new RequestOptions().centerCrop().skipMemoryCache(true))
                    .load(res)
                    .into(view);
            return;
        }
        Glide.with(context)
                .applyDefaultRequestOptions(new RequestOptions().centerCrop().placeholder(res).skipMemoryCache(true))
                .load(url)
                .into(view);
    }


    public static void setImageTarget(Context context, String url, SimpleTarget<Drawable> view) {

        Glide.with(context)
                .applyDefaultRequestOptions(new RequestOptions().dontAnimate())

                .load(url)

                .into(view);
    }

    public static void setImage(Context context, String url, ImageView view) {
        if(!ArmsUtils.isStringEmpty(url)){
            return;
        }
        Glide.with(context)
                .applyDefaultRequestOptions(new RequestOptions().centerCrop().dontAnimate().skipMemoryCache(false))

                .load(url)

                .into(view);

    }



    public static void setImageInside(Context context, String url, ImageView view) {
        if(!ArmsUtils.isStringEmpty(url)){
            return;
        }
        Glide.with(context)
                .applyDefaultRequestOptions(new RequestOptions().centerInside().dontAnimate().skipMemoryCache(true))

                .load(url)

                .into(view);

    }
    public static void setImageNormal(Context context, String url, ImageView view) {
        if(!ArmsUtils.isStringEmpty(url)){
            return;
        }
        Glide.with(context)
                .applyDefaultRequestOptions(new RequestOptions().dontAnimate().skipMemoryCache(true))

                .load(url)

                .into(view);

    }


    public static void setImage(View view, String url, int res, ImageView imageview) {
        if(!ArmsUtils.isStringEmpty(url)){
            Glide.with(view)
                    .applyDefaultRequestOptions(new RequestOptions().centerCrop())
                    .load(res)
                    .into(imageview);
            return;
        }
        Glide.with(view)
                .applyDefaultRequestOptions(new RequestOptions().centerCrop().placeholder(res))
                .load(url)
                .into(imageview);
    }

    public static void setLocalImage(Context context, String url, ImageView view) {

        Glide.with(context)
                .applyDefaultRequestOptions(new RequestOptions().centerCrop())
                .load(url)
                .into(view);
    }

    public static void setImage(Context context, int url, ImageView view) {

        Glide.with(context)
                .applyDefaultRequestOptions(new RequestOptions().centerCrop())
                .load(url)

                .into(view);
    }

    public static void setImageInside(Context context, int url, ImageView view) {

        Glide.with(context)
                .applyDefaultRequestOptions(new RequestOptions().centerInside())
                .load(url)

                .into(view);
    }

    public static void setImageDontWithAnimation(Context context, int url, ImageView view) {

        Glide.with(context)
                .applyDefaultRequestOptions(new RequestOptions().centerCrop().dontAnimate())
                .load(url)

                .into(view);
    }

    public static void setImageWithAnimation(Context context, int url, ImageView view) {

        Glide.with(context)
                .applyDefaultRequestOptions(new RequestOptions().centerCrop())
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view);
    }

    public static void getImageDrawable(Context view, String url, SimpleTarget<Drawable> simpleTarget) {
        if(!ArmsUtils.isStringEmpty(url)){
            return;
        }
        Glide.with(view)
                .load(url)
                .into(simpleTarget);
    }

    public static void getImageResource(Context view, String url, ImageView imageView) {
        if(!ArmsUtils.isStringEmpty(url)){
            return;
        }
        Glide.with(view)
                .load(url)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull @NotNull Drawable resource, @Nullable @org.jetbrains.annotations.Nullable Transition<? super Drawable> transition) {
                        imageView.setImageDrawable(resource);
                    }
                });
    }




}

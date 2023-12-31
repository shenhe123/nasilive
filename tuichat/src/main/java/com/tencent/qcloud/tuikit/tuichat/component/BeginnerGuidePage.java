package com.tencent.qcloud.tuikit.tuichat.component;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.PopupWindow;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;


import com.tencent.qcloud.tuicore.component.imageEngine.impl.GlideEngine;
import com.tencent.qcloud.tuikit.tuichat.R;

public class BeginnerGuidePage {

    private PopupWindow popupWindow;
    private ViewPager viewPager;
    private OnFinishListener onFinishListener;
    private int[] resIDs;
    public BeginnerGuidePage(Activity activity) {
        View popupView = LayoutInflater.from(activity).inflate(R.layout.layout_beginner_guide, null);
        viewPager = popupView.findViewById(R.id.view_pager);

        popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, true) {
            @Override
            public void showAtLocation(View anchor, int gravity, int x, int y) {
                if (activity != null && !activity.isFinishing()) {
                    Window dialogWindow = activity.getWindow();
                    startAnimation(dialogWindow, true);
                }
                super.showAtLocation(anchor, gravity, x, y);
            }

            @Override
            public void dismiss() {
                if (activity != null && !activity.isFinishing()) {
                    Window dialogWindow = activity.getWindow();
                    startAnimation(dialogWindow, false);
                }

                super.dismiss();
            }
        };
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setAnimationStyle(R.style.BeginnerGuidePopupAnimation);

      //  viewPager.setUserInputEnabled(false);
       // viewPager.setAdapter(new GuideAdapter());
    }

    private void startAnimation(Window window, boolean isShow) {
        LinearInterpolator interpolator = new LinearInterpolator();
        ValueAnimator animator;
        if (isShow) {
            animator = ValueAnimator.ofFloat(1.0f, 0.5f);
        } else {
            animator = ValueAnimator.ofFloat(0.5f, 1.0f);
        }
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.alpha = (float) animation.getAnimatedValue();
                window.setAttributes(lp);
            }
        });

        animator.setDuration(200);
        animator.setInterpolator(interpolator);
        animator.start();
    }

    public void setPagesResIDs(int... imageResIDs) {
        resIDs = imageResIDs;
        viewPager.setOffscreenPageLimit(resIDs.length);
        viewPager.getAdapter().notifyDataSetChanged();
        viewPager.setCurrentItem(0, false);
    }

    public void setOnFinishListener(OnFinishListener onFinishListener) {
        this.onFinishListener = onFinishListener;
    }

    public void show(View rootView, int gravity) {
        if (popupWindow != null) {
            popupWindow.showAtLocation(rootView, gravity, 0, 0);
        }
    }

    class GuideAdapter extends RecyclerView.Adapter<GuideAdapter.GuideViewHolder> {
        @NonNull
        @Override
        public GuideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new GuideViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_beginner_guide_item, parent ,false));
        }

        @Override
        public void onBindViewHolder(@NonNull GuideViewHolder holder, int position) {
            GlideEngine.loadImage(holder.image, resIDs[position]);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (viewPager != null) {
                        int currentPos = holder.getLayoutPosition();
                        if (currentPos < getItemCount() - 1) {
                            viewPager.setCurrentItem(currentPos + 1, true);
                        } else {
                            if (onFinishListener != null) {
                                onFinishListener.onFinish();
                            }
                            if (popupWindow != null && popupWindow.isShowing()) {
                                popupWindow.dismiss();
                            }
                        }
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            if (resIDs == null) {
                return 0;
            }
            return resIDs.length;
        }

        class GuideViewHolder extends RecyclerView.ViewHolder {
            private final ImageView image;
            public GuideViewHolder(@NonNull View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.center_image);
            }
        }
    }

    @FunctionalInterface
    public interface OnFinishListener {
        void onFinish();
    }
}

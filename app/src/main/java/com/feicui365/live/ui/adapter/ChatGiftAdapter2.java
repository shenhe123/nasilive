package com.feicui365.live.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.feicui365.live.R;
import com.feicui365.live.base.Constants;
import com.feicui365.live.model.entity.ChatGiftBean;
import com.feicui365.live.widget.DrawableTextView;
import com.feicui365.live.widget.MyRadioButton;

import java.util.List;


public class ChatGiftAdapter2 extends RecyclerView.Adapter<ChatGiftAdapter2.Vh> {

    private Context mContext;
    private List<ChatGiftBean> mList;
    private LayoutInflater mInflater;
    private View.OnClickListener mOnClickListener;
    private ActionListener mActionListener;
    private int mCheckedPosition = -1;
    private ScaleAnimation mAnimation;
    private View mAnimView;

    public ChatGiftAdapter2(Context context, LayoutInflater inflater, List<ChatGiftBean> list, String coinName) {
        mContext=context;
        mInflater = inflater;
        mList = list;

        mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object tag = v.getTag();
                if (tag != null) {
                    int position = (int) tag;
                    ChatGiftBean bean = mList.get(position);
                    if (!bean.isChecked()) {
                        if (!cancelChecked()) {
                            if (mActionListener != null) {
                                mActionListener.onCancel();
                            }
                        }
                        bean.setChecked(true);
                        notifyItemChanged(position, Constants.PAYLOAD);
                        View view = bean.getView();
                        if (view != null) {
                            view.startAnimation(mAnimation);
                            mAnimView = view;
                        }
                        mCheckedPosition = position;
                        if (mActionListener != null) {
                            mActionListener.onItemChecked(bean);
                        }
                    }
                }
            }
        };
        mAnimation = new ScaleAnimation(0.9f, 1.1f, 0.9f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimation.setDuration(400);
        mAnimation.setRepeatMode(Animation.REVERSE);
        mAnimation.setRepeatCount(-1);
    }

    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Vh(mInflater.inflate(R.layout.item_chat_gift_2, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Vh vh, int position) {

    }

    @Override
    public void onBindViewHolder(@NonNull Vh vh, int position, @NonNull List<Object> payloads) {
        Object payload = payloads.size() > 0 ? payloads.get(0) : null;
        vh.setData(mList.get(position), position, payload);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * 取消选中
     */
    public boolean cancelChecked() {
        if (mCheckedPosition >= 0 && mCheckedPosition < mList.size()) {
            ChatGiftBean bean = mList.get(mCheckedPosition);
            if (bean.isChecked()) {
                View view = bean.getView();
                if (mAnimView == view) {
                    mAnimView.clearAnimation();
                } else {
                    if (view != null) {
                        view.clearAnimation();
                    }
                }
                mAnimView = null;
                bean.setChecked(false);
                notifyItemChanged(mCheckedPosition, Constants.PAYLOAD);
            }
            mCheckedPosition = -1;
            return true;
        }
        return false;
    }

    public void setActionListener(ActionListener actionListener) {
        mActionListener = actionListener;
    }

    public void release() {
        if (mAnimView != null) {
            mAnimView.clearAnimation();
        }
        if (mList != null) {
            mList.clear();
        }
        mOnClickListener = null;
        mActionListener = null;
    }

    class Vh extends RecyclerView.ViewHolder {

        ImageView mIcon,iv_guardian,iv_all;
        TextView mName;
        DrawableTextView mPrice;
        MyRadioButton mRadioButton;

        public Vh(View itemView) {
            super(itemView);
            mIcon = (ImageView) itemView.findViewById(R.id.icon);
            iv_guardian = (ImageView) itemView.findViewById(R.id.iv_guardian);
            iv_all = (ImageView) itemView.findViewById(R.id.iv_all);
            mName = (TextView) itemView.findViewById(R.id.name);
            mPrice = (DrawableTextView) itemView.findViewById(R.id.price);
            mRadioButton = (MyRadioButton) itemView.findViewById(R.id.radioButton);
            mRadioButton.setOnClickListener(mOnClickListener);
        }

        void setData(ChatGiftBean bean, int position, Object payload) {
            if (payload == null) {
                Glide.with(mContext).load(bean.getIcon()).into(mIcon);
                bean.setView(mIcon);
                mName.setText(bean.getTitle());
                mPrice.setText(bean.getPrice() +" ");
                switch (bean.getUse_type()){
                    case "0":
                        iv_guardian.setVisibility(View.GONE);
                        iv_all.setVisibility(View.GONE);
                        break;
                    case "1":
                        iv_guardian.setVisibility(View.GONE);
                        iv_all.setVisibility(View.VISIBLE);
                        break;
                    case "2":
                        iv_guardian.setVisibility(View.VISIBLE);
                        iv_all.setVisibility(View.GONE);
                        break;

                }
            }
            mRadioButton.setTag(position);
            mRadioButton.doChecked(bean.isChecked());
        }
    }

    public interface ActionListener {
        void onCancel();

        void onItemChecked(ChatGiftBean bean);
    }

}

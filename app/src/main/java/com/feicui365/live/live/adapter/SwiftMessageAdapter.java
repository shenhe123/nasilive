package com.feicui365.live.live.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feicui365.live.R;
import com.feicui365.live.interfaces.OnSwiftMessageClickListener;
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.model.entity.SwiftMessageBean;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SwiftMessageAdapter  extends RecyclerView.Adapter<SwiftMessageAdapter.SwiftMessageViewHolder>{

    private final Context mContext;
    private List<SwiftMessageBean> mList = new ArrayList<>();
    OnSwiftMessageClickListener OnSwiftMessageClickListener ;

    public void setOnSwiftMessageClickListener(OnSwiftMessageClickListener OnSwiftMessageClickListener) {
        this.OnSwiftMessageClickListener = OnSwiftMessageClickListener;
    }
    public interface OnSwiftMessageClickListener {
        void onItemClick(String id);
    }
    public SwiftMessageAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public SwiftMessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_swift_message, parent, false);

        return new SwiftMessageViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull SwiftMessageViewHolder holder, int i) {

        holder.mMessageTv.setText(mList.get(i).getTitle());

        holder.mDeMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (OnSwiftMessageClickListener != null) {
                    OnSwiftMessageClickListener.onItemClick(mList.get(i).getId()+"");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }





    public void addData(List<SwiftMessageBean> data) {
        if (mList.size() != 0) mList.clear();
        mList.addAll(data);
        notifyDataSetChanged();
    }


    public class SwiftMessageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.quick_message_tv)
        TextView mMessageTv;

        @BindView(R.id.de_message)
        ImageView mDeMessage;

        public SwiftMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

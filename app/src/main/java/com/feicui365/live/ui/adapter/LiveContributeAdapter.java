package com.feicui365.live.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.feicui365.live.R;
import com.feicui365.live.model.entity.ContributeRank;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class LiveContributeAdapter extends RecyclerView.Adapter<LiveContributeAdapter.ViewHolder> {

    private ArrayList<ContributeRank> contributeRanks;
    Context context;
    RequestOptions options;
    public OnItemClikeListener onItemClikeListener;


    public  interface OnItemClikeListener{
        void onItemClick(String id);
    }

    public void setOnItemClikeListener(OnItemClikeListener onItemClikeListener) {
        this.onItemClikeListener = onItemClikeListener;
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout avatar_rl;
        CircleImageView avatar_iv;


        public ViewHolder (View view)
        {
            super(view);
            avatar_rl =  view.findViewById(R.id.avatar_rl);
            avatar_iv = view.findViewById(R.id.avatar_iv);


        }

    }

    public LiveContributeAdapter(ArrayList<ContributeRank> contributeRanks, Context context){
        this.context=context;
        this.contributeRanks = contributeRanks;
    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_rank_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        options = new RequestOptions()
                .placeholder(R.mipmap.moren)
                .centerCrop();
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){

        ContributeRank contributeRank = contributeRanks.get(position);

        switch (position){
            case 0:
                holder.avatar_rl.setBackgroundResource(R.mipmap.no1);
                break;
            case 1:
                holder.avatar_rl.setBackgroundResource(R.mipmap.no2);
                break;
            case 2:
                holder.avatar_rl.setBackgroundResource(R.mipmap.no);
                break;
            default:
                holder.avatar_rl.setBackgroundColor(Color.TRANSPARENT);
                break;
        }
        Glide.with(context).applyDefaultRequestOptions(new RequestOptions().placeholder(R.mipmap.moren)).load(contributeRanks.get(position).getUser().getAvatar()).into(holder.avatar_iv);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClikeListener!=null){
                    onItemClikeListener.onItemClick(contributeRanks.get(position).getUser().getId()+"");
                }
            }
        });
    }



    @Override
    public int getItemCount(){
        return contributeRanks.size();
    }
}
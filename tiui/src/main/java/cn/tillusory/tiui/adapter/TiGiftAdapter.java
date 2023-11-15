package cn.tillusory.tiui.adapter;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.dispatcher.DownloadDispatcher;
import com.liulishuo.okdownload.core.listener.DownloadListener2;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.tillusory.sdk.TiSDK;
import cn.tillusory.sdk.TiSDKManager;
import cn.tillusory.sdk.bean.TiGift;
import cn.tillusory.sdk.common.TiUtils;
import cn.tillusory.tiui.R;

/**
 * Created by Anko on 2018/7/18.
 * Copyright (c) 2018-2020 拓幻科技 - tillusory.cn. All rights reserved.
 */
public class TiGiftAdapter extends RecyclerView.Adapter<TiStickerViewHolder> {

    private int selectedPosition = 0;

    private List<TiGift> giftList;
    private TiSDKManager tiSDKManager;

    private Handler handler = new Handler();

    private Map<String, String> downloadingGifts = new ConcurrentHashMap<>();

    public TiGiftAdapter(List<TiGift> giftList, TiSDKManager tiSDKManager) {
        this.giftList = giftList;
        this.tiSDKManager = tiSDKManager;

        DownloadDispatcher.setMaxParallelRunningCount(5);
    }

    @NonNull
    @Override
    public TiStickerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ti_sticker, parent, false);
        return new TiStickerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TiStickerViewHolder holder, int position) {

        final TiGift tiGift = giftList.get(holder.getAdapterPosition());

        if (selectedPosition == position) {
            holder.itemView.setSelected(true);
        } else {
            holder.itemView.setSelected(false);
        }

        //显示封面
        if (tiGift == TiGift.NO_GIFT) {
            holder.thumbIV.setImageResource(R.drawable.ic_ti_none);
        } else {
            Glide.with(holder.itemView.getContext())
                    .load(giftList.get(position).getThumb())
                    .into(holder.thumbIV);
        }

        //判断是否已经下载
        if (tiGift.isDownloaded()) {
            holder.downloadIV.setVisibility(View.GONE);
            holder.loadingIV.setVisibility(View.GONE);
            holder.stopLoadingAnimation();
        } else {
            //判断是否正在下载，如果正在下载，则显示加载动画
            if (downloadingGifts.containsKey(tiGift.getName())) {
                holder.downloadIV.setVisibility(View.GONE);
                holder.loadingIV.setVisibility(View.VISIBLE);
                holder.startLoadingAnimation();
            } else {
                holder.downloadIV.setVisibility(View.VISIBLE);
                holder.loadingIV.setVisibility(View.GONE);
                holder.stopLoadingAnimation();
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //如果没有下载，则开始下载到本地
                if (!tiGift.isDownloaded()) {

                    //如果已经在下载了，则不操作
                    if (downloadingGifts.containsKey(tiGift.getName())) {
                        return;
                    }

                    new DownloadTask.Builder(tiGift.getUrl(), new File(TiSDK.getGiftPath(holder.itemView.getContext())))
                            .setMinIntervalMillisCallbackProcess(30)
                            .setConnectionCount(1)
                            .build()
                            .enqueue(new DownloadListener2() {
                                @Override
                                public void taskStart(@NonNull DownloadTask task) {
                                    downloadingGifts.put(tiGift.getName(), tiGift.getUrl());
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            notifyDataSetChanged();
                                        }
                                    });
                                }

                                @Override
                                public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable final Exception realCause) {
                                    if (cause == EndCause.COMPLETED) {
                                        downloadingGifts.remove(tiGift.getName());

                                        File targetDir = new File(TiSDK.getGiftPath(holder.itemView.getContext()));
                                        File file = task.getFile();
                                        try {
                                            //解压到礼物目录
                                            TiUtils.unzip(file, targetDir);
                                            if (file != null) {
                                                file.delete();
                                            }

                                            //修改内存与文件
                                            tiGift.setDownloaded(true);
                                            tiGift.giftDownload(holder.itemView.getContext());

                                            handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    notifyDataSetChanged();
                                                }
                                            });

                                        } catch (Exception e) {
                                            if (file != null) {
                                                file.delete();
                                            }
                                        }
                                    } else {
                                        downloadingGifts.remove(tiGift.getName());

                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                notifyDataSetChanged();
                                                if (realCause != null) {
                                                    Toast.makeText(holder.itemView.getContext(), realCause.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                }
                            });

                } else {
                    //如果已经下载了，则让礼物生效
                    tiSDKManager.setGift(tiGift.getName());

                    //切换选中背景
                    int lastPosition = selectedPosition;
                    selectedPosition = holder.getAdapterPosition();
                    notifyItemChanged(selectedPosition);
                    notifyItemChanged(lastPosition);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return giftList == null ? 0 : giftList.size();
    }
}

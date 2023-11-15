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
import com.hwangjr.rxbus.RxBus;
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
import cn.tillusory.sdk.bean.TiInteraction;
import cn.tillusory.sdk.common.TiUtils;
import cn.tillusory.tiui.R;
import cn.tillusory.tiui.model.RxBusAction;

/**
 * Created by Anko on 2020/4/27.
 * Copyright (c) 2020 拓幻科技 - tillusory.cn. All rights reserved.
 */
public class TiInteractionAdapter extends RecyclerView.Adapter<TiInteractionViewHolder> {

    private int selectedPosition = 0;

    private List<TiInteraction> interactionList;
    private TiSDKManager tiSDKManager;

    private Handler handler = new Handler();

    private Map<String, String> downloadingInteractions = new ConcurrentHashMap<>();

    public TiInteractionAdapter(List<TiInteraction> interactionList, TiSDKManager tiSDKManager) {
        this.interactionList = interactionList;
        this.tiSDKManager = tiSDKManager;

        DownloadDispatcher.setMaxParallelRunningCount(5);
    }

    @NonNull
    @Override
    public TiInteractionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ti_sticker, parent, false);
        return new TiInteractionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TiInteractionViewHolder holder, int position) {


        final TiInteraction tiInteraction = interactionList.get(holder.getAdapterPosition());

        if (selectedPosition == position) {
            holder.itemView.setSelected(true);
        } else {
            holder.itemView.setSelected(false);
        }

        //显示封面
        if (tiInteraction == TiInteraction.NO_INTERACTION) {
            holder.thumbIV.setImageResource(R.drawable.ic_ti_none);
        } else {
            Glide.with(holder.itemView.getContext())
                    .load(interactionList.get(position).getThumb())
                    .into(holder.thumbIV);
        }

        //判断是否已经下载
        if (tiInteraction.isDownloaded()) {
            holder.downloadIV.setVisibility(View.GONE);
            holder.loadingIV.setVisibility(View.GONE);
            holder.stopLoadingAnimation();
        } else {
            //判断是否正在下载，如果正在下载，则显示加载动画
            if (downloadingInteractions.containsKey(tiInteraction.getName())) {
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
                if (!tiInteraction.isDownloaded()) {

                    //如果已经在下载了，则不操作
                    if (downloadingInteractions.containsKey(tiInteraction.getName())) {
                        return;
                    }

                    new DownloadTask.Builder(tiInteraction.getUrl(), new File(TiSDK.getInteractionPath(holder.itemView.getContext())))
                            .setMinIntervalMillisCallbackProcess(30)
                            .setConnectionCount(1)
                            .build()
                            .enqueue(new DownloadListener2() {
                                @Override
                                public void taskStart(@NonNull DownloadTask task) {
                                    downloadingInteractions.put(tiInteraction.getName(), tiInteraction.getUrl());
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
                                        downloadingInteractions.remove(tiInteraction.getName());
                                        File targetDir = new File(TiSDK.getInteractionPath(holder.itemView.getContext()));
                                        File file = task.getFile();
                                        try {
                                            //解压到互动贴纸目录
                                            TiUtils.unzip(file, targetDir);
                                            if (file != null) {
                                                file.delete();
                                            }

                                            //修改内存与文件
                                            tiInteraction.setDownloaded(true);
                                            tiInteraction.interactionDownload(holder.itemView.getContext());

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
                                        downloadingInteractions.remove(tiInteraction.getName());

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
                    //如果已经下载了，则让贴纸生效
                    tiSDKManager.setInteraction(tiInteraction.getName());

                    //切换选中背景
                    int lastPosition = selectedPosition;
                    selectedPosition = holder.getAdapterPosition();
                    notifyItemChanged(selectedPosition);
                    notifyItemChanged(lastPosition);

                    RxBus.get().post(RxBusAction.ACTION_SHOW_INTERACTION, interactionList.get(selectedPosition).getHint());
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return interactionList == null ? 0 : interactionList.size();
    }
}

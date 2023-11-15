package com.feicui365.live.live.adapter;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feicui365.live.R;
import com.feicui365.live.base.MyApp;
import com.feicui365.live.dialog.BanDialog;
import com.feicui365.live.dialog.BaseDialog;
import com.feicui365.live.dialog.DisBanDialog;
import com.feicui365.live.dialog.KickDialog;
import com.feicui365.live.eventbus.LiveManageBus;
import com.feicui365.live.eventbus.UserListBus;
import com.feicui365.live.im.TxImSendUtils;
import com.feicui365.live.interfaces.OnCancelClickLinstener;
import com.feicui365.live.interfaces.OnGroupClickLinstener;
import com.feicui365.live.live.bean.GroupUserData;
import com.feicui365.live.live.utils.ArmsUtils;
import com.feicui365.live.live.utils.GlideUtils;
import com.feicui365.live.model.entity.AllUser;
import com.feicui365.live.model.entity.Comment;
import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.util.HttpUtils;
import com.feicui365.live.util.LevelUtils;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.live.util.ToastUtils;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.tencent.imsdk.v2.V2TIMGroupMemberOperationResult;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.imsdk.v2.V2TIMValueCallback;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class LiveUserAdapter extends BaseQuickAdapter<AllUser.ListBean, BaseViewHolder> {


    OnGroupClickLinstener onGroupClickLinstener;

    public void setOnGroupClickLinstener(OnGroupClickLinstener onGroupClickLinstener) {
        this.onGroupClickLinstener = onGroupClickLinstener;
    }

    public LiveUserAdapter() {
        super(R.layout.user_item_3);
    }


    @Override
    protected void convert(@NotNull BaseViewHolder helper, AllUser.ListBean bean) {

        GlideUtils.setImage(MyApp.getInstance(), bean.getAvatar(), R.mipmap.moren, helper.getView(R.id.civ_avatar));


        helper.setText(R.id.tv_name, bean.getNickName());
        helper.setText(R.id.tv_id,"ID:"+bean.getMember_Account());

        HttpUtils.getInstance().getGroupUserData(bean.getMember_Account(), new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                JSONObject data = HttpUtils.getInstance().check(response);
                if(data.getJSONObject("data")==null){
                    return;
                }
                if (HttpUtils.getInstance().swtichStatus(data)) {
                    GroupUserData result = JSON.toJavaObject(data.getJSONObject("data"), GroupUserData.class);
                    if(ArmsUtils.isStringEmpty(result.getArea())){
                        helper.setText(R.id.tv_ip, result.getArea());
                    }else{
                        helper.setText(R.id.tv_ip, "无");
                    }
                    if(result.getIs_admin()==0){
                        helper.setText(R.id.tv_manager,"设置管理员");
                    }else{
                        helper.setText(R.id.tv_manager,"取消管理员");
                    }
                    if(result.getIs_forbid_send_msg()==0){
                        helper.setText(R.id.tv_ban,"禁言");
                    }else{
                        helper.setText(R.id.tv_ban,"取消禁言");
                    }
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                helper.setText(R.id.tv_ip, "");
                helper.setText(R.id.tv_manager,"设置管理员");
                helper.setText(R.id.tv_ban,"禁言");
            }
        });

        helper.getView(R.id.tv_manager).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tvIP=helper.getView(R.id.tv_ip);
                TextView tvM=helper.getView(R.id.tv_manager);
//                if(tvIP.getText().length()<=3){
//                    ToastUtils.showT("该用户信息查询错误,或尚未得到查询结果,请稍后再试");
//                    return;
//                }
                EventBus.getDefault().post( new LiveManageBus());
                if(tvM.getText().toString().equals("设置管理员")){
                        if(onGroupClickLinstener!=null){
                            onGroupClickLinstener.onSetManagerClick(bean.getMember_Account(),1);
                            helper.setText(R.id.tv_manager,"取消管理员");
                        }

                }else{
                    if(onGroupClickLinstener!=null){
                        onGroupClickLinstener.onSetManagerClick(bean.getMember_Account(),0);
                        helper.setText(R.id.tv_manager,"设置管理员");
                    }
                }
            }
        });

        helper.getView(R.id.tv_ban).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             //   TextView tvIP=helper.getView(R.id.tv_ip);
                TextView tvM=helper.getView(R.id.tv_ban);
                String banTopic = tvM.getText().toString();
                if (TextUtils.equals(banTopic,"禁言")){
                    BanDialog banDialog = BanDialog.create(mContext);
                    banDialog.setOnContentTypeClickCallback(new BaseDialog.OnContentTypeClickCallback() {
                        @Override
                        public void onContentTypeClick(int type, Object content) {
                            String typeString = String.valueOf(content);
                            EventBus.getDefault().post( new LiveManageBus());
                            if(tvM.getText().toString().equals("禁言")){
                                if(onGroupClickLinstener!=null){
                                    onGroupClickLinstener.onBanClick(bean.getMember_Account(),1,typeString);
                                    if (type == 1) {
                                        helper.setText(R.id.tv_ban,"解除禁言");
                                    }
                                }
                            }else{
                                if(onGroupClickLinstener!=null){
                                    onGroupClickLinstener.onBanClick(bean.getMember_Account(),0,typeString);
                                    helper.setText(R.id.tv_ban,"禁言");
                                }
                            }
                        }
                    });
                }else {
                    DisBanDialog disBanDialog = DisBanDialog.create(mContext);
                    disBanDialog.setDesc(bean.getNickName());
                    disBanDialog.setOnContentClickCallback(new BaseDialog.OnContentClickCallback() {
                        @Override
                        public void onContentClick(String content) {
                            if(onGroupClickLinstener!=null){
                                helper.setText(R.id.tv_ban,"禁言");
                                onGroupClickLinstener.onBanClick(bean.getMember_Account(),2,content);
                            }
                        }
                    });
                }

//                if(tvIP.getText().length()<=3){
//                    ToastUtils.showT("该用户信息查询错误,或尚未得到查询结果,请稍后再试");
//                    return;
//                }

            }
        });

        helper.getView(R.id.tv_kick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KickDialog kickDialog = KickDialog.create(mContext);
                kickDialog.setDesc(bean.getNickName());
                kickDialog.setOnClickCallback(new BaseDialog.OnClickCallback() {
                    @Override
                    public void onClickType(int type) {
                        switch (type) {
                            case BaseDialog.SELECT_FIRST:
                              //  TxImSendUtils.exitRoom(bean.getId());
                                Map<String,Object> map = new HashMap<>() ;
                                map.put("userOutGroup", bean.getMember_Account());
                                JSONObject jsonObject = new JSONObject(map);
                                String s = jsonObject.toString();
                                Log.e("liudanhua","==="+s);
                                String id = MyUserInstance.getInstance().getUserinfo().getId();
                                V2TIMManager.getInstance().sendGroupCustomMessage(s.getBytes(),ArmsUtils.initLiveGroupId(id), V2TIMMessage.V2TIM_PRIORITY_NORMAL, new V2TIMValueCallback<V2TIMMessage>() {
                                    @Override
                                    public void onSuccess(V2TIMMessage message) {
                                        // 发送群聊自定义消息成功
                                        EventBus.getDefault().post(new UserListBus());
                                    }

                                    @Override
                                    public void onError(int code, String desc) {
                                        // 发送群聊自定义消息失败
                                        ToastUtils.showT(R.string.kick_fail);
                                    }
                                });
//                                List<String> userIDList = new ArrayList<>();
//                                userIDList.add(""+bean.getId());
//                                Log.e("liudanhua","==="+id+"===="+bean.getId());
//                                V2TIMManager.getGroupManager().kickGroupMember(ArmsUtils.initLiveGroupId(id), userIDList, "", new V2TIMValueCallback<List<V2TIMGroupMemberOperationResult>>() {
//                                    @Override
//                                    public void onSuccess(List<V2TIMGroupMemberOperationResult> v2TIMGroupMemberOperationResults) {
//                                        // 踢人成功
//                                        EventBus.getDefault().post(new UserListBus());
//                                    }
//
//                                    @Override
//                                    public void onError(int code, String desc) {
//                                        // 踢人失败
//                                        ToastUtils.showT(R.string.kick_fail+desc+code);
//                                    }
//                                });
                                break;
                        }
                    }
                });

            }
        });


    }
}

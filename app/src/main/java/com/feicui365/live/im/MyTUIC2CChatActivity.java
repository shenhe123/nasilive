package com.feicui365.live.im;

import android.arch.lifecycle.Lifecycle;
import android.os.Bundle;
import android.util.Log;

import com.tencent.imsdk.v2.V2TIMValueCallback;
import com.tencent.qcloud.tuicore.util.ToastUtil;
import com.tencent.qcloud.tuikit.tuichat.TUIChatConstants;
import com.tencent.qcloud.tuikit.tuichat.bean.ChatInfo;
import com.tencent.qcloud.tuikit.tuichat.bean.OpenGiftBus;
import com.tencent.qcloud.tuikit.tuichat.presenter.C2CChatPresenter;
import com.tencent.qcloud.tuikit.tuichat.ui.page.TUIBaseChatActivity;
import com.tencent.qcloud.tuikit.tuichat.ui.page.TUIC2CChatActivity;
import com.tencent.qcloud.tuikit.tuichat.ui.page.TUIC2CChatFragment;
import com.tencent.qcloud.tuikit.tuichat.util.TUIChatLog;
import com.tencent.qcloud.tuikit.tuichat.util.TUIChatUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import com.feicui365.live.contract.LivePushContrat;
import com.feicui365.live.interfaces.OnChoseGiftClickListener;
import com.feicui365.live.live.dialog.GiftDialog2;
import com.feicui365.live.model.entity.GiftInfo;
import com.feicui365.live.presenter.LivePushPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 *
 */
public class MyTUIC2CChatActivity extends TUIBaseChatActivity implements LivePushContrat.View {
    private static final String TAG = TUIC2CChatActivity.class.getSimpleName();

    private TUIC2CChatFragment chatFragment;
    private C2CChatPresenter presenter;
    private  LivePushPresenter mPresenter;
    String mAnchordID;
    @Override
    public void initChat(ChatInfo chatInfo) {
        mPresenter=new LivePushPresenter();
        mPresenter.attachView(this);
        TUIChatLog.i(TAG, "inti chat " + chatInfo);

        if (!TUIChatUtils.isC2CChat(chatInfo.getType())) {
            TUIChatLog.e(TAG, "init C2C chat failed , chatInfo = " + chatInfo);
            ToastUtil.toastShortMessage("init c2c chat failed.");
        }
        mAnchordID=chatInfo.getId();
        chatFragment = new TUIC2CChatFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TUIChatConstants.CHAT_INFO, chatInfo);
        chatFragment.setArguments(bundle);
        presenter = new C2CChatPresenter();
        presenter.initListener();
        chatFragment.setPresenter(presenter);
        getSupportFragmentManager().beginTransaction().replace(com.tencent.qcloud.tuikit.tuichat.R.id.empty_view, chatFragment).commitAllowingStateLoss();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(OpenGiftBus myEvent) {
        GiftDialog2 giftDialog = new GiftDialog2(true);

        giftDialog.show(getSupportFragmentManager(), "");
        giftDialog.setOnChoseGiftClickListener(new OnChoseGiftClickListener() {
            @Override
            public void onGiftClick(GiftInfo bean, int count) {

                mPresenter.sendGift2(mAnchordID, bean.getId(), null, count, null, null);
                sendMessage(bean.getTitle(),count);
            }


        });
    }


    private void sendMessage(String giftName,int count){
        StringBuilder sb=new StringBuilder();
        sb.append("送了 ");
        sb.append(giftName);
        sb.append(" x");
        sb.append(count);
        sb.append(" 给您");

        TxImSendUtils.sendImMessage(sb.toString(), mAnchordID, new V2TIMValueCallback() {
            @Override
            public void onSuccess(Object o) {
                Log.e("IMTAG","SUccess");
            }

            @Override
            public void onError(int i, String s) {
                Log.e("IMTAG","onError");
            }
        });
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public <T> AutoDisposeConverter<T> bindAutoDispose() {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider
                .from(this, Lifecycle.Event.ON_DESTROY));
    }
}

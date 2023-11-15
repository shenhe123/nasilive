package com.feicui365.live.im;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.tencent.qcloud.tuicore.component.action.PopActionClickListener;
import com.tencent.qcloud.tuicore.component.action.PopDialogAdapter;
import com.tencent.qcloud.tuicore.component.action.PopMenuAction;
import com.tencent.qcloud.tuicore.component.fragments.BaseFragment;
import com.tencent.qcloud.tuicore.component.interfaces.IUIKitCallback;
import com.tencent.qcloud.tuicore.util.ScreenUtil;
import com.tencent.qcloud.tuicore.util.ToastUtil;
import com.tencent.qcloud.tuikit.tuiconversation.bean.ConversationInfo;
import com.tencent.qcloud.tuikit.tuiconversation.presenter.ConversationPresenter;
import com.tencent.qcloud.tuikit.tuiconversation.ui.view.ConversationLayout;
import com.tencent.qcloud.tuikit.tuiconversation.ui.view.ConversationListLayout;

import com.feicui365.live.ui.act.SystemMessageActivity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class MyTUIConversationFragment extends BaseFragment {

    private View mBaseView;
    private ConversationLayout mConversationLayout;
    private ListView mConversationPopList;
    private PopDialogAdapter mConversationPopAdapter;
    private PopupWindow mConversationPopWindow;
    private List<PopMenuAction> mConversationPopActions = new ArrayList<>();

    private ConversationPresenter presenter;
    private TextView empty_tv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mBaseView = inflater.inflate(com.tencent.qcloud.tuikit.tuiconversation.R.layout.conversation_fragment, container, false);
        initView();
        return mBaseView;
    }

    private void initView() {
        // 从布局文件中获取会话列表面板
        mConversationLayout = mBaseView.findViewById(com.tencent.qcloud.tuikit.tuiconversation.R.id.conversation_layout);
        empty_tv = mBaseView.findViewById(com.tencent.qcloud.tuikit.tuiconversation.R.id.empty_tv);

        presenter = new ConversationPresenter();
        presenter.setConversationListener();
        mConversationLayout.setPresenter(presenter);

        // 会话列表面板的默认UI和交互初始化
        mConversationLayout.initDefault();
        // 通过API设置ConversataonLayout各种属性的样例，开发者可以打开注释，体验效果
//        ConversationLayoutSetting.customizeConversation(mConversationLayout);
        mConversationLayout.getConversationList().setOnItemClickListener(new ConversationListLayout.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int viewType, ConversationInfo conversationInfo) {
                //此处为demo的实现逻辑，更根据会话类型跳转到相关界面，开发者可根据自己的应用场景灵活实现
                if (conversationInfo.getId().equals("admin")) {
                    startActivity(new Intent(getContext(), SystemMessageActivity.class));
                } else {
                    TxImUtils.getSocketManager().startChat(conversationInfo);
                }

            }
        });

        mConversationLayout.getConversationList().setOnItemLongClickListener(new ConversationListLayout.OnItemLongClickListener() {
            @Override
            public void OnItemLongClick(View view, ConversationInfo conversationInfo) {
                showItemPopMenu(view, conversationInfo);
            }
        });

        initPopMenuAction();
        restoreConversationItemBackground();
    }


    public void restoreConversationItemBackground() {
        if (mConversationLayout.getConversationList().getAdapter() != null &&
                mConversationLayout.getConversationList().getAdapter().isClick()) {
            mConversationLayout.getConversationList().getAdapter().setClick(false);
            mConversationLayout.getConversationList().getAdapter().notifyItemChanged(mConversationLayout.getConversationList().getAdapter().getCurrentPosition());
        }
//        Log.e("111111","==="+ mConversationLayout.getConversationList().getAdapter().getItem(0).get());
//        if (mConversationLayout.getConversationList() != null
//                && mConversationLayout.getConversationList().getAdapter() != null
//                && mConversationLayout.getConversationList().getAdapter().getItemCount() > 0) {
//            empty_tv.setVisibility(View.GONE);
//            mConversationLayout.setVisibility(View.VISIBLE);
//        } else {
//            empty_tv.setVisibility(View.VISIBLE);
//            mConversationLayout.setVisibility(View.GONE);
//        }
    }

    private void initPopMenuAction() {
        // 设置长按conversation显示PopAction
        List<PopMenuAction> conversationPopActions = new ArrayList<PopMenuAction>();
        PopMenuAction action = new PopMenuAction();
        action.setActionName(getResources().getString(com.tencent.qcloud.tuikit.tuiconversation.R.string.chat_top));
        action.setActionClickListener(new PopActionClickListener() {
            @Override
            public void onActionClick(int index, Object data) {
                mConversationLayout.setConversationTop((ConversationInfo) data, new IUIKitCallback() {
                    @Override
                    public void onSuccess(Object data) {

                    }

                    @Override
                    public void onError(String module, int errCode, String errMsg) {
                        ToastUtil.toastLongMessage(module + ", Error code = " + errCode + ", desc = " + errMsg);
                    }
                });
            }
        });
        conversationPopActions.add(action);
        action = new PopMenuAction();
        action.setActionClickListener(new PopActionClickListener() {
            @Override
            public void onActionClick(int index, Object data) {
                mConversationLayout.deleteConversation((ConversationInfo) data);
            }
        });
        action.setActionName(getResources().getString(com.tencent.qcloud.tuikit.tuiconversation.R.string.chat_delete));
        conversationPopActions.add(action);

        action = new PopMenuAction();
        action.setActionName(getResources().getString(com.tencent.qcloud.tuikit.tuiconversation.R.string.clear_conversation_message));
        action.setActionClickListener(new PopActionClickListener() {
            @Override
            public void onActionClick(int index, Object data) {
                mConversationLayout.clearConversationMessage((ConversationInfo) data);
            }
        });
        conversationPopActions.add(action);

        mConversationPopActions.clear();
        mConversationPopActions.addAll(conversationPopActions);
    }

    /**
     * 长按会话item弹框
     *
     * @param view             长按 view
     * @param conversationInfo 会话数据对象
     */
    private void showItemPopMenu(View view, final ConversationInfo conversationInfo) {
        if (mConversationPopActions == null || mConversationPopActions.size() == 0) {
            empty_tv.setVisibility(View.VISIBLE);
            mConversationLayout.setVisibility(View.GONE);
            return;
        } else {
            empty_tv.setVisibility(View.GONE);
            mConversationLayout.setVisibility(View.VISIBLE);
        }
        Log.e("1111111", "===" + mConversationPopActions);

        View itemPop = LayoutInflater.from(getActivity()).inflate(com.tencent.qcloud.tuikit.tuiconversation.R.layout.conversation_pop_menu_layout, null);
        mConversationPopList = itemPop.findViewById(com.tencent.qcloud.tuikit.tuiconversation.R.id.pop_menu_list);
        mConversationPopList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PopMenuAction action = mConversationPopActions.get(position);
                if (action.getActionClickListener() != null) {
                    action.getActionClickListener().onActionClick(position, conversationInfo);
                }
                mConversationPopWindow.dismiss();
                restoreConversationItemBackground();
            }
        });

        for (int i = 0; i < mConversationPopActions.size(); i++) {
            PopMenuAction action = mConversationPopActions.get(i);
            if (conversationInfo.isTop()) {
                if (action.getActionName().equals(getResources().getString(com.tencent.qcloud.tuikit.tuiconversation.R.string.chat_top))) {
                    action.setActionName(getResources().getString(com.tencent.qcloud.tuikit.tuiconversation.R.string.quit_chat_top));
                }
            } else {
                if (action.getActionName().equals(getResources().getString(com.tencent.qcloud.tuikit.tuiconversation.R.string.quit_chat_top))) {
                    action.setActionName(getResources().getString(com.tencent.qcloud.tuikit.tuiconversation.R.string.chat_top));
                }

            }
        }
        mConversationPopAdapter = new PopDialogAdapter();
        mConversationPopList.setAdapter(mConversationPopAdapter);
        mConversationPopAdapter.setDataSource(mConversationPopActions);
        mConversationPopWindow = new PopupWindow(itemPop, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        mConversationPopWindow.setBackgroundDrawable(new ColorDrawable());
        mConversationPopWindow.setOutsideTouchable(true);
        mConversationPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                restoreConversationItemBackground();
            }
        });
        int x = view.getWidth() / 2;
        int y = -view.getHeight() / 3;
        int popHeight = ScreenUtil.dip2px(45) * 3;
        if (y + popHeight + view.getY() + view.getHeight() > mConversationLayout.getBottom()) {
            y = y - popHeight;
        }
        mConversationPopWindow.showAsDropDown(view, x, y, Gravity.TOP | Gravity.START);
    }


}


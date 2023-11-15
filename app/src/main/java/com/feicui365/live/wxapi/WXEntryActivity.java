/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.feicui365.live.wxapi;

import android.content.Intent;
import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.feicui365.live.eventbus.LoginChangeBus;
import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.ui.act.HomeActivity;
import com.feicui365.live.util.HttpUtils;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.nasinet.utils.AppManager;
import com.orhanobut.hawk.Hawk;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

import cn.sharesdk.wechat.utils.WechatHandlerActivity;

/** 微信客户端回调activity示例 */
public class WXEntryActivity extends WechatHandlerActivity  implements IWXAPIEventHandler {

	private static final String WEIXIN_ACCESS_TOKEN_KEY = "wx_access_token_key";
	private static final String WEIXIN_OPENID_KEY = "wx_openid_key";
	private static final String WEIXIN_REFRESH_TOKEN_KEY = "wx_refresh_token_key";
	private Gson mGson;

	private static final int TIMELINE_SUPPORTED_VERSION = 0x21020001;


	private IWXAPI api;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);

			api = WXAPIFactory.createWXAPI(this, "wx39c17448a6a2fc23", false);
			//注意：
			//第三方开发者如果使用透明界面来实现WXEntryActivity，需要判断handleIntent的返回值，如果返回值为false，则说明入参不合法未被SDK处理，
			// 应finish当前透明界面，避免外部通过传递非法参数的Intent导致停留在透明界面，引起用户的疑惑
			// 微信事件回调接口注册
			api.handleIntent(getIntent(), this);
		} catch (Exception e) {
			e.printStackTrace();
			finish();
		}

		mGson = new Gson();
	}

	//这个方法不重写 分享回调是会出问题,导致分享后不能回到自己得APP
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		boolean b = api.handleIntent(intent, this);
		finish();
		if (!b) {
			finish();
		}

	}

	// 微信发送请求到第三方应用时，会回调到该方法
	@Override
	public void onReq(BaseReq req) {
		this.finish();
		switch (req.getType()) {
			case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
//                goToGetMsg();
				break;
			case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
//                goToShowMsg((ShowMessageFromWX.Req) req);
				break;
			default:
				break;
		}
	}

	// 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
	@Override
	public void onResp(BaseResp resp) {
//        finish();
		switch (resp.errCode) {
			case BaseResp.ErrCode.ERR_OK:
				// 获取code   如果是分享这个位置的code强转会出错误,需要try起来
				String code = null;
				try {
					code = ((SendAuth.Resp) resp).code;

				} catch (Exception e) {
					e.printStackTrace();
					finish();
				}
				HttpUtils.getInstance().wxlogin(code, new StringCallback() {
					@Override
					public void onSuccess(Response<String> response) {

						JSONObject data = (JSONObject) HttpUtils.getInstance().check(response).getJSONObject("data");
						if (null != data) {
							UserRegist userRegist = (UserRegist) JSONObject.parseObject(data.toString(), UserRegist.class);
							MyUserInstance.getInstance().setUserInfo(userRegist);
							String temp= JSON.toJSONString(userRegist);
							Hawk.put("USER_REGIST",temp);
							EventBus.getDefault().post(LoginChangeBus.getInstance(""));

							AppManager.getAppManager().startActivity(HomeActivity.class);
							AppManager.getAppManager().finishActivity();
						}
					}

					@Override
					public void onError(Response<String> response) {
						super.onError(response);
					}
				});

				break;
			case BaseResp.ErrCode.ERR_USER_CANCEL:
				finish();
				break;
			case BaseResp.ErrCode.ERR_AUTH_DENIED:
				finish();
				break;
			case BaseResp.ErrCode.ERR_UNSUPPORT:
				finish();
				break;
			default:
				finish();
				break;
		}
	}



	/**
	 * 验证是否成功  *  * @param response 返回消息  * @return 是否成功
	 */
	private boolean validateSuccess(String response) {
		String errFlag = "errmsg";
		return (errFlag.contains(response) && !"ok".equals(response)) || (!"errcode".contains(response) && !errFlag.contains(response));
	}







}

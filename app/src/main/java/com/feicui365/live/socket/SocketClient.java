/*
package com.feicui365.live.socket;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.feicui365.live.base.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


public class SocketClient {
    private static final String TAG = "socket";
    private Socket mSocket;
    private boolean isConnet;
    private String mUid;
    public static final String IO_SERVER_URL = "http://1.116.79.39";
    //  public static final String IO_SERVER_URL = "http://192.168.0.100:9000";
    private static SocketMessageListener mMessageListener;
    private SocketHandler mSocketHandler;
    //连接
    private Emitter.Listener mConnectListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.e(TAG, "--onConnect-->" + args);
            isConnet = true;

        }
    };
    //重连
    private Emitter.Listener mReConnectListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.e(TAG, "--reConnect-->" + args);
            //conn();
        }
    };
    //断连
    private Emitter.Listener mDisConnectListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.e(TAG, "--onDisconnect-->" + args);
            isConnet = false;
        }
    };
    //超时
    private Emitter.Listener mTimeOutConnectListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.e(TAG, "--onTimeOut-->" + args);
            isConnet = false;
        }
    };

    //错误
    private Emitter.Listener mErrorConnectListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.e(TAG, "--onError-->" + args);
            isConnet = false;
        }
    };
    //消息
    private Emitter.Listener onBroadcastListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            if (mSocketHandler != null) {
                try {
                    if (args.length == 0) {
                        return;
                    }

                    JSONObject data = (JSONObject) args[0];

                    Message msg = Message.obtain();
                  //  msg.what = Constants.SOCKET_IS_BROADCAST;
                    msg.obj = data;
                    if (mSocketHandler != null) {
                        mSocketHandler.sendMessage(msg);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    };


    private SocketClient() {
    }

    private static class Holder {
        static SocketClient SIGNAL = new SocketClient();
    }

    public static SocketClient getInstance() {
        return Holder.SIGNAL;
    }


    */
/**
     * 逻辑梳理
     * 1,初始化基本参数
     * 2,创建的时候需要传入一个UID,如果UID不同,先停掉当先连接,然后重新创建
     *//*


    public void initSockt(String uid, SocketMessageListener listener) {
        if (mUid == null) {
            mUid = uid;
            getSocket(uid, listener);
        } else {
            //如果uid不同,先断开连接,然后重连
            if (!mUid.equals(uid)) {
                disConnect();
                getSocket(uid, listener);
            }
        }
    }

    public void getSocket(String uid, SocketMessageListener listener) {


        if (mSocket == null) {
            try {
                IO.Options option = new IO.Options();
                option.query = "uid=" + uid;
                mSocket = IO.socket(IO_SERVER_URL, option);
                mSocket.on(Socket.EVENT_CONNECT, mConnectListener);
                mSocket.on(Socket.EVENT_RECONNECT, mReConnectListener);
                mSocket.on(Socket.EVENT_DISCONNECT, mDisConnectListener);
                mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, mTimeOutConnectListener);
                mSocket.on(Socket.EVENT_CONNECT_ERROR, mErrorConnectListener);
               // mSocket.on(Constants.SOCKET_BROADCAST, onBroadcastListener);
                mSocketHandler = new SocketHandler(listener);


            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        mSocket.connect();

    }


    */
/**
     * 断开连接
     *//*

    public void disConnect() {
        if (mSocket != null) {

            mSocket.close();
            mSocket.off();
            mSocket = null;
        }

        if (mSocketHandler != null) {
            mSocketHandler.release();
            mSocketHandler = null;
        }
    }

    */
/**
     * 消息handler
     *//*

    private static class SocketHandler extends Handler {




        public SocketHandler(SocketMessageListener listener) {
            mMessageListener = new WeakReference<>(listener).get();
        }


        @Override
        public void handleMessage(Message msg) {


            //要做一下区分,系统消息或者其他



            if (mMessageListener == null) {
                return;
            }
            switch (msg.what) {

                case Constants.SOCKET_IS_BROADCAST:
                    processBroadcastAction((JSONObject) msg.obj);
                    break;

            }
        }


        private void processBroadcastAction(JSONObject socketMsg) {
            try {
                if(socketMsg.getString("action")==null){
                    return;
                }

                switch (socketMsg.getString("action")){

                    */
/**
                     * 加入直播间
                     *//*

                    case Constants.SOCKET_ACTION_JOINROOM:

                        MessageBean messageBean=new Gson().fromJson(socketMsg.toString(),MessageBean.class);
                      //  mMessageListener.onJoinRoom();
                        break;

                    */
/**
                     * 退出直播间
                     *//*

                    case Constants.SOCKET_ACTION_LEAVEROOM:
                        break;
                    */
/**
                     * 直播间消息
                     *//*

                    case Constants.SOCKET_ACTION_ROOMMESSAGE:
                        break;
                    */
/**
                     * 礼物动画
                     *//*

                    case Constants.SOCKET_ACTION_GIFTANIMATION:
                        break;
                    */
/**
                     * 系统消息
                     *//*

                    case Constants.SOCKET_ACTION_SYSTEMMESSAGE:
                        break;
                    */
/**
                     * 有观众进入或离开直播间
                     *//*

                    case Constants.SOCKET_ACTION_LIVEGROUPMEMBERJOINEXIT:
                        break;
                    */
/**
                     * 横幅推送
                     *//*

                    case Constants.SOCKET_ACTION_BROADCASTSTREAMER:
                        break;
                    */
/**
                     * 直播间通知消息
                     *//*

                    case Constants.SOCKET_ACTION_ROOMNOTIFICATION:
                        break;


                }








            } catch (JSONException e) {
                e.printStackTrace();
            }

        }



        private void processBroadcastNotify(JSONObject socketMsg) {
            try {


                switch (socketMsg.getString("action")){

                    */
/**
                     * 加入直播间
                     *//*

                    case Constants.SOCKET_ACTION_JOINROOM:
                        break;

                    */
/**
                     * 退出直播间
                     *//*

                    case Constants.SOCKET_ACTION_LEAVEROOM:
                        break;
                    */
/**
                     * 直播间消息
                     *//*

                    case Constants.SOCKET_ACTION_ROOMMESSAGE:
                        break;
                    */
/**
                     * 礼物动画
                     *//*

                    case Constants.SOCKET_ACTION_GIFTANIMATION:
                        break;
                    */
/**
                     * 系统消息
                     *//*

                    case Constants.SOCKET_ACTION_SYSTEMMESSAGE:
                        break;
                    */
/**
                     * 有观众进入或离开直播间
                     *//*

                    case Constants.SOCKET_ACTION_LIVEGROUPMEMBERJOINEXIT:
                        break;
                    */
/**
                     * 横幅推送
                     *//*

                    case Constants.SOCKET_ACTION_BROADCASTSTREAMER:
                        break;
                    */
/**
                     * 直播间通知消息
                     *//*

                    case Constants.SOCKET_ACTION_ROOMNOTIFICATION:
                        break;


                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        public void release() {
            mMessageListener = null;
        }

    }

    */
/**
     *设置直播间消息接收器
     *//*

    public static void setMessageListener(SocketMessageListener mListener) {
        SocketClient.mMessageListener = mListener;
    }

    public Socket getSocket() {

        return mSocket;
    }
}
*/

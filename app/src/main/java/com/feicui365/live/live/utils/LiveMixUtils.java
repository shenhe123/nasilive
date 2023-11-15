package com.feicui365.live.live.utils;


import com.tencent.live2.V2TXLiveDef;

import com.feicui365.live.util.MyUserInstance;

import java.util.ArrayList;

import static com.tencent.live2.V2TXLiveDef.V2TXLiveMixInputType.V2TXLiveMixInputTypeAudioVideo;

/**
 *
 */
public class LiveMixUtils {

    public static V2TXLiveDef.V2TXLiveTranscodingConfig getPkConfig(String linkStreamId, String linkUserId, boolean isHome) {
        if (isHome) {
            return getPkConfigHome(linkStreamId,linkUserId);
        }else{
            return getPkConfigAway(linkStreamId,linkUserId);
        }


    }

    public static V2TXLiveDef.V2TXLiveTranscodingConfig getPkConfigHome(String linkStreamId, String linkUserId) {
        V2TXLiveDef.V2TXLiveTranscodingConfig config = new V2TXLiveDef.V2TXLiveTranscodingConfig();
        config.videoWidth = 1440;
        config.videoHeight = 1280;
        config.videoBitrate = 1500;
        config.videoFramerate = 30;
        config.videoGOP = 2;
        config.backgroundColor = 0x000000;
        config.backgroundImage = null;
        config.audioSampleRate = 48000;
        config.audioBitrate = 64;
        config.audioChannels = 1;
        config.outputStreamId = null;
        config.mixStreams = new ArrayList<>();

        V2TXLiveDef.V2TXLiveMixStream mixStream = new V2TXLiveDef.V2TXLiveMixStream();
        mixStream.userId = MyUserInstance.getInstance().getUserinfo().getId();
        mixStream.streamId = "st_" + MyUserInstance.getInstance().getUserinfo().getId();
        mixStream.x = 0;
        mixStream.y = 0;
        mixStream.width = 720;
        mixStream.height = 1280;
        mixStream.zOrder = 0;
        mixStream.inputType = V2TXLiveMixInputTypeAudioVideo;
        config.mixStreams.add(mixStream);

        V2TXLiveDef.V2TXLiveMixStream remote = new V2TXLiveDef.V2TXLiveMixStream();
        remote.userId = linkUserId;
        remote.streamId = linkStreamId;
        remote.x = 720;
        remote.y = 0;
        remote.width = 720;
        remote.height = 1280;
        remote.zOrder = 1;
        mixStream.inputType = V2TXLiveMixInputTypeAudioVideo;
        config.mixStreams.add(remote);

        return config;
    }

    public static V2TXLiveDef.V2TXLiveTranscodingConfig getPkConfigAway(String linkStreamId, String linkUserId) {
        V2TXLiveDef.V2TXLiveTranscodingConfig config = new V2TXLiveDef.V2TXLiveTranscodingConfig();
        config.videoWidth = 1440;
        config.videoHeight = 1280;
        config.videoBitrate = 1500;
        config.videoFramerate = 30;
        config.videoGOP = 2;
        config.backgroundColor = 0x000000;
        config.backgroundImage = null;
        config.audioSampleRate = 48000;
        config.audioBitrate = 64;
        config.audioChannels = 1;
        config.outputStreamId = null;
        config.mixStreams = new ArrayList<>();

        V2TXLiveDef.V2TXLiveMixStream mixStream = new V2TXLiveDef.V2TXLiveMixStream();

        mixStream.userId = linkUserId;
        mixStream.streamId = linkStreamId;
        mixStream.x = 0;
        mixStream.y = 0;
        mixStream.width = 720;
        mixStream.height = 1280;
        mixStream.zOrder = 0;
        mixStream.inputType = V2TXLiveMixInputTypeAudioVideo;
        config.mixStreams.add(mixStream);

        V2TXLiveDef.V2TXLiveMixStream remote = new V2TXLiveDef.V2TXLiveMixStream();
        remote.userId = MyUserInstance.getInstance().getUserinfo().getId();
        remote.streamId = "st_" + MyUserInstance.getInstance().getUserinfo().getId();

        remote.x = 720;
        remote.y = 0;
        remote.width = 720;
        remote.height = 1280;
        remote.zOrder = 1;
        mixStream.inputType = V2TXLiveMixInputTypeAudioVideo;
        config.mixStreams.add(remote);

        return config;
    }

    public static V2TXLiveDef.V2TXLiveTranscodingConfig getLinkConfig(String linkStreamId, String linkUserId) {
        V2TXLiveDef.V2TXLiveTranscodingConfig config = new V2TXLiveDef.V2TXLiveTranscodingConfig();
        config.videoWidth = 720;
        config.videoHeight = 1280;
        config.videoBitrate = 1500;
        config.videoFramerate = 30;
        config.videoGOP = 2;
        config.backgroundColor = 0x000000;
        config.backgroundImage = null;
        config.audioSampleRate = 48000;
        config.audioBitrate = 64;
        config.audioChannels = 1;
        config.outputStreamId = null;
        config.mixStreams = new ArrayList<>();

        V2TXLiveDef.V2TXLiveMixStream mixStream = new V2TXLiveDef.V2TXLiveMixStream();
        mixStream.userId = MyUserInstance.getInstance().getUserinfo().getId();
        mixStream.streamId = "st_" + MyUserInstance.getInstance().getUserinfo().getId();
        mixStream.x = 0;
        mixStream.y = 0;
        mixStream.width = 720;
        mixStream.height = 1280;
        mixStream.zOrder = 0;
        mixStream.inputType = V2TXLiveMixInputTypeAudioVideo;
        config.mixStreams.add(mixStream);

        V2TXLiveDef.V2TXLiveMixStream remote = new V2TXLiveDef.V2TXLiveMixStream();
        remote.userId = linkUserId;
        remote.streamId = "st_"+linkUserId;
        remote.x = 460;
        remote.y = 645;
        remote.width = 230;
        remote.height = 335;
        remote.zOrder = 1;

        mixStream.inputType = V2TXLiveMixInputTypeAudioVideo;
        config.mixStreams.add(remote);

        return config;
    }
}

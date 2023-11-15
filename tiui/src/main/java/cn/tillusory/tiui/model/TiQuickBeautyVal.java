package cn.tillusory.tiui.model;

import cn.tillusory.sdk.bean.TiFilterEnum;

public enum  TiQuickBeautyVal {
        STANDARD(70, 70, 0, 40, 0, 60, 60, 80, 0, 0, 0, 0, 20, 0, 0, 0, TiFilterEnum.NO_FILTER, 0),
        DELICATE(80, 70, 0, 30, 0, 60, 50, 80, 5, 10, -5, 5, 20, 20, 30, 15, TiFilterEnum.DJANGO_FILTER, 60),
        CUTE(70, 70, 0, 70, 0, 60, 50, 90, 15, 10, -5, 5, 20, 20, 10, 25, TiFilterEnum.COBI_FILTER, 80),
        CELEBRITY(80, 90, 0, 50, 0, 80, 80, 100, 5, 10, 0, 5, 20, 25, 30, 40, TiFilterEnum.CLOUSEAU_FILTER, 80),
        NATURAL(70, 70, 0, 35, 0, 60, 50, 80, 5, 0, 0, 0, 20, 5, 5, 5, TiFilterEnum.NORMAL_FILTER, 50);
        
        public int whitening;
        public int blemishRemoval;
        public int brightness;
        public int tenderness;
        public int sharpness;

        public int eyeMagnify;
        public int chinSlim;
        public int faceNarrow;
        public int jawTransform;
        public int foreheadTransform;
        public int mouthTransform;
        public int noseMinify;
        public int teethWhiten;
        public int eyeSpace;
        public int noseElongate;
        public int eyeCorner;

        public TiFilterEnum filterEnum;
        public int filterVal;

    TiQuickBeautyVal(int whitening, int blemishRemoval, int brightness, int tenderness, int sharpness, int eyeMagnify, int chinSlim, int faceNarrow, int jawTransform, int foreheadTransform, int mouthTransform, int noseMinify, int teethWhiten, int eyeSpace, int noseElongate, int eyeCorner, TiFilterEnum filterEnum, int filterVal) {
        this.whitening = whitening;
        this.blemishRemoval = blemishRemoval;
        this.brightness = brightness;
        this.tenderness = tenderness;
        this.sharpness = sharpness;
        this.eyeMagnify = eyeMagnify;
        this.chinSlim = chinSlim;
        this.faceNarrow = faceNarrow;
        this.jawTransform = jawTransform;
        this.foreheadTransform = foreheadTransform;
        this.mouthTransform = mouthTransform;
        this.noseMinify = noseMinify;
        this.teethWhiten = teethWhiten;
        this.eyeSpace = eyeSpace;
        this.noseElongate = noseElongate;
        this.eyeCorner = eyeCorner;
        this.filterEnum = filterEnum;
        this.filterVal = filterVal;
    }
}

package cn.tillusory.tiui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;

import cn.tillusory.sdk.TiSDKManager;
import cn.tillusory.sdk.bean.TiFilterEnum;
import cn.tillusory.sdk.bean.TiMakeup;
import cn.tillusory.tiui.R;
import cn.tillusory.tiui.custom.TiSharePreferences;
import cn.tillusory.tiui.model.RxBusAction;
import cn.tillusory.tiui.model.TiQuickBeautyVal;

/**
 * Created by Anko on 2019-09-06.
 * Copyright (c) 2018-2020 拓幻科技 - tillusory.cn. All rights reserved.
 */
public class TiBarView extends LinearLayout {

    private TiSDKManager tiSDKManager;

    private TextView tiBubbleTV;
    private TextView tiNumberTV;
    private SeekBar tiSeekBar;
    private View tiProgressV;
    private View tiMiddleV;
    private ImageView tiRenderEnableIV;

    private String currentAction = RxBusAction.ACTION_SKIN_WHITENING;
    private String currentBeautyAction = RxBusAction.ACTION_SKIN_WHITENING;
    private String currentFaceTrimAction = RxBusAction.ACTION_EYE_MAGNIFYING;
    private String currentQuickBeautyAction = RxBusAction.ACTION_STANDARD;

    private TiFilterEnum tiFilterEnum = TiFilterEnum.NO_FILTER;

    private TiMakeup tiMakeup = TiMakeup.NO_MAKEUP;
    private String tiBlusherName = "";
    private String tiEyelashName = "";
    private String tiEyebrowName = "";

    private SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            switch (currentAction) {

                case RxBusAction.ACTION_SKIN_WHITENING:
                    styleNormal(progress);
                    tiSDKManager.setSkinWhitening(progress);
                    break;

                case RxBusAction.ACTION_SKIN_BLEMISH_REMOVAL:
                    styleNormal(progress);
                    tiSDKManager.setSkinBlemishRemoval(progress);
                    break;

                case RxBusAction.ACTION_SKIN_TENDERNESS:
                    styleNormal(progress);
                    tiSDKManager.setSkinTenderness(progress);
                    break;

                case RxBusAction.ACTION_SKIN_SATURATION:
                    styleTransform(progress);
                    tiSDKManager.setSkinSaturation(progress - 50);
                    break;

                case RxBusAction.ACTION_SKIN_BRIGHTNESS:
                    styleTransform(progress);
                    tiSDKManager.setSkinBrightness(progress - 50);
                    break;

                case RxBusAction.ACTION_SKIN_SHARPNESS:
                    styleNormal(progress);
                    tiSDKManager.setSkinSharpness(progress);
                    break;

                case RxBusAction.ACTION_EYE_MAGNIFYING:
                    styleNormal(progress);
                    tiSDKManager.setEyeMagnifying(progress);
                    break;

                case RxBusAction.ACTION_CHIN_SLIMMING:
                    styleNormal(progress);
                    tiSDKManager.setChinSlimming(progress);
                    break;

                case RxBusAction.ACTION_JAW_TRANSFORMING:
                    styleTransform(progress);
                    tiSDKManager.setJawTransforming(progress - 50);
                    break;

                case RxBusAction.ACTION_FOREHEAD_TRANSFORMING:
                    styleTransform(progress);
                    tiSDKManager.setForeheadTransforming(progress - 50);
                    break;

                case RxBusAction.ACTION_MOUTH_TRANSFORMING:
                    styleTransform(progress);
                    tiSDKManager.setMouthTransforming(progress - 50);
                    break;

                case RxBusAction.ACTION_NOSE_MINIFYING:
                    styleTransform(progress);
                    tiSDKManager.setNoseMinifying(-(progress - 50));
                    break;

                case RxBusAction.ACTION_TEETH_WHITENING:
                    styleNormal(progress);
                    tiSDKManager.setTeethWhitening(progress);
                    break;

                case RxBusAction.ACTION_FACE_NARROWING:
                    styleNormal(progress);
                    tiSDKManager.setFaceNarrowing(progress);
                    break;
                case RxBusAction.ACTION_EYE_SPACING:
                    styleTransform(progress);
                    tiSDKManager.setEyeSpacing(progress - 50);
                    break;
                case RxBusAction.ACTION_NOSE_ELONGATING:
                    styleNormal(progress);
                    tiSDKManager.setNoseElongating(progress);
                    break;
                case RxBusAction.ACTION_EYE_CORNERS:
                    styleTransform(progress);
                    tiSDKManager.setEyeCorners(progress - 50);
                    break;

                case RxBusAction.ACTION_FILTER:
                    styleNormal(progress);
                    tiSDKManager.setFilterEnum(tiFilterEnum, progress);
                    break;

                case RxBusAction.ACTION_STANDARD:
                    styleNormal(progress);
                    TiSharePreferences.getInstance().putStandardVal(progress);
                    setQuickBeautyValue(TiQuickBeautyVal.STANDARD, progress);
                    break;
                case RxBusAction.ACTION_DELICATE:
                    styleNormal(progress);
                    TiSharePreferences.getInstance().putDelicateVal(progress);
                    setQuickBeautyValue(TiQuickBeautyVal.DELICATE, progress);
                    break;
                case RxBusAction.ACTION_CUTE:
                    styleNormal(progress);
                    TiSharePreferences.getInstance().putCuteVal(progress);
                    setQuickBeautyValue(TiQuickBeautyVal.CUTE, progress);
                    break;
                case RxBusAction.ACTION_CELEBRITY:
                    styleNormal(progress);
                    TiSharePreferences.getInstance().putCelebrityVal(progress);
                    setQuickBeautyValue(TiQuickBeautyVal.CELEBRITY, progress);
                    break;
                case RxBusAction.ACTION_NATURAL:
                    styleNormal(progress);
                    TiSharePreferences.getInstance().putNaturalVal(progress);
                    setQuickBeautyValue(TiQuickBeautyVal.NATURAL, progress);
                    break;

                //美妆
                case RxBusAction.ACTION_BLUSHER:
                    styleNormal(progress);
                    tiSDKManager.setBlusher(tiBlusherName, progress);
                    TiSharePreferences.getInstance().putBlusherVal(tiBlusherName, progress);
                    break;
                case RxBusAction.ACTION_EYELASH:
                    styleNormal(progress);
                    tiSDKManager.setEyeLash(tiEyelashName, progress);
                    TiSharePreferences.getInstance().putEyelashVal(tiEyelashName, progress);
                    break;
                case RxBusAction.ACTION_EYEBROW:
                    styleNormal(progress);
                    tiSDKManager.setEyeBrow(tiEyebrowName, progress);
                    TiSharePreferences.getInstance().putEyebrowVal(tiEyebrowName, progress);
                    break;
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            tiBubbleTV.setVisibility(VISIBLE);
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            tiBubbleTV.setVisibility(GONE);
        }
    };

    public TiBarView(Context context) {
        super(context);
    }

    public TiBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TiBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TiBarView init(TiSDKManager tiSDKManager) {
        this.tiSDKManager = tiSDKManager;

        RxBus.get().register(this);

        initView();

        initData();

        return this;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        RxBus.get().unregister(this);
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_ti_bar, this);

        tiBubbleTV = findViewById(R.id.tiBubbleTV);
        tiNumberTV = findViewById(R.id.tiNumberTV);
        tiSeekBar = findViewById(R.id.tiSeekBar);
        tiProgressV = findViewById(R.id.tiProgressV);
        tiMiddleV = findViewById(R.id.tiMiddleV);
        tiRenderEnableIV = findViewById(R.id.tiRenderEnableIV);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initData() {

        tiSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
        tiSeekBar.setProgress(0);

        tiRenderEnableIV.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        tiSDKManager.renderEnable(false);
                        return true;
                    case MotionEvent.ACTION_UP:
                        tiSDKManager.renderEnable(true);
                        return true;
                }
                return false;
            }
        });

    }

    private void styleNormal(int progress) {

        tiSeekBar.setSecondaryProgress(0);
        tiMiddleV.setVisibility(GONE);

        CharSequence percent = new StringBuilder().append(progress).append("%");

        tiNumberTV.setText(percent);

        float width = tiSeekBar.getWidth() - (getContext().getResources().getDisplayMetrics().density * 32 + 0.5f);

        tiBubbleTV.setText(percent);
        tiBubbleTV.setX(width / 100 * progress + (getContext().getResources().getDisplayMetrics().density * 1f + 0.5f));

        tiProgressV.setVisibility(GONE);
    }

    private void styleTransform(int progress) {

        tiSeekBar.setSecondaryProgress(50);
        tiMiddleV.setVisibility((progress > 48 && progress < 52) ? GONE : VISIBLE);

        CharSequence percent = new StringBuilder().append(progress - 50).append("%");

        tiNumberTV.setText(percent);

        float width = tiSeekBar.getWidth() - (getContext().getResources().getDisplayMetrics().density * 32 + 0.5f);

        tiBubbleTV.setText(percent);
        tiBubbleTV.setX(width / 100 * progress + (getContext().getResources().getDisplayMetrics().density * 1f + 0.5f));

        tiProgressV.setVisibility(VISIBLE);
        ViewGroup.LayoutParams layoutParams = tiProgressV.getLayoutParams();
        layoutParams.width = (int) (progress < 51 ? width / 100 * progress - (getContext().getResources().getDisplayMetrics().density * 5f + 0.5f) : width / 2);
        tiProgressV.setLayoutParams(layoutParams);

    }

    public void selectBeauty() {
        tiNumberTV.setVisibility(VISIBLE);
        tiSeekBar.setVisibility(VISIBLE);

        tiSeekBar.setEnabled(tiSDKManager.isBeautyEnable());

        busCurrentAction(currentBeautyAction);
    }

    public void selectFaceTrim() {
        tiNumberTV.setVisibility(VISIBLE);
        tiSeekBar.setVisibility(VISIBLE);

        tiSeekBar.setEnabled(tiSDKManager.isFaceTrimEnable());

        busCurrentAction(currentFaceTrimAction);
    }

    public void selectFilter() {
        tiNumberTV.setVisibility(VISIBLE);
        tiSeekBar.setVisibility(VISIBLE);

        tiSeekBar.setEnabled(true);

        busCurrentFilter(tiFilterEnum);
    }

    public void selectQuickBeauty() {
        tiNumberTV.setVisibility(VISIBLE);
        tiSeekBar.setVisibility(VISIBLE);

        tiSeekBar.setEnabled(true);

        busCurrentAction(currentQuickBeautyAction);
    }

    public void selectMakeupBlusher() {
        tiNumberTV.setVisibility(VISIBLE);
        tiSeekBar.setVisibility(VISIBLE);

        tiSeekBar.setEnabled(TiSharePreferences.getInstance().isMakeupEnable());

        busCurrentBlusher(tiBlusherName);
    }

    public void selectMakeupEyelash() {
        tiNumberTV.setVisibility(VISIBLE);
        tiSeekBar.setVisibility(VISIBLE);

        tiSeekBar.setEnabled(TiSharePreferences.getInstance().isMakeupEnable());

        busCurrentEyelash(tiEyelashName);
    }

    public void selectMakeupEyebrow() {
        tiNumberTV.setVisibility(VISIBLE);
        tiSeekBar.setVisibility(VISIBLE);

        tiSeekBar.setEnabled(TiSharePreferences.getInstance().isMakeupEnable());

        busCurrentEyebrow(tiEyebrowName);
    }

    public void hideSeekBar() {
        tiNumberTV.setVisibility(INVISIBLE);
        tiSeekBar.setVisibility(INVISIBLE);
        tiProgressV.setVisibility(GONE);
        tiMiddleV.setVisibility(GONE);
    }

    public void showNormalSeekBar() {
        tiNumberTV.setVisibility(VISIBLE);
        tiSeekBar.setVisibility(VISIBLE);
        tiProgressV.setVisibility(GONE);
        tiMiddleV.setVisibility(GONE);
    }

    @Subscribe(thread = EventThread.MAIN_THREAD, tags = @Tag(RxBusAction.ACTION_FILTER))
    public void busCurrentFilter(TiFilterEnum filterEnum) {

        currentAction = RxBusAction.ACTION_FILTER;
        this.tiFilterEnum = filterEnum;

        if (filterEnum == TiFilterEnum.NO_FILTER) {
            hideSeekBar();
            tiSDKManager.setFilterEnum(filterEnum, 0);
        } else {
            showNormalSeekBar();
            int progress = tiSDKManager.getFilterProgress(filterEnum);
//            this.tiFilterEnum = filterEnum;
            tiSeekBar.setProgress(progress);
            styleNormal(progress);
            tiSDKManager.setFilterEnum(tiFilterEnum, progress);
        }
    }

    @Subscribe(thread = EventThread.MAIN_THREAD, tags = @Tag(RxBusAction.ACTION_BLUSHER))
    public void busCurrentBlusher(String blusherName) {
        currentAction = RxBusAction.ACTION_BLUSHER;
//        this.tiMakeup = makeup;
        tiBlusherName = blusherName;

        if (tiBlusherName.equals(TiMakeup.NO_MAKEUP.getName())) {
            hideSeekBar();
            tiSDKManager.setBlusher(tiBlusherName, 0);
        } else {
            showNormalSeekBar();
            int progress = TiSharePreferences.getInstance().getBlusherVal(tiBlusherName);
            tiSeekBar.setProgress(progress);
            styleNormal(progress);
            tiSDKManager.setBlusher(tiBlusherName, progress);
        }
    }

    @Subscribe(thread = EventThread.MAIN_THREAD, tags = @Tag(RxBusAction.ACTION_EYELASH))
    public void busCurrentEyelash(String eyelashName) {
        currentAction = RxBusAction.ACTION_EYELASH;
//        this.tiMakeup = makeup;
        tiEyelashName = eyelashName;

        if (tiEyelashName.equals(TiMakeup.NO_MAKEUP.getName())) {
            hideSeekBar();
            tiSDKManager.setEyeLash(tiEyelashName, 0);
        } else {
            showNormalSeekBar();
            int progress = TiSharePreferences.getInstance().getEyelashVal(tiEyelashName);
            tiSeekBar.setProgress(progress);
            styleNormal(progress);
            tiSDKManager.setEyeLash(tiEyelashName, progress);
        }
    }

    @Subscribe(thread = EventThread.MAIN_THREAD, tags = @Tag(RxBusAction.ACTION_EYEBROW))
    public void busCurrentEyebrow(String eyebrowName) {
        currentAction = RxBusAction.ACTION_EYEBROW;
//        this.tiMakeup = makeup;
        tiEyebrowName = eyebrowName;

        if (tiEyebrowName.equals(TiMakeup.NO_MAKEUP.getName())) {
            hideSeekBar();
            tiSDKManager.setEyeBrow(tiEyebrowName, 0);
        } else {
            showNormalSeekBar();
            int progress = TiSharePreferences.getInstance().getEyebrowVal(tiEyebrowName);
            tiSeekBar.setProgress(progress);
            styleNormal(progress);
            tiSDKManager.setEyeBrow(tiEyebrowName, progress);
        }
    }

    @Subscribe(thread = EventThread.MAIN_THREAD)
    public void busCurrentAction(String currentAction) {
        this.currentAction = currentAction;
        switch (currentAction) {

            case RxBusAction.ACTION_SKIN_WHITENING:
                tiSeekBar.setProgress(tiSDKManager.getSkinWhitening());
                styleNormal(tiSDKManager.getSkinWhitening());
                currentBeautyAction = RxBusAction.ACTION_SKIN_WHITENING;
                break;

            case RxBusAction.ACTION_SKIN_BLEMISH_REMOVAL:
                tiSeekBar.setProgress(tiSDKManager.getSkinBlemishRemoval());
                styleNormal(tiSDKManager.getSkinBlemishRemoval());
                currentBeautyAction = RxBusAction.ACTION_SKIN_BLEMISH_REMOVAL;
                break;

            case RxBusAction.ACTION_SKIN_TENDERNESS:
                tiSeekBar.setProgress(tiSDKManager.getSkinTenderness());
                styleNormal(tiSDKManager.getSkinTenderness());
                currentBeautyAction = RxBusAction.ACTION_SKIN_TENDERNESS;
                break;

            case RxBusAction.ACTION_SKIN_SATURATION:
                tiSeekBar.setProgress(tiSDKManager.getSkinSaturation() + 50);
                styleTransform(tiSDKManager.getSkinSaturation() + 50);
                currentBeautyAction = RxBusAction.ACTION_SKIN_SATURATION;
                break;

            case RxBusAction.ACTION_SKIN_BRIGHTNESS:
                tiSeekBar.setProgress(tiSDKManager.getSkinBrightness() + 50);
                styleTransform(tiSDKManager.getSkinBrightness() + 50);
                currentBeautyAction = RxBusAction.ACTION_SKIN_BRIGHTNESS;
                break;

            case RxBusAction.ACTION_SKIN_SHARPNESS:
                tiSeekBar.setProgress(tiSDKManager.getSkinSharpness());
                styleNormal(tiSDKManager.getSkinSharpness());
                currentBeautyAction = RxBusAction.ACTION_SKIN_SHARPNESS;
                break;

            case RxBusAction.ACTION_EYE_MAGNIFYING:
                tiSeekBar.setProgress(tiSDKManager.getEyeMagnifying());
                styleNormal(tiSDKManager.getEyeMagnifying());
                currentFaceTrimAction = RxBusAction.ACTION_EYE_MAGNIFYING;
                break;

            case RxBusAction.ACTION_CHIN_SLIMMING:
                tiSeekBar.setProgress(tiSDKManager.getChinSlimming());
                styleNormal(tiSDKManager.getChinSlimming());
                currentFaceTrimAction = RxBusAction.ACTION_CHIN_SLIMMING;
                break;

            case RxBusAction.ACTION_JAW_TRANSFORMING:
                tiSeekBar.setProgress(tiSDKManager.getJawTransforming() + 50);
                styleTransform(tiSDKManager.getJawTransforming() + 50);
                currentFaceTrimAction = RxBusAction.ACTION_JAW_TRANSFORMING;
                break;

            case RxBusAction.ACTION_FOREHEAD_TRANSFORMING:
                tiSeekBar.setProgress(tiSDKManager.getForeheadTransforming() + 50);
                styleTransform(tiSDKManager.getForeheadTransforming() + 50);
                currentFaceTrimAction = RxBusAction.ACTION_FOREHEAD_TRANSFORMING;
                break;

            case RxBusAction.ACTION_MOUTH_TRANSFORMING:
                tiSeekBar.setProgress(tiSDKManager.getMouthTransforming() + 50);
                styleTransform(tiSDKManager.getMouthTransforming() + 50);
                currentFaceTrimAction = RxBusAction.ACTION_MOUTH_TRANSFORMING;
                break;

            case RxBusAction.ACTION_NOSE_MINIFYING:
                tiSeekBar.setProgress(-(tiSDKManager.getNoseMinifying() - 50));
                styleTransform(-(tiSDKManager.getNoseMinifying() - 50));
                currentFaceTrimAction = RxBusAction.ACTION_NOSE_MINIFYING;
                break;

            case RxBusAction.ACTION_TEETH_WHITENING:
                tiSeekBar.setProgress(tiSDKManager.getTeethWhitening());
                styleNormal(tiSDKManager.getTeethWhitening());
                currentFaceTrimAction = RxBusAction.ACTION_TEETH_WHITENING;
                break;

            case RxBusAction.ACTION_FACE_NARROWING:
                tiSeekBar.setProgress(tiSDKManager.getFaceNarrowing());
                styleNormal(tiSDKManager.getFaceNarrowing());
                currentFaceTrimAction = RxBusAction.ACTION_FACE_NARROWING;
                break;

            case RxBusAction.ACTION_EYE_SPACING:
                tiSeekBar.setProgress(tiSDKManager.getEyeSpacing() + 50);
                styleTransform(tiSDKManager.getEyeSpacing() + 50);
                currentFaceTrimAction = RxBusAction.ACTION_EYE_SPACING;
                break;

            case RxBusAction.ACTION_NOSE_ELONGATING:
                tiSeekBar.setProgress(tiSDKManager.getNoseElongating());
                styleNormal(tiSDKManager.getNoseElongating());
                currentFaceTrimAction = RxBusAction.ACTION_NOSE_ELONGATING;
                break;

            case RxBusAction.ACTION_EYE_CORNERS:
                tiSeekBar.setProgress(tiSDKManager.getEyeCorners() + 50);
                styleTransform(tiSDKManager.getEyeCorners() + 50);
                currentFaceTrimAction = RxBusAction.ACTION_EYE_CORNERS;
                break;

            case RxBusAction.ACTION_STANDARD:
                setQuickBeautyValue(TiQuickBeautyVal.STANDARD, TiSharePreferences.getInstance().getStandardVal());
                tiSeekBar.setProgress(TiSharePreferences.getInstance().getStandardVal());
                styleNormal(TiSharePreferences.getInstance().getStandardVal());
                currentQuickBeautyAction = RxBusAction.ACTION_STANDARD;
                break;
            case RxBusAction.ACTION_DELICATE:
                setQuickBeautyValue(TiQuickBeautyVal.DELICATE, TiSharePreferences.getInstance().getDelicateVal());
                tiSeekBar.setProgress(TiSharePreferences.getInstance().getDelicateVal());
                styleNormal(TiSharePreferences.getInstance().getDelicateVal());
                currentQuickBeautyAction = RxBusAction.ACTION_DELICATE;
                break;
            case RxBusAction.ACTION_CUTE:
                setQuickBeautyValue(TiQuickBeautyVal.CUTE, TiSharePreferences.getInstance().getCuteVal());
                tiSeekBar.setProgress(TiSharePreferences.getInstance().getCuteVal());
                styleNormal(TiSharePreferences.getInstance().getCuteVal());
                currentQuickBeautyAction = RxBusAction.ACTION_CUTE;
                break;
            case RxBusAction.ACTION_CELEBRITY:
                setQuickBeautyValue(TiQuickBeautyVal.CELEBRITY, TiSharePreferences.getInstance().getCelebrityVal());
                tiSeekBar.setProgress(TiSharePreferences.getInstance().getCelebrityVal());
                styleNormal(TiSharePreferences.getInstance().getCelebrityVal());
                currentQuickBeautyAction = RxBusAction.ACTION_CELEBRITY;
                break;
            case RxBusAction.ACTION_NATURAL:
                setQuickBeautyValue(TiQuickBeautyVal.NATURAL, TiSharePreferences.getInstance().getNaturalVal());
                tiSeekBar.setProgress(TiSharePreferences.getInstance().getNaturalVal());
                styleNormal(TiSharePreferences.getInstance().getNaturalVal());
                currentQuickBeautyAction = RxBusAction.ACTION_NATURAL;
                break;
        }
    }

    private void setQuickBeautyValue(TiQuickBeautyVal quickBeautyVal, int progress) {
        float ratio = progress / 100f;
        tiSDKManager.setBeautyEnable(true);
        tiSDKManager.setSkinWhitening((int) (quickBeautyVal.whitening * ratio));
        tiSDKManager.setSkinBlemishRemoval((int) (quickBeautyVal.blemishRemoval * ratio));
        tiSDKManager.setSkinBrightness((int) (quickBeautyVal.brightness * ratio));
        tiSDKManager.setSkinTenderness((int) (quickBeautyVal.tenderness * ratio));
        tiSDKManager.setSkinSharpness((int) (quickBeautyVal.sharpness * ratio));

        tiSDKManager.setFaceTrimEnable(true);
        tiSDKManager.setEyeMagnifying((int) (quickBeautyVal.eyeMagnify * ratio));
        tiSDKManager.setChinSlimming((int) (quickBeautyVal.chinSlim * ratio));   //瘦脸
        tiSDKManager.setFaceNarrowing((int) (quickBeautyVal.faceNarrow * ratio));  //窄脸
        tiSDKManager.setJawTransforming((int) (quickBeautyVal.jawTransform * ratio));
        tiSDKManager.setForeheadTransforming((int) (quickBeautyVal.foreheadTransform * ratio));
        tiSDKManager.setMouthTransforming((int) (quickBeautyVal.mouthTransform * ratio));
        tiSDKManager.setNoseMinifying((int) (quickBeautyVal.noseMinify * ratio));
        tiSDKManager.setTeethWhitening((int) (quickBeautyVal.teethWhiten * ratio));
        tiSDKManager.setEyeSpacing((int)(quickBeautyVal.eyeSpace * ratio));
        tiSDKManager.setNoseElongating((int) (quickBeautyVal.noseElongate * ratio));
        tiSDKManager.setEyeCorners((int)(quickBeautyVal.eyeCorner * ratio));

        tiSDKManager.setFilterEnum(quickBeautyVal.filterEnum, (int) (quickBeautyVal.filterVal * ratio));
    }
}


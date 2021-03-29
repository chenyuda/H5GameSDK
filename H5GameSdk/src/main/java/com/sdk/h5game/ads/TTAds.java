/*******************************************************************************
 * Copyright (c) 2021 by Cyd Corporation all right reserved.                
 * 2021-03-22                                                     
 *
 *******************************************************************************/

package com.sdk.h5game.ads;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.bytedance.sdk.openadsdk.TTRewardVideoAd;
import com.sdk.h5game.manager.ConfigManager;
import com.tendcloud.tenddata.TCAgent;

import java.util.List;

/**
 * ================================================
 * 名 称: TTAds
 * 描 述: TODO
 * 日 期: 2021-03-22 15:41
 * 作 者: cyd
 * 邮 箱: cyd@woso.cn
 * 公 司: 我搜网络
 * ================================================
 * @author cyd
 */
public class TTAds implements Ads{

    private Context mContext;

    private TTAdNative mTTAdNative;

    private TTNativeExpressAd mTTAd;

    private TTRewardVideoAd mttRewardVideoAd;

    private static final int BANNER_TIME = 30*1000;

    //视频是否加载完成
    private boolean mIsLoaded = false;

    public TTAds(Context context) {
        mContext = context;
        mTTAdNative = TTAdSdk.getAdManager().createAdNative(context);
        TTAdSdk.getAdManager().requestPermissionIfNecessary(context);

    }

    @Override
    public void requestBannerAd(final ViewGroup viewGroup,String postId) {
        viewGroup.removeAllViews();
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(postId)
                .setSupportDeepLink(true)
                .setAdCount(1)
                .setExpressViewAcceptedSize(320,50)
                .build();
        TCAgent.onEvent(mContext,"头条横幅广告请求次数");
        TCAgent.onEvent(mContext,"横幅广告请求次数");
        mTTAdNative.loadBannerExpressAd(adSlot, new TTAdNative.NativeExpressAdListener() {
            @Override
            public void onError(int i, String s) {
                if(ConfigManager.isOpenDeBug()){
                    Log.e("H5GameSDK","TTBannerAd load error : " + i + ", " + s);
                }
                TCAgent.onEvent(mContext,"头条横幅广告请求失败次数");
                TCAgent.onEvent(mContext,"横幅广告请求失败次数");
            }

            @Override
            public void onNativeExpressAdLoad(List<TTNativeExpressAd> ads) {
                if (ads == null || ads.size() == 0) {
                    TCAgent.onEvent(mContext,"头条横幅广告无填充次数");
                    TCAgent.onEvent(mContext,"横幅广告无填充次数");
                    return;
                }
                TCAgent.onEvent(mContext,"头条横幅广告请求成功次数");
                TCAgent.onEvent(mContext,"横幅广告请求成功次数");
                mTTAd = ads.get(0);
                mTTAd.setSlideIntervalTime(BANNER_TIME);
                bindAdListener(mTTAd,viewGroup);

            }
        });
    }

    private void bindAdListener(TTNativeExpressAd ad, final ViewGroup viewGroup) {
        ad.setExpressInteractionListener(new TTNativeExpressAd.ExpressAdInteractionListener() {
            @Override
            public void onAdClicked(View view, int type) {
                TCAgent.onEvent(mContext,"头条横幅广告点击次数");
                TCAgent.onEvent(mContext,"横幅广告点击次数");
            }

            @Override
            public void onAdShow(View view, int type) {
                TCAgent.onEvent(mContext,"头条横幅广告展示次数");
                TCAgent.onEvent(mContext,"横幅广告展示次数");
            }

            @Override
            public void onRenderFail(View view, String msg, int code) {
                if(ConfigManager.isOpenDeBug()){
                    Log.e("H5GameSDK", "TTBannerAd render fail:" + code + "," + msg);
                }
                TCAgent.onEvent(mContext,"头条横幅广告渲染失败次数");
            }

            @Override
            public void onRenderSuccess(View view, float width, float height) {
                if(ConfigManager.isOpenDeBug()){
                    Log.e("H5GameSDK", "TTBannerAd render success:");
                }
                //返回view的宽高 单位 dp
                viewGroup.removeAllViews();
                viewGroup.addView(view);
                viewGroup.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void requestInterstitialAd(String postId, final InterstitialAdListener adListener) {
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(postId)
                .setSupportDeepLink(true)
                .setAdCount(1)
                .setExpressViewAcceptedSize(600,600)
                .build();
        TCAgent.onEvent(mContext,"头条插屏广告请求次数");
        TCAgent.onEvent(mContext,"插屏广告请求次数");
        mTTAdNative.loadInteractionExpressAd(adSlot, new TTAdNative.NativeExpressAdListener() {
            @Override
            public void onError(int i, String s) {
                if(ConfigManager.isOpenDeBug()){
                    Log.e("H5GameSDK","TTInterstitialAd load error : " + i + ", " + s);
                }
                adListener.onAdLoadError();
                TCAgent.onEvent(mContext,"头条插屏广告请求失败次数");
                TCAgent.onEvent(mContext,"插屏广告请求失败次数");
            }

            @Override
            public void onNativeExpressAdLoad(List<TTNativeExpressAd> ads) {
                if (ads == null || ads.size() == 0) {
                    adListener.onAdLoadError();
                    TCAgent.onEvent(mContext,"头条插屏广告无填充次数");
                    TCAgent.onEvent(mContext,"插屏广告无填充次数");
                    return;
                }

                TCAgent.onEvent(mContext,"头条插屏广告请求成功次数");
                TCAgent.onEvent(mContext,"插屏广告请求成功次数");
                mTTAd = ads.get(0);
                adListener.onAdLoad();
                bindAdListener(mTTAd,adListener);
            }
        });
    }

    @Override
    public void showInterstitialAd() {
        if (mTTAd != null) {
            mTTAd.render();
        }
    }

    private void bindAdListener(TTNativeExpressAd ad, final InterstitialAdListener adListener) {
        ad.setExpressInteractionListener(new TTNativeExpressAd.AdInteractionListener() {
            @Override
            public void onAdDismiss() {
                adListener.onAdClose();
                TCAgent.onEvent(mContext,"头条插屏广告关闭次数");
                TCAgent.onEvent(mContext,"插屏广告关闭次数");
            }

            @Override
            public void onAdClicked(View view, int type) {
                adListener.onAdClick();
                TCAgent.onEvent(mContext,"头条插屏广告点击次数");
                TCAgent.onEvent(mContext,"插屏广告点击次数");
            }

            @Override
            public void onAdShow(View view, int type) {
                adListener.onAdShow();
                TCAgent.onEvent(mContext,"头条插屏广告展示次数");
                TCAgent.onEvent(mContext,"插屏广告展示次数");
            }

            @Override
            public void onRenderFail(View view, String msg, int code) {
                if(ConfigManager.isOpenDeBug()){
                    Log.e("H5GameSDK", "TTInterstitialAd render fail:" + code + "," + msg);
                }
                adListener.onAdShowError();
                TCAgent.onEvent(mContext,"头条插屏广告渲染失败次数");
            }

            @Override
            public void onRenderSuccess(View view, float width, float height) {
                if(ConfigManager.isOpenDeBug()){
                    Log.e("H5GameSDK", "TTInterstitialAd render success:");
                }
                mTTAd.showInteractionExpressAd((Activity) mContext);

            }
        });
    }

    @Override
    public void requestRewardAd(String postId, final RewardListener rewardListener) {
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(postId)
                .setExpressViewAcceptedSize(500,500)
                .setOrientation(TTAdConstant.VERTICAL)
                .build();
        TCAgent.onEvent(mContext,"头条激励视频广告请求次数");
        TCAgent.onEvent(mContext,"激励视频广告请求次数");
        mTTAdNative.loadRewardVideoAd(adSlot, new TTAdNative.RewardVideoAdListener() {
            @Override
            public void onError(int i, String s) {
                if(ConfigManager.isOpenDeBug()){
                    Log.e("H5GameSDK","TTRewardAd load error : " + i + ", " + s);
                }
                rewardListener.onLoadError();
                TCAgent.onEvent(mContext,"头条激励视频广告请求失败次数");
                TCAgent.onEvent(mContext,"激励视频广告请求失败次数");
            }

            @Override
            public void onRewardVideoAdLoad(TTRewardVideoAd ttRewardVideoAd) {
                mIsLoaded = false;
                mttRewardVideoAd = ttRewardVideoAd;
                mttRewardVideoAd.setRewardAdInteractionListener(new TTRewardVideoAd.RewardAdInteractionListener() {
                    @Override
                    public void onAdShow() {
                        rewardListener.onRewardVideoStartPlay();
                        TCAgent.onEvent(mContext,"头条激励视频广告播放次数");
                        TCAgent.onEvent(mContext,"激励视频广告播放次数");
                    }

                    @Override
                    public void onAdVideoBarClick() {
                        rewardListener.onRewardAdClick();
                        TCAgent.onEvent(mContext,"头条激励视频广告点击次数");
                        TCAgent.onEvent(mContext,"激励视频广告点击次数");
                    }

                    @Override
                    public void onAdClose() {
                        rewardListener.onRewardAdClose();
                        TCAgent.onEvent(mContext,"头条激励视频广告关闭次数");
                        TCAgent.onEvent(mContext,"激励视频广告关闭次数");
                    }

                    @Override
                    public void onVideoComplete() {
                        rewardListener.onRewardAdPlayComplete();
                        TCAgent.onEvent(mContext,"头条激励视频广告播放完成次数");
                        TCAgent.onEvent(mContext,"激励视频广告播放完成次数");
                    }

                    @Override
                    public void onVideoError() {
                        rewardListener.onVideoPlayError();
                        TCAgent.onEvent(mContext,"头条激励视频广告播放错误次数");
                        TCAgent.onEvent(mContext,"激励视频广告播放错误次数");
                    }

                    @Override
                    public void onRewardVerify(boolean b, int i, String s, int i1, String s1) {
                        TCAgent.onEvent(mContext,"头条激励视频广告奖励次数");
                        TCAgent.onEvent(mContext,"激励视频广告奖励次数");
                        rewardListener.onReward();
                    }

                    @Override
                    public void onSkippedVideo() {
                        rewardListener.onRewardAdSkip();
                        TCAgent.onEvent(mContext,"头条激励视频广告播放跳过次数");
                        TCAgent.onEvent(mContext,"激励视频广告播放跳过次数");
                    }
                });
            }

            @Override
            public void onRewardVideoCached() {
                mIsLoaded = true;
                rewardListener.onRewardVideoCached();
                TCAgent.onEvent(mContext,"头条激励视频广告请求成功次数");
                TCAgent.onEvent(mContext,"激励视频广告请求成功次数");
            }
        });
    }

    @Override
    public void showRewardAd(){
        if (mttRewardVideoAd != null&&mIsLoaded) {
            //step6:在获取到广告后展示,强烈建议在onRewardVideoCached回调后，展示广告，提升播放体验
            //该方法直接展示广告
//                    mttRewardVideoAd.showRewardVideoAd(RewardVideoActivity.this);

            //展示广告，并传入广告展示的场景
            mttRewardVideoAd.showRewardVideoAd((Activity) mContext, TTAdConstant.RitScenes.CUSTOMIZE_SCENES, "game");
            mttRewardVideoAd = null;
        }
    }

    @Override
    public void destroyAd() {
        if (mTTAd != null) {
            mTTAd.destroy();
        }

        if (mttRewardVideoAd != null) {
            mttRewardVideoAd = null;
        }
    }
}

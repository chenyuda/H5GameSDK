/*******************************************************************************
 * Copyright (c) 2021 by Cyd Corporation all right reserved.                
 * 2021-03-23                                                     
 *
 *******************************************************************************/

package com.sdk.h5game.js;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.sdk.h5game.ads.InterstitialAdListener;
import com.sdk.h5game.ads.RewardListener;
import com.sdk.h5game.agentweb.AgentWeb;
import com.sdk.h5game.manager.InterstitialAdManager;
import com.sdk.h5game.manager.RewardAdManager;
import com.tendcloud.tenddata.TCAgent;

/**
 * ================================================
 * 名 称: JsInterface
 * 描 述: TODO
 * 日 期: 2021-03-23 15:40
 * 作 者: cyd
 * 邮 箱: cyd@woso.cn
 * 公 司: 我搜网络
 * ================================================
 * @author cyd
 */
public class JsInterface {

    private Context mContext;

    private AgentWeb mAgentWeb;

    public JsInterface(Context mContext, AgentWeb mAgentWeb) {
        this.mContext = mContext;
        this.mAgentWeb = mAgentWeb;
    }

    @JavascriptInterface
    public void init(){
    }

    @JavascriptInterface
    public void reloadBannerAds(String client){

    }

    @JavascriptInterface
    public void reloadRewardedAds(String client){
        TCAgent.setGlobalKV("开发者", client);
        RewardAdManager.getInstance().load(mContext, new RewardListener() {
            @Override
            public void onRewardVideoCached() {
                mAgentWeb.getJsAccessEntrace().quickCallJs("adSdk.isReadyRewardedAds","true");
            }

            @Override
            public void onLoadError() {
                mAgentWeb.getJsAccessEntrace().quickCallJs("adSdk.isReadyRewardedAds","false");
            }

            @Override
            public void onRewardVideoStartPlay() {
                mAgentWeb.getJsAccessEntrace().quickCallJs("adSdk.adStartedPlay");
            }

            @Override
            public void onRewardAdClick() {
                mAgentWeb.getJsAccessEntrace().quickCallJs("adSdk.adClicked");
            }

            @Override
            public void onRewardAdSkip() {
                mAgentWeb.getJsAccessEntrace().quickCallJs("adSdk.skipped");
            }

            @Override
            public void onRewardAdPlayComplete() {
            }

            @Override
            public void onVideoPlayError() {
                mAgentWeb.getJsAccessEntrace().quickCallJs("adSdk.admobRewardedError");
            }

            @Override
            public void onReward() {
                mAgentWeb.getJsAccessEntrace().quickCallJs("adSdk.completeRewarded");
            }

            @Override
            public void onRewardAdClose() {
                RewardAdManager.getInstance().destroyAd();
            }
        });
    }

    @JavascriptInterface
    public void playRewarded(String client){
        RewardAdManager.getInstance().show();
    }

    @JavascriptInterface
    public void reloadInterstitial(String client){
        TCAgent.setGlobalKV("开发者", client);
        InterstitialAdManager.getInstance().load(mContext, new InterstitialAdListener() {
            @Override
            public void onAdLoadError() {
                mAgentWeb.getJsAccessEntrace().quickCallJs("adSdk.isLoadedInterstitialAds","false");
            }

            @Override
            public void onAdLoad() {
                mAgentWeb.getJsAccessEntrace().quickCallJs("adSdk.isLoadedInterstitialAds","true");
            }

            @Override
            public void onAdShow() {
                mAgentWeb.getJsAccessEntrace().quickCallJs("adSdk.adStartedPlay");
            }

            @Override
            public void onAdClick() {
                mAgentWeb.getJsAccessEntrace().quickCallJs("adSdk.adClicked");
            }

            @Override
            public void onAdClose() {
                mAgentWeb.getJsAccessEntrace().quickCallJs("adSdk.closeInserstitial");
            }

            @Override
            public void onAdShowError() {
                mAgentWeb.getJsAccessEntrace().quickCallJs("adSdk.admobInterstitialError");
            }
        });
    }

    @JavascriptInterface
    public void playInterstitialAds(String client){
        InterstitialAdManager.getInstance().show();
    }

}

/*******************************************************************************
 * Copyright (c) 2021 by Cyd Corporation all right reserved.                
 * 2021-03-23                                                     
 *
 *******************************************************************************/

package com.sdk.h5game.manager;

import android.content.Context;
import android.util.Log;

import com.sdk.h5game.ads.InterstitialAdListener;
import com.sdk.h5game.ads.TTAds;

/**
 * ================================================
 * 名 称: InterstitialAdManager
 * 描 述: TODO
 * 日 期: 2021-03-23 10:54
 * 作 者: cyd
 * 邮 箱: cyd@woso.cn
 * 公 司: 我搜网络
 * ================================================
 * @author cyd
 */
public class InterstitialAdManager extends AdManager {

    private static InterstitialAdManager mInterstitialAdManager;

    private InterstitialAdManager(){}

    public static InterstitialAdManager getInstance(){
        if(mInterstitialAdManager == null){
            mInterstitialAdManager = new InterstitialAdManager();
        }

        return mInterstitialAdManager;
    }

    public void load(Context context, InterstitialAdListener adListener){
        if(ConfigManager.getAdConfig().containsKey(AD_PLAT_TT)){
            String postId = ConfigManager.getAdConfig().get(AD_PLAT_TT).get(AD_TYPE_CP);
            Log.e("TGA","postID="+postId);
            ads = new TTAds(context);
            ads.requestInterstitialAd(postId,adListener);
        }else if(ConfigManager.getAdConfig().containsKey(AD_PLAT_GDT)){
            String postId = ConfigManager.getAdConfig().get(AD_PLAT_GDT).get(AD_TYPE_CP);
        }
    }

    public void show(){
        if(ads != null){
            ads.showInterstitialAd();
        }
    }
}

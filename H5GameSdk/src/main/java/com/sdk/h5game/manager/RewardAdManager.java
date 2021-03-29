/*******************************************************************************
 * Copyright (c) 2021 by Cyd Corporation all right reserved.                
 * 2021-03-23                                                     
 *
 *******************************************************************************/

package com.sdk.h5game.manager;

import android.content.Context;

import com.sdk.h5game.ads.RewardListener;
import com.sdk.h5game.ads.TTAds;

/**
 * ================================================
 * 名 称: RewardAdManager
 * 描 述: TODO
 * 日 期: 2021-03-23 10:58
 * 作 者: cyd
 * 邮 箱: cyd@woso.cn
 * 公 司: 我搜网络
 * ================================================
 * @author cyd
 */
public class RewardAdManager extends AdManager {

    private static RewardAdManager rewardAdManager;

    private RewardAdManager(){}

    public static RewardAdManager getInstance(){
        if(rewardAdManager == null){
            rewardAdManager = new RewardAdManager();
        }
        return rewardAdManager;
    }

    public void load(Context context, RewardListener rewardListener){
        if(ConfigManager.getAdConfig().containsKey(AD_PLAT_TT)){
            String postId = ConfigManager.getAdConfig().get(AD_PLAT_TT).get(AD_TYPE_REWARD);
            ads = new TTAds(context);
            ads.requestRewardAd(postId,rewardListener);
        }else if(ConfigManager.getAdConfig().containsKey(AD_PLAT_GDT)){
            String postId = ConfigManager.getAdConfig().get(AD_PLAT_GDT).get(AD_TYPE_REWARD);
        }
    }

    public void show(){
        if(ads != null){
            ads.showRewardAd();
        }
    }
}

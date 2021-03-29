/*******************************************************************************
 * Copyright (c) 2021 by Cyd Corporation all right reserved.                
 * 2021-03-22                                                     
 *
 *******************************************************************************/

package com.sdk.h5game.ads;

import android.view.ViewGroup;

/**
 * ================================================
 * 名 称: Ads
 * 描 述: TODO
 * 日 期: 2021-03-22 15:43
 * 作 者: cyd
 * 邮 箱: cyd@woso.cn
 * 公 司: 我搜网络
 * ================================================
 * @author cyd
 */
public interface Ads {

    /**
      * @description: TODO
      * @date: 2021/3/22 0022 16:56
      * @author: cyd
      * @param: [context, viewGroup, postId]
      * @return: void
      */
    void requestBannerAd(ViewGroup viewGroup, String postId);

    /**
      * @description: 插屏广告请求
      * @date: 2021/3/22 0022 15:49
      * @author: cyd
      * @param: []
      * @return: void
      */
    void requestInterstitialAd(String postId, InterstitialAdListener adListener);

    void showInterstitialAd();

    void showRewardAd();

    /**
      * @description: 激励视频广告请求
      * @date: 2021/3/22 0022 15:49
      * @author: cyd
      * @param: []
      * @return: void
      */
    void requestRewardAd(String postId, RewardListener rewardListener);

    /**
      * @description: 广告销毁
      * @date: 2021/3/22 0022 16:07
      * @author: cyd
      * @param: []
      * @return: void
      */
    void destroyAd();
}

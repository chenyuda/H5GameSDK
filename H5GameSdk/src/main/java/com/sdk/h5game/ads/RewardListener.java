/*******************************************************************************
 * Copyright (c) 2021 by Cyd Corporation all right reserved.                
 * 2021-03-22                                                     
 *
 *******************************************************************************/

package com.sdk.h5game.ads;

/**
 * ================================================
 * 名 称: RewardListener
 * 描 述: TODO
 * 日 期: 2021-03-22 17:56
 * 作 者: cyd
 * 邮 箱: cyd@woso.cn
 * 公 司: 我搜网络
 * ================================================
 * @author cyd
 */
public interface RewardListener {

    void onRewardVideoCached();
    void onLoadError();
    void onRewardVideoStartPlay();
    void onRewardAdClick();
    void onRewardAdSkip();
    void onRewardAdPlayComplete();
    void onVideoPlayError();
    void onReward();
    void onRewardAdClose();
}

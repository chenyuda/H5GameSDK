/*******************************************************************************
 * Copyright (c) 2021 by Cyd Corporation all right reserved.                
 * 2021-03-23                                                     
 *
 *******************************************************************************/

package com.sdk.h5game.ads;

/**
 * ================================================
 * 名 称: InterstitialAdListener
 * 描 述: TODO
 * 日 期: 2021-03-23 15:51
 * 作 者: cyd
 * 邮 箱: cyd@woso.cn
 * 公 司: 我搜网络
 * ================================================
 */
public interface InterstitialAdListener {

    void onAdLoadError();
    void onAdLoad();
    void onAdShow();
    void onAdClick();
    void onAdClose();
    void onAdShowError();
}

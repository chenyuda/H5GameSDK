/*******************************************************************************
 * Copyright (c) 2021 by Cyd Corporation all right reserved.                
 * 2021-03-23                                                     
 *
 *******************************************************************************/

package com.sdk.h5game.manager;

import android.content.Context;
import android.view.ViewGroup;

import com.sdk.h5game.ads.TTAds;

/**
 * ================================================
 * 名 称: BannerAdManager
 * 描 述: TODO
 * 日 期: 2021-03-23 10:40
 * 作 者: cyd
 * 邮 箱: cyd@woso.cn
 * 公 司: 我搜网络
 * ================================================
 * @author cyd
 */
public class BannerAdManager extends AdManager{

    public void load(Context context, ViewGroup viewGroup){
        if(ConfigManager.getAdConfig().containsKey(AD_PLAT_TT)){
            String postId = ConfigManager.getAdConfig().get(AD_PLAT_TT).get(AD_TYPE_BANNER);
            ads = new TTAds(context);
            ads.requestBannerAd(viewGroup,postId);
        }else if(ConfigManager.getAdConfig().containsKey(AD_PLAT_GDT)){
            String postId = ConfigManager.getAdConfig().get(AD_PLAT_GDT).get(AD_TYPE_BANNER);
        }
    }

}

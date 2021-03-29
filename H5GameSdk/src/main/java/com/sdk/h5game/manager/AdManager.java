/*******************************************************************************
 * Copyright (c) 2021 by Cyd Corporation all right reserved.                
 * 2021-03-22                                                     
 *
 *******************************************************************************/

package com.sdk.h5game.manager;

import com.sdk.h5game.ads.Ads;

/**
 * ================================================
 * 名 称: AdManager
 * 描 述: TODO
 * 日 期: 2021-03-22 18:02
 * 作 者: cyd
 * 邮 箱: cyd@woso.cn
 * 公 司: 我搜网络
 * ================================================
 * @author cyd
 */
public class AdManager {

    protected static final int AD_PLAT_TT = 1;

    protected static final int AD_PLAT_GDT = 2;

    protected static final int AD_TYPE_BANNER = 1;

    protected static final int AD_TYPE_CP = 2;

    protected static final int AD_TYPE_REWARD = 3;

    protected Ads ads;

    public void destroyAd(){
        if(ads != null){
            ads.destroyAd();
        }
    }


}

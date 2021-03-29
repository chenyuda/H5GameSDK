/*******************************************************************************
 * Copyright (c) 2021 by Cyd Corporation all right reserved.                
 * 2021-03-23                                                     
 *
 *******************************************************************************/

package com.sdk.app2;

import android.app.Application;

import com.sdk.h5game.H5Game;


/**
 * ================================================
 * 名 称: MyApplication
 * 描 述: TODO
 * 日 期: 2021-03-23 17:04
 * 作 者: cyd
 * 邮 箱: cyd@woso.cn
 * 公 司: 我搜网络
 * ================================================
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        H5Game.init(this,"221","gamesdk");
        H5Game.openDeBug();
    }
}

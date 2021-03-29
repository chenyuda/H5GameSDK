/*******************************************************************************
 * Copyright (c) 2021 by Cyd Corporation all right reserved.                
 * 2021-03-18                                                     
 *
 *******************************************************************************/

package com.sdk.h5game;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.sdk.h5game.manager.ConfigManager;

/**
 * ================================================
 * 名 称: H5Game
 * 描 述: TODO
 * 日 期: 2021-03-18 15:20
 * 作 者: cyd
 * 邮 箱: cyd@woso.cn
 * 公 司: 我搜网络
 * ================================================
 * @author cyd
 */
public class H5Game {

    public static void init(Application application,String channelId,String appName){
        ConfigManager.init(application,channelId,appName);
    }

    public static void start(Context context){
        context.startActivity(new Intent(context, Game.class));
    }

    public static void setShowToolbar(boolean isShow){
        ConfigManager.setShowToolbar(isShow);
    }

    public static void openDeBug(){
        ConfigManager.openBug();
    }

}

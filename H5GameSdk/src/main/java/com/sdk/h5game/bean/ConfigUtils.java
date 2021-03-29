/*******************************************************************************
 * Copyright (c) 2021 by Cyd Corporation all right reserved.                
 * 2021-03-22                                                     
 *
 *******************************************************************************/

package com.sdk.h5game.bean;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * 名 称: ConfigUtils
 * 描 述: TODO
 * 日 期: 2021-03-22 11:12
 * 作 者: cyd
 * 邮 箱: cyd@woso.cn
 * 公 司: 我搜网络
 * ================================================
 * @author cyd
 */
public class ConfigUtils {

    private static final String SP_NAME = "config";
    private static final String SP_DATA = "configData";
    private static final String SP_TIME = "configDataTime";

    private static final long TEMP = 1000*60*60*24;

    public static ConfigBean getConfigBean(Context context,String channelId){

        SharedPreferences sharedPreferences = context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
        String configData = sharedPreferences.getString(SP_DATA+"_"+channelId,"");
        long time = sharedPreferences.getLong(SP_TIME+"_"+channelId,0);
        if(System.currentTimeMillis() - time >= TEMP){
            return parseData("");
        }else {
            return parseData(configData);
        }
    }

    public static ConfigBean parseData(String data){
        ConfigBean configBean = ConfigBean.getInstance();
        if(TextUtils.isEmpty(data)){
            return configBean;
        }
        try {
            JSONObject jsonObject = new JSONObject(data);
            int code = jsonObject.optInt("code",0);
            String msg = jsonObject.optString("msg","");
            configBean.setCode(code);
            configBean.setMsg(msg);
            if(code == 0){
                return configBean;
            }
            String doMainName = jsonObject.optString("domain","");
            String channelName = jsonObject.optString("item","");
            List<ConfigBean.ConfigData> dataList = new ArrayList<>();
            JSONArray jsonArray = jsonObject.optJSONArray("data");
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject object = jsonArray.optJSONObject(i);
                int sdkType = object.optInt("sdktype",0);
                int sdkPlat = object.optInt("sdkplat",0);
                String postId = object.optString("sdkId","");
                ConfigBean.ConfigData configData = new ConfigBean.ConfigData();
                configData.setSdkType(sdkType);
                configData.setSdkPlat(sdkPlat);
                configData.setPostId(postId);
                dataList.add(configData);
            }

            configBean.setDoMainName(doMainName);
            configBean.setChannelName(channelName);
            configBean.setConfigDataList(dataList);

            return configBean;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return configBean;
    }

    public static void saveConfigData(Context context,String channelId,String data){
        if(TextUtils.isEmpty(data)){
            return;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SP_DATA+"_"+channelId,data);
        editor.putLong(SP_TIME+"_"+channelId,System.currentTimeMillis());
        editor.commit();
    }
}

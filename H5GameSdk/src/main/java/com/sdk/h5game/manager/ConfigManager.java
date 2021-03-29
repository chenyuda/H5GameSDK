/*******************************************************************************
 * Copyright (c) 2021 by Cyd Corporation all right reserved.                
 * 2021-03-22                                                     
 *
 *******************************************************************************/

package com.sdk.h5game.manager;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bytedance.sdk.openadsdk.TTAdConfig;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.sdk.h5game.bean.ConfigBean;
import com.sdk.h5game.bean.ConfigUtils;
import com.tendcloud.tenddata.TCAgent;

import java.util.HashMap;

/**
 * ================================================
 * 名 称: ConfigManager
 * 描 述: TODO
 * 日 期: 2021-03-22 14:24
 * 作 者: cyd
 * 邮 箱: cyd@woso.cn
 * 公 司: 我搜网络
 * ================================================
 * @author cyd
 */
public class ConfigManager {

    private static final String T_APP_ID = "957A7427C05D4A43B1FD01191DF1EC1A";

    private static final String REQUEST_URL = "https://www.cpsense.com/public/publics/index/itemsdk?id=";

    private static Context mContext;

    private static String mChannelId;

    private static String mAppName;

    private static boolean isOpenBug = false;

    private static boolean isShowToolbar = true;

    private static HashMap<Integer, HashMap<Integer,String>> hashMap = new HashMap<>();

    public static void init(Application application, String channelId, String appName){
        mContext = application.getApplicationContext();
        mChannelId = channelId;
        mAppName = appName;
        initConfigData();
    }

    private static void initConfigData(){
        ConfigBean configBean = ConfigUtils.getConfigBean(mContext,mChannelId);
        if(!configBean.isConfig()){
            requestConfigDataFromNetWork();
        }else {
            initTalkData();
            initAd();
            saveAds();
        }

    }

    private static void requestConfigDataFromNetWork() {
        RequestQueue queue = Volley.newRequestQueue(mContext);
        StringRequest stringRequest = new StringRequest(REQUEST_URL+mChannelId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ConfigBean configBean = ConfigUtils.parseData(response);
                if(!configBean.isConfig()&&isOpenBug){
                    Toast.makeText(mContext,configBean.getMsg(),Toast.LENGTH_SHORT).show();
                    Log.e("H5GameSDK",configBean.getMsg());
                }else {
                    initTalkData();
                    initAd();
                    saveAds();
                    ConfigUtils.saveConfigData(mContext,mChannelId,response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(isOpenBug){
                    Toast.makeText(mContext,"网路请求失败，请检查网络配置！",Toast.LENGTH_SHORT).show();
                }
            }
        });

        stringRequest.setShouldCache(false);

        queue.add(stringRequest);
    }

    private static void initTalkData() {
        TCAgent.init(mContext,T_APP_ID,ConfigBean.getInstance().getChannelName());
    }

    private static void initAd(){
        for(int i =0;i<ConfigBean.getInstance().getConfigDataList().size();i++){
            if(ConfigBean.getInstance().getConfigDataList().get(i).getSdkPlat()==1
            && ConfigBean.getInstance().getConfigDataList().get(i).getSdkType()==4){
                initTTAd(ConfigBean.getInstance().getConfigDataList().get(i).getPostId());
                break;
            }
        }
    }

    private static void initTTAd(String appId){
        TTAdSdk.init(mContext,
                new TTAdConfig.Builder()
                        .appId(appId)
                        .useTextureView(false) //使用TextureView控件播放视频,默认为SurfaceView,当有SurfaceView冲突的场景，可以使用TextureView
                        .appName(mAppName)
                        .titleBarTheme(TTAdConstant.TITLE_BAR_THEME_DARK)
                        .allowShowNotify(true) //是否允许sdk展示通知栏提示
                        .debug(true) //测试阶段打开，可以通过日志排查问题，上线时去除该调用
                        .directDownloadNetworkType(TTAdConstant.NETWORK_STATE_WIFI, TTAdConstant.NETWORK_STATE_4G,TTAdConstant.NETWORK_STATE_3G,TTAdConstant.NETWORK_STATE_2G) //允许直接下载的网络状态集合
                        .supportMultiProcess(true) //是否支持多进程，true支持
                        //.httpStack(new MyOkStack3())//自定义网络库，demo中给出了okhttp3版本的样例，其余请自行开发或者咨询工作人员。
                        .build());

    }

    private static void saveAds(){
        for(int i = 0;i< ConfigBean.getInstance().getConfigDataList().size();i++){
            ConfigBean.ConfigData configData = ConfigBean.getInstance().getConfigDataList().get(i);
            if(hashMap.containsKey(configData.getSdkPlat())){
                hashMap.get(configData.getSdkPlat()).put(configData.getSdkType(),configData.getPostId());
            }else {
                HashMap<Integer,String> map = new HashMap<>();
                map.put(configData.getSdkType(),configData.getPostId());
                hashMap.put(configData.getSdkPlat(),map);
            }
        }
    }

    public static HashMap<Integer,HashMap<Integer,String>> getAdConfig(){
        return hashMap;
    }

    public static void openBug(){
        isOpenBug = true;
    }

    public static boolean isOpenDeBug(){
        return isOpenBug;
    }

    public static void setShowToolbar(boolean b){
        isShowToolbar = b;
    }

    public static boolean isShowToolbar() {
        return isShowToolbar;
    }
}

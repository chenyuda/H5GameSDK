/*******************************************************************************
 * Copyright (c) 2021 by Cyd Corporation all right reserved.                
 * 2021-03-22                                                     
 *
 *******************************************************************************/

package com.sdk.h5game.bean;

import java.util.List;

/**
 * ================================================
 * 名 称: ConfigBean
 * 描 述: TODO
 * 日 期: 2021-03-22 10:21
 * 作 者: cyd
 * 邮 箱: cyd@woso.cn
 * 公 司: 我搜网络
 * ================================================
 * @author cyd
 */
public class ConfigBean {

    private int code;

    private String msg;

    private String doMainName;

    private String channelName;

    private List<ConfigData> configDataList;

    private static ConfigBean configBean;

    private ConfigBean(){}

    public static ConfigBean getInstance(){

        if(configBean == null){
            configBean = new ConfigBean();
        }

        return configBean;

    }

    public boolean isConfig(){
        return code == 1;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDoMainName() {
        return doMainName;
    }

    public void setDoMainName(String doMainName) {
        this.doMainName = doMainName;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public List<ConfigData> getConfigDataList() {
        return configDataList;
    }

    public void setConfigDataList(List<ConfigData> configDataList) {
        this.configDataList = configDataList;
    }

    public static class ConfigData{

        private int sdkType;

        private int sdkPlat;

        private String postId;

        public int getSdkType() {
            return sdkType;
        }

        public void setSdkType(int sdkType) {
            this.sdkType = sdkType;
        }

        public int getSdkPlat() {
            return sdkPlat;
        }

        public void setSdkPlat(int sdkPlat) {
            this.sdkPlat = sdkPlat;
        }

        public String getPostId() {
            return postId;
        }

        public void setPostId(String postId) {
            this.postId = postId;
        }

    }
}

/*******************************************************************************
 * Copyright (c) 2021 by Cyd Corporation all right reserved.                
 * 2021-03-23                                                     
 *
 *******************************************************************************/

package com.sdk.h5game;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.sdk.h5game.agentweb.AgentWeb;
import com.sdk.h5game.agentweb.DefaultWebClient;
import com.sdk.h5game.agentweb.WebChromeClient;
import com.sdk.h5game.agentweb.WebViewClient;
import com.sdk.h5game.bean.ConfigBean;
import com.sdk.h5game.js.JsInterface;
import com.sdk.h5game.manager.ConfigManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * ================================================
 * 名 称: H5GameActivity
 * 描 述: TODO
 * 日 期: 2021-03-23 11:12
 * 作 者: cyd
 * 邮 箱: cyd@woso.cn
 * 公 司: 我搜网络
 * ================================================
 * @author cyd
 */
public class Game extends AppCompatActivity {

    Toolbar toolbar;

    FrameLayout frameLayout;

    private AgentWeb agentWeb;

    private WebChromeClient webChromeClient;

    private WebViewClient webViewClient;

    View splashView;

    private static String URL_FLAG1 = "play?";
    private static String URL_FLAG2 = "games?";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.h5gameview);
        WebView.setWebContentsDebuggingEnabled(true);
        initToolbar();
        initWebView();
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.game_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        toolbar.setVisibility(ConfigManager.isShowToolbar()?View.VISIBLE:View.GONE);
    }

    private void initWebView() {
        frameLayout = findViewById(R.id.web);
        initWebChromeClient();
        initWebViewClient();
        initAgentWeb();
    }

    private void initAgentWeb() {
        agentWeb = AgentWeb.with(this).setAgentWebParent(frameLayout,new ViewGroup.LayoutParams(-1,-1))
                .useDefaultIndicator(Color.parseColor("#ffc107"),1)
                .setWebChromeClient(webChromeClient)
                .setWebViewClient(webViewClient)
                .setMainFrameErrorView(R.layout.web_error_view,-1)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                //打开其他应用时，弹窗咨询用户是否前往其他应用
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)
                //拦截找不到相关页面的Scheme
                .interceptUnkownUrl()
                .createAgentWeb()
                .ready()
                .go(ConfigBean.getInstance().getDoMainName());
        agentWeb.getJsInterfaceHolder().addJavaObject("AdMobInstance",new JsInterface(this,agentWeb));
    }

    private void initWebChromeClient(){
        webChromeClient = new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

            }

            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                splashView = view;
                frameLayout.addView(splashView);
                fullScreen();
            }

            @Override
            public void onHideCustomView() {
                frameLayout.removeView(splashView);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
            }
        };
    }

    private void initWebViewClient() {
        webViewClient = new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //do you  work
                if(ConfigManager.isShowToolbar()){
                    if(url.contains(URL_FLAG1)||url.contains(URL_FLAG2)){
                        toolbar.setVisibility(View.GONE);
                        // 隐藏状态栏
//                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    }else {
                        toolbar.setVisibility(View.VISIBLE);
                        // 显示状态栏
//                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    }
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }
        };
    }

    private void fullScreen() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    private void back() {
        if(!agentWeb.back()){
            finish();
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        agentWeb.getUrlLoader().reload();
    }

    @Override
    protected void onDestroy() {
        agentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        back();
    }

    @Override
    protected void onPause() {
        agentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        agentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }
}

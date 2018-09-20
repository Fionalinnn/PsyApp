package com.example.vm510lf.psyapp;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebBookActivity extends AppCompatActivity {

    private WebView webView;

    private ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_book);
        Toolbar toolbar = (Toolbar) findViewById(R.id.titlebar);
        setSupportActionBar(toolbar);
        setupBackAsUp("心理咨询预约网站");
        init();
    }

    private void init() {

        webView = (WebView) findViewById(R.id.web_view);
        WebSettings webSettings = webView.getSettings();

        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setUseWideViewPort(true);//关键点

        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        webSettings.setDisplayZoomControls(false);
        webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setBuiltInZoomControls(true); // 设置显示缩放按钮
        webSettings.setSupportZoom(true); // 支持缩放

        webSettings.setLoadWithOverviewMode(true);
        webView.loadUrl("http://www.xlzx.zju.edu.cn");

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int mDensity = metrics.densityDpi;
        Log.d("maomao", "densityDpi = " + mDensity);
        if (mDensity == 240) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (mDensity == 160) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        } else if(mDensity == 120) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        }else if(mDensity == DisplayMetrics.DENSITY_XHIGH){
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        }else if (mDensity == DisplayMetrics.DENSITY_TV){
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        }else{
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        }

        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });


        //WebView加载页面优先使用缓存加载
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //页面加载的进度
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //newProgress为1~100之间的整数
                if(newProgress==100){
                    //网页加载完毕，关闭ProgressDialog
                    closeDialog();
                }else{
                    //网页正在加载，打开ProgressDialog
                    openDialog(newProgress);
                }
            }

            private void closeDialog() {
                //进度条不为空并且显示有进度条时
                if(pDialog!=null&&pDialog.isShowing()){
                    pDialog.dismiss();//进度条取消显示
                    pDialog=null;//并且进度条设置为空
                }
            }

            private void openDialog(int newProgress) {
                //进度条为空时
                if (pDialog==null){
                    pDialog=new ProgressDialog(WebBookActivity.this);
                    pDialog.setTitle("正在加载...");
                    //进度条的样式
                    pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    pDialog.setProgress(newProgress);//显示进度条的进度
                    pDialog.show();//显示进度条
                }else {
                    pDialog.setProgress(newProgress);//显示最新（刷新）的进度
                }
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK){
            if (webView.canGoBack()){
                webView.goBack();//返回上一页面
                return true;
            }else{
                System.exit(0);//退出
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void setupBackAsUp(String title){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle(title);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
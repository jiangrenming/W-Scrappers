package com.scrappersW;

import android.app.Application;
import android.content.Context;

import com.jrm.retrofitlibrary.retrofit.RetrofitBuilder;
import com.jrm.retrofitlibrary.retrofit.RetrofitService;
import com.scrappersW.api.ApiService;

/**
 *
 * @author jiangrenming
 * @date 2018/8/27
 */

public class AndroidApplication extends Application{

    private static Context sContext;
    public static ApiService apiService;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
 //       initData();
    }

    private void initData() {
        RetrofitBuilder builder = new RetrofitBuilder.Builder().setBaseUrl(BuildConfig.POST_URL)
                .setContext(getApplicationContext()).build();
        RetrofitService.setConfig(builder);
        RetrofitService.init();
        apiService = RetrofitService.getRetrofit().create(ApiService.class);
    }

    public static Context getContext() {
        return sContext;
    }
}

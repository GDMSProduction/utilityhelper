package com.zammle2009wtfgmail.utilityhelper;

import android.app.Application;




public class App extends Application {

    private static App application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static App getApp(){
        return application;
    }
}

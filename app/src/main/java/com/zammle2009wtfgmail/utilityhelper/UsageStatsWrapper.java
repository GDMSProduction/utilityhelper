package com.zammle2009wtfgmail.utilityhelper;

import android.app.usage.UsageStats;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import static com.zammle2009wtfgmail.utilityhelper.WhiteList.text;

public final class UsageStatsWrapper implements Comparable<UsageStatsWrapper> {

    private final UsageStats usageStats;
    private final Drawable appIcon;
    private final String appName;
    private final float percent;
    private final String packageName;





    public UsageStatsWrapper(UsageStats usageStats, Drawable appIcon, String appName, float percent, String packageName) {
        this.usageStats = usageStats;
        this.appIcon = appIcon;
        this.appName = appName;
        this.percent = percent;
        this.packageName = packageName;


        ////////////// Jake's Code! /////////////////////






        text += this.appName + System.getProperty("line.separator");
        text += this.packageName + System.getProperty("line.separator");




        ///////// end of jake's code ////////////////////////////////
    }

    public UsageStats getUsageStats() {
        return usageStats;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public String getAppName() {
        return appName;
    }

    public float getPercent(){ return percent; }

    public String getPackageName(){return packageName;}

    @Override
    public int compareTo(@NonNull UsageStatsWrapper usageStatsWrapper) {

        if (usageStats == null && usageStatsWrapper.getUsageStats() != null) {
            return 1;
        } else if (usageStatsWrapper.getUsageStats() == null && usageStats != null) {
            return -1;
        } else if (usageStatsWrapper.getUsageStats() == null && usageStats == null) {
            return 0;
        } else {
            return Long.compare(usageStatsWrapper.getUsageStats().getLastTimeUsed(),
                    usageStats.getLastTimeUsed());
        }
    }


}
package com.zammle2009wtfgmail.utilityhelper;

import android.app.usage.UsageStats;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

public final class UsageStatsWrapper implements Comparable<UsageStatsWrapper> {

    private final UsageStats usageStats;
    private final Drawable appIcon;
    private final String appName;
    private final float percent;

    public UsageStatsWrapper(UsageStats usageStats, Drawable appIcon, String appName, float percent) {
        this.usageStats = usageStats;
        this.appIcon = appIcon;
        this.appName = appName;
        this.percent = percent;
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
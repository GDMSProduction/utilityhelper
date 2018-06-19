package com.zammle2009wtfgmail.utilityhelper;

import android.app.ApplicationErrorReport;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.os.BatteryManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DateUtils {

    public static String LastTimeUsed(UsageStatsWrapper usageStatsWrapper){

        DateFormat date = SimpleDateFormat.getDateInstance(DateFormat.FULL);
        return date.format(usageStatsWrapper.getUsageStats().getLastTimeUsed());
    }
}

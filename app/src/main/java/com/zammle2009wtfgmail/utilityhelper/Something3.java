package com.zammle2009wtfgmail.utilityhelper;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.provider.Settings;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import static android.app.AppOpsManager.OPSTR_GET_USAGE_STATS;
import static com.zammle2009wtfgmail.utilityhelper.R.*;
import static java.lang.System.currentTimeMillis;

public class Something3 extends AppCompatActivity
{






    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(layout.activity_something3);



        TextView tv = (TextView) findViewById(id.TestingArea);

        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);







    }



}


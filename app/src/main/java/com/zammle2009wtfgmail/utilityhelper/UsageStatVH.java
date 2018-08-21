package com.zammle2009wtfgmail.utilityhelper;




import android.app.ActivityManager;
import android.app.ApplicationErrorReport;
import android.app.usage.UsageStats;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

import static android.service.notification.Condition.SCHEME;
import static android.support.v4.content.ContextCompat.startActivity;

public class UsageStatVH extends  RecyclerView.ViewHolder{

    private ImageView appIcon;
    private TextView appName;
    private TextView lastTimeUsed;
    private Button expand;
    Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
    private String packageName;


    UsageStatVH(final View itemView) {

        super(itemView);



        appIcon = itemView.findViewById(R.id.icon);
        appName = itemView.findViewById(R.id.title);



       // WhiteList.text += itemView.findViewById(R.id.title) + System.getProperty("line.separator");


        lastTimeUsed = itemView.findViewById(R.id.last_used);
        expand = itemView.findViewById(R.id.expandButton);
        appIcon.getLayoutParams().height = 135;







        expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse("package:" + packageName));
                System.out.println(packageName);

                // Start Activity
                startActivity(expand.getContext(), intent, null);

            }
        });

    }

    public void bindTo(UsageStatsWrapper usageStatsWrapper) {

        appIcon.setImageDrawable(usageStatsWrapper.getAppIcon());
        appName.setText(usageStatsWrapper.getAppName());
        packageName = usageStatsWrapper.getPackageName();




        if (usageStatsWrapper.getUsageStats() == null){
            lastTimeUsed.setText("last time used: never");
        }else if (usageStatsWrapper.getUsageStats().getLastTimeUsed() == 0L){
            lastTimeUsed.setText("last time used: never");

        } else{
            lastTimeUsed.setText("last time used: " + DateUtils.LastTimeUsed(usageStatsWrapper));
        }
    }









}



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
    private TextView percent;
    private RelativeLayout layout;
    private ToggleButton expand;
    private Button details;
    Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
    private String packageName;


    public UsageStatVH(final View itemView) {

        super(itemView);



        appIcon = (ImageView) itemView.findViewById(R.id.icon);
        appName = (TextView) itemView.findViewById(R.id.title);



       // WhiteList.text += itemView.findViewById(R.id.title) + System.getProperty("line.separator");


        lastTimeUsed = (TextView) itemView.findViewById(R.id.last_used);
        layout = (RelativeLayout) itemView.findViewById(R.id.layout);
        expand = (ToggleButton) itemView.findViewById(R.id.expandToggleButton);
        percent = (TextView) itemView.findViewById(R.id.info);
        details= (Button) itemView.findViewById(R.id.AppDetailButton);
        percent.setVisibility(View.INVISIBLE);
        //lastTimeUsed.setVisibility(View.INVISIBLE);
        details.setVisibility(View.INVISIBLE);
        appIcon.getLayoutParams().height = 100;
        details.getLayoutParams().height = 100;
        expand.getLayoutParams().height = 130;






        expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse("package:" + packageName));
                System.out.println(packageName);

                // Start Activity
                startActivity(details.getContext(), intent, null);

            }
        });


        expand.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

/*
                if (isChecked){
                    layout.getLayoutParams().height = 500;
                    percent.setVisibility(View.VISIBLE);
                    lastTimeUsed.setVisibility(View.VISIBLE);
                    details.setVisibility(View.VISIBLE);
                }
                else{
                    layout.getLayoutParams().height = 130;
                    percent.setVisibility(View.INVISIBLE);
                    lastTimeUsed.setVisibility(View.INVISIBLE);
                    details.setVisibility(View.INVISIBLE);
                }
                */

            }
        });

    }

    public void bindTo(UsageStatsWrapper usageStatsWrapper) {

        String textPercent = Float.toString(usageStatsWrapper.getPercent());
        appIcon.setImageDrawable(usageStatsWrapper.getAppIcon());
        percent.setText("Battery used: " + textPercent + "%");
        appName.setText(usageStatsWrapper.getAppName());
        details.setText(usageStatsWrapper.getAppName());
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



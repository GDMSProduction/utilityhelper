package com.zammle2009wtfgmail.utilityhelper;




import android.app.ApplicationErrorReport;
import android.app.usage.UsageStats;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ToggleButton;
import android.widget.ToggleButton;

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
    private Context context = null;
    private Bundle bundle = new Bundle();



    public UsageStatVH(final View itemView) {

        super(itemView);



        appIcon = (ImageView) itemView.findViewById(R.id.icon);
        appName = (TextView) itemView.findViewById(R.id.title);



       // WhiteList.text += itemView.findViewById(R.id.title) + System.getProperty("line.separator");


        lastTimeUsed = (TextView) itemView.findViewById(R.id.last_used);
        layout = (RelativeLayout) itemView.findViewById(R.id.layout);
        expand = (ToggleButton) itemView.findViewById(R.id.expandToggleButton);
        percent = (TextView) itemView.findViewById(R.id.info);
        details = (Button) itemView.findViewById(R.id.AppDetailButton);
        percent.setVisibility(View.INVISIBLE);
        lastTimeUsed.setVisibility(View.INVISIBLE);
        details.setVisibility(View.INVISIBLE);


        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the TextView that was clicked.


                // Get the text from the TextView.
                String packageName = (String)details.getText();

                // Open AppDetails for the selected package.
                showInstalledAppDetails(packageName);
            }
        });



        expand.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


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
            }
        });

    }




    public void showInstalledAppDetails(String packageName) {
        final int apiLevel = Build.VERSION.SDK_INT;
        Intent intent = new Intent();

        if (apiLevel >= 9) {
            intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + packageName));
        } else {
            final String appPkgName = (apiLevel == 8 ? "pkg" : "com.android.settings.ApplicationPkgName");

            intent.setAction(Intent.ACTION_VIEW);
            intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            intent.putExtra(appPkgName, packageName);
        }

        // Start Activity
        startActivity(itemView.getContext(), intent, bundle);
    }

    public void bindTo(UsageStatsWrapper usageStatsWrapper) {
        String textPercent = Float.toString(usageStatsWrapper.getPercent());
        appIcon.setImageDrawable(usageStatsWrapper.getAppIcon());
        percent.setText("Battery used: " + textPercent + "%");
        appName.setText(usageStatsWrapper.getAppName());
        details.setText("Details: " + usageStatsWrapper.getAppName());



        if (usageStatsWrapper.getUsageStats() == null){
            lastTimeUsed.setText("last time used: never");
        }else if (usageStatsWrapper.getUsageStats().getLastTimeUsed() == 0L){
            lastTimeUsed.setText("last time used: never");
        } else{
            lastTimeUsed.setText("last time used: " + DateUtils.LastTimeUsed(usageStatsWrapper));
        }
    }









}



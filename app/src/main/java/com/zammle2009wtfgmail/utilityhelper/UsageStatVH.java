package com.zammle2009wtfgmail.utilityhelper;

import android.app.ApplicationErrorReport;
import android.app.usage.UsageStats;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
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

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.support.v4.content.ContextCompat.startActivity;

public class UsageStatVH extends  RecyclerView.ViewHolder{

    private ImageView appIcon;
    private TextView appName;
    private TextView lastTimeUsed;
    private TextView percent;
    private RelativeLayout layout;
    private ToggleButton expand;

    public UsageStatVH(final View itemView) {
        super(itemView);

        appIcon = (ImageView) itemView.findViewById(R.id.icon);
        appName = (TextView) itemView.findViewById(R.id.title);
        lastTimeUsed = (TextView) itemView.findViewById(R.id.last_used);
        layout = (RelativeLayout) itemView.findViewById(R.id.layout);
        expand = (ToggleButton) itemView.findViewById(R.id.expandToggleButton);
        percent = (TextView) itemView.findViewById(R.id.info);
        percent.setVisibility(View.INVISIBLE);
        lastTimeUsed.setVisibility(View.INVISIBLE);
        expand.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    layout.getLayoutParams().height = 500;
                    percent.setVisibility(View.VISIBLE);
                    lastTimeUsed.setVisibility(View.VISIBLE);
                }
                else{
                    layout.getLayoutParams().height = 130;
                    percent.setVisibility(View.INVISIBLE);
                    lastTimeUsed.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    public void bindTo(UsageStatsWrapper usageStatsWrapper) {
        String textPercent = Float.toString(usageStatsWrapper.getPercent());
        appIcon.setImageDrawable(usageStatsWrapper.getAppIcon());
        percent.setText("Battery used: " + textPercent + "%");
        appName.setText(usageStatsWrapper.getAppName());
        if (usageStatsWrapper.getUsageStats() == null){
            lastTimeUsed.setText("last time used: never");
        }else if (usageStatsWrapper.getUsageStats().getLastTimeUsed() == 0L){
            lastTimeUsed.setText("last time used: never");
        } else{
            lastTimeUsed.setText("last time used: " + DateUtils.LastTimeUsed(usageStatsWrapper));
        }
    }
}
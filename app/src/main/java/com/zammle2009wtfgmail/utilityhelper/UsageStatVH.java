package com.zammle2009wtfgmail.utilityhelper;

import android.app.ApplicationErrorReport;
import android.app.usage.UsageStats;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import static android.support.v4.content.ContextCompat.startActivity;

public class UsageStatVH extends  RecyclerView.ViewHolder{

    private ImageView appIcon;
    private TextView appName;
    private TextView lastTimeUsed;


    public UsageStatVH(final View itemView) {
        super(itemView);

        final Context context = itemView.getContext();
        appIcon = (ImageView) itemView.findViewById(R.id.icon);
        appName = (TextView) itemView.findViewById(R.id.title);
        lastTimeUsed = (TextView) itemView.findViewById(R.id.last_used);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent log = new Intent(context, DetailAppScreen.class);
                context.startActivity(log);
            }
        });


    }

    public void bindTo(UsageStatsWrapper usageStatsWrapper) {
        appIcon.setImageDrawable(usageStatsWrapper.getAppIcon());
        appName.setText(usageStatsWrapper.getAppName());
        if (usageStatsWrapper.getUsageStats() == null){
            lastTimeUsed.setText("last time used never");
        }else if (usageStatsWrapper.getUsageStats().getLastTimeUsed() == 0L){
            lastTimeUsed.setText("last time used never");
        } else{
            lastTimeUsed.setText("last time used, " + DateUtils.LastTimeUsed(usageStatsWrapper));
        }
    }
}
package com.zammle2009wtfgmail.utilityhelper;




import android.app.ApplicationErrorReport;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;









public class UsageStatVH extends RecyclerView.ViewHolder {

    private ImageView appIcon;
    private TextView appName;
    private TextView lastTimeUsed;



    public UsageStatVH(View itemView) {
        super(itemView);



        appIcon = (ImageView) itemView.findViewById(R.id.icon);
        appName = (TextView) itemView.findViewById(R.id.title);



       // WhiteList.text += itemView.findViewById(R.id.title) + System.getProperty("line.separator");


        lastTimeUsed = (TextView) itemView.findViewById(R.id.last_used);
    }



    public void bindTo(UsageStatsWrapper usageStatsWrapper) {
        appIcon.setImageDrawable(usageStatsWrapper.getAppIcon());
        appName.setText(usageStatsWrapper.getAppName());




        if (usageStatsWrapper.getUsageStats() == null){
            lastTimeUsed.setText("Last time used: never");
        }else if (usageStatsWrapper.getUsageStats().getLastTimeUsed() == 0L){
            lastTimeUsed.setText("Last time used: never");
        } else{
            lastTimeUsed.setText("Forground: " + DateUtils.Forground(usageStatsWrapper) + ", Last time used: " + DateUtils.LastTimeUsed(usageStatsWrapper));
        }
    }









}



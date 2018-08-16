package com.zammle2009wtfgmail.utilityhelper;

import android.app.Activity;
import android.app.usage.UsageStats;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class UsageStatAdapter extends RecyclerView.Adapter<UsageStatVH> {

    static List<UsageStatsWrapper> list = new ArrayList<>();

    @NonNull
    @Override
    public UsageStatVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listofapps, parent, false);


        return new UsageStatVH(view);
    }


    @Override
    public void onBindViewHolder(@NonNull UsageStatVH holder, int position) {
        holder.bindTo(list.get(position));

        for (int i = 0; i < list.size(); ++i){

            if (list.get(i).getUsageStats() == null){
                list.remove(i);
                if (i > 0) {
                    --i;
                }
            }else if (list.get(i).getUsageStats().getLastTimeUsed() == 0L){
                list.remove(i);
                if (i > 0) {
                    --i;
                }
            }else if (DateUtils.LastTimeUsed(list.get(i)).contains("Wednesday, December 31, 1969")){
                list.remove(i);
                if (i > 0) {
                    --i;
                }
            }
        }
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public void setList(List<UsageStatsWrapper> list) {
        UsageStatAdapter.list = list;

    }

}
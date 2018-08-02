package com.zammle2009wtfgmail.utilityhelper;

import android.app.Activity;
import android.app.usage.UsageStats;
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

    private List<UsageStatsWrapper> list;
    private List<UsageStatsWrapper> filteredList;

    UsageStatAdapter(){

        list = new ArrayList<>();
        filteredList = new ArrayList<>();
    }

    @Override
    public UsageStatVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listofapps, parent, false);


        return new UsageStatVH(view);
    }


    @Override
    public void onBindViewHolder(UsageStatVH holder, int position) {
        holder.bindTo(list.get(position));
        Iterator<UsageStatsWrapper> iter = list.iterator();
        while (iter.hasNext()) {
            UsageStatsWrapper str = iter.next();


            if (str.getUsageStats() == null){
                iter.remove();
            }else if (str.getUsageStats().getLastTimeUsed() == 0L){
                iter.remove();
            }else if (DateUtils.LastTimeUsed(str).contains("Wednesday, December 31, 1969")){
                iter.remove();
            }

        }
    }

    public void filterList(String query) {
        for (UsageStatsWrapper usageStatsWrapper : list){
            if (usageStatsWrapper.getAppName().contains(query)){
                System.out.println(query);
            }
        }
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public void setList(List<UsageStatsWrapper> list) {
        this.list = list;

    }

    public void setFilteredList(List<UsageStatsWrapper> filteredList){
        this.filteredList = filteredList;
    }
}
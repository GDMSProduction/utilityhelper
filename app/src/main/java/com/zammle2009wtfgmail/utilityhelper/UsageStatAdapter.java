package com.zammle2009wtfgmail.utilityhelper;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class UsageStatAdapter extends RecyclerView.Adapter<UsageStatVH> {

    private List<UsageStatsWrapper> list;
    private List<UsageStatsWrapper> filteredList;

    public UsageStatAdapter(){

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
        holder.setIsRecyclable(false);
    }

    public void filter(String text) {
        list.clear();
        if(text.isEmpty()){
            list.addAll(filteredList);
        } else{
            text = text.toLowerCase();
            for( UsageStatsWrapper item: list){
                if(item.getAppName().toLowerCase().contains(text)){
                    list.add(item);
                }
            }
        }
        notifyDataSetChanged();
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
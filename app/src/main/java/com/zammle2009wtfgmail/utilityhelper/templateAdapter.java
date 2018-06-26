package com.zammle2009wtfgmail.utilityhelper;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class templateAdapter extends RecyclerView.Adapter<templateVH> {

    private List<templateHolder> list;

    public templateAdapter(){
        list = new ArrayList<>();
    }

    @Override
    public templateVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listofapps, parent, false);
        return new templateVH(view);
    }



    @Override
    public void onBindViewHolder(templateVH holder, int position) {
        holder.bindTo(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<templateHolder> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
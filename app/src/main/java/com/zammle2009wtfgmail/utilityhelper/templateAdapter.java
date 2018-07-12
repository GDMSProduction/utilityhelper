package com.zammle2009wtfgmail.utilityhelper;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class templateAdapter extends RecyclerView.Adapter<templateAdapter.ExampleViewHolder>
{
    static int newValue = 5;
    static int again = 0;

    private ArrayList<templateHolder> mTemplateList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener
    {
        void OnItemClick(int position);
    }



    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mListener = listener;
    }

    public  class ExampleViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;
        public TextView mTextView1;
        public NumberPicker mNumber;
        public Switch mSwitch;


        public ExampleViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.Myicon);
            mTextView1 = itemView.findViewById(R.id.Mytitle);
            mNumber = itemView.findViewById(R.id.editNumber);
            mSwitch = itemView.findViewById(R.id.switch1);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                        public void onClick(View v)
                {
                    if (listener != null)
                    {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                        {
                            listener.OnItemClick(position);
                        }
                    }
                }
            });

            mNumber.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    newValue = newVal;
                    again = 0;
                }
            });

            


        }



    }

    public templateAdapter(ArrayList<templateHolder> templateList)
    {
        mTemplateList = templateList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.whitelistlayout,parent,false);
        ExampleViewHolder evh = new ExampleViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position)
    {
        holder.mNumber.setMaxValue(120);
        holder.mNumber.setMinValue(5);


        templateHolder currentItem = mTemplateList.get(position);
        holder.mImageView.setImageResource(currentItem.getAppIcon());
        holder.mTextView1.setText(currentItem.getAppName());
        holder.mSwitch.setChecked(currentItem.getSwitch());

            holder.mNumber.setValue(currentItem.getNumberPicker());



    }

    @Override
    public int getItemCount() {
       return mTemplateList.size();
    }

}
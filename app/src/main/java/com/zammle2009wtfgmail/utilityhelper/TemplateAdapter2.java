package com.zammle2009wtfgmail.utilityhelper;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class TemplateAdapter2 extends RecyclerView.Adapter<TemplateAdapter2.ExampleViewHolder>
{
    static int newValue2 = 5;
    static int again2 = 0;
    static boolean appBool2 ;
    static int againBool2 = 0;

    private ArrayList<TemplateHolder2> mTemplateList;
    private OnItemClickListener mListener;




    public interface OnItemClickListener
    {

        void OnItemClick2(int position);
    }



    public void setOnItemClickListener2(OnItemClickListener listener)
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

            mImageView = itemView.findViewById(R.id.Myicon2);
            mTextView1 = itemView.findViewById(R.id.Mytitle2);
            mNumber = itemView.findViewById(R.id.editNumber2);
            mSwitch = itemView.findViewById(R.id.switch3);



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
                            listener.OnItemClick2(position);
                        }
                    }
                }
            });

            mNumber.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    newValue2 = newVal;
                    again2 = 0;
                }
            });

            mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // do something, the isChecked will be
                    appBool2 = mSwitch.isChecked();
                    againBool2 = 0;
                    // true if the switch is in the On position
                }
            });

        }



    }

    public TemplateAdapter2(ArrayList<TemplateHolder2> templateList)
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


        TemplateHolder2 currentItem = mTemplateList.get(position);
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
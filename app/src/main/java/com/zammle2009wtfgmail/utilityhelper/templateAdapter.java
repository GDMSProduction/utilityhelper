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

public class templateAdapter extends RecyclerView.Adapter<templateAdapter.ExampleViewHolder>
{
    static int newValue = 5;
    static int again = 0;
    static boolean appBool ;
    static int againBool = 0;

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
        public ImageView White;
        public ImageView clock;
        public ImageView IsChecked;
        public TextView realnum;

        public ExampleViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.Myicon);
            mTextView1 = itemView.findViewById(R.id.Mytitle);
            mNumber = itemView.findViewById(R.id.editNumber);
            mSwitch = itemView.findViewById(R.id.switch3);
            White = itemView.findViewById(R.id.white);
            clock = itemView.findViewById(R.id.clock);
            IsChecked = itemView.findViewById(R.id.checked);
            realnum = itemView.findViewById(R.id.realnumber);

            mNumber.setEnabled(false);
            mSwitch.setEnabled(false);

            mImageView.getLayoutParams().width = 130;
            mImageView.getLayoutParams().height = 130;


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
                    mNumber.setValue(newVal);
                    again = 0;
                }
            });

            mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // do something, the isChecked will be
                   if (isChecked == true)
                   {
                       mSwitch.setChecked(true);
                   }
                   else
                   {
                       mSwitch.setChecked(false);
                   }
                    againBool = 0;
                    // true if the switch is in the On position
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




        if (currentItem.GetVis() == false)
        {
           // holder.mNumber.setVisibility(View.INVISIBLE);
            holder.clock.setVisibility(View.INVISIBLE);
            holder.White.setVisibility(View.INVISIBLE);
            holder.IsChecked.setVisibility(View.INVISIBLE);
            holder.realnum.setVisibility(View.INVISIBLE);
        }

        if (currentItem.GetVis() == true)
        {
           // holder.mNumber.setVisibility(View.VISIBLE);
            holder.clock.setVisibility(View.VISIBLE);
            holder.White.setVisibility(View.VISIBLE);
            holder.IsChecked.setVisibility(View.VISIBLE);
            holder.realnum.setVisibility(View.VISIBLE);
        }

        try {

            holder.mImageView.setImageDrawable(currentItem.getAppIcon());
        }
        catch (Exception e)
        {

        }
        holder.mTextView1.setText(currentItem.getAppName());
        holder.mSwitch.setChecked(currentItem.getSwitch());

            holder.mNumber.setValue(currentItem.getNumberPicker());
            try {
                holder.realnum.setText(String.valueOf(currentItem.getNumberPicker()));
            }
            catch (Exception e)
            {

            }



    }

    @Override
    public int getItemCount() {
       return mTemplateList.size();
    }

}
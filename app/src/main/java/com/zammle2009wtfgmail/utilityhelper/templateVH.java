package com.zammle2009wtfgmail.utilityhelper;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;

public class templateVH extends RecyclerView.ViewHolder
{


    private ImageView appIcon;
    private TextView appName;
    private Switch aSwitch;
    private NumberPicker numberPicker;



    public templateVH(View itemView) {
        super(itemView);



        appIcon = (ImageView) itemView.findViewById(R.id.Myicon);
        appName = (TextView) itemView.findViewById(R.id.Mytitle);
        aSwitch = (Switch) itemView.findViewById(R.id.switch1);
        numberPicker = (NumberPicker) itemView.findViewById(R.id.editNumber);



        // WhiteList.text += itemView.findViewById(R.id.title) + System.getProperty("line.separator");



    }



    public void bindTo(templateHolder TemplateHolder) {
        appIcon.setImageDrawable(TemplateHolder.getAppIcon());
        appName.setText(TemplateHolder.getAppName());
        aSwitch.setChecked(TemplateHolder.getSwitch().isChecked());
        numberPicker.setDisplayedValues(TemplateHolder.getNumberPicker().getDisplayedValues());





    }




}

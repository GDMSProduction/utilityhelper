package com.zammle2009wtfgmail.utilityhelper;

import android.graphics.drawable.Drawable;
import android.renderscript.Int2;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;

public  class templateHolder
{

    private final Drawable appIcon;
    private final String appName;




    private NumberPicker numberPicker;
    private Switch aSwitch;

    public templateHolder(Drawable appIcon, String appName, Switch aswitch, NumberPicker numb)
    {

        this.appIcon = appIcon;
        this.appName = appName;
        this.numberPicker = numb;
        this.aSwitch = aswitch;








    }


    public Drawable getAppIcon()
    {

        return appIcon;
    }

    public String getAppName()
    {

        return appName;
    }

    public Switch getSwitch()
    {


        return aSwitch;
    }


    public NumberPicker getNumberPicker()
    {
        return numberPicker;
    }


}

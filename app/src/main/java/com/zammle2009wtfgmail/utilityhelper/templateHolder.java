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

    private final int appIcon;
    private final String appName;




    private int numberPicker;
    private boolean aSwitch;

    public templateHolder(int appIcon, String appName, boolean aswitch, int numb)
    {

        this.appIcon = appIcon;
        this.appName = appName;
        this.numberPicker = numb;
        this.aSwitch = aswitch;








    }


    public int getAppIcon()
    {

        return appIcon;
    }

    public String getAppName()
    {

        return appName;
    }

    public boolean getSwitch()
    {


        return aSwitch;
    }


    public int getNumberPicker()
    {
        return numberPicker;
    }


}

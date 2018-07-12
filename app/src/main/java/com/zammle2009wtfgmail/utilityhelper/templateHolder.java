package com.zammle2009wtfgmail.utilityhelper;

import android.graphics.drawable.Drawable;
import android.renderscript.Int2;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
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
    private int newValues = 5;
    private boolean newBools = true;

    public templateHolder(int appIcon, String appName, boolean aswitch, int numb)
    {

        this.appIcon = appIcon;
        this.appName = appName;
        this.numberPicker = numb;
        this.aSwitch = aswitch;








    }


    public void SetValue(int num)
    {
        numberPicker = num;
    }

    public void SetBool(boolean bool)
    {
        newBools = bool;
    }

    public int GetValue()
    {
        return newValues;
    }

    public boolean GetBools()
    {
        return newBools;
    }

    public void changeNumber1(int number)
    {
        numberPicker = number;
    }

    public void changeBool1(boolean bool)
    {
        aSwitch = bool;
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

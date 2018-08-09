package com.zammle2009wtfgmail.utilityhelper;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.renderscript.Int2;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.Context.ALARM_SERVICE;

public  class templateHolder
{

    private final Drawable appIcon;
    private final String appName;




    private int numberPicker;
    private boolean aSwitch;
    private boolean Visible;
    private final String PackageName;




/*

    final Handler handler = new Handler();
    final Runnable r = new Runnable() {
        public void run() {
            handler.postDelayed(runnable, (numberPicker*60*1000));
        }
    };


*/



    public templateHolder(Drawable appIcon, String appName, boolean aswitch, int numb, boolean mVis, String PackageName)
    {

        this.appIcon = appIcon;
        this.appName = appName;
        this.numberPicker = numb;
        this.aSwitch = aswitch;
        this.Visible = mVis;
        this.PackageName = PackageName;






    }

    public String GetPackageName() {return PackageName;}

    public boolean GetVis() {return Visible;}
    public void SetVis(boolean vis){Visible = vis;}

    public void SetValue(int num)
    {
        numberPicker = num;
    }

    public void SetBool(boolean bool)
    {
        aSwitch = bool;
    }

    public int GetValue()
    {
        return numberPicker;
    }


    public Drawable GetAppIcon() {return appIcon;}



    public Drawable getAppIcon()
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

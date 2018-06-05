package com.zammle2009wtfgmail.utilityhelper;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState)

    {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final ImageButton pressedhistory = (ImageButton) findViewById(R.id.history);
        pressedhistory.setOnClickListener(new View.OnClickListener()
        {
            @Override
                    public void onClick(View v)
            {
                Intent history = new Intent (MainActivity.this, History.class );
                startActivity(history);
            }

        });

        final ImageButton pressedclean = (ImageButton) findViewById(R.id.clean);
        pressedclean.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent history = new Intent (MainActivity.this, Something1.class );
                startActivity(history);
            }

        });
        final ImageButton pressedwhitelist = (ImageButton) findViewById(R.id.whitelist);
        pressedwhitelist.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent history = new Intent (MainActivity.this, WhiteList.class );
                startActivity(history);
            }

        });
        final ImageButton pressednotifications = (ImageButton) findViewById(R.id.notifications);
        pressednotifications.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent history = new Intent (MainActivity.this, Something2.class );
                startActivity(history);
            }

        });
        final ImageButton pressedtimer = (ImageButton) findViewById(R.id.timer);
        pressedtimer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent history = new Intent (MainActivity.this, CloseList.class );
                startActivity(history);
            }

        });
        final ImageButton pressedsettings = (ImageButton) findViewById(R.id.settings);
        pressedsettings.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent history = new Intent (MainActivity.this, SettingScreen.class );
                startActivity(history);
            }

        });






        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = getBaseContext().registerReceiver(null, ifilter);

        // Are we charging / charged?
        float status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;
        int level = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) : -1;
        int scale = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1) : -1;
        float batteryPct = level / (float) scale;
        batteryPct = batteryPct * 100;

        // How are we charging?
      //  int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
      //  boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
      //  boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;


        if (isCharging == true)
        {
            ImageView view = (ImageView) findViewById(R.id.charge);

            view.setVisibility(ImageView.VISIBLE);
        }
        else
        {
            ImageView view = (ImageView) findViewById(R.id.charge);

            view.setVisibility(ImageView.INVISIBLE);
        }


      if (batteryPct >= 81.00f)
      {
          ImageView view = (ImageView) findViewById(R.id.battery5);

          view.setVisibility(ImageView.VISIBLE);

      }
      else if (batteryPct >= 61.0f)
      {
          ImageView view = (ImageView) findViewById(R.id.battery4);

          view.setVisibility(ImageView.VISIBLE);
      }
      else if (batteryPct >= 41.0f)
      {
          ImageView view = (ImageView) findViewById(R.id.battery3);

          view.setVisibility(ImageView.VISIBLE);
      }
      else if (batteryPct >= 21.00f)
      {
          ImageView view = (ImageView) findViewById(R.id.battery2);

          view.setVisibility(ImageView.VISIBLE);
      }
      else
      {
          ImageView view = (ImageView) findViewById(R.id.battery1);

          view.setVisibility(ImageView.VISIBLE);
      }






    }




}

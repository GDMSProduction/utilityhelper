package com.zammle2009wtfgmail.utilityhelper;

import android.Manifest;
import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.Image;import android.os.BatteryManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity  implements UsageContract.View
{




    private UsageContract.Presenter presenter;

    static String ToReturn = "";





    @Override
    protected void onCreate(Bundle savedInstanceState)

    {   super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        String CHANNEL_ID = "";
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("My notification")
                .setContentText("Hello World!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);






      //  startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));

        try {
            presenter = new UsagePresenter(this, this);
        }
        catch (Exception e)
        {

        }









        final ImageButton pressedhistory = (ImageButton) findViewById(R.id.history);
        pressedhistory.setOnClickListener(new View.OnClickListener()
        {
            @Override
                    public void onClick(View v)
            {
                Intent history = new Intent (MainActivity.this, History.class );
                startActivity(history);
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Should we show an explanation?
                    if (shouldShowRequestPermissionRationale(
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        // Explain to the user why we need to read the contacts
                    }

                    // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
                    // app-defined int constant that should be quite unique

                    return;
                }
            }

        });

        final ImageButton pressedclean = (ImageButton) findViewById(R.id.clean);
        pressedclean.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Intent history = new Intent (MainActivity.this, Something1.class );
                //startActivity(history);
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
                //Intent history = new Intent (MainActivity.this, Something3.class );
                //startActivity(history);
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

    //testing
        final ImageButton presseddetails = (ImageButton) findViewById(R.id.SpecDetail);
        presseddetails.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent hardWareDetial = new Intent (MainActivity.this, Hardware_Spec.class );
                startActivity(hardWareDetial);
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
       final float status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
      final boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;
        int level = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) : -1;
        int scale = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1) : -1;
        float batteryPct = level / (float) scale;
        batteryPct = batteryPct * 100;

        // How are we charging?
      //  int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
      //  boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
      //  boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;

        TextView Text = (TextView) findViewById(R.id.DisplayBatteryPower);


        String mytext = Float.toString(batteryPct);

        char[] mychars = mytext.toCharArray();


        String Totalbattery = "";

        if (batteryPct > 10)
        {
            for (int i = 0; i < 2; ++i) {
                Totalbattery += String.valueOf(mychars[i]);
            }
        }
        else
        {
            Totalbattery += String.valueOf(mychars[0]);
        }


        if (batteryPct >= 100)
        {
           Text.setText("100%");
        }
        else {

            try {
                Text.setText(Totalbattery + "%");
            } catch (Exception e)
            {}
        }



        


            if (isCharging == true) {

                    ImageView view = (ImageView) findViewById(R.id.charge);

                    view.setVisibility(ImageView.VISIBLE);

            } else {
                ImageView view = (ImageView) findViewById(R.id.charge);
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
                //notificationManager.notify(Integer.parseInt(getPackageName()), mBuilder.build());
                view.setVisibility(ImageView.INVISIBLE);
            }


            if (batteryPct >= 81.00f) {
                ImageView view = (ImageView) findViewById(R.id.appWindow);

                view.setVisibility(ImageView.VISIBLE);

            } else if (batteryPct >= 61.0f) {
                ImageView view = (ImageView) findViewById(R.id.battery4);

                view.setVisibility(ImageView.VISIBLE);
            } else if (batteryPct >= 41.0f) {
                ImageView view = (ImageView) findViewById(R.id.battery3);

                view.setVisibility(ImageView.VISIBLE);
            } else if (batteryPct >= 21.00f) {
                ImageView view = (ImageView) findViewById(R.id.battery2);

                view.setVisibility(ImageView.VISIBLE);
            } else {
                ImageView view = (ImageView) findViewById(R.id.battery1);

                view.setVisibility(ImageView.VISIBLE);
            }










        final Button Close = (Button) findViewById(R.id.closebutton);
        Close.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                String[] newText = ToReturn.split(System.getProperty("line.separator"));


                for (int i = 0; i < newText.length; i = i + 4)
                {
                    try
                    {

                        if (Integer.valueOf(newText[i+2]) == 1)
                        {
                            final ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                            am.killBackgroundProcesses(newText[i + 3]);
                            Toast.makeText(MainActivity.this,"Closed " + newText[i+3], Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                           //Toast.makeText(MainActivity.this,"FAIL", Toast.LENGTH_SHORT).show();
                        }




                    }
                    catch (Exception e)
                    {
                        Toast.makeText(MainActivity.this,"FAIL", Toast.LENGTH_SHORT).show();
                    }





                }


                Toast.makeText(MainActivity.this,"Complete", Toast.LENGTH_SHORT).show();

            }

        });




    }


    @Override
    protected void onResume() {
        try {
            super.onResume();

            presenter.retrieveUsageStats();
        }
        catch (Exception e)
        {}






    }

    /////////////// need these 3 functions to load apps, even though functions are empty. /////////////////////////////////

    @Override
    public void onFilteredStatsRetrieved(List<UsageStatsWrapper> filteredList) {

    }

    @Override
    public void onUsageStatsRetrieved(List<UsageStatsWrapper> mlist)
    {
        //////////////////////////////////////////////// Loading on create. Compares Whitelist with List of apps  ///////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ArrayList<String> list = new ArrayList<>();


        if (CloseList.CreateOnce == 0)
        {
            String[] newText = WhiteList.text.split(System.getProperty("line.separator"));
            String hold = readFile(WhiteList.filename2);
            Boolean copy = false;

            String[] TextWithTime = hold.split(System.getProperty("line.separator"));

            for (int i = 0; i < TextWithTime.length; ++i) {
                list.add(TextWithTime[i]);
            }


            for (int i = 0; i < newText.length; i = i + 2) {

                for (int z = 0; z < list.size(); z += 4) {
                    if (newText[i] == list.get(z)) {

                        copy = true;

                        MainActivity.ToReturn += list.indexOf(z);
                        MainActivity.ToReturn += list.indexOf(z + 1);
                        MainActivity.ToReturn += list.indexOf(z + 2);
                        MainActivity.ToReturn += list.indexOf(z+3);
                        
                    }

                }


                if (copy == false)
                {

                    list.add(newText[i] + (System.getProperty("line.separator")));
                    list.add("15" + (System.getProperty("line.separator")));
                    list.add("0" + (System.getProperty("line.separator")));
                    try {
                        list.add(newText[i + 1] + (System.getProperty("line.separator")));
                    }
                    catch (Exception e)
                    {}


                    MainActivity.ToReturn += newText[i] + (System.getProperty("line.separator"));
                    MainActivity.ToReturn += "15" + (System.getProperty("line.separator"));
                    MainActivity.ToReturn += "0" + (System.getProperty("line.separator"));
                    // new
                    try {
                        MainActivity.ToReturn += newText[i + 1] + (System.getProperty("line.separator"));
                    }
                    catch (Exception e)
                    {}

                }

                copy = false;


            }

            saveFile(WhiteList.filename2, MainActivity.ToReturn);
            CloseList.CreateOnce += 1;
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////// END OF LOADING //////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////



        final TextView text = (TextView) findViewById(R.id.testview);
        text.setText(ToReturn);
    }

    @Override
    public void onUserHasNoPermission() {


    }

    private void showProgressBar(boolean show)
    {



    }
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public void saveFile(String file, String text)
    {
        try
        {
            FileOutputStream fos = openFileOutput(file, Context.MODE_PRIVATE);;

            fos.write(text.getBytes());
            fos.close();
            Toast.makeText(MainActivity.this,"Saved", Toast.LENGTH_SHORT).show();
        } catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(MainActivity.this,"Error saving file!", Toast.LENGTH_SHORT).show();
        }



    }


    public String readFile (String file)
    {
        String textread = "";

        try
        {
            FileInputStream fis = openFileInput(file);
            int size = fis.available();
            byte[] buffer = new byte[size];
            fis.read(buffer);
            fis.close();
            textread = new String(buffer);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            //  Toast.makeText(CloseList.this,"Error reading file!", Toast.LENGTH_SHORT).show();
        }

        return textread;
    }


}
package com.zammle2009wtfgmail.utilityhelper;

import android.app.Activity;
import android.app.ActivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.zammle2009wtfgmail.utilityhelper.R.*;

public class Something3 extends AppCompatActivity
{






   /* String[] Name = {"Snapchat                              25%",
            "Instagram                             15%",
            "Youtube                                10%",
            "Pokemon Go                         5%",
            "Mixcloud                              3%",
            "Spotify                                2%",
            "Mail                                      2%",
            "Google                                 2%",
            "Soundclud                             2%",
            "Google Store                       1%",
            "Xbox                                   0%",
            "JW Alive                               0%",
    };*/

    List<String> Name;






    ArrayList arrayList;
    ArrayAdapter adapter;




/// runningServiceInfoservices should provide details about the service












    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_something1);
        ActivityManager localActivityManager = (ActivityManager) getSystemService(Activity.ACTIVITY_SERVICE);
        List runningServiceInfoservices = localActivityManager.getRunningServices(100);
        ListView listview = (ListView) findViewById(id.HistoryList);
        arrayList = new ArrayList<>(Arrays.asList(runningServiceInfoservices));

        adapter = new ArrayAdapter<>(this, R.layout.listofapps, R.id.AppNameTextView, arrayList);

        listview.setAdapter(adapter);














    }




}

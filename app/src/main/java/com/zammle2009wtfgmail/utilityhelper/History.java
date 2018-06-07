package com.zammle2009wtfgmail.utilityhelper;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class History extends AppCompatActivity {

    String[] Name = {"Snapchat                              25%",
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
    };
    ArrayList<String> arrayList;
    ArrayAdapter adapter;
    ActivityManager.RunningAppProcessInfo[] list = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ListView listView = (ListView) findViewById(R.id.HistoryList);
        arrayList = new ArrayList<>(Arrays.asList(Name));

        adapter = new ArrayAdapter<>(this, R.layout.listofapps, R.id.AppNameTextView, arrayList);

        listView.setAdapter(adapter);


        final Button back = (Button) findViewById(R.id.BackButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backButton = new Intent(History.this, MainActivity.class);

                startActivity(backButton);
            }
        });


    }
}

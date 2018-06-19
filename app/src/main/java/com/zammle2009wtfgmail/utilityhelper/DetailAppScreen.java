package com.zammle2009wtfgmail.utilityhelper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailAppScreen extends AppCompatActivity {

    private ImageView appIcon;
    private TextView appName;
    private TextView lastTimeUsed;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_app_screen);
        appIcon = (ImageView) findViewById(R.id.icon);
        appName = (TextView) findViewById(R.id.title);
        lastTimeUsed = (TextView) findViewById(R.id.last_used);

    }
}

package com.zammle2009wtfgmail.utilityhelper;



import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class History extends AppCompatActivity implements UsageContract.View {

    private ProgressBar progressBar;
    private TextView permissionMessage;

    private UsageContract.Presenter presenter;
    private UsageStatAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        final Button back = (Button) findViewById(R.id.BackButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backButton = new Intent(History.this, MainActivity.class);

                startActivity(backButton);
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        permissionMessage = (TextView) findViewById(R.id.grant_permission_message);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UsageStatAdapter();
        recyclerView.setAdapter(adapter);

        presenter = new UsagePresenter(this, this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        showProgressBar(true);
        presenter.retrieveUsageStats();
    }

    @Override
    public void onUsageStatsRetrieved(List<UsageStatsWrapper> list) {
        showProgressBar(false);
        permissionMessage.setVisibility(GONE);
        adapter.setList(list);
    }

    @Override
    public void onUserHasNoPermission() {
        showProgressBar(false);
        permissionMessage.setVisibility(VISIBLE);
    }

    private void showProgressBar(boolean show) {
        if (show) {
            progressBar.setVisibility(VISIBLE);
        } else {
            progressBar.setVisibility(GONE);
        }
    }
}



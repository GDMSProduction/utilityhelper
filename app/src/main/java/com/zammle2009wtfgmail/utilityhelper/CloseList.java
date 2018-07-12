package com.zammle2009wtfgmail.utilityhelper;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class CloseList extends AppCompatActivity implements UsageContract.View{


    public Button listsave, listload;


    private UsageContract.Presenter presenter;
    private UsageStatAdapter adapter;



////////////////////////////////////////////////////////////////////////////////
/////////////////// ON CREATE //////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close_list);


        listsave = (Button) findViewById(R.id.buttonsave);
        listload = (Button) findViewById(R.id.buttonload);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UsageStatAdapter();
        recyclerView.setAdapter(adapter);

        listload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        listsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }


        @Override
        protected void onResume()
        {
        super.onResume();

        presenter.retrieveUsageStats();
       }

    @Override
    public boolean onQueryTextChange(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public void onEditStarted() {

    }

    @Override
    public void onEditFinished() {

    }

    @Override
        public void onUsageStatsRetrieved(List<UsageStatsWrapper> list)
        {

           for (int i = 0; i < list.size(); ++i)
           {

           }

        adapter.setList(list);


        }

        @Override
        public void onUserHasNoPermission()
        {

        }

        private void showProgressBar(boolean show)
        {

        }
}









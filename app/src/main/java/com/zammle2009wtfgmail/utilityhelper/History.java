package com.zammle2009wtfgmail.utilityhelper;



import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.app.SearchManager;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;


public class History extends AppCompatActivity implements UsageContract.View, android.support.v7.widget.SearchView.OnQueryTextListener{

    private ProgressBar progressBar;
    private TextView permissionMessage;

    private UsageContract.Presenter presenter;

    static List <UsageStatsWrapper> JakeList = new ArrayList <>();

    private UsageStatAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Hardware_Spec.timer = 601;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        permissionMessage = (TextView) findViewById(R.id.grant_permission_message);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        toolbar.setTitle("History List");

        SearchView searches = findViewById(R.id.search);




        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new UsageStatAdapter();
        recyclerView.setAdapter(adapter);
        setSupportActionBar(toolbar);
        recyclerView.setHasFixedSize(true);

        adapter.notifyDataSetChanged();

        presenter = new UsagePresenter(this, this);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);


        searches.setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }






            @Override
            public boolean onQueryTextChange(String query) {
                query = query.toLowerCase();
                List<UsageStatsWrapper> filtered = new ArrayList<>();
                for (int i = 0; i < JakeList.size(); ++i)
                {

                    String name = JakeList.get(i).getAppName().toLowerCase();
                    if(name.contains(query)) {

                        filtered.add(JakeList.get(i));


                        adapter.setList(filtered);
                        adapter.notifyDataSetChanged();
                    }
                    if (query.isEmpty()){
                        UsageStatAdapter.list = JakeList;
                        adapter.setList(JakeList);
                        adapter.notifyDataSetChanged();
                    }




                }
                adapter.notifyDataSetChanged();
                return true;
            }






        });
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

        JakeList = list;
        adapter.notifyItemRangeRemoved(0,list.size());
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

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
package com.zammle2009wtfgmail.utilityhelper;



import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.ActivityManager;import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.ListAdapter;
import android.widget.ListView;

import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class History extends AppCompatActivity implements UsageContract.View, android.support.v7.widget.SearchView.OnQueryTextListener{

    private ProgressBar progressBar;
    private TextView permissionMessage;
    private Toolbar toolbar;

    private UsageContract.Presenter presenter;


    private UsageStatAdapter adapter;
    private Animator mAnimator;


    private List<UsageStatsWrapper> mModels;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);







        toolbar = (Toolbar) findViewById(R.id.toolBar);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        permissionMessage = (TextView) findViewById(R.id.grant_permission_message);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UsageStatAdapter();
        recyclerView.setAdapter(adapter);
        setSupportActionBar(toolbar);



        adapter.notifyDataSetChanged();


        presenter = new UsagePresenter(this, this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        try {
            final List<UsageStatsWrapper> filteredModelList = filter(mModels, query);
        }catch (Exception e){

        }
        adapter.notifyDataSetChanged();
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private static List<UsageStatsWrapper> filter(List<UsageStatsWrapper> names, String query) {
        final String lowerCaseQuery = query.toLowerCase();

        final List<UsageStatsWrapper> filteredModelList = new ArrayList<>();
        for (UsageStatsWrapper name :  names) {
            final String text = name.getAppName().toLowerCase();
            final String rank = String.valueOf(name.getUsageStats());
            if (text.contains(lowerCaseQuery) || rank.contains(lowerCaseQuery)) {
                filteredModelList.add(name);
            }
        }


        return filteredModelList;
    }

    @Override
    public void onEditStarted() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        if (progressBar.getVisibility() != View.VISIBLE) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setAlpha(0.0f);
        }

        if (mAnimator != null) {
            mAnimator.cancel();
        }

        mAnimator = ObjectAnimator.ofFloat(progressBar, View.ALPHA, 1.0f);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.start();

        recyclerView.animate().alpha(0.5f);
    }

    @Override
    public void onEditFinished() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        recyclerView.scrollToPosition(0);
        recyclerView.animate().alpha(1.0f);

        if (mAnimator != null) {
            mAnimator.cancel();
        }

        mAnimator = ObjectAnimator.ofFloat(progressBar, View.ALPHA, 0.0f);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {

            private boolean mCanceled = false;

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                mCanceled = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (!mCanceled) {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        mAnimator.start();
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



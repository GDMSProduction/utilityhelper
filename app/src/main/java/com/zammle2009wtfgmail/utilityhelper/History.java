package com.zammle2009wtfgmail.utilityhelper;



import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
<<<<<<< HEAD
=======
import android.widget.ListAdapter;
import android.widget.ListView;
>>>>>>> 91a50dd9e23db7b75e4bde046e52f7938637a5cf
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.List;

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

        // back button
        final Button back = (Button) findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backButton = new Intent(History.this, MainActivity.class);

                startActivity(backButton);
            }
        });

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
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



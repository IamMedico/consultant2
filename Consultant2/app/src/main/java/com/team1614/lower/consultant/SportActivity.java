package com.team1614.lower.consultant;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SportActivity extends AppCompatActivity {
    private RecyclerView rec;
    private List<QueAns> qaList;
    QueAnsAdapter adapter;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edu);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("အားကစား");
        qaList = new ArrayList<>();
        CategoryDatabaseHandler dbhandler = new CategoryDatabaseHandler(this);
        qaList = dbhandler.getAllContact("Sport");
        adapter = new QueAnsAdapter(this, qaList, "Sport");
        rec = (RecyclerView) findViewById(R.id.edu_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(SportActivity.this);
        rec.setLayoutManager(mLayoutManager);
        rec.setItemAnimator(new DefaultItemAnimator());
        rec.setAdapter(adapter);
    }

}


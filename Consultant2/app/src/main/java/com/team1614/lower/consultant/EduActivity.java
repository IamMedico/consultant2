package com.team1614.lower.consultant;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class EduActivity extends AppCompatActivity {
    private RecyclerView rec;
    //private QueAnsAdapter adapter;
    private List<QueAns> qaList;
    QueAnsAdapter adapter;
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edu);
        actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("ပညာေရး");
        qaList = new ArrayList<>();
        CategoryDatabaseHandler dbhandler = new CategoryDatabaseHandler(this);
        qaList = dbhandler.getAllContact("Education");
        adapter = new QueAnsAdapter(this,qaList,"Education");
        rec = (RecyclerView) findViewById(R.id.edu_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(EduActivity.this);
        rec.setLayoutManager(mLayoutManager);
        rec.setItemAnimator(new DefaultItemAnimator());
        rec.setAdapter(adapter);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

package com.team1614.lower.consultant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;


import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private RecyclerView rec;
    private QueAnsAdapter adapters;
    private List<QueAns> qaList;
    MaterialSearchView searchView;
    String[] QueArry;
    QueAns qa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        searchView = (MaterialSearchView) findViewById(R.id.msearch1);
        rec = (RecyclerView) findViewById(R.id.Search_recycler_view1);
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                rec.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onSearchViewClosed() {
                if(rec.getVisibility()==View.VISIBLE)
                {

                }else{Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
        searchView.setFocusable(true);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchDataShow(query);
                return false;
            }
            public void searchDataShow(String query){
                CategoryDatabaseHandler dbhandlers = new CategoryDatabaseHandler(SearchActivity.this);
                qaList = dbhandlers.getAll(query);
                adapters = new QueAnsAdapter(SearchActivity.this, qaList, "NULL");
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(SearchActivity.this);
                rec.setLayoutManager(mLayoutManager);
                rec.setItemAnimator(new DefaultItemAnimator());
                rec.setAdapter(adapters);
                rec.setVisibility(View.VISIBLE);
                searchView.closeSearch();
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                CategoryDatabaseHandler dbhandlers1 = new CategoryDatabaseHandler(SearchActivity.this);
                qaList = dbhandlers1.getAll(newText);
                QueArry = new String[qaList.size()];
                for (int i = 0; i < qaList.size(); i++) {
                    qa = qaList.get(i);
                    QueArry[i] = qa.getQue().toLowerCase();
                    searchView.setSuggestions(QueArry);
                    searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            searchDataShow(QueArry[i]);

                        }
                    });


                }
                return false;

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem search = menu.findItem(R.id.serach);
        searchView.setMenuItem(search);
        searchView.showSearch();
        return true;
    }
}

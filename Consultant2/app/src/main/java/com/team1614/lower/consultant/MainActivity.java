package com.team1614.lower.consultant;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    String DB_PATH = "/data/data/com.team1614.lower.consultant/databases/";
    String DB_NAME = "Consultant.db";
    private RecyclerView recyclerView;
    private AlbumsAdapter adapter;
    private List<Album> albumList;
    List<String> list;
    Album qa;
    CollapsingToolbarLayout collapsingToolbarLayout;
    AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        qa = new Album();
        recyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
     //   appBarLayout.setBackgroundColor(Color.parseColor("#4682b4"));

        albumList = new ArrayList<>();
        Log.i("DATABASE EXIST : ", "" + checkDataBase());
        if (!checkDataBase()) {
            if (CreateDir_NotExists(DB_PATH))
                copyDataBase();
        }


        CategoryDatabaseHandler dbhandler = new CategoryDatabaseHandler(this);
        albumList = dbhandler.getAllMainContact();
        adapter = new AlbumsAdapter(this, albumList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        VideoView v = (VideoView) findViewById(R.id.backdrop);
        //MediaPlayer mdplayer;
        Uri path = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.aaa);
        v.setVideoURI(path);
        v.start();


        // To Show Cover Of Collapsing Toolbar
//        try {
//            Glide.with(this).load(R.drawable.know3).into((ImageView) findViewById(R.id.backdrop));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        collapsingToolbarLayout.setTitle(" ");
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;


            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle("အရူး လြယ္အိတ္");
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.online_cc);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
    }

    private void copyDataBase() {

        ContextWrapper cw = new ContextWrapper(getApplicationContext());

        Log.i("Database", "New database is being copied to device!");
        byte[] buffer = new byte[1024];
        OutputStream myOutput = null;
        int length;

        InputStream myInput = null;
        try {
            myInput = MainActivity.this.getAssets().open(DB_NAME);

            myOutput = new FileOutputStream(DB_PATH + DB_NAME);
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myOutput.close();
            myOutput.flush();
            myInput.close();
            Log.i("Database", "New database has been copied to device!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    public static boolean CreateDir_NotExists(String path) {
        boolean ret = true;

        File file = new File(path);
        if (!file.exists()) {

            if (!file.mkdirs()) {
                Log.e("CREATE DIR : ", "Problem creating folder");
                ret = false;
            }
        }
        return ret;
    }

    private static final int TIME_INTERVAL = 1000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(getBaseContext(), "Press one more to exit from Crazy BAG", Toast.LENGTH_SHORT).show();
        }

        mBackPressed = System.currentTimeMillis();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        VideoView v = (VideoView) findViewById(R.id.backdrop);
        //MediaPlayer mdplayer;
        Uri path = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.aaa);
        v.setVideoURI(path);
        v.start();
        collapsingToolbarLayout.setTitle(" ");
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;


            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle("အရူး လြယ္အိတ္");
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       /*  Handle action bar item clicks here. The action bar will
         automatically handle clicks on the Home/Up button, so long
         as you specify a parent activity in AndroidManifest.xml.*/


        int id = item.getItemId();
        if (id == R.id.maserach) {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);

        }
        if (id == R.id.feedback) {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto","developermyanmar2017@gmail.com", null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
            startActivity(Intent.createChooser(emailIntent, "Send email..."));

        }
        if (id == R.id.help) {
            Intent intent = new Intent(MainActivity.this, HelpActivity.class);
            startActivity(intent);

        }

        if (id == R.id.action_settings) {
            this.finish();
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);


        }
        if (id == R.id.action_fav) {
            Intent intent = new Intent(MainActivity.this, Favourite.class);
            startActivity(intent);

        }
        if (id == R.id.action_share) {
            String shareTxt = getResources().getString(R.string.share);
            Log.i("Share : ", shareTxt);
            share(MainActivity.this, "", shareTxt, "", "Share");

        }
        if (id == R.id.about) {

//            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this,R.style.MyAlertDialogStyle);
//            builder.setView(R.layout.custom_couple_about);
//            LayoutInflater inflater = getLayoutInflater();
//            View dialogView = inflater.inflate(R.layout.custom_couple_about, null);
//            ImageView couple_about = (ImageView) dialogView.findViewById(R.id.couple_about);
//            couple_about.setImageResource(R.drawable.anal_love_rupar);
//            builder.setView(dialogView)
//
//                    .create().show();

            AlertDialog.Builder builderFont = new AlertDialog.Builder(MainActivity.this);
            builderFont.setTitle("အရူး လြယ္အိတ္");
            builderFont.setIcon(R.drawable.mc);
            builderFont.setMessage(" အခ်င္းခ်င္း အသိျမင္ ဖလွယ္နိုင္ျခင္း အလို႔ငွာ ");
            builderFont.show();
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Beauty) {
            Intent intent = new Intent(MainActivity.this, BeautyActivity.class);
            startActivity(intent);

            // set the toolbar title
        } else if (id == R.id.Edu) {
            Intent intent = new Intent(MainActivity.this, EduActivity.class);
            startActivity(intent);

        } else if (id == R.id.economic) {
            Intent intent = new Intent(MainActivity.this, EcoActivity.class);
            startActivity(intent);

        } else if (id == R.id.health) {
            Intent intent = new Intent(MainActivity.this, HealthActivity.class);
            startActivity(intent);

        } else if (id == R.id.tech) {
            Intent intent = new Intent(MainActivity.this, TechActivity.class);
            startActivity(intent);

        } else if (id == R.id.sport) {
            Intent intent = new Intent(MainActivity.this, SportActivity.class);
            startActivity(intent);

        } else if (id == R.id.love) {
            Intent intent = new Intent(MainActivity.this, LoveActivity.class);
            startActivity(intent);

        } else if (id == R.id.bartaryay) {
            Intent intent = new Intent(MainActivity.this, BartharActivity.class);
            startActivity(intent);

        } else if (id == R.id.other) {
            Intent intent = new Intent(MainActivity.this, OtherActivity.class);
            startActivity(intent);
        } else if (id == R.id.mshare) {

            String shareTxt = getResources().getString(R.string.share);
            Log.i("Share : ", shareTxt);
            share(MainActivity.this, "", shareTxt, "", "Share");

        } else if (id == R.id.mabout) {
            AlertDialog.Builder builderFont = new AlertDialog.Builder(MainActivity.this);
            builderFont.setTitle("Crazy BAG(အရူးလြယ္အိတ္)");
            builderFont.setIcon(R.drawable.mc);
            builderFont.setMessage(" အခ်င္းခ်င္း အသိျမင္ ဖလွယ္နိုင္ျခင္း အလို႔ငွာ ");
            builderFont.show();

        } else if (id == R.id.online) {
            Intent intent = new Intent(MainActivity.this, SignInActivity.class);
            startActivity(intent);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //To share text
    public static void share(Context context, String text, String subject, String title, String dialogHeaderText) {
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
        intent.putExtra(android.content.Intent.EXTRA_TEXT, subject);
        intent.putExtra(Intent.EXTRA_TITLE, title);
        context.startActivity(Intent.createChooser(intent, dialogHeaderText));
    }

public void feedback(){
   /* Intent sendIntent = new Intent(android.content.Intent.ACTION_VIEW);
    sendIntent.setType("plain/text");
    sendIntent.setData(Uri.parse("monophoto1500@gmail.com"));
    sendIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
    sendIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{""});
    sendIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
    sendIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
    startActivity(sendIntent);*/


}
}


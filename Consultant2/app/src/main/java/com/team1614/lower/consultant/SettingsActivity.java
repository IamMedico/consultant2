package com.team1614.lower.consultant;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class SettingsActivity extends AppCompatActivity {
    LinearLayout linearLayout;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setTitle("Settings");
        sharedPreferences = this.getSharedPreferences("fontsize", 0);
        linearLayout = (LinearLayout) findViewById(R.id.fsl);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String font[] = {"Small", "Medium", "Large", "Larger", "Huge"};
                AlertDialog.Builder builderFont = new AlertDialog.Builder(SettingsActivity.this);
                builderFont.setTitle("Choose Font Size");
                builderFont.setItems(font, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putInt("font", which);
                        editor.commit();
                        editor.apply();
                        startActivity(new Intent(SettingsActivity.this, MainActivity.class));
                        finish();

                    }
                });
                builderFont.create().show();

            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SettingsActivity.this, MainActivity.class));
        finish();
        super.onBackPressed();

    }

}


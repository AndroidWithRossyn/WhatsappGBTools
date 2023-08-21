package com.statuswa.fasttalkchat.toolsdownload.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.statuswa.fasttalkchat.toolsdownload.R;

public class ExitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit);
    }
    @Override
    public void onBackPressed() {
        finishAffinity();
        System.exit(0);
    }
}
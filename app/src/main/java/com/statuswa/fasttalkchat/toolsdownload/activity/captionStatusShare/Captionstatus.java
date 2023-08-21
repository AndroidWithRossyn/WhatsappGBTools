package com.statuswa.fasttalkchat.toolsdownload.activity.captionStatusShare;

import static com.pesonal.adsdk.AppManage.ADMOB_N;
import static com.pesonal.adsdk.AppManage.FACEBOOK_N;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.statuswa.fasttalkchat.toolsdownload.R;
import com.pesonal.adsdk.AppManage;

public class Captionstatus extends AppCompatActivity {
    String name;
    String[] items;
    ListView listViews;
    int position;
    ImageView back_btn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captionstatus);
        AppManage.getInstance(Captionstatus.this).showNative((ViewGroup) findViewById(R.id.native_ads), ADMOB_N[0], FACEBOOK_N[0]);
        this.name = getIntent().getStringExtra("image");
//        getSupportActionBar().setTitle(this.name);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        back_btn=findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManage.getInstance(Captionstatus.this).showInterstitialAd(Captionstatus.this, new AppManage.MyCallback() {
                    public void callbackCall() {
                        Captionstatus.super.onBackPressed();
                    }
                }, AppManage.ADMOB,AppManage.app_mainClickCntSwAd);
            }
        });
        this.position = getIntent().getIntExtra("P", 0);
        if (this.position == 0) {
            this.items = getResources().getStringArray(R.array.s_best);
        } else if (this.position == 1) {
            this.items = getResources().getStringArray(R.array.s_clever);
        } else if (this.position == 2) {
            this.items = getResources().getStringArray(R.array.s_cool);
        } else if (this.position == 3) {
            this.items = getResources().getStringArray(R.array.s_cute);
        } else if (this.position == 4) {
            this.items = getResources().getStringArray(R.array.s_fitness);
        } else if (this.position == 5) {
            this.items = getResources().getStringArray(R.array.s_funny);
        } else if (this.position == 6) {
            this.items = getResources().getStringArray(R.array.s_life);
        } else if (this.position == 7) {
            this.items = getResources().getStringArray(R.array.s_love);
        } else if (this.position == 8) {
            this.items = getResources().getStringArray(R.array.s_motivation);
        } else if (this.position == 9) {
            this.items = getResources().getStringArray(R.array.s_sad);
        } else if (this.position == 10) {
            this.items = getResources().getStringArray(R.array.s_savage);
        } else if (this.position == 11) {
            this.items = getResources().getStringArray(R.array.s_selfie);
        } else if (this.position == 12) {
            this.items = getResources().getStringArray(R.array.s_song);
        }
        this.listViews = findViewById(R.id.simpleListView);
        this.listViews.setAdapter(new CustomAdapter1(getApplicationContext(), this.items));
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }


    //Back press method
    public void onBackPressed() {
        AppManage.getInstance(Captionstatus.this).showInterstitialAd(Captionstatus.this, new AppManage.MyCallback() {
            public void callbackCall() {
                Captionstatus.super.onBackPressed();
                finish();
            }
        }, AppManage.ADMOB,AppManage.app_mainClickCntSwAd);
    }
}

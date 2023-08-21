package com.statuswa.fasttalkchat.toolsdownload.activity.captionStatusShare;

import static com.pesonal.adsdk.AppManage.ADMOB_N;
import static com.pesonal.adsdk.AppManage.FACEBOOK_N;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.statuswa.fasttalkchat.toolsdownload.R;
import com.pesonal.adsdk.AppManage;

public class Captionitem extends AppCompatActivity {
    String[] logos = new String[]{"Best", "Clever", "Cool", "Cute", "Fitness", "Funny", "Life", "Love", "Motivation", "Sad", "Savage", "Selfie", "Song"};
    GridView simpleGrid;
    ImageView back_btn;


    //It's called While click on gridview
    private class simpleGridListner implements OnItemClickListener {
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            Intent intent = new Intent(Captionitem.this, Captionstatus.class);
            intent.putExtra("image", Captionitem.this.logos[position]);
            intent.putExtra("P", position);
            Captionitem.this.startActivity(intent);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captionitem);
        AppManage.getInstance(Captionitem.this).loadInterstitialAd(this);
        AppManage.getInstance(Captionitem.this).showNative((ViewGroup) findViewById(R.id.native_ads), ADMOB_N[0], FACEBOOK_N[0]);
//        getSupportActionBar().setTitle("Caption Status");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        back_btn=findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManage.getInstance(Captionitem.this).showInterstitialAd(Captionitem.this, new AppManage.MyCallback() {
                    public void callbackCall() {
                        Captionitem.super.onBackPressed();
                    }
                }, AppManage.ADMOB,AppManage.app_mainClickCntSwAd);
            }
        });
        this.simpleGrid = findViewById(R.id.simpleGridView);
        this.simpleGrid.setAdapter(new CustomAdapter(getApplicationContext(), this.logos));
        this.simpleGrid.setOnItemClickListener(new simpleGridListner());
    }


    //Option menu button click
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }


    //Back press method
    public void onBackPressed() {
        AppManage.getInstance(Captionitem.this).showInterstitialAd(Captionitem.this, new AppManage.MyCallback() {
            public void callbackCall() {
                Captionitem.super.onBackPressed();
                finish();
            }
        }, AppManage.ADMOB,AppManage.app_mainClickCntSwAd);
    }
}

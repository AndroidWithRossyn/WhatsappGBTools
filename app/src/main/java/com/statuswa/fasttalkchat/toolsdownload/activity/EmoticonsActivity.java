package com.statuswa.fasttalkchat.toolsdownload.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.statuswa.fasttalkchat.toolsdownload.R;
import com.statuswa.fasttalkchat.toolsdownload.adapter.MyFragmentAdapter;
import com.pesonal.adsdk.AppManage;

public class EmoticonsActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private MyFragmentAdapter adapter;
    ImageView back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emoticons);
        viewPager2 = (ViewPager2) findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManage.getInstance(EmoticonsActivity.this).showInterstitialAd(EmoticonsActivity.this, new AppManage.MyCallback() {
                    public void callbackCall() {
                        EmoticonsActivity.super.onBackPressed();
                    }
                }, AppManage.ADMOB,AppManage.app_mainClickCntSwAd);

            }
        });
        tabLayout.addTab(tabLayout.newTab().setText("Happy"));
        tabLayout.addTab(tabLayout.newTab().setText("Sad"));
        tabLayout.addTab(tabLayout.newTab().setText("Sorry"));
        tabLayout.addTab(tabLayout.newTab().setText("Love"));
        tabLayout.addTab(tabLayout.newTab().setText("Suprise"));
        tabLayout.addTab(tabLayout.newTab().setText("Scare"));
        tabLayout.addTab(tabLayout.newTab().setText("Music"));
        tabLayout.addTab(tabLayout.newTab().setText("Dance"));
        tabLayout.addTab(tabLayout.newTab().setText("Smug"));
        tabLayout.addTab(tabLayout.newTab().setText("Failure"));
        tabLayout.addTab(tabLayout.newTab().setText("Animal"));
        tabLayout.addTab(tabLayout.newTab().setText("Evil"));
        tabLayout.addTab(tabLayout.newTab().setText("Angry"));
        tabLayout.addTab(tabLayout.newTab().setText("Confuse"));
        tabLayout.addTab(tabLayout.newTab().setText("Kiss"));
        tabLayout.addTab(tabLayout.newTab().setText("Shy"));
        tabLayout.addTab(tabLayout.newTab().setText("Tired"));
        tabLayout.addTab(tabLayout.newTab().setText("Wink"));
        FragmentManager fragmentManager = getSupportFragmentManager();
        adapter = new MyFragmentAdapter(fragmentManager , getLifecycle());
        viewPager2.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });


    }
    public void onBackPressed() {
        AppManage.getInstance(EmoticonsActivity.this).showInterstitialAd(EmoticonsActivity.this, new AppManage.MyCallback() {
            public void callbackCall() {
                EmoticonsActivity.super.onBackPressed();
            }
        }, AppManage.ADMOB,AppManage.app_mainClickCntSwAd);
    }
}
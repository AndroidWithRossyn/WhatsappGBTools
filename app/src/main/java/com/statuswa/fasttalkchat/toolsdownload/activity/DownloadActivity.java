package com.statuswa.fasttalkchat.toolsdownload.activity;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

import static com.statuswa.fasttalkchat.toolsdownload.utils.Utils.createFileFolder;
import static com.pesonal.adsdk.AppManage.ADMOB_N;
import static com.pesonal.adsdk.AppManage.FACEBOOK_N;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.statuswa.fasttalkchat.toolsdownload.Fragment.AllinOneGalleryFragment;
import com.statuswa.fasttalkchat.toolsdownload.Fragment.FBDownloadedFragment;
import com.statuswa.fasttalkchat.toolsdownload.Fragment.InstaDownloadedFragment;
import com.statuswa.fasttalkchat.toolsdownload.Fragment.LikeeDownloadedFragment;
import com.statuswa.fasttalkchat.toolsdownload.Fragment.RoposoDownloadedFragment;
import com.statuswa.fasttalkchat.toolsdownload.Fragment.SharechatDownloadedFragment;
import com.statuswa.fasttalkchat.toolsdownload.Fragment.SnackVideoDownloadedFragment;
import com.statuswa.fasttalkchat.toolsdownload.Fragment.TikTokDownloadedFragment;
import com.statuswa.fasttalkchat.toolsdownload.Fragment.TwitterDownloadedFragment;
import com.statuswa.fasttalkchat.toolsdownload.Fragment.WhatsAppDowndlededFragment;
import com.statuswa.fasttalkchat.toolsdownload.R;
import com.statuswa.fasttalkchat.toolsdownload.databinding.ActivityDownloadBinding;
import com.statuswa.fasttalkchat.toolsdownload.utils.AppLangSessionManager;
import com.statuswa.fasttalkchat.toolsdownload.utils.Utils;
import com.pesonal.adsdk.AppManage;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DownloadActivity extends AppCompatActivity {
    DownloadActivity activity;
    ActivityDownloadBinding binding;
    ImageView imBack;

    AppLangSessionManager appLangSessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_download);
        activity = this;
        imBack=findViewById(R.id.imBack);
        imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManage.getInstance(DownloadActivity.this).showInterstitialAd(DownloadActivity.this, new AppManage.MyCallback() {
                    public void callbackCall() {
                        Intent intent=new Intent(DownloadActivity.this,DashboardActivity.class);
                        startActivity(intent);
                    }
                }, AppManage.ADMOB,AppManage.app_mainClickCntSwAd);

            }
        });
        AppManage.getInstance(activity).showNative((ViewGroup) findViewById(R.id.native_ads), ADMOB_N[0], FACEBOOK_N[0]);

        appLangSessionManager = new AppLangSessionManager(activity);
        setLocale(appLangSessionManager.getLanguage());

        initViews();
    }

    public void initViews() {
        setupViewPager(binding.viewpager);
        binding.tabs.setupWithViewPager(binding.viewpager);
        binding.imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        for (int i = 0; i < binding.tabs.getTabCount(); i++) {
            TextView tv=(TextView) LayoutInflater.from(activity).inflate(R.layout.custom_tab,null);
            binding.tabs.getTabAt(i).setCustomView(tv);
        }

        binding.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        createFileFolder();
    }



    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(activity.getSupportFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(new AllinOneGalleryFragment(Utils.ROOTDIRECTORYJOSHSHOW), "Josh");
        adapter.addFragment(new AllinOneGalleryFragment(Utils.ROOTDIRECTORYCHINGARISHOW), "Chingari");
        adapter.addFragment(new AllinOneGalleryFragment(Utils.ROOTDIRECTORYMITRONSHOW), "Mitron");
        adapter.addFragment(new SnackVideoDownloadedFragment(), "Snack Video");
        adapter.addFragment(new SharechatDownloadedFragment(), "Sharechat");
        adapter.addFragment(new RoposoDownloadedFragment(), "Roposo");
        adapter.addFragment(new InstaDownloadedFragment(), "Instagram");
        adapter.addFragment(new WhatsAppDowndlededFragment(), "Whatsapp");
        adapter.addFragment(new TikTokDownloadedFragment(), "TikTok");
        adapter.addFragment(new FBDownloadedFragment(), "Facebook");
        adapter.addFragment(new TwitterDownloadedFragment(), "Twitter");
        adapter.addFragment(new LikeeDownloadedFragment(), "Likee");
        adapter.addFragment(new AllinOneGalleryFragment(Utils.ROOTDIRECTORYMXSHOW), "MXTakaTak");
        adapter.addFragment(new AllinOneGalleryFragment(Utils.ROOTDIRECTORYMOJSHOW), "Moj");

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }
    @Override
    public void onBackPressed() {
        AppManage.getInstance(DownloadActivity.this).showInterstitialAd(DownloadActivity.this, new AppManage.MyCallback() {
            public void callbackCall() {
                Intent intent=new Intent(DownloadActivity.this,DashboardActivity.class);
                startActivity(intent);
            }
        }, AppManage.ADMOB,AppManage.app_mainClickCntSwAd);
    }
}
package com.statuswa.fasttalkchat.toolsdownload.activity;

import static com.pesonal.adsdk.AppManage.ADMOB_N;
import static com.pesonal.adsdk.AppManage.FACEBOOK_N;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.statuswa.fasttalkchat.toolsdownload.R;
import com.statuswa.fasttalkchat.toolsdownload.BuildConfig;
import com.statuswa.fasttalkchat.toolsdownload.activity.captionStatusShare.Captionitem;
import com.statuswa.fasttalkchat.toolsdownload.activity.cleaner.WACleanMainActivity;
import com.statuswa.fasttalkchat.toolsdownload.activity.fackChat.MainFackChat;
import com.statuswa.fasttalkchat.toolsdownload.databinding.ActivityDashboardBinding;
import com.pesonal.adsdk.AppManage;

public class DashboardActivity extends AppCompatActivity {
    ActivityDashboardBinding binding;
    TextView saved_status_txt;
    DrawerLayout draw_lay;
    ImageView menu_btn;
    RelativeLayout saved_status_btn, share_btn, rate_us_btn, privacy_policy_btn;
    String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    ImageView status_saver, direct_chat, whatsapp_web, emoticons, text_to_emoji, deleted_txt, shake_to_open, text_repeater, caption_status, whats_clean;
    String CopyKey = "";
    String CopyValue = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        AppManage.getInstance(DashboardActivity.this).loadInterstitialAd(this);
        AppManage.getInstance(DashboardActivity.this).showNative((ViewGroup) findViewById(R.id.native_ads), ADMOB_N[0], FACEBOOK_N[0]);
        status_saver = findViewById(R.id.status_saver);
        direct_chat = findViewById(R.id.direct_chat);
        saved_status_txt = findViewById(R.id.saved_status_txt);
        draw_lay = findViewById(R.id.draw_lay);
        whatsapp_web = findViewById(R.id.whatsapp_web);
        emoticons = findViewById(R.id.emoticons);
        deleted_txt = findViewById(R.id.fake_chat);
        text_to_emoji = findViewById(R.id.text_to_emoji);
        shake_to_open = findViewById(R.id.shake_to_open);
        text_repeater = findViewById(R.id.text_repeater);
        caption_status = findViewById(R.id.caption_status);
        whats_clean = findViewById(R.id.whats_cleaner);
        menu_btn = findViewById(R.id.menu_btn);
        shake_to_open = findViewById(R.id.shake_to_open);
        saved_status_btn = findViewById(R.id.saved_status_btn);
        share_btn = findViewById(R.id.share_btn);
        rate_us_btn = findViewById(R.id.rate_us_btn);
        privacy_policy_btn = findViewById(R.id.privacy_policy_btn);
        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                draw_lay.openDrawer(Gravity.RIGHT);
            }
        });
        saved_status_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManage.getInstance(DashboardActivity.this).showInterstitialAd(DashboardActivity.this, new AppManage.MyCallback() {
                    public void callbackCall() {
                        Intent intent = new Intent(DashboardActivity.this, DownloadActivity.class);
                        startActivity(intent);
                    }
                }, AppManage.ADMOB,AppManage.app_mainClickCntSwAd);

            }
        });
        caption_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManage.getInstance(DashboardActivity.this).showInterstitialAd(DashboardActivity.this, new AppManage.MyCallback() {
                    public void callbackCall() {
                        Intent intent = new Intent(DashboardActivity.this, Captionitem.class);
                        startActivity(intent);
                    }
                }, AppManage.ADMOB,AppManage.app_mainClickCntSwAd);

            }
        });
        whats_clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManage.getInstance(DashboardActivity.this).showInterstitialAd(DashboardActivity.this, new AppManage.MyCallback() {
                    public void callbackCall() {
                        Intent intent = new Intent(DashboardActivity.this, WACleanMainActivity.class);
                        startActivity(intent);
                    }
                }, AppManage.ADMOB,AppManage.app_mainClickCntSwAd);

            }
        });
        shake_to_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManage.getInstance(DashboardActivity.this).showInterstitialAd(DashboardActivity.this, new AppManage.MyCallback() {
                    public void callbackCall() {
                        Intent intent = new Intent(DashboardActivity.this, ShakerActivity.class);
                        startActivity(intent);
                    }
                }, AppManage.ADMOB,AppManage.app_mainClickCntSwAd);

            }
        });
        saved_status_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManage.getInstance(DashboardActivity.this).showInterstitialAd(DashboardActivity.this, new AppManage.MyCallback() {
                    public void callbackCall() {
                        Intent intent = new Intent(DashboardActivity.this, DownloadActivity.class);
                        startActivity(intent);
                    }
                }, AppManage.ADMOB,AppManage.app_mainClickCntSwAd);

            }
        });
        deleted_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManage.getInstance(DashboardActivity.this).showInterstitialAd(DashboardActivity.this, new AppManage.MyCallback() {
                    public void callbackCall() {
                        Intent intent = new Intent(DashboardActivity.this, MainFackChat.class);
                        startActivity(intent);
                    }
                }, AppManage.ADMOB,AppManage.app_mainClickCntSwAd);

            }
        });
        text_to_emoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManage.getInstance(DashboardActivity.this).showInterstitialAd(DashboardActivity.this, new AppManage.MyCallback() {
                    public void callbackCall() {
                        Intent intent = new Intent(DashboardActivity.this, TextToEmojiActivity.class);
                        startActivity(intent);
                    }
                }, AppManage.ADMOB,AppManage.app_mainClickCntSwAd);

            }
        });
        rate_us_btn.setOnClickListener(v -> {
            draw_lay.closeDrawers();
            launchMarket();
        });
        privacy_policy_btn.setOnClickListener(v -> {
            draw_lay.closeDrawers();
            gotoUrl("https://mideyemediainc.blogspot.com/2023/02/mideye-media-inc_55.html");
        });
        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "WA GB Tools 2022");
                    String shareMessage = "\nLet me recommend you this application\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch (Exception e) {
                    //e.toString();
                }
            }
        });

        emoticons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManage.getInstance(DashboardActivity.this).showInterstitialAd(DashboardActivity.this, new AppManage.MyCallback() {
                    public void callbackCall() {
                        Intent intent = new Intent(DashboardActivity.this, EmoticonsActivity.class);
                        startActivity(intent);
                    }
                }, AppManage.ADMOB,AppManage.app_mainClickCntSwAd);
            }
        });
        text_repeater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManage.getInstance(DashboardActivity.this).showInterstitialAd(DashboardActivity.this, new AppManage.MyCallback() {
                    public void callbackCall() {
                        Intent intent = new Intent(DashboardActivity.this, TextRepeaterActivity.class);
                        startActivity(intent);
                    }
                }, AppManage.ADMOB,AppManage.app_mainClickCntSwAd);


            }
        });
        direct_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManage.getInstance(DashboardActivity.this).showInterstitialAd(DashboardActivity.this, new AppManage.MyCallback() {
                    public void callbackCall() {
                        Intent intent = new Intent(DashboardActivity.this, DirectChatActivity.class);
                        startActivity(intent);
                    }
                }, AppManage.ADMOB,AppManage.app_mainClickCntSwAd);


            }
        });
        whatsapp_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManage.getInstance(DashboardActivity.this).showInterstitialAd(DashboardActivity.this, new AppManage.MyCallback() {
                    public void callbackCall() {
                        Intent intent = new Intent(DashboardActivity.this, WebActivity.class);
                        startActivity(intent);
                    }
                }, AppManage.ADMOB,AppManage.app_mainClickCntSwAd);


            }
        });
        status_saver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManage.getInstance(DashboardActivity.this).showInterstitialAd(DashboardActivity.this, new AppManage.MyCallback() {
                    public void callbackCall() {
                        Intent intent = new Intent(DashboardActivity.this, StatusSaverOfAllAppActivity.class);
                        startActivity(intent);
                    }
                }, AppManage.ADMOB,AppManage.app_mainClickCntSwAd);

            }
        });
    }

    private void launchMarket() {
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, " unable to find market app", Toast.LENGTH_LONG).show();
        }
    }

    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(DashboardActivity.this, ThankuActivity.class);
        startActivity(intent);

    }


}
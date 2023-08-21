package com.statuswa.fasttalkchat.toolsdownload.activity;

import static com.pesonal.adsdk.AppManage.ADMOB_N;
import static com.pesonal.adsdk.AppManage.FACEBOOK_N;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pesonal.adsdk.AppManage;
import com.statuswa.fasttalkchat.toolsdownload.R;

public class PrivacyPolicyActivity extends AppCompatActivity {
    RelativeLayout agree_btn;
    CheckBox checkbox;
    TextView goto_privacy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        agree_btn = findViewById(R.id.agree_btn);
        checkbox = findViewById(R.id.checkBox);
        goto_privacy = findViewById(R.id.goto_privacy);
        AppManage.getInstance(PrivacyPolicyActivity.this).loadInterstitialAd(this);
        AppManage.getInstance(PrivacyPolicyActivity.this).showNative((ViewGroup) findViewById(R.id.native_ads), ADMOB_N[0], FACEBOOK_N[0]);
        agree_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkbox.isChecked() == true) {
                    AppManage.getInstance(PrivacyPolicyActivity.this).showInterstitialAd(PrivacyPolicyActivity.this, new AppManage.MyCallback() {
                        public void callbackCall() {
                            Intent intent = new Intent(PrivacyPolicyActivity.this, DashboardActivity.class);
                            startActivity(intent);
                        }
                    }, AppManage.ADMOB,AppManage.app_mainClickCntSwAd);
                } else {
                    Toast.makeText(PrivacyPolicyActivity.this, "Please Accept Privacy Policy", Toast.LENGTH_SHORT).show();
                }


            }
        });
        goto_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl("https://mideyemediainc.blogspot.com/2023/02/mideye-media-inc_55.html");
            }
        });


    }

    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
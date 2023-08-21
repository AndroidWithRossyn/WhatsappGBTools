package com.statuswa.fasttalkchat.toolsdownload.activity;

import static com.pesonal.adsdk.AppManage.ADMOB_N;
import static com.pesonal.adsdk.AppManage.FACEBOOK_N;
import static com.statuswa.fasttalkchat.toolsdownload.activity.VPNActivity.stopVpn;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.statuswa.fasttalkchat.toolsdownload.R;
import com.pesonal.adsdk.AppManage;

public class ThankuActivity extends AppCompatActivity {
    TextView yes, no, rateus;
    private Dialog adprogress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanku);
        AppManage.getInstance(ThankuActivity.this).showNative((ViewGroup) findViewById(R.id.native_ads), ADMOB_N[0], FACEBOOK_N[0]);
        AppManage.getInstance(ThankuActivity.this).showNative((ViewGroup) findViewById(R.id.native_ads1), ADMOB_N[0], FACEBOOK_N[0]);
        yes = findViewById(R.id.yes);
        no = findViewById(R.id.no);
        rateus = findViewById(R.id.rateus);
        //todo Ad Loading Dialog
        adprogress = new Dialog(ThankuActivity.this, R.style.Custom);
        adprogress.requestWindowFeature(1);
        adprogress.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        adprogress.setCancelable(false);
        adprogress.setContentView(R.layout.z1_ads_loading_dialog_inter);
        rateus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("market://details?id=" + getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                }
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopVpn();
                Intent i = new Intent(ThankuActivity.this, ExitActivity.class);
                startActivity(i);


            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ThankuActivity.this, DashboardActivity.class);
                startActivity(intent);


            }
        });
    }

}
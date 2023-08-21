package com.statuswa.fasttalkchat.toolsdownload.activity;

import static com.pesonal.adsdk.AppManage.ADMOB_N;
import static com.pesonal.adsdk.AppManage.FACEBOOK_N;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.statuswa.fasttalkchat.toolsdownload.CountryCodePicker.CountryCodePicker;
import com.statuswa.fasttalkchat.toolsdownload.R;
import com.statuswa.fasttalkchat.toolsdownload.utils.SharePreferencess;
import com.pesonal.adsdk.AppManage;

public class DirectChatActivity extends AppCompatActivity {
    CountryCodePicker spin;
    TextView send_btn;
    EditText edit_number, text_mess;
    String code;
    ImageView back_btn;
    LinearLayout native_ads2, native_ads1;
    SharePreferencess sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_chat);
        edit_number = findViewById(R.id.edit_number);
        send_btn = findViewById(R.id.send_btn);
        spin = findViewById(R.id.spin);
        text_mess = findViewById(R.id.text_mess);
        spin.setDefaultCountryUsingNameCode("in");
        back_btn = findViewById(R.id.back_btn);
        native_ads2 = findViewById(R.id.native_ads);
        sp = new SharePreferencess(this);
        AppManage.getInstance(DirectChatActivity.this).loadInterstitialAd(this);
        AppManage.getInstance(DirectChatActivity.this).showNative((ViewGroup) findViewById(R.id.native_ads), ADMOB_N[0], FACEBOOK_N[0]);
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManage.getInstance(DirectChatActivity.this).showInterstitialAd(DirectChatActivity.this, new AppManage.MyCallback() {
                    public void callbackCall() {
                        code = spin.getSelectedCountryCode();

                        if (code.isEmpty() || code.equals("")) {
                            Toast.makeText(getApplicationContext(), "please select county code!", Toast.LENGTH_SHORT).show();
                        } else if (edit_number.getText().toString().isEmpty() || edit_number.getText().toString().equals("")) {
                            Toast.makeText(getApplicationContext(), "please enter number!", Toast.LENGTH_SHORT).show();
                        } else if (text_mess.getText().toString().isEmpty() || text_mess.getText().toString().equals("")) {
                            Toast.makeText(getApplicationContext(), "please select message!", Toast.LENGTH_SHORT).show();
                        } else {
                            spin.registerPhoneNumberTextView(edit_number);
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=" + spin.getFullNumberWithPlus() + "&text=" + text_mess.getText().toString())));
                        }
                    }
                }, AppManage.ADMOB,AppManage.app_mainClickCntSwAd);

            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManage.getInstance(DirectChatActivity.this).showInterstitialAd(DirectChatActivity.this, new AppManage.MyCallback() {
                    public void callbackCall() {
                        DirectChatActivity.super.onBackPressed();
                    }
                }, AppManage.ADMOB,AppManage.app_mainClickCntSwAd);

            }
        });
    }

    @Override
    public void onBackPressed() {
        AppManage.getInstance(DirectChatActivity.this).showInterstitialAd(DirectChatActivity.this, new AppManage.MyCallback() {
            public void callbackCall() {
                DirectChatActivity.super.onBackPressed();
            }
        }, AppManage.ADMOB,AppManage.app_mainClickCntSwAd);
    }
}
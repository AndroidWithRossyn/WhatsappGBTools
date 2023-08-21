package com.statuswa.fasttalkchat.toolsdownload.activity;

import static com.pesonal.adsdk.AppManage.ADMOB_N;
import static com.pesonal.adsdk.AppManage.FACEBOOK_N;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.statuswa.fasttalkchat.toolsdownload.R;
import com.statuswa.fasttalkchat.toolsdownload.utils.SharePreferencess;
import com.statuswa.fasttalkchat.toolsdownload.utils.Utils;
import com.pesonal.adsdk.AppManage;

public class TextRepeaterActivity extends AppCompatActivity {
    TextView repeat_btn;
    EditText editText_data, editText_limit;
    SwitchMaterial newLine;
    TextView txt_data;
    String title;
    ImageView delete, share, copy;
    String str = "";
    ImageView back_btn;
    LinearLayout native_ads1, native_ads;
    SharePreferencess sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_repeater);
        repeat_btn = findViewById(R.id.repeat_btn);
        editText_data = findViewById(R.id.editText_data);
        editText_limit = findViewById(R.id.editText_limit);
        txt_data = findViewById(R.id.txt_data);
        newLine = findViewById(R.id.newLine);
        delete = findViewById(R.id.delete);
        share = findViewById(R.id.share);
        copy = findViewById(R.id.copy);
        back_btn = findViewById(R.id.back_btn);
        native_ads = findViewById(R.id.native_ads);
        title = getIntent().getStringExtra("cat");
        txt_data.setText(title);
        sp = new SharePreferencess(this);
        AppManage.getInstance(TextRepeaterActivity.this).loadInterstitialAd(this);
        AppManage.getInstance(TextRepeaterActivity.this).showNative((ViewGroup) findViewById(R.id.native_ads), ADMOB_N[0], FACEBOOK_N[0]);
        newLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newLine.isChecked()) {
                    newLine.setChecked(false);
                } else {
                    newLine.setChecked(true);
                }
            }
        });

        repeat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManage.getInstance(TextRepeaterActivity.this).showInterstitialAd(TextRepeaterActivity.this, new AppManage.MyCallback() {
                    public void callbackCall() {
                        String str = "Please Enter All Requirement";
                        if (editText_limit.getText().toString().isEmpty()) {
                            Toast.makeText(TextRepeaterActivity.this, str, Toast.LENGTH_SHORT).show();
                            return;
                        }
                        try {
                            if (newLine.isChecked()) {
                                txt_data.setText(Utils.m15861b(editText_data.getText().toString(), Integer.parseInt(editText_limit.getText().toString()), true, true, false, ""));
                            } else {
                                txt_data.setText(Utils.m15861b(editText_data.getText().toString(), Integer.parseInt(editText_limit.getText().toString()), true, false, false, ""));
                            }
                        } catch (Exception unused) {
                            Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                        }
                    }
                }, AppManage.ADMOB,AppManage.app_mainClickCntSwAd);


            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText_data.setText(str);
                editText_limit.setText(str);
                newLine.setChecked(false);
                txt_data.setText(str);
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
//                whatsappIntent.setType("text/plain");
//                whatsappIntent.setPackage("com.whatsapp");
//                whatsappIntent.putExtra(Intent.EXTRA_TEXT, title);
//                try {
//                    startActivity(whatsappIntent);
//                } catch (android.content.ActivityNotFoundException ex) {
//                    Toast.makeText(getApplicationContext(),"Whatsapp have not been installed.",Toast.LENGTH_SHORT).show();
//                }
                String str = "com.whatsapp";
                if (!txt_data.getText().toString().isEmpty()) {
                    PackageManager packageManager = getPackageManager();
                    try {
                        Intent intent = new Intent("android.intent.action.SEND");
                        intent.setType("text/plain");
                        packageManager.getPackageInfo(str, 0);
                        intent.setPackage(str);
                        intent.putExtra("android.intent.extra.TEXT", TextRepeaterActivity.this.txt_data.getText().toString());
                        startActivity(Intent.createChooser(intent, "Share with"));
                    } catch (PackageManager.NameNotFoundException unused) {
                        Toast.makeText(TextRepeaterActivity.this, "WhatsApp not Installed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(TextRepeaterActivity.this, "Please Enter Text", Toast.LENGTH_SHORT).show();
                }

            }
        });
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextRepeaterActivity.super.onBackPressed();

            }
        });

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE)).setText(txt_data.getText());
                Toast.makeText(TextRepeaterActivity.this, "Copied to clipboard", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
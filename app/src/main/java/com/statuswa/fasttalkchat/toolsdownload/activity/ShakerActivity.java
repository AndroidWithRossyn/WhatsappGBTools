package com.statuswa.fasttalkchat.toolsdownload.activity;

import static com.pesonal.adsdk.AppManage.ADMOB_N;
import static com.pesonal.adsdk.AppManage.FACEBOOK_N;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.statuswa.fasttalkchat.toolsdownload.R;
import com.statuswa.fasttalkchat.toolsdownload.shake_Detector.ShakeCallback;
import com.statuswa.fasttalkchat.toolsdownload.shake_Detector.ShakeDetector;
import com.statuswa.fasttalkchat.toolsdownload.shake_Detector.ShakeOptions;
import com.pesonal.adsdk.AppManage;

public class ShakerActivity extends AppCompatActivity {
    private static final String TAG = "ShakerActivity";
    public static boolean ShakeCheck = true;
    ImageView ShakeButton;
    ImageView ShakeDemo, back_btn;
    private ShakeDetector shakeDetector;

    //Method of shake listener
    private class btnShakeListner implements View.OnClickListener {
        public void onClick(View view) {
            if (ShakerActivity.ShakeCheck) {
                ShakerActivity.ShakeCheck = false;
                ShakerActivity.this.ShakeButton.setImageResource(R.drawable.offs);
                ShakerActivity.this.shakeDetector.stopShakeDetector(ShakerActivity.this.getBaseContext());
            } else {
                ShakerActivity.ShakeCheck = true;
                ShakerActivity.this.ShakeButton.setImageResource(R.drawable.ons);
                ShakerActivity.this.shakeDetector.startShakeService(ShakerActivity.this.getBaseContext());
            }
        }
    }


    //Method of when shake is detect
    private class shakeDetectListner implements ShakeCallback {
        public void onShake() {
            String packagename = "com.whatsapp";
            try {
                ShakerActivity.this.startActivity(ShakerActivity.this.getPackageManager().getLaunchIntentForPackage(packagename));
            } catch (Exception e) {
                Toast.makeText(ShakerActivity.this, "Whatsapp not found!! Redirecting to Play Store", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+packagename)));
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shaker);
        AppManage.getInstance(ShakerActivity.this).showNative((ViewGroup) findViewById(R.id.native_ads), ADMOB_N[0], FACEBOOK_N[0]);
        if (!(ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_NETWORK_STATE") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.SET_WALLPAPER") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.INTERNET") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.SYSTEM_ALERT_WINDOW") == 0) && Build.VERSION.SDK_INT >= 23) {
            requestPermissions(new String[]{"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.ACCESS_NETWORK_STATE", "android.permission.SET_WALLPAPER", "android.permission.INTERNET", "android.permission.SYSTEM_ALERT_WINDOW"}, 0);
        }
        this.ShakeButton = findViewById(R.id.btnShake);
        this.ShakeDemo = findViewById(R.id.shake);
        this.back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShakerActivity.super.onBackPressed();
            }
        });
        this.ShakeButton.setOnClickListener(new btnShakeListner());
        this.shakeDetector = new ShakeDetector(new ShakeOptions().background(Boolean.FALSE).interval(1000).shakeCount(2).sensibility(2.0f)).start(this, new shakeDetectListner());
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        AppManage.getInstance(ShakerActivity.this).showInterstitialAd(ShakerActivity.this, new AppManage.MyCallback() {
            public void callbackCall() {
                ShakerActivity.super.onBackPressed();
            }
        }, AppManage.ADMOB,AppManage.app_mainClickCntSwAd);
    }


    protected void onStop() {
        super.onStop();
    }

    protected void onDestroy() {
        this.shakeDetector.destroy(getBaseContext());
        super.onDestroy();
    }
}

package com.statuswa.fasttalkchat.toolsdownload.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.ads.nativead.NativeAd;
import com.statuswa.fasttalkchat.toolsdownload.R;

import java.util.ArrayList;

public class Constant {
    public static boolean SHOW_OPEN_ADS = true;
    public static ArrayList<String> f16277a = new ArrayList<>();
    public static String f16282f = "http://jalanmudu.website";
    public static String f16283g = "2776119817185_2776163150422";
    public static String f16284h = "2776158197185_2776178596981";
    public static String f16285i = "2776119817185_2776202816738";
    public static String f16286j = "2776158817185_2776206483376";
    public static String f16287k = "2776159817185_2776156483588";
    public static ArrayList<String> recentImageArraylist = new ArrayList<>();
    public static ArrayList<String> recentVideoArraylist = new ArrayList<>();
    public static ArrayList<String> savedImagesArraylist;
    public static ArrayList<String> savedVideoArraylist;
    //Native Ads
    public static NativeAd adAdmobNative;

    public static boolean isConnected(Context context) {

        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
    public static void FullScreencall(Activity activity) {
        /*if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = activity.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = activity.getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }*/
    }
    public static void privacyPolicy(Context context) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        String titltStre = "Privacy Policy";
        alert.setTitle(titltStre);
        alert.setCancelable(false);
        final WebView wv = new WebView(context);


        String urlStr = context.getString(R.string.privacy_policy);

        wv.loadUrl(urlStr);
        wv.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                webView.loadUrl(url);
                return false;
            }

            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest request) {
                Uri uri = request.getUrl();
                webView.loadUrl(uri.toString());
                return false;
            }
        });
        alert.setView(wv);
        alert.setPositiveButton("Agree", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

    public static void TermsOfService(Context context) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        String titltStre = "Terms Of Service";
        alert.setTitle(titltStre);
        alert.setCancelable(false);
        final WebView wv = new WebView(context);


        String urlStr = "file:///android_asset/ppandtu.html";

        wv.loadUrl(urlStr);
        wv.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                webView.loadUrl(url);
                return false;
            }

            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest request) {
                Uri uri = request.getUrl();
                webView.loadUrl(uri.toString());
                return false;
            }
        });
        alert.setView(wv);
        alert.setPositiveButton("Agree", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

}

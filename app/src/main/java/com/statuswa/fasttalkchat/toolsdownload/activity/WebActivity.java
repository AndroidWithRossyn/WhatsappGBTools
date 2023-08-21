package com.statuswa.fasttalkchat.toolsdownload.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.ConsoleMessage;
import android.webkit.DownloadListener;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.statuswa.fasttalkchat.toolsdownload.R;
import com.statuswa.fasttalkchat.toolsdownload.utils.BlobDownloader;

import java.util.Arrays;
import java.util.Locale;

public class WebActivity extends AppCompatActivity {
    public static final String DEBUG_TAG = "WAWEBTOGO";
    private static final String STORAGE_PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    ImageView back_btn,open_key_btn;
    WebView webview;
    private static final int STORAGE_PERMISSION_RESULTCODE = 204;
    private static final int VIDEO_PERMISSION_RESULTCODE   = 203;
    private static final int FILECHOOSER_RESULTCODE        = 200;
    private static final int CAMERA_PERMISSION_RESULTCODE  = 201;
    private static final int AUDIO_PERMISSION_RESULTCODE   = 202;
    private String mCurrentDownloadRequest = null;
    Activity activity;
    private static final String CHROME_FULL = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36";
    private static final String USER_AGENT = CHROME_FULL;

    private static final String CAMERA_PERMISSION = Manifest.permission.CAMERA;
    private static final String AUDIO_PERMISSION = Manifest.permission.RECORD_AUDIO;
    private static final String[] VIDEO_PERMISSION = {CAMERA_PERMISSION, AUDIO_PERMISSION};

    private static final String WHATSAPP_HOMEPAGE_URL = "https://www.whatsapp.com/";

    private static final String WHATSAPP_WEB_BASE_URL = "web.whatsapp.com";
    private static final String WORLD_ICON = "\uD83C\uDF10";
    private static final String WHATSAPP_WEB_URL = "https://" + WHATSAPP_WEB_BASE_URL
            + "/" + WORLD_ICON + "/"
            + Locale.getDefault().getLanguage();
    private PermissionRequest mCurrentPermissionRequest;
    private ValueCallback<Uri[]> mUploadMessage;
    boolean mKeyboardEnabled = false;
    private SharedPreferences mSharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        activity=this;

        back_btn=findViewById(R.id.back_btn);
        webview=findViewById(R.id.webview);
        open_key_btn=findViewById(R.id.open_key_btn);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mSharedPrefs = this.getSharedPreferences(this.getPackageName(), Context.MODE_PRIVATE);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebActivity.super.onBackPressed();
            }
        });
        open_key_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        webview.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {
                mCurrentDownloadRequest = url;
                if(checkPermission(STORAGE_PERMISSION)) {
                    webview.loadUrl(BlobDownloader.getBase64StringFromBlobUrl(url));
                    triggerDownload();
                } else {
                    requestPermission(STORAGE_PERMISSION);
                }
            }
        });
        webview.addJavascriptInterface(new BlobDownloader(getApplicationContext()), BlobDownloader.JsInstance);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setAllowContentAccess(true);
        webview.getSettings().setAllowFileAccess(true);
        webview.getSettings().setAllowFileAccessFromFileURLs(true);
        webview.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webview.getSettings().setMediaPlaybackRequiresUserGesture(false);

        webview.getSettings().setDomStorageEnabled(true);
        webview.getSettings().setDatabaseEnabled(true);
        webview.getSettings().setAppCacheEnabled(false);
        webview.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setUseWideViewPort(true);

        webview.getSettings().setSupportZoom(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setDisplayZoomControls(false);

        webview.getSettings().setSaveFormData(true);
        webview.getSettings().setLoadsImagesAutomatically(true);
        webview.getSettings().setBlockNetworkImage(false);
        webview.getSettings().setBlockNetworkLoads(false);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webview.getSettings().setNeedInitialFocus(false);
        webview.getSettings().setGeolocationEnabled(true);
        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webview.setScrollbarFadingEnabled(true);

        webview.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onCreateWindow(WebView view, boolean dialog, boolean userGesture, Message resultMsg) {
                Toast.makeText(getApplicationContext(), "OnCreateWindow", Toast.LENGTH_LONG).show();
                return true;
            }

            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                if (request.getResources()[0].equals(PermissionRequest.RESOURCE_VIDEO_CAPTURE)) {
                    if (ContextCompat.checkSelfPermission(activity, CAMERA_PERMISSION) == PackageManager.PERMISSION_DENIED
                            && ContextCompat.checkSelfPermission(activity, AUDIO_PERMISSION) == PackageManager.PERMISSION_DENIED) {
                        ActivityCompat.requestPermissions(activity, VIDEO_PERMISSION, VIDEO_PERMISSION_RESULTCODE);
                        mCurrentPermissionRequest = request;
                    } else if (ContextCompat.checkSelfPermission(activity, CAMERA_PERMISSION) == PackageManager.PERMISSION_DENIED) {
                        ActivityCompat.requestPermissions(activity, new String[]{CAMERA_PERMISSION}, CAMERA_PERMISSION_RESULTCODE);
                        mCurrentPermissionRequest = request;
                    } else if (ContextCompat.checkSelfPermission(activity, AUDIO_PERMISSION) == PackageManager.PERMISSION_DENIED) {
                        ActivityCompat.requestPermissions(activity, new String[]{AUDIO_PERMISSION}, AUDIO_PERMISSION_RESULTCODE);
                        mCurrentPermissionRequest = request;
                    } else {
                        request.grant(request.getResources());
                    }
                } else if (request.getResources()[0].equals(PermissionRequest.RESOURCE_AUDIO_CAPTURE)) {
                    if (ContextCompat.checkSelfPermission(activity, AUDIO_PERMISSION) == PackageManager.PERMISSION_GRANTED) {
                        request.grant(request.getResources());
                    } else {
                        ActivityCompat.requestPermissions(activity, new String[]{AUDIO_PERMISSION}, AUDIO_PERMISSION_RESULTCODE);
                        mCurrentPermissionRequest = request;
                    }
                } else {
                    try {
                        request.grant(request.getResources());
                    } catch (RuntimeException e) {
                        Log.d(DEBUG_TAG, "Granting permissions failed", e);
                    }
                }
            }

            public boolean onConsoleMessage(ConsoleMessage cm) {
                Log.d(DEBUG_TAG, "WebView console message: " + cm.message());
                return super.onConsoleMessage(cm);
            }

            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                mUploadMessage = filePathCallback;
                Intent chooserIntent = fileChooserParams.createIntent();
                startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE);
                return true;
            }
        });

        webview.setWebViewClient(new WebViewClient() {

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                setContentSize(view);
               /* if (mDarkMode) {
                    addDarkMode(view);
                }*/
            }

            @Override
            public void onPageCommitVisible(WebView view, String url) {
                super.onPageCommitVisible(view, url);
                setContentSize(view);
               /* if (mDarkMode) {
                    addDarkMode(view);
                }*/
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.scrollTo(0, 0);

                setContentSize(view);
                Log.e("Finish","Finished");
               /* if (mDarkMode) {
                    addDarkMode(view);
                }*/
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Uri url = request.getUrl();
                Log.d(DEBUG_TAG, url.toString());

                if (url.toString().equals(WHATSAPP_HOMEPAGE_URL)){
                    // when whatsapp somehow detects that waweb is running on a phone (e.g. trough
                    // the user agent, but apparently somehow else), it automatically redicts to the
                    // WHATSAPP_HOMEPAGE_URL. It's higly unlikely that a user wants to visit the
                    // WHATSAPP_HOMEPAGE_URL from within waweb.
                    // -> block the request and reload waweb
                    //  showToast("WA Web has to be reloaded to keep the app running");
                    loadWhatsapp();
                    return true;
                } else if (url.getHost().equals(WHATSAPP_WEB_BASE_URL)) {
                    // whatsapp web request -> fine
                    return super.shouldOverrideUrlLoading(view, request);
                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW, url);
                    startActivity(intent);
                    return true;
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                String msg = String.format("Error: %s - %s", error.getErrorCode(), error.getDescription());
                Log.d(DEBUG_TAG, msg);
            }

            public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
                Log.d(DEBUG_TAG, "Unhandled key event: " + event.toString());
            }
        });

        if (savedInstanceState == null) {
            loadWhatsapp();
        } else {
            Log.d(DEBUG_TAG, "savedInstanceState is present");
        }

        webview.getSettings().setUserAgentString(USER_AGENT);
    }
    private boolean checkPermission(String permission) {
        return PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(WebActivity.this, permission);
    }
    private void triggerDownload(){
        if(null != mCurrentDownloadRequest) {
            webview.loadUrl(BlobDownloader.getBase64StringFromBlobUrl(mCurrentDownloadRequest));
        }
        mCurrentDownloadRequest = null;
    }

    private void requestPermission(String permission) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(WebActivity.this, permission)) {
            ActivityCompat.requestPermissions(WebActivity.this, new String[]{permission}, STORAGE_PERMISSION_RESULTCODE);
        }
    }

    public void setContentSize(final WebView mWebView){
        if (getResources().getBoolean(R.bool.isTablet)) {
            // only change content sizes if device has a smaller screen than normally used for
            // whatsapp web
            // see https://stackoverflow.com/questions/9279111/determine-if-the-device-is-a-smartphone-or-tablet
            return;
        }

        mWebView.loadUrl("javascript:(function(){" +
                "  try { " +
                "	var css = '.two > div:nth-child(4){flex: 1 0 100vmin;}.two{overflow:visible}'," +
                "    	head = document.head || document.getElementsByTagName('head')[0]," +
                "    	style = document.createElement('style');" +
                "	head.appendChild(style);" +
                "	style.type = 'text/css';" +
                "	if (style.styleSheet){" +
                "  		style.styleSheet.cssText = css;" +
                "	} else {" +
                "  		style.appendChild(document.createTextNode(css));" +
                "	}" +
                "} catch(err) { }" +
                "})()");
    }
    private void loadWhatsapp() {
        webview.getSettings().setUserAgentString(USER_AGENT);
        webview.loadUrl(WHATSAPP_WEB_URL);
    }

    private void setKeyboardEnabled(final boolean enable) {
        mKeyboardEnabled = enable;
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (enable && webview.getDescendantFocusability() == ViewGroup.FOCUS_BLOCK_DESCENDANTS) {
            webview.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
            // showSnackbar("Unblocking keyboard...");
            //inputMethodManager.showSoftInputFromInputMethod(activity.getCurrentFocus().getWindowToken(), 0);
        } else if (!enable) {
            webview.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
            webview.getRootView().requestFocus();
            // showSnackbar("Blocking keyboard...");
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
        mSharedPrefs.edit().putBoolean("keyboardEnabled", enable).apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        webview.onResume();

        mKeyboardEnabled = mSharedPrefs.getBoolean("keyboardEnabled", true);
        setAppbarEnabled(mSharedPrefs.getBoolean("appbarEnabled", true));

        setKeyboardEnabled(mKeyboardEnabled);

        // showIntroInfo();
        // showVersionInfo();
    }

    private void setAppbarEnabled(boolean enable) {
        ActionBar actionBar= getSupportActionBar();
        if (actionBar != null) {
            if (enable) {
                actionBar.show();
            } else {
                actionBar.hide();
            }
            mSharedPrefs.edit().putBoolean("appbarEnabled", enable).apply();
        }
    }


    public void logout(){
        new AlertDialog.Builder(this)
                .setTitle("Do you want to log out?")
                .setMessage("When logging out, you will need to scan the QR code again with your phone to connect Whatsapp Web.")
                .setPositiveButton("Yes", (dialog, which) -> {
                    webview.loadUrl("javascript:localStorage.clear()");
                    WebStorage.getInstance().deleteAllData();
                    loadWhatsapp();
                    dialog.dismiss();
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case VIDEO_PERMISSION_RESULTCODE:
                if (permissions.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        mCurrentPermissionRequest.grant(mCurrentPermissionRequest.getResources());
                    } catch (RuntimeException e) {
                        Log.e(DEBUG_TAG, "Granting permissions failed", e);
                    }
                } else {
                    mCurrentPermissionRequest.deny();
                }
                break;
            case CAMERA_PERMISSION_RESULTCODE:
            case AUDIO_PERMISSION_RESULTCODE:
                //same same
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        mCurrentPermissionRequest.grant(mCurrentPermissionRequest.getResources());
                    } catch (RuntimeException e) {
                        Log.e(DEBUG_TAG, "Granting permissions failed", e);
                    }
                } else {

                    mCurrentPermissionRequest.deny();
                }
                break;
            case STORAGE_PERMISSION_RESULTCODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    triggerDownload();
                } else {

                    mCurrentDownloadRequest = null;
                }
                break;
            default:
                Log.d(DEBUG_TAG, "Got permission result with unknown request code " +
                        requestCode + " - " + Arrays.asList(permissions).toString());
        }
        mCurrentPermissionRequest = null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
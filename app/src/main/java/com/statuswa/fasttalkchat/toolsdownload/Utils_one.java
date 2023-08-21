package com.statuswa.fasttalkchat.toolsdownload;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.widget.Toast;

import java.io.File;
import java.util.Iterator;

public class Utils_one {
    public static Dialog customDialog;
    private static Context context;
    public static Uri wa_path = Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp/Media/.Statuses");


    public static Uri wa_business_path = Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp Business/Media/.Statuses");
    public static Uri wa_gb_path = Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath() + "/GBWhatsApp/Media/.Statuses");

    public static Uri wa_businnes_status_uri = Uri.parse("content://com.android.externalstorage.documents/tree/primary%3AAndroid%2Fmedia%2Fcom.whatsapp.w4b%2FWhatsApp Business%2FMedia%2F.Statuses");


    public static Uri wa_status_uri = Uri.parse("content://com.android.externalstorage.documents/tree/primary%3AAndroid%2Fmedia%2Fcom.whatsapp%2FWhatsApp%2FMedia%2F.Statuses");

    public static final String WA_BUSINESS_PACKAGE_NAME = "com.whatsapp.w4b";
    public static final String WA_PACKAGE_NAME = "com.whatsapp";

    public static File RootDirectoryWhatsappShow = new File(Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_PICTURES + "/WPTools/WhatsAppStatusSaverr");
    public static String RootDirectoryInsta = "/StatusSaver/Insta/";

    public static String PrivacyPolicyUrl = "http://codingdunia.com/ccprojects/statussaver/privacy_policy.html";
    public static String TikTokUrl = "http://codingdunia.com/ccprojects/tiktok/api/getContent/";
    public Utils_one(Context _mContext) {
        context = _mContext;
    }

    public static void setToast(Context _mContext, String str) {
        Toast toast = Toast.makeText(_mContext, str, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static boolean isWhatsAppInstall(Context context) {
        context.getPackageManager();
        try {
            boolean bl = Utils_one.isAppInstalled(context, WA_PACKAGE_NAME);
            return bl;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public static boolean isAppInstalled(Context context, String string2) {
        try {
            Intent intent = new Intent("android.intent.action.MAIN", null);
            Iterator iterator = context.getPackageManager().queryIntentActivities(intent, 0).iterator();
            while (iterator.hasNext()) {
                boolean bl = ((ResolveInfo)iterator.next()).activityInfo.packageName.equals((Object)string2);
                if (!bl) continue;
                return true;
            }
            return false;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public static boolean isBusinessWhatsAppInstall(Context context) {
        try {
            boolean bl = Utils_one.isAppInstalled(context, WA_BUSINESS_PACKAGE_NAME);
            return bl;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }


    public static void createFileFolder() {
        if (!RootDirectoryWhatsappShow.exists()) {
            RootDirectoryWhatsappShow.mkdirs();
        }
    }

    public static void shareImage(Context context, String filePath) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, context.getResources().getString(R.string.share_txt));
            String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), filePath, "", null);
            Uri screenshotUri = Uri.parse(path);
            intent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
            intent.setType("image/*");
            context.startActivity(Intent.createChooser(intent, context.getResources().getString(R.string.share_image_via)));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static void shareImageVideoOnWhatsapp(Context context, String filePath, boolean isVideo) {
        Uri imageUri = Uri.parse(filePath);
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setPackage("com.whatsapp");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "");
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        if (isVideo) {
            shareIntent.setType("video/*");
        }else {
            shareIntent.setType("image/*");
        }
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            context.startActivity(shareIntent);
        } catch (Exception e) {
            Utils_one.setToast(context,"Whtasapp not installed.");
        }
    }

    public static void shareVideo(Context context, String filePath) {
        Uri mainUri = Uri.parse(filePath);
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("video/mp4");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, mainUri);
        sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            context.startActivity(Intent.createChooser(sharingIntent, "Share Video using"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "No application found to open this file.", Toast.LENGTH_LONG).show();
        }
    }

    public static void OpenApp(Context context,String Package) {
        Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(Package);
        if (launchIntent != null) {
            context.startActivity(launchIntent);
        } else {
            setToast(context,"App Not Available.");
        }
    }

}

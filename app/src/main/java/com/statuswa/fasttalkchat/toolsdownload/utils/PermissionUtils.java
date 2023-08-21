package com.statuswa.fasttalkchat.toolsdownload.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.statuswa.fasttalkchat.toolsdownload.model.Contect_number;

import java.util.ArrayList;

public class PermissionUtils {
    public static boolean CheckingPermissionIsEnabledOrNot(Activity activity) {
        return ContextCompat.checkSelfPermission(activity.getApplicationContext(), "android.permission.WRITE_EXTERNAL_STORAGE") == 0 && ContextCompat.checkSelfPermission(activity.getApplicationContext(), "android.permission.READ_EXTERNAL_STORAGE") == 0 ;
    }

    public static void RequestMultiplePermission(Activity activity, int i) {
        ActivityCompat.requestPermissions(activity, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE","android.permission.READ_CONTACTS"}, i);
    }
    public static boolean checkReadPermission(Activity activity)
    {
        return ContextCompat.checkSelfPermission(activity.getApplicationContext(), "android.permission.READ_CONTACTS") == 0;
    }

    public static void RequestReadPermission(Activity activity, int i)
    {
        ActivityCompat.requestPermissions(activity,new String[]{"android.permission.READ_CONTACTS"},i);
    }

    public static ArrayList<Contect_number> ContectList(Context context) {
        String str;
        String str2;
        ArrayList<Contect_number> arrayList = new ArrayList<>();
        Cursor query = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, "account_type= ?", new String[]{"com.whatsapp"}, null);
        while (true) {
            str = "data1";
            str2 = "display_name";
            if (!query.moveToNext()) {
                break;
            }
            @SuppressLint("Range") String string = query.getString(query.getColumnIndex(str2));
            @SuppressLint("Range") String string2 = query.getString(query.getColumnIndex(str));
            Contect_number contect_number = new Contect_number();
            contect_number.setNumber(string);
            contect_number.setName(string2);
            arrayList.add(contect_number);
        }
        query.close();
        if (arrayList.size() == 0) {
            Cursor query2 = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, "account_type= ?", new String[]{"com.whatsapp.w4b"}, null);
            while (query2.moveToNext()) {
                @SuppressLint("Range") String string3 = query2.getString(query2.getColumnIndex(str2));
                @SuppressLint("Range") String string4 = query2.getString(query2.getColumnIndex(str));
                Contect_number contect_number = new Contect_number();
                contect_number.setNumber(string3);
                contect_number.setName(string4);
                arrayList.add(contect_number);
            }
            query2.close();
        }
        return arrayList;
    }

}
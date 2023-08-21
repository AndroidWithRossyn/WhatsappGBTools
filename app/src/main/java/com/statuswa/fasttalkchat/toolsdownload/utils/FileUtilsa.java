package com.statuswa.fasttalkchat.toolsdownload.utils;

import android.content.ContentUris;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import com.statuswa.fasttalkchat.toolsdownload.R;

import java.io.File;

public class FileUtilsa {
    public static String getRealPath(Context context, Uri uri) {
        if (Build.VERSION.SDK_INT < 11) {
            return getRealPathFromURI_BelowAPI11(context, uri);
        }
        if (Build.VERSION.SDK_INT < 19) {
            return getRealPathFromURI_API11to18(context, uri);
        }
        return getRealPathFromURI_API19(context, uri);
    }

    public static String getRealPathFromURI_API11to18(Context context, Uri uri) {
        Cursor loadInBackground = new CursorLoader(context, uri, new String[]{"_data"}, null, null, null).loadInBackground();
        if (loadInBackground == null) {
            return null;
        }
        int columnIndexOrThrow = loadInBackground.getColumnIndexOrThrow("_data");
        loadInBackground.moveToFirst();
        String string = loadInBackground.getString(columnIndexOrThrow);
        loadInBackground.close();
        return string;
    }

    public static String getRealPathFromURI_BelowAPI11(Context context, Uri uri) {
        Cursor query = context.getContentResolver().query(uri, new String[]{"_data"}, null, null, null);
        if (query == null) {
            return "";
        }
        int columnIndexOrThrow = query.getColumnIndexOrThrow("_data");
        query.moveToFirst();
        String string = query.getString(columnIndexOrThrow);
        query.close();
        return string;
    }

    public static String getRealPathFromURI_API19(Context context, Uri uri) {
        Uri uri2;
        if (!(Build.VERSION.SDK_INT >= 19) || !DocumentsContract.isDocumentUri(context, uri)) {
            if ("content".equalsIgnoreCase(uri.getScheme())) {
                if (!isGooglePhotosUri(uri) && !isDriveFile(uri) && !isGoogleOtherUri(uri)) {
                    return getDataColumn(context, uri, null, null);
                }
                return null;
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
        } else if (isExternalStorageDocument(uri)) {
            String documentId = DocumentsContract.getDocumentId(uri);
            String[] split = documentId.split(context.getString(R.string.gb_two_dot));
            if (!context.getString(R.string.gb_primary).equalsIgnoreCase(split[0])) {
                return context.getString(R.string.gb_storage) + context.getString(R.string.gb_pathSeparator) + documentId.replace(context.getString(R.string.gb_two_dot), context.getString(R.string.gb_pathSeparator));
            } else if (split.length > 1) {
                return Environment.getExternalStorageDirectory() + context.getString(R.string.gb_pathSeparator) + split[1];
            } else {
                return Environment.getExternalStorageDirectory() + context.getString(R.string.gb_pathSeparator);
            }
        } else if (isDownloadsDocument(uri)) {
            String filePath = getFilePath(context, uri);
            if (filePath != null) {
                return Environment.getExternalStorageDirectory().toString() + context.getString(R.string.gb_download) + filePath;
            }
            String documentId2 = DocumentsContract.getDocumentId(uri);
            if (documentId2.startsWith("raw:")) {
                documentId2 = documentId2.replaceFirst("raw:", "");
                if (new File(documentId2).exists()) {
                    return documentId2;
                }
            }
            return getDataColumn(context, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId2).longValue()), null, null);
        } else if (isMediaDocument(uri)) {
            String[] split2 = DocumentsContract.getDocumentId(uri).split(":");
            String str = split2[0];
            if ("image".equals(str)) {
                uri2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            } else if ("video".equals(str)) {
                uri2 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            } else if ("audio".equals(str)) {
                uri2 = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            } else {
                uri2 = MediaStore.Files.getContentUri("external");
            }
            return getDataColumn(context, uri2, "_id=?", new String[]{split2[1]});
        } else if (!"content".equalsIgnoreCase(uri.getScheme()) || isGooglePhotosUri(uri) || isDriveFile(uri) || isGoogleOtherUri(uri)) {
            return null;
        } else {
            return getDataColumn(context, uri, null, null);
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String str, String[] strArr) {
        Throwable th;
        Cursor cursor = null;
        try {
            Cursor query = context.getContentResolver().query(uri, new String[]{"_data"}, str, strArr, null);
            if (query != null) {
                try {
                    if (query.moveToFirst()) {
                        String string = query.getString(query.getColumnIndexOrThrow("_data"));
                        if (query != null) {
                            query.close();
                        }
                        return string;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    cursor = query;
                    if (cursor != null) {
                    }
                    throw th;
                }
            }
            if (query != null) {
                query.close();
            }
            return null;
        } catch (Throwable th3) {
            th = th3;
            if (cursor != null) {
                cursor.close();
            }
            try {
                throw th;
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        return str;
    }

    public static String getFilePath(Context context, Uri uri) {
        Throwable th;
        Cursor cursor = null;
        try {
            Cursor query = context.getContentResolver().query(uri, new String[]{"_display_name"}, null, null, null);
            if (query != null) {
                try {
                    if (query.moveToFirst()) {
                        String string = query.getString(query.getColumnIndexOrThrow("_display_name"));
                        if (query != null) {
                            query.close();
                        }
                        return string;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    cursor = query;
                    if (cursor != null) {
                    }
                    throw th;
                }
            }
            if (query != null) {
                query.close();
            }
            return null;
        } catch (Throwable th3) {
            th = th3;
            if (cursor != null) {
                cursor.close();
            }
            try {
                throw th;
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static boolean isGoogleOtherUri(Uri uri) {
        return uri.getAuthority().contains("com.google.android.apps.");
    }

    public static boolean isDriveFile(Uri uri) {
        if ("com.google.android.apps.docs.storage".equals(uri.getAuthority())) {
            return true;
        }
        return "com.google.android.apps.docs.storage.legacy".equals(uri.getAuthority());
    }
}

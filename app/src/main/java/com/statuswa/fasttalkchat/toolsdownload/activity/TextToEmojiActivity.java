package com.statuswa.fasttalkchat.toolsdownload.activity;

import static com.pesonal.adsdk.AppManage.ADMOB_N;
import static com.pesonal.adsdk.AppManage.FACEBOOK_N;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.statuswa.fasttalkchat.toolsdownload.R;
import com.pesonal.adsdk.AppManage;

import java.io.IOException;
import java.io.InputStream;

public class TextToEmojiActivity extends AppCompatActivity {
    Button convertButton;
    EditText convertedText;
    ImageView copy, share, delete, back_btn;
    EditText emojeeText;
    EditText inputText;

    //button convert click event listener
    private class btnConvertListner implements View.OnClickListener {
        public void onClick(View view) {
            AppManage.getInstance(TextToEmojiActivity.this).showInterstitialAd(TextToEmojiActivity.this, new AppManage.MyCallback() {
                public void callbackCall() {
                    if (TextToEmojiActivity.this.inputText.getText().toString().isEmpty()) {
                        Toast.makeText(TextToEmojiActivity.this.getApplicationContext(), "Enter text", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    char[] charArray = TextToEmojiActivity.this.inputText.getText().toString().toCharArray();
                    TextToEmojiActivity.this.convertedText.setText(".\n");
                    for (char character : charArray) {
                        byte[] localObject3;
                        InputStream localObject2;
                        if (character == '?') {
                            try {
                                InputStream localObject1 = TextToEmojiActivity.this.getBaseContext().getAssets().open("ques.txt");
                                localObject3 = new byte[localObject1.available()];
                                localObject1.read(localObject3);
                                localObject1.close();
                                TextToEmojiActivity.this.convertedText.append(new String(localObject3).replaceAll("[*]", TextToEmojiActivity.this.emojeeText.getText().toString()) + "\n\n");
                            } catch (IOException ioe) {
                                ioe.printStackTrace();
                            }
                        } else if (character == ((char) (character & 95)) || Character.isDigit(character)) {
                            try {
                                localObject2 = TextToEmojiActivity.this.getBaseContext().getAssets().open(character + ".txt");
                                localObject3 = new byte[localObject2.available()];
                                localObject2.read(localObject3);
                                localObject2.close();
                                TextToEmojiActivity.this.convertedText.append(new String(localObject3).replaceAll("[*]", TextToEmojiActivity.this.emojeeText.getText().toString()) + "\n\n");
                            } catch (IOException ioe2) {
                                ioe2.printStackTrace();
                            }
                        } else {
                            try {
                                localObject2 = TextToEmojiActivity.this.getBaseContext().getAssets().open("low" + character + ".txt");
                                localObject3 = new byte[localObject2.available()];
                                localObject2.read(localObject3);
                                localObject2.close();
                                TextToEmojiActivity.this.convertedText.append(new String(localObject3).replaceAll("[*]", TextToEmojiActivity.this.emojeeText.getText().toString()) + "\n\n");
                            } catch (IOException ioe22) {
                                ioe22.printStackTrace();
                            }
                        }
                    }
                }
            },"",AppManage.app_mainClickCntSwAd);
        }

    }

    //Button - clear Text click listener method
    private class btnClearTextListner implements View.OnClickListener {
        public void onClick(View view) {
            TextToEmojiActivity.this.convertedText.setText("");
        }
    }

    //Button  - Convert Text click listener method
    private class btnConvertedTextListner implements View.OnClickListener {
        @SuppressLint({"WrongConstant"})
        public void onClick(View view) {
            if (!TextToEmojiActivity.this.convertedText.getText().toString().isEmpty()) {
                ((ClipboardManager) TextToEmojiActivity.this.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText(TextToEmojiActivity.this.inputText.getText().toString(), TextToEmojiActivity.this.convertedText.getText().toString()));
                Toast.makeText(TextToEmojiActivity.this.getApplicationContext(), "Copied to Clipboard", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Copy button click listener method
    private class btnCopyButtonListner implements View.OnClickListener {
        @SuppressLint({"WrongConstant"})
        public void onClick(View view) {
            if (TextToEmojiActivity.this.convertedText.getText().toString().isEmpty()) {
                Toast.makeText(TextToEmojiActivity.this.getApplicationContext(), "Convert text before copy", Toast.LENGTH_SHORT).show();
                return;
            }
            ((ClipboardManager) TextToEmojiActivity.this.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText(TextToEmojiActivity.this.inputText.getText().toString(), TextToEmojiActivity.this.convertedText.getText().toString()));
            Toast.makeText(TextToEmojiActivity.this.getApplicationContext(), "Copied to Clipboard", Toast.LENGTH_SHORT).show();
        }
    }

    //Share Button click listener method
    private class btnShareListner implements View.OnClickListener {
        public void onClick(View view) {
            if (TextToEmojiActivity.this.convertedText.getText().toString().isEmpty()) {
                Toast.makeText(TextToEmojiActivity.this.getApplicationContext(), "Convert text to share", Toast.LENGTH_LONG).show();
                return;
            }
            Intent shareIntent = new Intent();
            shareIntent.setAction("android.intent.action.SEND");
            shareIntent.setPackage("com.whatsapp");
            shareIntent.putExtra("android.intent.extra.TEXT", TextToEmojiActivity.this.convertedText.getText().toString());
            shareIntent.setType("text/plain");
            TextToEmojiActivity.this.startActivity(Intent.createChooser(shareIntent, "Select an app to share"));
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_emoji);
        AppManage.getInstance(TextToEmojiActivity.this).loadInterstitialAd(this);
        AppManage.getInstance(TextToEmojiActivity.this).showNative((ViewGroup) findViewById(R.id.native_ads), ADMOB_N[0], FACEBOOK_N[0]);
//        getSupportActionBar().setTitle("Text To Emoji");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextToEmojiActivity.super.onBackPressed();
            }
        });
        this.inputText = findViewById(R.id.inputText);
        this.emojeeText = findViewById(R.id.emojeeTxt);
        this.convertedText = findViewById(R.id.convertedEmojeeTxt);
        this.convertButton = findViewById(R.id.convertEmojeeBtn);
        this.copy = findViewById(R.id.copy);
        this.share = findViewById(R.id.share);
        this.delete = findViewById(R.id.delete);
        this.convertButton.setOnClickListener(new btnConvertListner());
        this.delete.setOnClickListener(new btnClearTextListner());
        this.convertedText.setOnClickListener(new btnConvertedTextListner());
        this.copy.setOnClickListener(new btnCopyButtonListner());
        this.share.setOnClickListener(new btnShareListner());
    }


    //Menu initialisation
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
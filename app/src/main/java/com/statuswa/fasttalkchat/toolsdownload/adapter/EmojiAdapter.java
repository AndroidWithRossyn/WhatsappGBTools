package com.statuswa.fasttalkchat.toolsdownload.adapter;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.statuswa.fasttalkchat.toolsdownload.R;
import com.statuswa.fasttalkchat.toolsdownload.model.Emoji_Model;

import java.util.ArrayList;

public class EmojiAdapter extends RecyclerView.Adapter<EmojiAdapter.EmojiViewHolder> {
    private Context context;
    private ArrayList<Emoji_Model> list;

    public EmojiAdapter(Context context, ArrayList<Emoji_Model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public EmojiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.text_style_list, parent, false);
        return new EmojiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmojiViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.text_style.setText(list.get(position).getText());
        holder.whatsapp_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent whatsapp = new Intent(Intent.ACTION_SEND);
                    whatsapp.setType("text/plane");
                    whatsapp.setPackage("com.whatsapp");
                    whatsapp.putExtra(Intent.EXTRA_TEXT, list.get(position).getText());
                    context.startActivity(whatsapp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        holder.share_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.setType("text/plane");
                    share.putExtra(Intent.EXTRA_TEXT, list.get(position).getText());
                    context.startActivity(share);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        holder.copy_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager=(ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData data=(ClipData) ClipData.newPlainText("text",list.get(position).getText());
                clipboardManager.setPrimaryClip(data);
                Toast.makeText(context, "Text Copied", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class EmojiViewHolder extends RecyclerView.ViewHolder {
        private ImageView whatsapp_b, copy_b, share_b;
        private TextView text_style;

        public EmojiViewHolder(@NonNull View itemView) {
            super(itemView);
            whatsapp_b = itemView.findViewById(R.id.whatsapp_b);
            copy_b = itemView.findViewById(R.id.copy_b);
            share_b = itemView.findViewById(R.id.share_b);
            text_style = itemView.findViewById(R.id.text_style);
        }
    }
}

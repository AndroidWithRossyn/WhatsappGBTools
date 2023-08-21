package com.statuswa.fasttalkchat.toolsdownload.Fragment;

import static com.pesonal.adsdk.AppManage.ADMOB_N;
import static com.pesonal.adsdk.AppManage.FACEBOOK_N;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.statuswa.fasttalkchat.toolsdownload.R;
import com.statuswa.fasttalkchat.toolsdownload.adapter.EmojiAdapter;
import com.statuswa.fasttalkchat.toolsdownload.model.Emoji_Model;
import com.pesonal.adsdk.AppManage;

import java.util.ArrayList;


public class MusicFragment extends Fragment {
    private RecyclerView recycle_view;
    private ArrayList<Emoji_Model> list;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_music, container, false);
        AppManage.getInstance(getActivity()).showNative((ViewGroup) view.findViewById(R.id.native_ads), ADMOB_N[0], FACEBOOK_N[0]);
        list=new ArrayList<>();
        recycle_view=view.findViewById(R.id.recycle_view);
        initList();
        recycle_view.setHasFixedSize(true);
        recycle_view.setLayoutManager(new LinearLayoutManager(getContext()));
        recycle_view.setAdapter(new EmojiAdapter(getContext(),list));
        return view;
    }

    private void initList() {
        list.add(new Emoji_Model("|̲̅̅●̲̅̅|̲̅̅=̲̅̅|̲̅̅●̲̅̅|"));
        list.add(new Emoji_Model("d[-_-]b"));
        list.add(new Emoji_Model("{ o }==(::)"));
        list.add(new Emoji_Model("(´△｀)♪"));
        list.add(new Emoji_Model("♪┏(°.°)┛"));
        list.add(new Emoji_Model("♫♪.ılılıll|̲̅̅●̲̅̅|̲̅̅=̲̅̅|̲̅̅●̲̅̅|llılılı.♫♪"));
        list.add(new Emoji_Model("{ o }===(:::)"));
        list.add(new Emoji_Model("இwஇ"));
        list.add(new Emoji_Model("ヾ(´ρ｀)〃"));
        list.add(new Emoji_Model("ιllιlı."));
        list.add(new Emoji_Model("[ↂ■■ↂ]"));
        list.add(new Emoji_Model("c====(=#O| )"));
        list.add(new Emoji_Model("（＾Ｏ＾☆♪"));
        list.add(new Emoji_Model("(ﾉ´▽｀)ﾉ♪"));
        list.add(new Emoji_Model("☊"));
        list.add(new Emoji_Model("|[●▪▪●]|"));
        list.add(new Emoji_Model("(´▽｀)ノ♪"));
    }
}
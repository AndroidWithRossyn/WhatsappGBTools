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

public class SupriseFragment extends Fragment {
    private RecyclerView recycle_view;
    private ArrayList<Emoji_Model> list;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_suprise, container, false);
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
        list.add(new Emoji_Model("ᕕ(ಥʖ̯ಥ)ᕗ"));
        list.add(new Emoji_Model("｢(◔ω◔「)三"));
        list.add(new Emoji_Model("｢(⑅◔ω◔「)三"));
        list.add(new Emoji_Model("╰། ◉ ◯ ◉ །╯"));
        list.add(new Emoji_Model("(⊙ˍ⊙)"));
        list.add(new Emoji_Model("(°"));
        list.add(new Emoji_Model("(꒪Д꒪)ノ"));
        list.add(new Emoji_Model("ԅ⁞ ◑ ₒ ◑ ⁞ᓄ"));
        list.add(new Emoji_Model("⊙０⊙"));
        list.add(new Emoji_Model("ο°)"));
        list.add(new Emoji_Model("（￣□￣；）"));
        list.add(new Emoji_Model("(ʘᗩʘ’)"));
        list.add(new Emoji_Model("(⊙＿⊙)"));
        list.add(new Emoji_Model("o_O"));
        list.add(new Emoji_Model("w(°ｏ°)w"));
        list.add(new Emoji_Model("༼⁰o⁰；༽"));
        list.add(new Emoji_Model("(@口@)"));
        list.add(new Emoji_Model("(@[]@!!)"));
        list.add(new Emoji_Model("l(・o・)」"));
    }
}
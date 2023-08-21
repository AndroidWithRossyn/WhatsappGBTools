package com.statuswa.fasttalkchat.toolsdownload.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.statuswa.fasttalkchat.toolsdownload.Fragment.AngryFragment;
import com.statuswa.fasttalkchat.toolsdownload.Fragment.AnimalFragment;
import com.statuswa.fasttalkchat.toolsdownload.Fragment.ConfuseFragment;
import com.statuswa.fasttalkchat.toolsdownload.Fragment.DanceFragment;
import com.statuswa.fasttalkchat.toolsdownload.Fragment.EvilFragment;
import com.statuswa.fasttalkchat.toolsdownload.Fragment.FailureFragment;
import com.statuswa.fasttalkchat.toolsdownload.Fragment.HappyFragment;
import com.statuswa.fasttalkchat.toolsdownload.Fragment.KissFragment;
import com.statuswa.fasttalkchat.toolsdownload.Fragment.LoveFragment;
import com.statuswa.fasttalkchat.toolsdownload.Fragment.MusicFragment;
import com.statuswa.fasttalkchat.toolsdownload.Fragment.SadFragment;
import com.statuswa.fasttalkchat.toolsdownload.Fragment.ScareFragment;
import com.statuswa.fasttalkchat.toolsdownload.Fragment.ShyFragment;
import com.statuswa.fasttalkchat.toolsdownload.Fragment.SmugFragment;
import com.statuswa.fasttalkchat.toolsdownload.Fragment.SorryFragment;
import com.statuswa.fasttalkchat.toolsdownload.Fragment.SupriseFragment;
import com.statuswa.fasttalkchat.toolsdownload.Fragment.TiredFragment;
import com.statuswa.fasttalkchat.toolsdownload.Fragment.WinkFragment;

public class MyFragmentAdapter extends FragmentStateAdapter {


    public MyFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new HappyFragment();
        } else if (position == 1) {
            return new SadFragment();
        } else if (position == 2) {
            return new SorryFragment();
        }else if (position == 3) {
            return new LoveFragment();
        }else if (position == 4) {
            return new SupriseFragment();
        }else if (position == 5) {
            return new ScareFragment();
        }else if (position == 6) {
            return new MusicFragment();
        }else if (position == 7) {
            return new DanceFragment();
        }else if (position == 8) {
            return new SmugFragment();
        }else if (position == 9) {
            return new FailureFragment();
        }else if (position == 10) {
            return new AnimalFragment();
        }else if (position == 11) {
            return new EvilFragment();
        }else if (position == 12) {
            return new AngryFragment();
        }else if (position == 13) {
            return new ConfuseFragment();
        }else if (position == 14) {
            return new KissFragment();
        }else if (position == 15) {
            return new ShyFragment();
        }else if (position == 16) {
            return new TiredFragment();
        } else {
            return new WinkFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 17;
    }
}

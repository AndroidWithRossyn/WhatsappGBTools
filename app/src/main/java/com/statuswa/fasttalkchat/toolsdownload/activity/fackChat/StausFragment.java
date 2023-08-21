package com.statuswa.fasttalkchat.toolsdownload.activity.fackChat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.statuswa.fasttalkchat.toolsdownload.R;

public class StausFragment extends Fragment {
    View view;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_status, container, false);
        return this.view;
    }
}

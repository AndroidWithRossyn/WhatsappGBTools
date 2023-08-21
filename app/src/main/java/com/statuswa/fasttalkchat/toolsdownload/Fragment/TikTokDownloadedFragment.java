package com.statuswa.fasttalkchat.toolsdownload.Fragment;

import static androidx.databinding.DataBindingUtil.inflate;

import static com.statuswa.fasttalkchat.toolsdownload.utils.Utils.RootDirectoryTikTokShow;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.statuswa.fasttalkchat.toolsdownload.R;
import com.statuswa.fasttalkchat.toolsdownload.activity.DownloadActivity;
import com.statuswa.fasttalkchat.toolsdownload.activity.FullViewActivity;
import com.statuswa.fasttalkchat.toolsdownload.adapter.FileListAdapter;
import com.statuswa.fasttalkchat.toolsdownload.databinding.FragmentHistoryBinding;
import com.statuswa.fasttalkchat.toolsdownload.interfaces.FileListClickInterface;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;

public class TikTokDownloadedFragment extends Fragment implements FileListClickInterface {
    private FragmentHistoryBinding binding;
    private FileListAdapter fileListAdapter;
    private ArrayList<File> fileArrayList;
    private DownloadActivity activity;
    public static TikTokDownloadedFragment newInstance(String param1) {
        TikTokDownloadedFragment fragment = new TikTokDownloadedFragment();
        Bundle args = new Bundle();
        args.putString("m", param1);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onAttach(@NotNull Context _context) {
        super.onAttach(_context);
        activity = (DownloadActivity) _context;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String mParam1 = getArguments().getString("m");
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        activity = (DownloadActivity) getActivity();
        getAllFiles();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = inflate(inflater, R.layout.fragment_history, container, false);
        initViews();
        return binding.getRoot();
    }
    private void initViews(){
        binding.swiperefresh.setOnRefreshListener(() -> {
            getAllFiles();
            binding.swiperefresh.setRefreshing(false);
        });
    }

    private void getAllFiles(){
        fileArrayList = new ArrayList<>();
        File[] files = RootDirectoryTikTokShow.listFiles();
        if (files!=null) {
            for (File file : files) {
                fileArrayList.add(file);
            }

            fileListAdapter = new FileListAdapter(activity, fileArrayList, TikTokDownloadedFragment.this);
            binding.rvFileList.setAdapter(fileListAdapter);
        }

    }

    @Override
    public void getPosition(int position, File file) {
        Intent inNext = new Intent(activity, FullViewActivity.class);
        inNext.putExtra("ImageDataFile", fileArrayList);
        inNext.putExtra("Position", position);
        activity.startActivity(inNext);
    }
}

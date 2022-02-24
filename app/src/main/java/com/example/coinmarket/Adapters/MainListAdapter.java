package com.example.coinmarket.Adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.coinmarket.ui.MainListFragment;

public class MainListAdapter extends FragmentStateAdapter {

    public MainListAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        Fragment fragment = new MainListFragment();

        Bundle args = new Bundle();
        args.putInt("pos",position);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}

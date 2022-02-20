package com.example.coinmarket.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.coinmarket.R;
import com.example.coinmarket.databinding.ViewPagerBinding;

import java.util.ArrayList;

public class viewPagerAdapter extends RecyclerView.Adapter<viewPagerAdapter.SliderImageViewHolder> {

    LayoutInflater layoutInflater;

    ArrayList<Integer> arrayList;

    public viewPagerAdapter(ArrayList<Integer> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public SliderImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (layoutInflater == null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ViewPagerBinding viewPagerBinding = DataBindingUtil.inflate(layoutInflater, R.layout.view_pager,parent,false);

        return new SliderImageViewHolder(viewPagerBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull SliderImageViewHolder holder, int position) {
        holder.bind(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class SliderImageViewHolder extends RecyclerView.ViewHolder{
        ViewPagerBinding viewPagerBinding;

        public SliderImageViewHolder(@NonNull ViewPagerBinding viewPagerBinding) {
            super(viewPagerBinding.getRoot());
            this.viewPagerBinding = viewPagerBinding;
        }

        public void bind(int photo) {

            Glide.with(viewPagerBinding.getRoot().getContext())
                    .load(photo)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(viewPagerBinding.imageSlide);
            viewPagerBinding.executePendingBindings();
        }
    }
}
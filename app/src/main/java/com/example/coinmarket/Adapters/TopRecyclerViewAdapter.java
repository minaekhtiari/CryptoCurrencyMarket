package com.example.coinmarket.Adapters;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.coinmarket.PojoModels.DataItem;
import com.example.coinmarket.R;
import com.example.coinmarket.databinding.TopRecyclerviewItemBinding;


import java.util.ArrayList;

public class TopRecyclerViewAdapter extends RecyclerView.Adapter<TopRecyclerViewAdapter.TopCoinRecyclerViewHolder>{

    LayoutInflater layoutInflater;
    ArrayList<DataItem> dataItems;

    public TopRecyclerViewAdapter(ArrayList<DataItem> dataItems) {
        this.dataItems=dataItems;
    }

    @NonNull
    @Override
    public TopCoinRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        TopRecyclerviewItemBinding topRecyclerviewItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.top_recyclerview_item,parent,false);
        return new TopCoinRecyclerViewHolder(topRecyclerviewItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TopCoinRecyclerViewHolder holder, int position) {
        holder.bind(dataItems.get(position));
    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public void updateData(ArrayList<DataItem> dataItems) {
        dataItems.clear();
        dataItems.addAll(dataItems);
        notifyDataSetChanged();
    }

    public class TopCoinRecyclerViewHolder extends RecyclerView.ViewHolder {

        TopRecyclerviewItemBinding topRecyclerviewItemBinding;

        public TopCoinRecyclerViewHolder(TopRecyclerviewItemBinding topRecyclerviewItemBinding) {
            super(topRecyclerviewItemBinding.getRoot());
            this.topRecyclerviewItemBinding = topRecyclerviewItemBinding;
        }

        @SuppressLint("DefaultLocale")
        public void bind(DataItem dataItem) {
            loadCoinLogo(dataItem);
            topRecyclerviewItemBinding.coinSymbol.setText(dataItem.getSymbol());
            topRecyclerviewItemBinding.coinName.setText(dataItem.getName());
           topRecyclerviewItemBinding.coinPrice.setText(String.format("%.2f",dataItem.getListQuote().get(0).getPrice()));
        }

        private void loadCoinLogo(DataItem dataItem) {
            Glide.with(topRecyclerviewItemBinding.getRoot().getContext())
                    .load("https://s2.coinmarketcap.com/static/img/coins/32x32/" + dataItem.getId() + ".png")
                    .into(topRecyclerviewItemBinding.coinLogo);
        }
    }
}

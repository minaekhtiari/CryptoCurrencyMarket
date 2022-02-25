package com.example.coinmarket.Adapters;

import android.annotation.SuppressLint;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.coinmarket.PojoModels.DataItem;
import com.example.coinmarket.R;
import com.example.coinmarket.databinding.MainListRecyclerItemBinding;


import java.util.ArrayList;

public class MainListRecyclerViewAdapter extends RecyclerView.Adapter<MainListRecyclerViewAdapter
        .MainListRecyclerViewHolder> {
//    public interface OnItemClickListener {
//        void onItemClick(DataItem item, int position);
//
//
//    }
private onRecyclerViewItemClickListener mItemClickListener;

    public void setOnItemClickListener(onRecyclerViewItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface onRecyclerViewItemClickListener {
        void onItemClickListener(View view, int position);
    }
    private ArrayList<DataItem> dataItems;
    LayoutInflater layoutInflater;
    //private final OnItemClickListener listener;

    public MainListRecyclerViewAdapter(ArrayList<DataItem> dataItems) {
        this.dataItems = dataItems;

    }

    @NonNull
    @Override
    public MainListRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        MainListRecyclerItemBinding mainListRecyclerItemBinding = DataBindingUtil.inflate(layoutInflater,
                R.layout.main_list_recycler_item, parent, false);
        return new MainListRecyclerViewHolder(mainListRecyclerItemBinding);
    }

    @Override
    public void onBindViewHolder(MainListRecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.bind(dataItems.get(position));


    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }


    public void updateData(ArrayList<DataItem> newdata) {
        dataItems = newdata;
        notifyDataSetChanged();

    }

    class MainListRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        MainListRecyclerItemBinding mainListRecyclerItemBinding;


        public MainListRecyclerViewHolder(MainListRecyclerItemBinding mainListRecyclerItemBinding)  {
            super(mainListRecyclerItemBinding.getRoot());
            this.mainListRecyclerItemBinding = mainListRecyclerItemBinding;
        }

        public void bind(DataItem dataItem) {

            loadCoinLogo(dataItem);
            loadChart(dataItem);

            mainListRecyclerItemBinding.coinName.setText(dataItem.getName());
            mainListRecyclerItemBinding.coinSymbol.setText(dataItem.getSymbol());
            mainListRecyclerItemBinding.recyclerItemLayout.setOnClickListener(this::onClick);



        }

        private void loadCoinLogo(DataItem dataItem) {
            Glide.with(mainListRecyclerItemBinding.getRoot().getContext())
                    .load("https://s2.coinmarketcap.com/static/img/coins/32x32/" + dataItem.getId() + ".png")

                    .into(mainListRecyclerItemBinding.mainListLogo);
        }

        private void loadChart(DataItem dataItem) {
            Glide.with(mainListRecyclerItemBinding.getRoot().getContext())
                    .load("https://s3.coinmarketcap.com/generated/sparklines/web/7d/usd/" + dataItem.getId() + ".png")
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(mainListRecyclerItemBinding.imageView);
        }


        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClickListener(v, getAdapterPosition());

            }
        }
    }
}

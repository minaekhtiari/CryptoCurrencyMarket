package com.example.coinmarket.ui;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coinmarket.PojoModels.DataItem;
import com.example.coinmarket.R;
import com.example.coinmarket.databinding.FragmentBookmarkBinding;
import com.example.coinmarket.databinding.FragmentDetailBinding;


public class DetailFragment extends Fragment {
FragmentDetailBinding fragmentDetailBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       fragmentDetailBinding= DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false);




        DataItem dataItem=  getArguments().getParcelable("dataItemModel");
   fragmentDetailBinding.textView.setText("Selected Item Name: "+ dataItem.getName());

        return fragmentDetailBinding.getRoot();
    }
}
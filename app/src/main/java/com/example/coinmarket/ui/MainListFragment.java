package com.example.coinmarket.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.coinmarket.Adapters.MainListAdapter;
import com.example.coinmarket.Adapters.MainListRecyclerViewAdapter;
import com.example.coinmarket.PojoModels.AllMarketModel;
import com.example.coinmarket.PojoModels.DataItem;
import com.example.coinmarket.R;
import com.example.coinmarket.ViewModel.AppViewModel;
import com.example.coinmarket.databinding.FragmentMainListBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainListFragment extends Fragment {
    FragmentMainListBinding fragmentMainListBinding;
    MainListRecyclerViewAdapter mainListRecyclerViewAdapter;
    AppViewModel appViewModel;
    List<DataItem> data;
    CompositeDisposable compositeDisposable;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fragmentMainListBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_main_list, container, false);

        compositeDisposable = new CompositeDisposable();

        Bundle args = getArguments();
        int pos = args.getInt("pos");

        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);

        setRecyclerView(pos);
        return fragmentMainListBinding.getRoot();
    }




    public void setRecyclerView(int pos) {

        Disposable disposable = appViewModel.getAllMarketData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(roomMarketEntity -> {
                    AllMarketModel allMarketModel = roomMarketEntity.getMarketModel();
                    data = allMarketModel.getRootData().getCryptoCurrencyList();



                    try {
                        ArrayList<DataItem> dataItems = new ArrayList<>();
                        //if page was MainList
                        if (pos == 0) {
                            for (int i = 0;i < 50;i++){
                                dataItems.add(data.get(i));
                            }
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                            fragmentMainListBinding.mainList.setLayoutManager(linearLayoutManager);

                            if (fragmentMainListBinding.mainList.getAdapter() == null) {
                                mainListRecyclerViewAdapter = new MainListRecyclerViewAdapter(dataItems);
                                fragmentMainListBinding.mainList.setAdapter(mainListRecyclerViewAdapter);
                            } else {
                                mainListRecyclerViewAdapter = (MainListRecyclerViewAdapter) fragmentMainListBinding.mainList.getAdapter();
                                mainListRecyclerViewAdapter.updateData(dataItems);
                            }

                            //if page was TextTab
                        } else if (pos == 1) {
                            fragmentMainListBinding.textView.setVisibility(View.VISIBLE);

                        }



                    } catch (Exception e) {
                        Log.e("exception", "setupRecyclerView: " + e.getMessage());
                    }

                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
